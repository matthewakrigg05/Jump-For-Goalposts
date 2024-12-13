package league;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import leagueMembers.Referee;

public class Match {

	private int matchId;
	private Referee matchReferee;
	private Team homeTeam;
	private Team awayTeam;
	private int homeScore = 0;
	private int awayScore = 0;
	private int matchWeek;
	
	public Match(int matchId, Team homeTeam, Team awayTeam, int matchWeek) {
		this.matchId = matchId;
		setTeams(homeTeam, awayTeam);
		setMatchWeek(matchWeek);
	}
	
	public int getMatchId() { return this.matchId; }
	
	public String getMatchSummary() {
		return String.format("%s VS %s", 
				getHomeTeam().getName(), getAwayTeam().getName());
	}

	public Team getHomeTeam() { return this.homeTeam; }
	public Team getAwayTeam() { return this.awayTeam; }
	public void setTeams(Team homeTeam, Team awayTeam) { this.homeTeam = homeTeam; this.awayTeam = awayTeam; }

	public int getMatchWeek() { return this.matchWeek; }
	public void setMatchWeek(int week) { this.matchWeek = week; }
	
	public Referee getMatchReferee() { return matchReferee; }
	public void setMatchReferee(Referee matchReferee) { this.matchReferee = matchReferee; }

	public int getHomeScore() { return homeScore; }
	public void setHomeScore(int homeScore) { this.homeScore = homeScore; }

	public int getAwayScore() { return awayScore; }
	public void setAwayScore(int awayScore) { this.awayScore = awayScore; }
	
	// Asserts whether the match already has a referee assigned in order to prompt
	// the user for confirmation that they want to reassign the referee
	public boolean checkRefAssigned(Connection connection) {
		try {
			PreparedStatement assignedStatement = (connection).prepareStatement(
			        "SELECT refereeId FROM matches WHERE matchId = ? AND refereeId IS NOT NULL;");
			assignedStatement.setInt(1, getMatchId());
			ResultSet res = assignedStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
}
