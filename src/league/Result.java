package league;

public class Result extends Match {
	private MatchEvent[] events;
	private int resultId;
	private boolean isComplete;
	private int attendance;

	public Result(int resultId, int matchId, Team homeTeam, Team awayTeam, int matchWeek) {
		super(matchId, homeTeam, awayTeam, matchId);
		this.setResultId(resultId);
	}
	
	public Result(int matchId, Team homeTeam, Team awayTeam, int matchWeek) {
		super(matchId, homeTeam, awayTeam, matchWeek);
	}

	// Standard getters and setters
	public MatchEvent[] getEvents() { return events; }
	public void setEvents(MatchEvent[] events) { this.events = events; }

	public boolean isComplete() { return isComplete; }
	public void setComplete(boolean isComplete) { this.isComplete = isComplete; }

	public int getAttendance() { return attendance; }
	public void setAttendance(int attendance) { this.attendance = attendance; }

	public int getResultId() { return resultId; }
	public void setResultId(int resultId) { this.resultId = resultId; }
}