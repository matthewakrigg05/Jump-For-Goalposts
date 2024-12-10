package league;
import leagueMembers.Referee;

public class Match {

	private int matchId;
	private Referee matchReferee;
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
}
