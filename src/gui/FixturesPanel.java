package gui;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import league.Match;
import league.Season;
import league.Stadium;
import league.Team;
import leagueDB.JFGPdb;

import leagueMembers.Referee;

@SuppressWarnings("serial")
public class FixturesPanel extends JPanel {
	JfgpWindow frame;
	JFGPdb db;
	Insets insets;
	List<String> matchSelection = new ArrayList<String>();
	List<Match> matches;
	Match selectedMatch;
	JList matchList;
	Season currentSeason;
	private JLabel teamsLabel;
	private JLabel home;
	private JLabel away;
	private JLabel gameWeek;
	private JLabel stadium;
	private JLabel referee; 
	
	public FixturesPanel(JfgpWindow frame) {
		this.frame = frame;
		this.db = frame.getDb();
		initialise();
	}

	public void initialise() {
		insets = new Insets(0, 0, 10, 25);
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		addPanelComponents(this, frame);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel, JfgpWindow frame) {
		currentSeason = db.findCurrentSeason();
		matches = new ArrayList<Match>(currentSeason.getSeasonFixtures(db));
		for(Match fixture : matches) { matchSelection.add(fixture.getMatchSummary()); }
		
		matchList = new JList(matchSelection.toArray());
		
		panel.add(matchList);
		
		JPanel playerProfile = new JPanel();
		playerProfile.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = insets;
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		
		teamsLabel = new JLabel("Match: ");
		home = new JLabel("Home: ");
		away = new JLabel("Away: ");
		gameWeek = new JLabel("Week: ");
		stadium = new JLabel("Stadium: ");
		referee = new JLabel("Referee: ");
		
		playerProfile.add(teamsLabel);
		playerProfile.add(home);
		playerProfile.add(away);
		playerProfile.add(gameWeek);
		playerProfile.add(stadium);
		playerProfile.add(referee);
		
		panel.add(playerProfile);
	}
	
	public void addActionListeners() {
		
		matchList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedMatch = matches.get(matchList.getSelectedIndex());
				
				teamsLabel.setText("Match: " + selectedMatch.getMatchSummary());
				home.setText("Home: " + selectedMatch.getHomeTeam().getName());
				away.setText("Away: " + selectedMatch.getAwayTeam().getName());
				gameWeek.setText("Week: " + selectedMatch.getMatchWeek());
				
				
				if (selectedMatch.getHomeTeam().getStadium() == null) {
					String stad = "TBC";
					stadium.setText("Stadium: " + stad);
				}
				else {
					Stadium stad = selectedMatch.getHomeTeam().getStadium();
					stadium.setText("Stadium: " + stad.getStadiumName());
				}
				
				if (selectedMatch.getMatchReferee() == null) {
					String ref = "TBC";
					referee.setText("Referee: " + ref);
				}
				else {
					Referee ref = selectedMatch.getMatchReferee();
					referee.setText("Referee: " + ref);
				}
			}
		});
	}
	
	
}
