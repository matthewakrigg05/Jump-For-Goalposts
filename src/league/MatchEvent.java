package league;
import leagueMembers.Player;

public class MatchEvent {
	
	private int matchEventId;
	private String eventType;
	private int eventMinute;
	private Player playerInvolved;
	
	public Player getPlayerInvolved() { return playerInvolved; }
	public void setPlayerInvolved(Player playerInvolved) { this.playerInvolved = playerInvolved; }
	
	public String getEventType() { return eventType; }
	public void setEventType(String eventType) { this.eventType = eventType; }
	
	public int getEventMinute() { return eventMinute; }
	public void setEventMinute(int eventMinute) { this.eventMinute = eventMinute; }
	
	public int getMatchEventId() { return matchEventId; }
}