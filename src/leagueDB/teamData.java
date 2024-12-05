package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import league.Team;

public interface teamData {
	
	
	
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
}
