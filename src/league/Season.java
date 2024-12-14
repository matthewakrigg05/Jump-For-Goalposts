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
	private boolean isCurrent;
	private List<Match> fixtures;
	private List<Match> results;
	
	public Season(int id, String seasonStart, String seasonEnd, boolean isCurrent) {
		setId(id);
		setSeasonStart(seasonStart);
		setSeasonEnd(seasonEnd);
		setIsCurrent(isCurrent);
	}

	// Gets and sets the season id.
	public int getId() { return this.seasonId; }
	public void setId(int id) { this.seasonId = id; }

	// Gets and sets the start year of the season.
	public String getSeasonStart() { return seasonStart; }
	public void setSeasonStart(String seasonYear) { this.seasonStart = seasonYear; }
	
	// Gets and sets the season end year.
	public String getSeasonEnd() { return seasonEnd; }
	public void setSeasonEnd(String seasonEnd) { this.seasonEnd = seasonEnd; }
	public String getSeasonStartEnd() { return this.seasonStart + "/" + this.seasonEnd; }

	// Gets and sets whether instance of the season is the current one.
	public boolean getIsCurrent() { return this.isCurrent; }
	public void setIsCurrent(boolean current) { this.isCurrent = current; }
	
	// Gets and sets the fixtures of a season.
	public List<Match> getFixtures() { return fixtures; }
	public void setFixtures(List<Match> fixtures) { this.fixtures = fixtures; }

	// Gets and sets the results from a season.
	public List<Match> getResults() { return results; }
	public void setResults(List<Match> results) { this.results = results; }
	
	// Calculates the top scorer of a season.
	public Player getTopScorer(JFGPdb db, Season season) {
		List<Player> players = db.getAllPlayers();
		Player topScorer = new Player(0, "No", "Player");
		int highestGoals = 0;
		
		// loop iterates through each player determining their num of goals and assigning
		// them as the top scorer if they have more goals than the current assigned top scorer
		for(Player player : players) {
			if (highestGoals < player.getGoals(db.getConnection())) {
				topScorer = player;
				highestGoals = player.getGoals(db.getConnection());
			}
		}
		return topScorer;
	}
	
	// Retrieves all the teams playing in the season.
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
	
	// frequent appearing AND (homeTeamId <> 1 AND awayTeamId <> 1) in the sql statements simply ensures that 
	// no bye week matches are unnecessarily displayed in the gui
	public int getCurrentGameWeek(Connection connection) {
		try {
			PreparedStatement currentMatchWeekStatement = (connection).prepareStatement(
					"SELECT MIN(matchWeek) AS currentWeek FROM matches WHERE isComplete = 0 AND (homeTeamId <> 1 AND awayTeamId <> 1) AND seasonId = ?;" );
			currentMatchWeekStatement.setInt(1, getId());
			ResultSet matchweekResult = currentMatchWeekStatement.executeQuery();
			
			int matchWeek = matchweekResult.getInt("currentWeek");
			
			return matchWeek;
		} catch (SQLException e) { e.printStackTrace(); return 0; }	
	}
	
	// Retrieves all matches to be played within the next five match weeks.
	public List<Match> getNextFiveGameWeeks(JFGPdb db) {
		List<Match> matches = new ArrayList<Match>();
		int currentGameWeek = getCurrentGameWeek(db.getConnection());
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND (homeTeamId <> 1 AND awayTeamId <> 1) AND matchWeek < ? + 5 ORDER BY matchWeek ASC;");
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
	
	// Retrieves all the matches to be played in a season
	public List<Match> getSeasonMatches(JFGPdb db) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND (homeTeamId <> 1 AND awayTeamId <> 1) ORDER BY matchWeek ASC;");
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
	
	// Retrieves all matches that are not completed.
	public List<Match> getSeasonFixtures(JFGPdb db) {
		List<Match> fixtures = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND isComplete = 0 AND (homeTeamId <> 1 AND awayTeamId <> 1) ORDER BY matchWeek ASC;");
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
		
		setFixtures(fixtures);
		return fixtures;
	}
	
	// Retrieves all the matches of a season that are completed
	public List<Match> getSeasonResults(JFGPdb db) {
		List<Match> results = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND isComplete = 1 AND (homeTeamId <> 1 AND awayTeamId <> 1) ORDER BY matchWeek ASC;");
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
		
		setResults(results);
		return results;
	}
	
	// Calculates league table data.
	public String[][] getLeagueTableData(Connection connection) {
		List<Team> teams = getSeasonTeams(connection);
		
		teams.sort((team1, team2) -> Integer.compare(
		        team2.getTeamPoints(connection), 
		        team1.getTeamPoints(connection)
		    ));
		
		// num of teams and columns (team name, games played, wins, draws, losses and points)
		String[][] leagueTableData = new String[teams.size()][6]; 
		
		for(int i = 0; i < teams.size(); i++) {
			Team team = teams.get(i);
			leagueTableData[i][0] = team.getName(); 
			leagueTableData[i][1] = String.valueOf(team.getGamesPlayed(connection));
			leagueTableData[i][2] = String.valueOf(team.getTeamWins(connection));
			leagueTableData[i][3] = String.valueOf(team.getTeamDraws(connection));
			leagueTableData[i][4] = String.valueOf(team.getTeamLosses(connection));
			leagueTableData[i][5] = String.valueOf(team.getTeamPoints(connection));
		}
		return leagueTableData;
	}
	
	// Retrieves matches to be played in a specific match week.
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
