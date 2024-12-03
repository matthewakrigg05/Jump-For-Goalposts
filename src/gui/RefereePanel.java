package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import leagueMembers.Referee;

@SuppressWarnings("serial")
public class RefereePanel extends panel {

	List<String> refereeButtons = new ArrayList<String>(List.of("Record Matches", "View My Upcoming Fixtures"));
	Referee referee;

	public RefereePanel() { initialise(); }
	
	@Override
	public void initialise() {
		setButtonNames(refereeButtons);
		setPanel(new JPanel());
		getPanel().setLayout(new GridBagLayout());
		setInsets(new Insets(0, 0, 10, 25));
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		panelButton = new JButton[getButtonNames().size()];
		addPanelComponents(getPanel());
		addActionListeners();
	}

}
