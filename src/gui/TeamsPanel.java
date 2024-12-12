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
import league.Team;

@SuppressWarnings("serial")
public class TeamsPanel extends JPanel {

	List<String> teamSelection = new ArrayList<String>();
	List<Team> teams;
	Team selectedTeam;
	JList playerList;
	Insets insets;
	JfgpWindow frame;
	
	JLabel teamLabel;
	JLabel gamesPlayedLabel;
	JLabel goalsLabel;
	JLabel assistsLabel;
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
		teams = new ArrayList<Team>(frame.getDb().getAllTeams());
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
		
		teamLabel = new JLabel("Team: ");
		gamesPlayedLabel = new JLabel("Games Played: ");
		goalsLabel = new JLabel("Goals: ");
		assistsLabel = new JLabel("Assists: ");
		foulsLabel = new JLabel("Fouls: ");
		yellowsLabel = new JLabel("Yellow Cards: ");
		redsLabel = new JLabel("Red Cards: ");
		winsLabel = new JLabel("Wins: ");
		drawsLabel = new JLabel("Draws: ");
		lossesLabel = new JLabel("Losses: ");
		
		playerProfile.add(teamLabel);
		playerProfile.add(gamesPlayedLabel);
		playerProfile.add(goalsLabel);
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
				gamesPlayedLabel.setText("Games Played: " + selectedTeam.getGamesPlayed(frame.getDb().getConnection()));
				goalsLabel.setText("Goals: " + selectedTeam.getGoals(frame.getDb().getConnection()));
				assistsLabel.setText("Assits: " + selectedTeam.getAssists(frame.getDb().getConnection()));
				foulsLabel.setText("Fouls: " + selectedTeam.getFouls(frame.getDb().getConnection()));
				yellowsLabel.setText("Yellow Cards: " + selectedTeam.getYellows(frame.getDb().getConnection()));
				redsLabel.setText("Red Cards: " + selectedTeam.getReds(frame.getDb().getConnection()));
				winsLabel.setText("Wins: " + selectedTeam.getTeamWins(frame.getDb().getConnection()));
				drawsLabel.setText("Draws: " + selectedTeam.getTeamDraws(frame.getDb().getConnection()));
				lossesLabel.setText("Losses: " + selectedTeam.getTeamLosses(frame.getDb().getConnection()));
			}
		});
		
	}
}
