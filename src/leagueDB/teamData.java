package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import league.Stadium;
import league.Team;
import leagueMembers.Manager;

public interface teamData {

	// need team name, stadium, manager, players, rival teams, create stats for the team, create team employees for team
	// constructor for team: int id, String name, Stadium stadium, Manager manager, Object[] players
	
	public static void createTeam(String teamName) {
		JFGPdb db = new JFGPdb();
		Connection connection = db.getConnection();
		try {
			
			int newStatsId = createStats(connection);
			
			PreparedStatement refAccStatement = (connection).prepareStatement(
			        "INSERT INTO teams(teamName, statsId) VALUES (?, ?);");
			
			refAccStatement.setString(1, teamName);
			refAccStatement.setInt(2, newStatsId);
			refAccStatement.executeUpdate();
			db.closeConnection();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static List<Team> getAllTeams() { return null; }
	
	public static Team getTeam() { return null; }
	
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
}
