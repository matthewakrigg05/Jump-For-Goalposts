package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import league.Match;

@SuppressWarnings("serial")
public class recordMatchPanel extends JPanel {
	
	List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));
	JfgpWindow frame;

	public recordMatchPanel(JfgpWindow frame, Match match) { this.frame = frame; initialise(frame); }
	
	public void initialise(JfgpWindow frame) {
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JButton[] panelButton = new JButton[managerButtons.size()];
		addPanelComponents(this);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel) {
		// need to be able to pick a player in their team and change their shirt number, have a squad lineup and view next 5 games
		
	}
	
	public void addActionListeners() {
		
	}

}
