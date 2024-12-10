package gui;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import league.Match;
import leagueDB.leagueData;

@SuppressWarnings("serial")
public class AdminPanel extends JPanel implements IAdminPanel, leagueData {
	
	private List<String> adminButtons = new ArrayList<String>(List.of("League", "Generate Fixtures", "Season", 
			"Assign Match Referees", "Team", "Managers", "Players", "Referees", "Assign Players' Team", 
			"Assign Teams' Manager", "Stadiums", "Assign Teams' Stadium"));
	JButton[] panelButton = new JButton[adminButtons.size()];
	JfgpWindow frame;
	Insets insets;
	List<String> matches = new ArrayList<String>();

	public AdminPanel(JfgpWindow frame) { 
		 this.frame = frame;
		 insets = new Insets(0, 0, 10, 25);
		 initialise();
		 }
	
	public void initialise() {
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		setLayout(new GridBagLayout());
		addPanelComponents(this);
		addActionListeners();
	}
	
	protected void addPanelComponents(JPanel panel) {
		for(int i = 0; i < adminButtons.size(); i++) {
			panelButton[i] = new JButton(adminButtons.get(i));
			panelButton[i].setFont(getFont());
		}
		
		int currentMatchWeek = leagueData.getCurrentGameWeek(frame.getDbConnection(), 
				leagueData.getCurrentSeason(frame.getDbConnection()).getId());
		
		for(Match match : leagueData.getMatchWeekMatches(frame.getDbConnection(), currentMatchWeek)) { 
			matches.add(match.getMatchSummary()); }

		if (matches.isEmpty()) {
			matches.add("There are no matches to record");
		}
		
		JLabel leagueOptLabel = new JLabel("League Options:");
		leagueOptLabel.setFont(getFont());
		GridBagConstraints gbc_leagueOptLabel = new GridBagConstraints();
		gbc_leagueOptLabel.insets = insets;
		gbc_leagueOptLabel.gridx = 1;
		gbc_leagueOptLabel.gridy = 1;
		panel.add(leagueOptLabel, gbc_leagueOptLabel);
		
		JLabel matchOptLabel = new JLabel("Other Options:");
		matchOptLabel.setFont(getFont());
		GridBagConstraints gbc_matchOptLabel = new GridBagConstraints();
		gbc_matchOptLabel.insets = insets;
		gbc_matchOptLabel.gridx = 3;
		gbc_matchOptLabel.gridy = 1;
		panel.add(matchOptLabel, gbc_matchOptLabel);
		
		GridBagConstraints gbc_leagueButton = new GridBagConstraints();
		gbc_leagueButton.insets = insets;
		gbc_leagueButton.gridx = 1;
		gbc_leagueButton.gridy = 3;
		panel.add(panelButton[0], gbc_leagueButton);
		
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = insets;
		gbc_genFixturesButton.gridx = 3;
		gbc_genFixturesButton.gridy = 3;
		panel.add(panelButton[1], gbc_genFixturesButton);
		
		GridBagConstraints gbc_seasonButton = new GridBagConstraints();
		gbc_seasonButton.insets = insets;
		gbc_seasonButton.gridx = 1;
		gbc_seasonButton.gridy = 4;
		panel.add(panelButton[2], gbc_seasonButton);
		
		GridBagConstraints gbc_matchRefsButton = new GridBagConstraints();
		gbc_matchRefsButton.insets = insets;
		gbc_matchRefsButton.gridx = 3;
		gbc_matchRefsButton.gridy = 4;
		panel.add(panelButton[3], gbc_matchRefsButton);
		
		GridBagConstraints gbc_teamButton = new GridBagConstraints();
		gbc_teamButton.insets = insets;
		gbc_teamButton.gridx = 1;
		gbc_teamButton.gridy = 5;
		panel.add(panelButton[4], gbc_teamButton);
		
		GridBagConstraints gbc_managersButton = new GridBagConstraints();
		gbc_managersButton.insets = insets;
		gbc_managersButton.gridx = 1;
		gbc_managersButton.gridy = 6;
		panel.add(panelButton[5], gbc_managersButton);
		
		GridBagConstraints gbc_refereesButton = new GridBagConstraints();
		gbc_refereesButton.insets = insets;
		gbc_refereesButton.gridx = 1;
		gbc_refereesButton.gridy = 7;
		panel.add(panelButton[6], gbc_refereesButton);

		GridBagConstraints gbc_playersButton = new GridBagConstraints();
		gbc_playersButton.insets = insets;
		gbc_playersButton.gridx = 1;
		gbc_playersButton.gridy = 8;
		panel.add(panelButton[7], gbc_playersButton);
		
		GridBagConstraints gbc_assignPlayerButton = new GridBagConstraints();
		gbc_assignPlayerButton.insets = insets;
		gbc_assignPlayerButton.gridx = 3;
		gbc_assignPlayerButton.gridy = 5;
		panel.add(panelButton[8], gbc_assignPlayerButton);
		
		GridBagConstraints gbc_assignManagerButton = new GridBagConstraints();
		gbc_assignManagerButton.insets = insets;
		gbc_assignManagerButton.gridx = 3;
		gbc_assignManagerButton.gridy = 6;
		panel.add(panelButton[9], gbc_assignManagerButton);
		
		GridBagConstraints gbc_stadiumsButton = new GridBagConstraints();
		gbc_stadiumsButton.insets = insets;
		gbc_stadiumsButton.gridx = 3;
		gbc_stadiumsButton.gridy = 7;
		panel.add(panelButton[10], gbc_stadiumsButton);
		
		GridBagConstraints gbc_assignStadiumsButton = new GridBagConstraints();
		gbc_assignStadiumsButton.insets = insets;
		gbc_assignStadiumsButton.gridx = 3;
		gbc_assignStadiumsButton.gridy = 8;
		panel.add(panelButton[11], gbc_assignStadiumsButton);
		
		JLabel recMatchesLabel = new JLabel("Record A Match This Week: ");
		recMatchesLabel.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = insets;
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		panel.add(recMatchesLabel, gbc_recMatchesLabel);
		
		JList<String> matchesToRecordList = new JList(matches.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = insets;
		gbc_matchesToRecordList.gridx = 5;
		gbc_matchesToRecordList.gridy = 3;
		panel.add(matchesToRecordList, gbc_matchesToRecordList);
	}

	protected void addActionListeners() {
		panelButton[0].addActionListener(e -> { IAdminPanel.getLeagueDialog(frame); });
		panelButton[1].addActionListener(e -> { IAdminPanel.getGenFixturesDialog(frame); });
		panelButton[2].addActionListener(e -> { IAdminPanel.getSeasonDialog(frame); });
		panelButton[3].addActionListener(e -> { IAdminPanel.getAssignRefDialog(frame); });
		panelButton[4].addActionListener(e -> { IAdminPanel.getTeamDialog(frame); });
		panelButton[5].addActionListener(e -> { IAdminPanel.getManagersDialog(frame); });
		panelButton[6].addActionListener(e -> { IAdminPanel.getPlayerDialog(frame); });
		panelButton[7].addActionListener(e -> { IAdminPanel.getRefereeDialog(frame); });
		panelButton[8].addActionListener(e -> { IAdminPanel.getAssignPlayerDialog(frame); });
		panelButton[9].addActionListener(e -> { IAdminPanel.getAssignManagerDialog(frame); });
		panelButton[10].addActionListener(e -> { IAdminPanel.getStadiumDialog(frame); });
		panelButton[11].addActionListener(e -> { IAdminPanel.getAssignStadiumDialog(frame); });
	}	
}
