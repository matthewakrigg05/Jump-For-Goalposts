package gui.dialogs;

import java.awt.Insets;
import javax.swing.*;

import league.Match;
import leagueDB.matchData;

public class displayMatchDialog extends JDialog implements matchData {
	
    Insets insets;
	
	public displayMatchDialog(Match match) { 
		this.insets = new Insets(0, 0, 5, 5);
		initialise();
		}
	
	public void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(450, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Referees");
	}
}
