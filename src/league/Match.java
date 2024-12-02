package league;
import java.sql.Date;
import leagueMembers.Referee;

public class Match {

	private int matchId;
	private Referee matchReferee;
	private Date dateAndTime;
	private Team homeTeam;
	private Team awayTeam;
	private int matchWeek;
	
	public Match(int matchId, Team homeTeam, Team awayTeam, int matchWeek) {
		this.matchId = matchId;
		setTeams(homeTeam, awayTeam);
		setMatchWeek(matchWeek);
	}
	
	public int getMatchId() { return this.matchId; }
	
	public String getMatchSummary() {
		return String.format("Gameweek %d - Home: %s, Away: %s", getMatchWeek(), 
				getHomeTeam().getName(), getAwayTeam().getName());
	}

	public Team getHomeTeam() { return this.homeTeam; }
	public Team getAwayTeam() { return this.awayTeam; }
	public void setTeams(Team homeTeam, Team awayTeam) { this.homeTeam = homeTeam; this.awayTeam = awayTeam; }

	public int getMatchWeek() { return this.matchWeek; }
	public void setMatchWeek(int week) { this.matchWeek = week; }
	
	public Referee getMatchReferee() { return matchReferee; }
	public void setMatchReferee(Referee matchReferee) { this.matchReferee = matchReferee; }

	public Date getDateAndTime() { return dateAndTime; }
	public void setDateAndTime(Date dateAndTime) { this.dateAndTime = dateAndTime; }
}
