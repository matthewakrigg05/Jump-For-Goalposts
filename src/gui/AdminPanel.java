package gui;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import gui.dialogs.*;
import league.Match;
import league.Season;
import leagueDB.matchData;
import leagueDB.seasonData;

@SuppressWarnings("serial")
public class AdminPanel extends panel implements seasonData, matchData {
	
	List<String> adminButtons = new ArrayList<String>(List.of("League", "Generate Fixtures", "Season", "Assign Match Referees", 
			"Team", "Record Matches", "Managers", "Update League Data", "Players", "Referees", "Assign Player to Team", 
			"Assign Manager to Team"));
	
	Season currentSeason;	
	int currentMatchWeek;
	List<Match> matchesToRecord; 
	List<String> matches = new ArrayList<String>();

	public AdminPanel(JfgpWindow frame) { 
		 this.connection = frame.getDbConnection();
		 initialise(); }
	
	@Override
	public void initialise() {
		setButtonNames(adminButtons);
		setPanel(new JPanel());
		getPanel().setLayout(new GridBagLayout());
		setInsets(new Insets(0, 0, 10, 25));
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelButton = new JButton[getButtonNames().size()];
		
		currentSeason = seasonData.getCurrentSeason(getConnection());
		currentMatchWeek = seasonData.getCurrentGameWeek(getConnection(), currentSeason.getId());
		matchesToRecord = matchData.getMatchWeekMatches(getConnection(), currentMatchWeek);
		
		addPanelComponents(getPanel());
		addActionListeners();
	}
	
