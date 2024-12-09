package accounts;
import javax.swing.JDialog;

import gui.JfgpWindow;
import league.Match;
import league.MatchEvent;

public interface IRefereeRole {
	
	public static void recordMatchScore(Match match, String score) {
		
	}
	
	public static void matchToResult(Match match, MatchEvent[] events) {
		
	}
	
	public static void recordMatchEvents() {
		
	}
	
	public static JDialog getMatchEventDialog(JfgpWindow frame) {
		JDialog matchEventDialog = new JDialog();
		
		return matchEventDialog;
	}
}
