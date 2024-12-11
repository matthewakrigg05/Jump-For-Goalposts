package gui;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	// all static - information based dashboard, no jdialogues required as there should not be 
	// any interaction with this panel. Displays league table, some top goalscorers/assists and maybe
	// some information on upcoming matches
	JfgpWindow frame;
	
	public HomePanel(JfgpWindow frame) {
		this.frame = frame;

		JLabel label = new JLabel("test");
		label.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = getInsets();
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		add(label, gbc_recMatchesLabel);
		
		JList playerList = new JList();
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = getInsets();
		gbc_matchesToRecordList.gridx = 5;
		gbc_matchesToRecordList.gridy = 3;
		add(playerList, gbc_matchesToRecordList);
		
		// league table
		
		// top scorers and top assists
		
		// upcoming matches - game weeks

	}
	
//	@Override
//	public void initialise() {
//		
//	}
//	
//	@Override
//	public void addPanelComponents(JPanel panel) {
//		
//	}
//	
//	@Override
//	public void addActionListeners() {
//		
//	}

}
