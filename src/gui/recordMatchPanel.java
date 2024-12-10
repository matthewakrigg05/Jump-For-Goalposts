package gui;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import accounts.IRefereeRole;
import league.Match;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class recordMatchPanel extends JPanel implements IRefereeRole {
	
	private List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));
	private JfgpWindow frame;
	private JButton recordButton;
	private JButton backButton;
	private Match match;
	private JButton eventButton;

	public recordMatchPanel(JfgpWindow frame, Match match) { 
		this.frame = frame; 
		this.match = match;
		initialise(frame); }
	
	public void initialise(JfgpWindow frame) {
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		addPanelComponents(this);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel) {
		JButton[] panelButton = new JButton[managerButtons.size()];
		
		JLabel lblNewLabel = new JLabel("Match: " + match.getMatchSummary());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		eventButton = new JButton("Add Event");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		add(eventButton, gbc_btnNewButton);
		
		JList events = new JList();
		GridBagConstraints gbc_events = new GridBagConstraints();
		gbc_events.insets = new Insets(0, 0, 5, 5);
		gbc_events.fill = GridBagConstraints.BOTH;
		gbc_events.gridx = 1;
		gbc_events.gridy = 2;
		add(events, gbc_events);
		
		backButton = new JButton("Back");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 0, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 4;
		add(backButton, gbc_backButton);
		
		recordButton = new JButton("Record");
		GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
		gbc_btnNewButton_1_1.gridx = 2;
		gbc_btnNewButton_1_1.gridy = 4;
		add(recordButton, gbc_btnNewButton_1_1);
	}
	
	public void addActionListeners() {
		
		eventButton.addActionListener(e -> { IRefereeRole.getMatchEventDialog(frame, match); });
		
		
		recordButton.addActionListener(e -> {
			/*
			 * on record, go through list and add events, record match result, update stats where appropriate too
			 *
			 * */
			 
		});
		
		backButton.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new toolBar(frame), BorderLayout.WEST);
			frame.getContentPane().add(new HomePanel(frame), BorderLayout.CENTER);
			frame.revalidate();
			frame.repaint();
		});
	}

}
