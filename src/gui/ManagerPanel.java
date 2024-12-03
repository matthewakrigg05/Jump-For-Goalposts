package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import accounts.ManagerAccount;

@SuppressWarnings("serial")
public class ManagerPanel extends panel {

	List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));

	public ManagerPanel() { initialise(); }
	
	public ManagerPanel(ManagerAccount manager) { initialise(); }
	
	@Override
	public void initialise() {
		setButtonNames(managerButtons);
		setPanel(new JPanel());
		getPanel().setLayout(new GridBagLayout());
		setInsets(new Insets(0, 0, 10, 25));
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		panelButton = new JButton[getButtonNames().size()];
		addPanelComponents(getPanel());
		addActionListeners();
	}
}
