package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import league.Match;
import league.Season;
import league.Team;

public interface matchData {

	public static void createMatch(Team homeTeam, Team awayTeam, Season season, int matchWeek) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO matches(isComplete, matchWeek, seasonId, homeTeamId, awayTeamId) VALUES (FALSE, ?, ?, ?, ?);");
			
			seasonStatement.setInt(1 , matchWeek);
			seasonStatement.setInt(2, season.getId());
			seasonStatement.setInt(3, homeTeam.getTeamId());
			seasonStatement.setInt(4, awayTeam.getTeamId());
			seasonStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static void createSeasonMatches(List<Team> teams, Season season) {
		if (teams.size() % 2 != 0) {teams.add(teamData.getTeam(1)); }
	
	    int numRounds = teams.size() - 1; // Number of rounds
	    int numMatchesPerRound = teams.size() / 2;
	
	    // Create a list to rotate teams (excluding the first team)
	    List<Team> rotatingTeams = new ArrayList<Team>(teams.subList(1, teams.size()));
	
	    for (int round = 0; round < numRounds; round++) {
	        for (int i = 0; i < numMatchesPerRound; i++) {
	            Team homeTeam = (i == 0) ? teams.get(0) : rotatingTeams.get(i - 1);
	            Team awayTeam = rotatingTeams.get(rotatingTeams.size() - i - 1);
	            
	            createMatch(homeTeam, awayTeam, season, round + 1);
	            createMatch(awayTeam, homeTeam, season,  numRounds + round + 1);
	        }
	        Collections.rotate(rotatingTeams, 1);
	    }
	}
	
	public static List<Match> getNextFiveGameWeeks(Season currentSeason, int currentGameWeek) {
		JFGPdb connection = new JFGPdb();
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND matchWeek < ? + 5 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			gameWeeksStatement.setInt(2, currentGameWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						teamData.getTeam(gameWeeks.getInt("homeTeamId"), connection.getConnection()),
						teamData.getTeam(gameWeeks.getInt("awayTeamId"), connection.getConnection()),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return matches;
	}
	
	public static List<Match> getSeasonMatches(Season currentSeason) {
		JFGPdb connection = new JFGPdb();
		List<Match> matches = new ArrayList<Match>();
		
		
		return matches;
	}
	
	public static boolean checkRefAssigned(Match match) {
		boolean isRef = false;
		
		return isRef;
	}
	
	
	
	
	
}

