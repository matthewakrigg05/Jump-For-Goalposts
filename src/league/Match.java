package league;
import java.sql.Date;
import leagueMembers.Referee;

public class Match {

	private int matchId;
	private Team[] teams;
	private Referee matchReferee;
	private Date dateAndTime;
	
	public Match(int matchId, Team[] teams) {
		this.matchId = matchId;
		setTeams(teams);
	}
	
	public int getMatchId() { return this.matchId; }

	public Team[] getTeams() { return teams; }
	public void setTeams(Team[] teams) { this.teams = teams; }

	public Referee getMatchReferee() { return matchReferee; }
	public void setMatchReferee(Referee matchReferee) { this.matchReferee = matchReferee; }

	public Date getDateAndTime() { return dateAndTime; }
	public void setDateAndTime(Date dateAndTime) { this.dateAndTime = dateAndTime; }
}
