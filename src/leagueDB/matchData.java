package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import league.Match;
import league.Season;
import league.Team;
import leagueMembers.Referee;

public interface matchData {

	public static void createMatch(Connection connection, Team homeTeam, Team awayTeam, Season season, int matchWeek) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "INSERT INTO matches(isComplete, matchWeek, seasonId, homeTeamId, awayTeamId) VALUES (FALSE, ?, ?, ?, ?);");
			
			seasonStatement.setInt(1 , matchWeek);
			seasonStatement.setInt(2, season.getId());
			seasonStatement.setInt(3, homeTeam.getTeamId());
			seasonStatement.setInt(4, awayTeam.getTeamId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void createSeasonMatches(Connection connection, List<Team> teams, Season season) {
		if (teams.size() % 2 != 0) {teams.add(teamData.getTeam(1)); }
	
	    int numRounds = teams.size() - 1; // Number of rounds
	    int numMatchesPerRound = teams.size() / 2;
	
	    // Create a list to rotate teams (excluding the first team)
	    List<Team> rotatingTeams = new ArrayList<Team>(teams.subList(1, teams.size()));
	
	    for (int round = 0; round < numRounds; round++) {
	        for (int i = 0; i < numMatchesPerRound; i++) {
	            Team homeTeam = (i == 0) ? teams.get(0) : rotatingTeams.get(i - 1);
	            Team awayTeam = rotatingTeams.get(rotatingTeams.size() - i - 1);
	            
	            createMatch(connection, homeTeam, awayTeam, season, round + 1);
	            createMatch(connection, awayTeam, homeTeam, season,  numRounds + round + 1);
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
		
		try {
			PreparedStatement gameWeeksStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
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
	
	public static boolean checkRefAssigned(Match match) {
		JFGPdb connection = new JFGPdb();
		
		try {
			PreparedStatement matchStatement = (connection.getConnection()).prepareStatement(
			        "SELECT refereeId FROM matches WHERE matchId = ? AND refereeId IS NOT NULL;");
			
			matchStatement.setInt(1, match.getMatchId());
			ResultSet res = matchStatement.executeQuery();
			connection.closeConnection();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static void assignRef(Match match, Referee ref) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement assignRefStatement = (connection.getConnection()).prepareStatement(
			        "UPDATE matches SET refereeId = ? WHERE matchId = ?;");
			
			assignRefStatement.setInt(1, ref.getId());
			assignRefStatement.setInt(2, match.getMatchId());
			assignRefStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static List<Match> getNextFiveRefMatches(Referee referee) {
		JFGPdb connection = new JFGPdb();
		List<Match> matchesToAttend = new ArrayList<Match>();
		
		try {
			PreparedStatement matchStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE refereeId = ? AND isComplete = false ORDER BY matchWeek ASC LIMIT 5;");
			
			matchStatement.setInt(1, referee.getId());
			ResultSet res = matchStatement.executeQuery();
			
			while(res.next()) {
				Match match = new Match (
						res.getInt("matchId"),
						teamData.getTeam(res.getInt("homeTeamId"), connection.getConnection()),
						teamData.getTeam(res.getInt("awayTeamId"), connection.getConnection()),
						res.getInt("matchWeek")
						);
				matchesToAttend.add(match);
			}
			
			connection.closeConnection();			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matchesToAttend;
	}
	
	public static List<Match> getMatchWeekMatches( Connection connection, int matchWeek) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE matchweek = ? AND (homeTeamId <> 1 AND awayTeamId <> 1);");
			
			gameWeeksStatement.setInt(1, matchWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						teamData.getTeam(gameWeeks.getInt("homeTeamId"), connection),
						teamData.getTeam(gameWeeks.getInt("awayTeamId"), connection),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
}