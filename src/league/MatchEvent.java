package league;
import leagueMembers.Player;

public class MatchEvent {
	
	private int matchEventId;
	private String eventType;
	private int eventMinute;
	private Player playerInvolved;
	private Team team;
	
	public MatchEvent(Team team, String eventType, int eventMinute, Player playerInvolved) {
		setEventType(eventType);
		setEventMinute(eventMinute);
		setPlayerInvolved(playerInvolved);
	}
	
	public MatchEvent() {}
	
	// Gets a small event summary
	public String getEventSummary() { return "Event: " + this.eventType + "    Minute: " + this.eventMinute + "    Player: " + this.playerInvolved.getLName(); }
	
	// Gets and sets the player involved
	public Player getPlayerInvolved() { return playerInvolved; }
	public void setPlayerInvolved(Player playerInvolved) { this.playerInvolved = playerInvolved; }
	
	// Gets and sets the type of the event
	public String getEventType() { return eventType; }
	public void setEventType(String eventType) { this.eventType = eventType; }
	
	// Gets and sets the event minute
	public int getEventMinute() { return eventMinute; }
	public void setEventMinute(int eventMinute) { this.eventMinute = eventMinute; }
	
	// gets event id
	public int getMatchEventId() { return matchEventId; }

	// Gets and sets the team played for
	public Team getTeam() { return team; }
	public void setTeam(Team team) { this.team = team; }
}