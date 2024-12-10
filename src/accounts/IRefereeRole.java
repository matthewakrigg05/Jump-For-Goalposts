package accounts;
import java.awt.GridBagLayout;

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
	
	public static JDialog getMatchEventDialog(JfgpWindow frame, Match match) {
		JDialog matchEventDialog = new JDialog();
		matchEventDialog.setModal(true);
		matchEventDialog.setAlwaysOnTop(true);
		matchEventDialog.setFocusable(true);
		matchEventDialog.setSize(760, 500);
		matchEventDialog.setTitle("Match Event");
		matchEventDialog.setLayout(new GridBagLayout());
		
		matchEventDialog.setVisible(true);
		return matchEventDialog;
	}
}
