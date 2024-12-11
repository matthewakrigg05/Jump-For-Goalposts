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
	
	public String getEventSummary() { return "Event: " + this.eventType + "    Minute: " + this.eventMinute + "    Player: " + this.playerInvolved.getLName(); }
	
	public Player getPlayerInvolved() { return playerInvolved; }
	public void setPlayerInvolved(Player playerInvolved) { this.playerInvolved = playerInvolved; }
	
	public String getEventType() { return eventType; }
	public void setEventType(String eventType) { this.eventType = eventType; }
	
	public int getEventMinute() { return eventMinute; }
	public void setEventMinute(int eventMinute) { this.eventMinute = eventMinute; }
	
	public int getMatchEventId() { return matchEventId; }

	public Team getTeam() { return team; }
	public void setTeam(Team team) { this.team = team; }
}