	@Override
	protected void addPanelComponents(JPanel panel) {
		for(int i = 0; i < getButtonNames().size(); i++) {
			panelButton[i] = new JButton(getButtonNames().get(i));
			panelButton[i].setFont(getFont());
		}
		
		for(Match match : matchesToRecord) { matches.add(match.getMatchSummary()); }
		
		JLabel leagueOptLabel = new JLabel("League Options:");
		leagueOptLabel.setFont(getFont());
		GridBagConstraints gbc_leagueOptLabel = new GridBagConstraints();
		gbc_leagueOptLabel.insets = getInsets();
		gbc_leagueOptLabel.gridx = 1;
		gbc_leagueOptLabel.gridy = 1;
		panel.add(leagueOptLabel, gbc_leagueOptLabel);
		
		JLabel matchOptLabel = new JLabel("Match Options:");
		matchOptLabel.setFont(getFont());
		GridBagConstraints gbc_matchOptLabel = new GridBagConstraints();
		gbc_matchOptLabel.insets = getInsets();
		gbc_matchOptLabel.gridx = 3;
		gbc_matchOptLabel.gridy = 1;
		panel.add(matchOptLabel, gbc_matchOptLabel);
		
		JLabel teamManOpts = new JLabel("Team Management Options:");
		teamManOpts.setFont(getFont());
		GridBagConstraints gbc_teamManOpts = new GridBagConstraints();
		gbc_teamManOpts.insets = getInsets();
		gbc_teamManOpts.gridx = 5;
		gbc_teamManOpts.gridy = 1;
		panel.add(teamManOpts, gbc_teamManOpts);
		
		GridBagConstraints gbc_leagueButton = new GridBagConstraints();
		gbc_leagueButton.insets = getInsets();
		gbc_leagueButton.gridx = 1;
		gbc_leagueButton.gridy = 3;
		panel.add(panelButton[0], gbc_leagueButton);
		
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = getInsets();
		gbc_genFixturesButton.gridx = 3;
		gbc_genFixturesButton.gridy = 3;
		panel.add(panelButton[1], gbc_genFixturesButton);
		
		GridBagConstraints gbc_seasonButton = new GridBagConstraints();
		gbc_seasonButton.insets = getInsets();
		gbc_seasonButton.gridx = 1;
		gbc_seasonButton.gridy = 4;
		panel.add(panelButton[2], gbc_seasonButton);
		
		GridBagConstraints gbc_matchRefsButton = new GridBagConstraints();
		gbc_matchRefsButton.insets = getInsets();
		gbc_matchRefsButton.gridx = 3;
		gbc_matchRefsButton.gridy = 4;
		panel.add(panelButton[3], gbc_matchRefsButton);
		
		GridBagConstraints gbc_teamButton = new GridBagConstraints();
		gbc_teamButton.insets = getInsets();
		gbc_teamButton.gridx = 1;
		gbc_teamButton.gridy = 5;
		panel.add(panelButton[4], gbc_teamButton);

		GridBagConstraints gbc_recMatchButton = new GridBagConstraints();
		gbc_recMatchButton.insets = getInsets();
		gbc_recMatchButton.gridx = 3;
		gbc_recMatchButton.gridy = 5;
		panel.add(panelButton[5], gbc_recMatchButton);
		
		GridBagConstraints gbc_managersButton = new GridBagConstraints();
		gbc_managersButton.insets = getInsets();
		gbc_managersButton.gridx = 1;
		gbc_managersButton.gridy = 6;
		panel.add(panelButton[6], gbc_managersButton);
		
		GridBagConstraints gbc_updateLeagueDataButton = new GridBagConstraints();
		gbc_updateLeagueDataButton.insets = getInsets();
		gbc_updateLeagueDataButton.gridx = 3;
		gbc_updateLeagueDataButton.gridy = 6;
		panel.add(panelButton[7], gbc_updateLeagueDataButton);
		
		GridBagConstraints gbc_refereesButton = new GridBagConstraints();
		gbc_refereesButton.insets = getInsets();
		gbc_refereesButton.gridx = 1;
		gbc_refereesButton.gridy = 7;
		panel.add(panelButton[8], gbc_refereesButton);

		GridBagConstraints gbc_playersButton = new GridBagConstraints();
		gbc_playersButton.insets = getInsets();
		gbc_playersButton.gridx = 1;
		gbc_playersButton.gridy = 8;
		panel.add(panelButton[9], gbc_playersButton);
		
		GridBagConstraints gbc_assignPlayerButton = new GridBagConstraints();
		gbc_assignPlayerButton.insets = getInsets();
		gbc_assignPlayerButton.gridx = 5;
		gbc_assignPlayerButton.gridy = 3;
		panel.add(panelButton[10], gbc_assignPlayerButton);
		
		GridBagConstraints gbc_assignManagerButton = new GridBagConstraints();
		gbc_assignManagerButton.insets = getInsets();
		gbc_assignManagerButton.gridx = 5;
		gbc_assignManagerButton.gridy = 4;
		panel.add(panelButton[11], gbc_assignManagerButton);
		
		JLabel recMatchesLabel = new JLabel("Record A Match This Week: ");
		recMatchesLabel.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = getInsets();
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 5;
		panel.add(recMatchesLabel, gbc_recMatchesLabel);
		
		JList matchesToRecordList = new JList(matches.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = getInsets();
		gbc_matchesToRecordList.gridx = 5;
		gbc_matchesToRecordList.gridy = 6;
		panel.add(matchesToRecordList, gbc_matchesToRecordList);
	}
	
	@Override
	protected void addActionListeners() {
		panelButton[0].addActionListener(e -> { new leagueDialog(connection).setVisible(true); });
		panelButton[1].addActionListener(e -> { new genFixturesDialog().setVisible(true); });
		panelButton[2].addActionListener(e -> { new seasonDialog().setVisible(true); });
		panelButton[3].addActionListener(e -> { new assignRefDialog().setVisible(true); });
		panelButton[4].addActionListener(e -> { new teamDialog().setVisible(true); });
		
//		panelButton[5].addActionListener(e -> { 
//			new recordMatchPanel(this).setVisible(true);
//			this.setVisible(false); });
		
		panelButton[6].addActionListener(e -> { new managersDialog().setVisible(true); });
		panelButton[7].addActionListener(e -> { new updateDialog().setVisible(true); });
		panelButton[8].addActionListener(e -> { new playersDialog().setVisible(true); });
		panelButton[9].addActionListener(e -> { new refereesDialog().setVisible(true); });
	}	
}
