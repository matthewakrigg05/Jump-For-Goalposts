package accounts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import league.Match;
import league.Team;
import leagueMembers.*;

public class AdminAccount extends Account {
	
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
	
	public void createPlayer(Connection connection, String fname, String lname, String positionType) {
		try {
			int statsId = createStats(connection);
			PreparedStatement playerStatement = connection.prepareStatement(
			        "INSERT INTO players(fName, lName, positionType, statsId) VALUES (?, ?, ?, ?);");
			
			playerStatement.setString(1, fname);
			playerStatement.setString(2, lname);
			playerStatement.setString(3, positionType);
			playerStatement.setInt(4, statsId);
			playerStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void removePlayer(Connection connection, Player player) {
		try {
			PreparedStatement attackerStatement = connection.prepareStatement(
			        "DELETE FROM players WHERE playerId = ?;");
			
			attackerStatement.setInt(1, player.getId());
			attackerStatement.executeUpdate();

			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void assignRef(Connection connection, Match match, Referee ref) {
		try {
			PreparedStatement assignRefStatement = (connection).prepareStatement(
			        "UPDATE matches SET refereeId = ? WHERE matchId = ?;");
			
			assignRefStatement.setInt(1, ref.getId());
			assignRefStatement.setInt(2, match.getMatchId());
			assignRefStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void removeReferee(Connection connection, Referee ref) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			
			seasonStatement.setInt(1, ref.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = (connection).prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			
			refAccDel.setInt(1, ref.getUserId());
			refAccDel.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void createReferee(Connection connection, String fname, String lname, String city, int id) {
		try {
			PreparedStatement refStatement = (connection).prepareStatement(
			        "INSERT INTO referees(fName, lName, preferredLocation, leagueId, userId) VALUES (?, ?, ?, 1, ?);");
			
			refStatement.setString(1, fname);
			refStatement.setString(2, lname);
			refStatement.setString(3, city);
			refStatement.setInt(4, id);
			refStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void createRefereeAccount(Connection connection, String fname, String lname, String city) {
		try {
			PreparedStatement refAccStatement = (connection).prepareStatement(
			        "INSERT INTO userAccounts(userType, emailAddress, password, leagueId) VALUES ('referee', ?, ?, 1);");
			
			refAccStatement.setString(1, lname + "@jfgp.org");
			refAccStatement.setString(2, lname + city);
			refAccStatement.executeUpdate();
			
			PreparedStatement lastId = (connection.prepareStatement(
					"SELECT userId FROM userAccounts ORDER BY ROWID DESC limit 1;"));
			
			ResultSet id = lastId.executeQuery();
			
			int refId = id.getInt("userId");
			
			createReferee(connection, fname, lname, city, refId);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
