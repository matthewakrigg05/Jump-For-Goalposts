package league;

import leagueMembers.Attacker;
import leagueMembers.Defender;
import leagueMembers.Goalkeeper;
import leagueMembers.Midfielder;

public class MatchEvent {
	
	private int matchEventId;
	private String eventType;
	private int eventMinute;
	private String playerInvolved;
	
	public Object getPlayerInvolved() { return playerInvolved; }
	public void setPlayerInvolved(Object playerInvolved) {
		if (playerInvolved instanceof Attacker) {
			this.playerInvolved = ((Attacker) playerInvolved).getFullName();
		}
		
		else if (playerInvolved instanceof Midfielder) {
			this.playerInvolved = ((Midfielder) playerInvolved).getFullName();
		}
		
		else if (playerInvolved instanceof Defender) {
			this.playerInvolved = ((Defender) playerInvolved).getFullName();
		}
		
		else if (playerInvolved instanceof Goalkeeper) {
			this.playerInvolved = ((Goalkeeper) playerInvolved).getFullName();
		}
	}
	
	public String getEventType() { return eventType; }
	public void setEventType(String eventType) { this.eventType = eventType; }
	
	public int getEventMinute() { return eventMinute; }
	public void setEventMinute(int eventMinute) { this.eventMinute = eventMinute; }
	
	public int getMatchEventId() { return matchEventId; }
}