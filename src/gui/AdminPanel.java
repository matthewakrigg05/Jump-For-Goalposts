package gui;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import league.Match;
import leagueDB.leagueData;

@SuppressWarnings("serial")
public class AdminPanel extends panel implements IAdminPanel, leagueData {
	
	private List<String> adminButtons = new ArrayList<String>(List.of("League", "Generate Fixtures", "Season", 
			"Assign Match Referees", "Team", "Managers", "Players", "Referees", "Assign Players' Team", 
			"Assign Teams' Manager", "Stadiums", "Assign Teams' Stadium"));
		
	List<String> matches = new ArrayList<String>();

	public AdminPanel(JfgpWindow frame) { 
		 super(frame); 
		 initialise();
		 }
	
	@Override
	public void initialise() {
		setButtonNames(adminButtons);
		setPanel(new JPanel());
		getPanel().setLayout(new GridBagLayout());
		setInsets(new Insets(0, 0, 10, 25));
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelButton = new JButton[getButtonNames().size()];
		
		addPanelComponents(getPanel());
		addActionListeners();
	}
	
	@Override
	protected void addPanelComponents(JPanel panel) {
		for(int i = 0; i < getButtonNames().size(); i++) {
			panelButton[i] = new JButton(getButtonNames().get(i));
			panelButton[i].setFont(getFont());
		}
		
		int currentMatchWeek = leagueData.getCurrentGameWeek(getFrame().getDbConnection(), 
				leagueData.getCurrentSeason(getFrame().getDbConnection()).getId());
		
		for(Match match : leagueData.getMatchWeekMatches(getFrame().getDbConnection(), currentMatchWeek)) { 
			matches.add(match.getMatchSummary()); }
		
		if (matches.isEmpty()) {
			matches.add("There are no matches to record");
		}
		
		JLabel leagueOptLabel = new JLabel("League Options:");
		leagueOptLabel.setFont(getFont());
		GridBagConstraints gbc_leagueOptLabel = new GridBagConstraints();
		gbc_leagueOptLabel.insets = getInsets();
		gbc_leagueOptLabel.gridx = 1;
		gbc_leagueOptLabel.gridy = 1;
		panel.add(leagueOptLabel, gbc_leagueOptLabel);
		
		JLabel matchOptLabel = new JLabel("Other Options:");
		matchOptLabel.setFont(getFont());
		GridBagConstraints gbc_matchOptLabel = new GridBagConstraints();
		gbc_matchOptLabel.insets = getInsets();
		gbc_matchOptLabel.gridx = 3;
		gbc_matchOptLabel.gridy = 1;
		panel.add(matchOptLabel, gbc_matchOptLabel);
		
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
		
		GridBagConstraints gbc_managersButton = new GridBagConstraints();
		gbc_managersButton.insets = getInsets();
		gbc_managersButton.gridx = 1;
		gbc_managersButton.gridy = 6;
		panel.add(panelButton[5], gbc_managersButton);
		
		GridBagConstraints gbc_refereesButton = new GridBagConstraints();
		gbc_refereesButton.insets = getInsets();
		gbc_refereesButton.gridx = 1;
		gbc_refereesButton.gridy = 7;
		panel.add(panelButton[6], gbc_refereesButton);

		GridBagConstraints gbc_playersButton = new GridBagConstraints();
		gbc_playersButton.insets = getInsets();
		gbc_playersButton.gridx = 1;
		gbc_playersButton.gridy = 8;
		panel.add(panelButton[7], gbc_playersButton);
		
		GridBagConstraints gbc_assignPlayerButton = new GridBagConstraints();
		gbc_assignPlayerButton.insets = getInsets();
		gbc_assignPlayerButton.gridx = 3;
		gbc_assignPlayerButton.gridy = 5;
		panel.add(panelButton[8], gbc_assignPlayerButton);
		
		GridBagConstraints gbc_assignManagerButton = new GridBagConstraints();
		gbc_assignManagerButton.insets = getInsets();
		gbc_assignManagerButton.gridx = 3;
		gbc_assignManagerButton.gridy = 6;
		panel.add(panelButton[9], gbc_assignManagerButton);
		
		GridBagConstraints gbc_stadiumsButton = new GridBagConstraints();
		gbc_stadiumsButton.insets = getInsets();
		gbc_stadiumsButton.gridx = 3;
		gbc_stadiumsButton.gridy = 7;
		panel.add(panelButton[10], gbc_stadiumsButton);
		
		GridBagConstraints gbc_assignStadiumsButton = new GridBagConstraints();
		gbc_assignStadiumsButton.insets = getInsets();
		gbc_assignStadiumsButton.gridx = 3;
		gbc_assignStadiumsButton.gridy = 8;
		panel.add(panelButton[11], gbc_assignStadiumsButton);
		
		JLabel recMatchesLabel = new JLabel("Record A Match This Week: ");
		recMatchesLabel.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = getInsets();
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		panel.add(recMatchesLabel, gbc_recMatchesLabel);
		
		JList<String> matchesToRecordList = new JList(matches.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = getInsets();
		gbc_matchesToRecordList.gridx = 5;
		gbc_matchesToRecordList.gridy = 3;
		panel.add(matchesToRecordList, gbc_matchesToRecordList);
	}
	
	@Override
	protected void addActionListeners() {
		panelButton[0].addActionListener(e -> { IAdminPanel.getLeagueDialog(getFrame()); });
		panelButton[1].addActionListener(e -> { IAdminPanel.getGenFixturesDialog(getFrame()); });
		panelButton[2].addActionListener(e -> { IAdminPanel.getSeasonDialog(getFrame()); });
		panelButton[3].addActionListener(e -> { IAdminPanel.getAssignRefDialog(getFrame()); });
		panelButton[4].addActionListener(e -> { IAdminPanel.getTeamDialog(getFrame()); });
		panelButton[5].addActionListener(e -> { IAdminPanel.getManagersDialog(getFrame()); });
		panelButton[6].addActionListener(e -> { IAdminPanel.getPlayerDialog(getFrame()); });
		panelButton[7].addActionListener(e -> { IAdminPanel.getRefereeDialog(getFrame()); });
		panelButton[8].addActionListener(e -> { IAdminPanel.getAssignPlayerDialog(getFrame()); });
		panelButton[9].addActionListener(e -> { IAdminPanel.getAssignManagerDialog(getFrame()); });
		panelButton[10].addActionListener(e -> { IAdminPanel.getStadiumDialog(getFrame()); });
		panelButton[11].addActionListener(e -> { IAdminPanel.getAssignStadiumDialog(getFrame()); });
	}	
}
