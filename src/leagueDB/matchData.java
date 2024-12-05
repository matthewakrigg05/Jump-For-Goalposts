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
		if (teams.size() % 2 != 0) {teams.add(teamData.getTeam(connection, 1)); }
	
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
	
	public static List<Match> getNextFiveGameWeeks(Connection connection, Season currentSeason, int currentGameWeek) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND matchWeek < ? + 5 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			gameWeeksStatement.setInt(2, currentGameWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						teamData.getTeam(connection, gameWeeks.getInt("homeTeamId")),
						teamData.getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		return matches;
	}
	
	public static List<Match> getSeasonMatches(Connection connection, Season currentSeason) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						teamData.getTeam(connection, gameWeeks.getInt("homeTeamId")),
						teamData.getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
	
	public static boolean checkRefAssigned(Connection connection, Match match) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT refereeId FROM matches WHERE matchId = ? AND refereeId IS NOT NULL;");
			
			matchStatement.setInt(1, match.getMatchId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static List<Match> getNextFiveRefMatches(Connection connection, Referee referee) {
		List<Match> matchesToAttend = new ArrayList<Match>();
		
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE refereeId = ? AND isComplete = false ORDER BY matchWeek ASC LIMIT 5;");
			
			matchStatement.setInt(1, referee.getId());
			ResultSet res = matchStatement.executeQuery();
			
			while(res.next()) {
				Match match= new Match (
						res.getInt("matchId"),
						teamData.getTeam(connection, res.getInt("homeTeamId")),
						teamData.getTeam(connection, res.getInt("awayTeamId") ),
						res.getInt("matchWeek")
						);
				matchesToAttend.add(match);
			}
					
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
						teamData.getTeam(connection, gameWeeks.getInt("homeTeamId")),
						teamData.getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
}