package league;

import java.sql.Date;

import leagueMembers.Referee;

public class Result extends Match {
	private MatchEvent[] events;
	private boolean isComplete;
	private int attendance;

	public Result(int matchId, Team homeTeam, Team awayTeam) {
		super(matchId, homeTeam, awayTeam, matchId);
	}

	
	// Standard getters and setters
	public MatchEvent[] getEvents() { return events; }
	public void setEvents(MatchEvent[] events) { this.events = events; }

	public boolean isComplete() { return isComplete; }
	public void setComplete(boolean isComplete) { this.isComplete = isComplete; }

	public int getAttendance() { return attendance; }
	public void setAttendance(int attendance) { this.attendance = attendance; }
}