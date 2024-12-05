package accounts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import league.Team;
import leagueDB.JFGPdb;
import leagueMembers.Manager;
import leagueMembers.Player;

public class AdminAccount extends Account implements IManagerRole, IRefereeRole {
	
	public AdminAccount(int id, String emailAddress, String password) {
		super(id, emailAddress, password, true);
	}

	public void removeManager(Connection connection, Manager man) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			
			seasonStatement.setInt(1, man.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = (connection).prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			
			refAccDel.setInt(1, man.getUserId());
			refAccDel.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void createManager(Connection connection, String fname, String lname, int id) {
		try {
			PreparedStatement manStatement = (connection).prepareStatement(
			        "INSERT INTO managers(fName, lName, userId) VALUES (?, ?, ?);");
			
			manStatement.setString(1, fname);
			manStatement.setString(2, lname);
			manStatement.setInt(3, id);
			manStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void createManagerAccount(Connection connection, String fname, String lname) {
		try {
			PreparedStatement refAccStatement = connection.prepareStatement(
			        "INSERT INTO userAccounts(userType, emailAddress, password, leagueId) VALUES ('manager', ?, ?, 1);");
			
			refAccStatement.setString(1, lname + "@jfgp.org");
			refAccStatement.setString(2, lname + fname);
			refAccStatement.executeUpdate();
			
			PreparedStatement lastId = connection.prepareStatement(
					"SELECT userId FROM userAccounts ORDER BY ROWID DESC limit 1;");
			
			ResultSet id = lastId.executeQuery();
			
			int manId = id.getInt("userId");
	
			createManager(connection, fname, lname, manId);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void createTeam(Connection connection, String teamName) {
		try {
			
			int newStatsId = createStats(connection);
			
			PreparedStatement teamStatement = (connection).prepareStatement(
			        "INSERT INTO teams(teamName, statsId) VALUES (?, ?);");
			
			teamStatement.setString(1, teamName);
			teamStatement.setInt(2, newStatsId);
			teamStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void removeTeam(Connection connection, Team team) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "DELETE FROM teams WHERE teamId = ?;");
			
			seasonStatement.setInt(1, team.getTeamId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static int createStats(Connection connection) {
		try {
			PreparedStatement statsStatement = (connection).prepareStatement(
			        "INSERT INTO statsForPlayerOrTeam(assists, goals, fouls, yellowCards, redCards, wins, draws, losses) "
			        + "VALUES (0, 0, 0, 0, 0, 0, 0, 0);");
			statsStatement.executeUpdate();
			
			PreparedStatement lastId = (connection.prepareStatement(
					"SELECT statsId FROM statsForPlayerOrTeam ORDER BY ROWID DESC limit 1;"));
			
			ResultSet id = lastId.executeQuery();
			int statsId = id.getInt("statsId");

			return statsId;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return 0;
	}
	
	public void createPlayer(String fname, String lname, String positionType) {
		JFGPdb connection = new JFGPdb();
		try {
			int statsId = createStats(connection.getConnection());
			PreparedStatement playerStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO players(fName, lName, positionType, statsId) VALUES (?, ?, ?, ?);");
			
			playerStatement.setString(1, fname);
			playerStatement.setString(2, lname);
			playerStatement.setString(3, positionType);
			playerStatement.setInt(4, statsId);
			playerStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public void removePlayer(Player player) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement attackerStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM players WHERE playerId = ?;");
			
			attackerStatement.setInt(1, player.getId());
			attackerStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
}
