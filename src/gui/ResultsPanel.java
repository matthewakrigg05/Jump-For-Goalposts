package gui;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import league.Match;
import league.Season;
import league.Stadium;
import leagueMembers.Referee;

@SuppressWarnings("serial")
public class ResultsPanel extends JPanel {
	
	JfgpWindow frame;
	Insets insets;
	
	List<String> matchSelection = new ArrayList<String>();
	List<Match> results;
	Match selectedMatch;
	Season currentSeason;
	
	JList matchList;
	JLabel teamsLabel;
	JLabel score;
	JList events;
	JLabel home;
	JLabel away;
	JLabel gameWeek;
	JLabel stadium;
    JLabel referee; 
		
	public ResultsPanel(JfgpWindow frame) {
		this.frame = frame;
		initialise();
	}

	public void initialise() {
		insets = new Insets(0, 0, 10, 25);
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		addPanelComponents(this);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel) {
		currentSeason = frame.getDb().findCurrentSeason();
		results = new ArrayList<Match>(currentSeason.getSeasonResults(frame.getDb()));
		for(Match result : results) { matchSelection.add(result.getMatchSummary()); }
		
		matchList = new JList(matchSelection.toArray());
		
		panel.add(matchList);
		
		JPanel resultProfile = new JPanel();
		resultProfile.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = insets;
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		
		teamsLabel = new JLabel("Match: ");
		score = new JLabel("Score: ");
		home = new JLabel("Home: ");
		away = new JLabel("Away: ");
		gameWeek = new JLabel("Week: ");
		stadium = new JLabel("Stadium: ");
		referee = new JLabel("Referee: ");
		
		resultProfile.add(teamsLabel);
		resultProfile.add(score);
		resultProfile.add(home);
		resultProfile.add(away);
		resultProfile.add(gameWeek);
		resultProfile.add(stadium);
		resultProfile.add(referee);
		panel.add(resultProfile);
	}
	
	public void addActionListeners() {
		matchList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedMatch = results.get(matchList.getSelectedIndex());
				
				teamsLabel.setText("Match: " + selectedMatch.getMatchSummary());
				score.setText("Score: " + selectedMatch.getHomeScore() + "-" + selectedMatch.getAwayScore());
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


