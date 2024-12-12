package league;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import leagueDB.JFGPdb;
import leagueMembers.Player;

public class Season {

	private int seasonId;
	private String seasonStart;
	private String seasonEnd;
	private Team[] teams;
	private Match[] fixtures;
	private Match[] results;
	private boolean isCurrent;
	
	public Season(int id, String seasonStart, String seasonEnd, boolean isCurrent) {
		setId(id);
		setSeasonStart(seasonStart);
		setSeasonEnd(seasonEnd);
		setIsCurrent(isCurrent);
	}

	// Standard getters and setters
	public int getId() { return this.seasonId; }
	public void setId(int id) { this.seasonId = id; }

	public String getSeasonStart() { return seasonStart; }
	public void setSeasonStart(String seasonYear) { this.seasonStart = seasonYear; }
	
	public String getSeasonEnd() { return seasonEnd; }
	public void setSeasonEnd(String seasonEnd) { this.seasonEnd = seasonEnd; }
	
	public String getSeasonStartEnd() { return this.seasonStart + "/" + this.seasonEnd; }
	
	public Team[] getTeams() { return teams; }
	public void setTeams(Team[] teams) { this.teams = teams; }

	public Match[] getFixtures() { return fixtures; }
	public void setFixtures(Match[] fixtures) { this.fixtures = fixtures; }

	public Match[] getResults() { return results; }
	public void setResults(Match[] results) { this.results = results; }

	public boolean getIsCurrent() { return this.isCurrent; }
	public void setIsCurrent(boolean current) { this.isCurrent = current; }
	
	public Player getTopScorer(JFGPdb db, Season season) {
		List<Player> players = db.getAllPlayers();
		Player topScorer = new Player(0, "No", "Player");
		int highestGoals = 0;
		
		for(Player player : players) {
			if (highestGoals < player.getGoals(db.getConnection())) {
				topScorer = player;
				highestGoals = player.getGoals(db.getConnection());
			}
		}
		
		return topScorer;
	}
	
	public  List<Team> getSeasonTeams(Connection connection) {
		List<Team> teams = new ArrayList<Team>();
		try {
			PreparedStatement teamsStatement = (connection).prepareStatement( "SELECT * FROM teams WHERE teamId IN (SELECT teamId FROM teamSeason WHERE seasonId = ?);");
			teamsStatement.setInt(1, getId());
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
			} catch (SQLException e) { e.printStackTrace(); }
		
		return teams;
	}
	
	public int getCurrentGameWeek(Connection connection) {
		try {
			PreparedStatement currentMatchWeekStatement = (connection).prepareStatement(
					"SELECT MIN(matchWeek) AS currentWeek FROM matches WHERE isComplete = 0 AND seasonId = ?;" );
			currentMatchWeekStatement.setInt(1, getId());
			ResultSet matchweekResult = currentMatchWeekStatement.executeQuery();
			
			int matchWeek = matchweekResult.getInt("currentWeek");
			
			return matchWeek;
		} catch (SQLException e) { e.printStackTrace(); return 0; }	
	}
	
	public List<Match> getNextFiveGameWeeks(JFGPdb db) {
		List<Match> matches = new ArrayList<Match>();
		int currentGameWeek = getCurrentGameWeek(db.getConnection());
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND matchWeek < ? + 5 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, getId());
			gameWeeksStatement.setInt(2, currentGameWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						db.getTeam(gameWeeks.getInt("homeTeamId")),
						db.getTeam(gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		return matches;
	}
	
	public List<Match> getSeasonMatches(JFGPdb db) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, seasonId);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						db.getTeam(gameWeeks.getInt("homeTeamId")),
						db.getTeam(gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
	
	public List<Match> getSeasonFixtures(JFGPdb db) {
		List<Match> fixtures = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND isComplete = 0 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, getId());
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						db.getTeam(gameWeeks.getInt("homeTeamId")),
						db.getTeam(gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				fixtures.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return fixtures;
	}
	
	public List<Match> getSeasonResults(JFGPdb db) {
		List<Match> results = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND isComplete = 1 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, getId());
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						db.getTeam(gameWeeks.getInt("homeTeamId")),
						db.getTeam(gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				results.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return results;
	}
	
	public String[][] getLeagueTableData(Connection connection) {
		List<Team> teams = getSeasonTeams(connection);
		String[][] leagueTableData = new String[teams.size()][6];
		
		for(int i = 0; i < teams.size(); i++) {
			leagueTableData[i][0] = teams.get(i).getName(); 
			leagueTableData[i][1] = String.valueOf(teams.get(i).getGamesPlayed(connection));
			leagueTableData[i][2] = String.valueOf(teams.get(i).getTeamWins(connection));
			leagueTableData[i][3] = String.valueOf(teams.get(i).getTeamDraws(connection));
			leagueTableData[i][4] = String.valueOf(teams.get(i).getTeamLosses(connection));
			leagueTableData[i][5] = String.valueOf(teams.get(i).getTeamPoints(connection));
		}
		return leagueTableData;
	}
	
	public List<Match> getMatchWeekMatches(JFGPdb db, int matchWeek) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE matchweek = ? AND isComplete = 0 AND (homeTeamId <> 1 AND awayTeamId <> 1);");
			
			gameWeeksStatement.setInt(1, matchWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						db.getTeam(gameWeeks.getInt("homeTeamId")),
						db.getTeam(gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
}
