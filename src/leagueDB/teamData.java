package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import accounts.RefereeAccount;
import league.Stadium;
import league.Team;
import leagueMembers.Manager;
import leagueMembers.Referee;

public interface teamData {

	// need team name, stadium, manager, players, rival teams, create stats for the team, create team employees for team
	// constructor for team: int id, String name, Stadium stadium, Manager manager, Object[] players
	
	public static void createTeam(String teamName) {
		JFGPdb db = new JFGPdb();
		Connection connection = db.getConnection();
		try {
			
			int newStatsId = createStats(connection);
			
			PreparedStatement teamStatement = (connection).prepareStatement(
			        "INSERT INTO teams(teamName, statsId) VALUES (?, ?);");
			
			teamStatement.setString(1, teamName);
			teamStatement.setInt(2, newStatsId);
			teamStatement.executeUpdate();
		
			db.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void removeTeam(Team team) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM teams WHERE teamId = ?;");
			
			seasonStatement.setInt(1, team.getTeamId());
			seasonStatement.executeUpdate();
			
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static List<Team> getAllTeams() {
		JFGPdb connection = new JFGPdb();
		List<Team> teams = new ArrayList<Team>();
		
		try {
			PreparedStatement teamsStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM teams");
			ResultSet teamResult = teamsStatement.executeQuery();
			
			while(teamResult.next()) {
				Team team = new Team(
						teamResult.getInt("teamId"),
						teamResult.getString("teamName")
		        		);
				teams.add(team);
			}
			connection.closeConnection();					
			return teams;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Team getTeam(int id) { 
		JFGPdb connection = new JFGPdb();
		Team team = null;
		
		try {
			PreparedStatement teamStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM teams WHERE teamId = ?;");
			
			teamStatement.setInt(1, id);
			ResultSet teamResult = teamStatement.executeQuery();
			team = new Team(teamResult.getInt("teamId"), teamResult.getString("teamName")); 
			
			connection.closeConnection();					
			return team;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
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
}
