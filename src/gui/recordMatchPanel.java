package gui;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import accounts.AdminAccount;
import accounts.RefereeAccount;
import league.Match;
import league.MatchEvent;

import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class recordMatchPanel extends JPanel implements IPanel {
	
	List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));
	List<MatchEvent> events = new ArrayList<MatchEvent>();
	List<String> eventSummaries = new ArrayList<String>();
	Match match;
	
	int homeScore= 0;
	int awayScore = 0;
	 
	JfgpWindow frame;
	JList eventsList;
	DefaultListModel demoList = new DefaultListModel();
	JButton recordButton;
	JButton backButton;
	JButton eventButton;
	
	AdminAccount admin;
	RefereeAccount referee;

	// Constructor used if the user attempting to record a matcg is an admin
	public recordMatchPanel(JfgpWindow frame, Match match, AdminAccount admin) { 
		this.frame = frame; 
		this.match = match;
		this.admin = admin;
		initialise();
		}
	
	// Constructor used if the user attempting to record a matcg is a referee
	public recordMatchPanel(JfgpWindow frame, Match match, RefereeAccount referee) { 
		this.frame = frame; 
		this.match = match;
		this.referee = referee;
		initialise(); }
	
	@Override
	public void initialise() {
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		addPanelComponents(this);
		addActionListeners();
	}
	
	/*
	 * Shows information about the match and accesses the record match event dialog in which the user can add details
	 * of a highlight of the game, once this has been done and the event has been added, the event is then added to 
	 * the list of events which are displayed on the record match panel so that the user can see what they have added.
	 */
	@Override
	public void addPanelComponents(JPanel panel) {
		JButton[] panelButton = new JButton[managerButtons.size()];
		
		JLabel lblNewLabel = new JLabel("Match: " + match.getMatchSummary());
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel scoreLabel = new JLabel(match.getHomeTeam().getName() + " " + homeScore + " - " + awayScore + " " + match.getAwayTeam().getName());
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_scoreLabel.gridx = 1;
		gbc_scoreLabel.gridy = 1;
		add(scoreLabel, gbc_scoreLabel);
		
		eventButton = new JButton("Add Event");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		add(eventButton, gbc_btnNewButton);
		
		eventsList = new JList(demoList);
		GridBagConstraints gbc_events = new GridBagConstraints();
		gbc_events.insets = new Insets(0, 0, 5, 5);
		gbc_events.fill = GridBagConstraints.BOTH;
		gbc_events.gridx = 1;
		gbc_events.gridy = 3;
		add(eventsList, gbc_events);
		
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
	
	@Override
	public void addActionListeners() {
		
		eventButton.addActionListener(e -> { 
			if (admin == null) {
				MatchEvent event = referee.matchEventDialog(frame, match); 
				events.add(event);
				demoList.addElement(event.getEventSummary());
				eventsList.setModel(demoList);
			} else {
				MatchEvent event = admin.matchEventDialog(frame, match); 
				events.add(event);
				demoList.addElement(event.getEventSummary());
				eventsList.setModel(demoList);
			}
		}); 
		
		recordButton.addActionListener(e -> {	
			if (admin == null) {
				Match res = referee.matchToResult(frame.getDb().getConnection(), match, homeScore, awayScore);
				referee.recordMatchEvents(frame.getDb().getConnection(), events, res);
				JOptionPane.showMessageDialog(frame, "Successfully recorded!");
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new toolBar(frame), BorderLayout.WEST);
				frame.getContentPane().add(new HomePanel(frame), BorderLayout.CENTER);
				frame.revalidate();
				frame.repaint(); 
				} else {
					Match res = admin.matchToResult(frame.getDb().getConnection(), match, homeScore, awayScore);
					admin.recordMatchEvents(frame.getDb().getConnection(), events, res);
					JOptionPane.showMessageDialog(frame, "Successfully recorded!");
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new toolBar(frame), BorderLayout.WEST);
					frame.getContentPane().add(new HomePanel(frame), BorderLayout.CENTER);
					frame.revalidate();
					frame.repaint();
				}
			} ); 
		
		backButton.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new toolBar(frame), BorderLayout.WEST);
			frame.getContentPane().add(new HomePanel(frame), BorderLayout.CENTER);
			frame.revalidate();
			frame.repaint();
		});
	}

}
