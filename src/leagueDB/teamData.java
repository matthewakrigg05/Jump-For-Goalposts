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
	
	public static void createTeam(Connection connection, String teamName) {
		try {
			
			int newStatsId = createStats(connection);
			
			PreparedStatement teamStatement = (connection).prepareStatement(
			        "INSERT INTO teams(teamName, statsId) VALUES (?, ?);");
			
			teamStatement.setString(1, teamName);
			teamStatement.setInt(2, newStatsId);
			teamStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void removeTeam(Connection connection, Team team) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "DELETE FROM teams WHERE teamId = ?;");
			
			seasonStatement.setInt(1, team.getTeamId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static List<Team> getAllTeams(Connection connection) {
		List<Team> teams = new ArrayList<Team>();
		
		try {
			PreparedStatement teamsStatement = (connection).prepareStatement( "SELECT * FROM teams");
			ResultSet teamResult = teamsStatement.executeQuery();
			
			while(teamResult.next()) {
				if (teamResult.getInt("teamId") == 1) { continue; } 
				else {
				Team team = new Team(
						teamResult.getInt("teamId"),
						teamResult.getString("teamName")
		        		);
				teams.add(team);
				}
			}				
			return teams;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Team getTeam(Connection connection, int id) { 
		Team team = null;
		
		try {
			PreparedStatement teamStatement = (connection).prepareStatement(
			        "SELECT * FROM teams WHERE teamId = ?;");
			
			teamStatement.setInt(1, id);
			ResultSet teamResult = teamStatement.executeQuery();
			team = new Team(teamResult.getInt("teamId"), teamResult.getString("teamName")); 
					
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
