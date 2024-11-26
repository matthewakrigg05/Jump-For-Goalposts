package gui.admin;
import javax.swing.JPanel;

import gui.JfgpWindow;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Font;

public class AdminPanel extends JfgpWindow {
	
	JPanel AdminPanel;
	Insets labelFieldInsets;
	Font labelFont;
	GridBagLayout gridBagLayout;
	String[] buttonNames = {"League", "Generate Fixtures", "Season", "Assign Match Referees", 
			"Team", "Record Matches", "Managers", "Update League Data", "Players", "Referees"};
	JButton[] panelButton;

	public AdminPanel() {
		initialiseAdminPanel();
	}
	
	public void initialiseAdminPanel() {
		panelButton = new JButton[buttonNames.length];
		AdminPanel = new JPanel();
		labelFieldInsets = new Insets(0, 0, 10, 25);
		labelFont = (new Font("Tahoma", Font.PLAIN, 25));
		AdminPanel.setLayout(new GridBagLayout());
		
		addPanelComponents(AdminPanel);
		addActionListeners();
	}
	
	@Override
	protected void addPanelComponents(JPanel panel) {
		for(int i = 0; i < buttonNames.length; i++) {
			panelButton[i] = new JButton(buttonNames[i]);
			panelButton[i].setFont(labelFont);
		}
		
		JLabel leagueOptLabel = new JLabel("League Options:");
		leagueOptLabel.setFont(labelFont);
		GridBagConstraints gbc_leagueOptLabel = new GridBagConstraints();
		gbc_leagueOptLabel.insets = new Insets(0, 0, 5, 5);
		gbc_leagueOptLabel.gridx = 1;
		gbc_leagueOptLabel.gridy = 1;
		panel.add(leagueOptLabel, gbc_leagueOptLabel);
		
		JLabel matchOptLabel = new JLabel("Match Options:");
		matchOptLabel.setFont(labelFont);
		GridBagConstraints gbc_matchOptLabel = new GridBagConstraints();
		gbc_matchOptLabel.insets = labelFieldInsets;
		gbc_matchOptLabel.gridx = 3;
		gbc_matchOptLabel.gridy = 1;
		panel.add(matchOptLabel, gbc_matchOptLabel);
		
		GridBagConstraints gbc_leagueButton = new GridBagConstraints();
		gbc_leagueButton.insets = labelFieldInsets;
		gbc_leagueButton.gridx = 1;
		gbc_leagueButton.gridy = 3;
		panel.add(panelButton[0], gbc_leagueButton);
		
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = labelFieldInsets;
		gbc_genFixturesButton.gridx = 3;
		gbc_genFixturesButton.gridy = 3;
		panel.add(panelButton[1], gbc_genFixturesButton);
		
		GridBagConstraints gbc_seasonButton = new GridBagConstraints();
		gbc_seasonButton.insets = labelFieldInsets;
		gbc_seasonButton.gridx = 1;
		gbc_seasonButton.gridy = 4;
		panel.add(panelButton[2], gbc_seasonButton);
		
		GridBagConstraints gbc_matchRefsButton = new GridBagConstraints();
		gbc_matchRefsButton.insets = labelFieldInsets;
		gbc_matchRefsButton.gridx = 3;
		gbc_matchRefsButton.gridy = 4;
		panel.add(panelButton[3], gbc_matchRefsButton);
		
		GridBagConstraints gbc_teamButton = new GridBagConstraints();
		gbc_teamButton.insets = labelFieldInsets;
		gbc_teamButton.gridx = 1;
		gbc_teamButton.gridy = 5;
		panel.add(panelButton[4], gbc_teamButton);

		GridBagConstraints gbc_recMatchButton = new GridBagConstraints();
		gbc_recMatchButton.insets = labelFieldInsets;
		gbc_recMatchButton.gridx = 3;
		gbc_recMatchButton.gridy = 5;
		panel.add(panelButton[5], gbc_recMatchButton);
		
		GridBagConstraints gbc_managersButton = new GridBagConstraints();
		gbc_managersButton.insets = labelFieldInsets;
		gbc_managersButton.gridx = 1;
		gbc_managersButton.gridy = 6;
		panel.add(panelButton[6], gbc_managersButton);
		
		GridBagConstraints gbc_updateLeagueDataButton = new GridBagConstraints();
		gbc_updateLeagueDataButton.insets = labelFieldInsets;
		gbc_updateLeagueDataButton.gridx = 3;
		gbc_updateLeagueDataButton.gridy = 6;
		panel.add(panelButton[7], gbc_updateLeagueDataButton);
		
		GridBagConstraints gbc_refereesButton = new GridBagConstraints();
		gbc_refereesButton.insets = labelFieldInsets;
		gbc_refereesButton.gridx = 1;
		gbc_refereesButton.gridy = 7;
		panel.add(panelButton[8], gbc_refereesButton);

		GridBagConstraints gbc_playersButton = new GridBagConstraints();
		gbc_playersButton.insets = labelFieldInsets;
		gbc_playersButton.gridx = 1;
		gbc_playersButton.gridy = 8;
		panel.add(panelButton[9], gbc_playersButton);
	}
	
	@Override
	protected void addActionListeners() {
		panelButton[0].addActionListener(e -> { new leagueDialog().setVisible(true); });
		panelButton[2].addActionListener(e -> { new seasonDialog().setVisible(true); });
	}
	
	@Override
	public JPanel getPanel() { return this.AdminPanel; }
}
