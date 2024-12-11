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

import league.Team;
import leagueDB.ComputeTeamStatistics;
import leagueDB.leagueData;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class TeamsPanel extends JPanel implements ComputeTeamStatistics {

	List<String> teamSelection = new ArrayList<String>();
	List<Team> teams;
	Team selectedTeam;
	JList playerList;
	Insets insets;
	JfgpWindow frame;
	
	JLabel teamLabel;
	JLabel gamesPlayedLabel;
	JLabel goalsLabel;
	JLabel assitsLabel;
	JLabel foulsLabel;
	JLabel yellowsLabel;
	JLabel redsLabel;
	JLabel winsLabel;
	JLabel drawsLabel;
	JLabel lossesLabel;

	public TeamsPanel(JfgpWindow frame) {
		this.frame = frame;
		initialise(frame);
		}

	public void initialise(JfgpWindow frame) {
		insets = new Insets(0, 0, 10, 25);
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		addPanelComponents(this, frame);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel, JfgpWindow frame) {
		teams = new ArrayList<Team>(leagueData.getAllTeams(frame.getDbConnection()));
		for(Team team : teams) { teamSelection.add(team.getName()); }
		
		playerList = new JList(teamSelection.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = insets;
		gbc_matchesToRecordList.gridx = 1;
		gbc_matchesToRecordList.gridy = 1;
		panel.add(playerList, gbc_matchesToRecordList);
		
		JPanel playerProfile = new JPanel();
		playerProfile.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = insets;
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		
		teamLabel = new JLabel("Player: ");
		gamesPlayedLabel = new JLabel("Games Played: ");
		goalsLabel = new JLabel("Goals: ");
		assitsLabel = new JLabel("Assits: ");
		foulsLabel = new JLabel("Fouls: ");
		yellowsLabel = new JLabel("Yellow Cards: ");
		redsLabel = new JLabel("Red Cards: ");
		winsLabel = new JLabel("Wins: ");
		drawsLabel = new JLabel("Draws: ");
		lossesLabel = new JLabel("Losses: ");
		
		playerProfile.add(teamLabel);
		playerProfile.add(gamesPlayedLabel);
		playerProfile.add(goalsLabel);
		playerProfile.add(assitsLabel);
		playerProfile.add(foulsLabel);
		playerProfile.add(yellowsLabel);
		playerProfile.add(redsLabel);
		playerProfile.add(winsLabel);
		playerProfile.add(drawsLabel);
		playerProfile.add(lossesLabel);
		
		panel.add(playerProfile, gbc_recMatchesLabel);
	}
	
	public void addActionListeners() {
		
		playerList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedTeam = teams.get(playerList.getSelectedIndex());
				
				teamLabel.setText("Team: " + selectedTeam.getName());
//				gamesPlayedLabel.setText("Games Played: " + selectedTeam.getStats().getGamesPlayed());
				goalsLabel.setText("Goals: " + ComputeTeamStatistics.getTeamGoals(frame.getDbConnection(), selectedTeam));
//				assitsLabel.setText("Assits: " + selectedTeam.getStats().getAssits());
//				foulsLabel.setText("Fouls: " + selectedTeam.getStats().getFoulsCommitted());
//				yellowsLabel.setText("Yellow Cards: " + selectedTeam.getStats().getYellowCards());
//				redsLabel.setText("Red Cards: " + selectedTeam.getStats().getRedCards());
//				winsLabel.setText("Wins: " + selectedTeam.getStats().getWins());
//				drawsLabel.setText("Draws: " + selectedTeam.getStats().getDraws());
//				lossesLabel.setText("Losses: " + selectedTeam.getStats().getLosses());
			}
		});
		
	}
}
