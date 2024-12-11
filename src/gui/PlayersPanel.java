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

import leagueDB.ComputePlayerStatistics;
import leagueDB.leagueData;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel implements leagueData, ComputePlayerStatistics {
	
	List<String> playerSelection = new ArrayList<String>();
	List<Player> players;
	Player selectedPlayer;
	JList playerList;
	Insets insets;
	JfgpWindow frame;
	
	JLabel playerNameLabel;
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

	public PlayersPanel(JfgpWindow frame) {
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
		players = new ArrayList<Player>(leagueData.getAllPlayers(frame.getDbConnection()));
		for(Player player : players) { playerSelection.add(player.getFullName()); }
		
		playerList = new JList(playerSelection.toArray());
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
		
		playerNameLabel = new JLabel("Player: ");
		teamLabel = new JLabel("Plays for: ");
		gamesPlayedLabel = new JLabel("Games Played: ");
		goalsLabel = new JLabel("Goals: ");
		assitsLabel = new JLabel("Assits: ");
		foulsLabel = new JLabel("Fouls: ");
		yellowsLabel = new JLabel("Yellow Cards: ");
		redsLabel = new JLabel("Red Cards: ");
		winsLabel = new JLabel("Wins: ");
		drawsLabel = new JLabel("Draws: ");
		lossesLabel = new JLabel("Losses: ");
		
		playerProfile.add(playerNameLabel);
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
				selectedPlayer = players.get(playerList.getSelectedIndex());
				
				playerNameLabel.setText("Player: " + selectedPlayer.getFullName());
				teamLabel.setText("Plays for: ");
				gamesPlayedLabel.setText("Games Played: ");
				goalsLabel.setText("Goals: " + ComputePlayerStatistics.getPlayerGoals(frame.getDbConnection(), selectedPlayer));
//				assitsLabel.setText("Assits: " + selectedPlayer.getStats().getAssits());
//				foulsLabel.setText("Fouls: " + selectedPlayer.getStats().getFoulsCommitted());
//				yellowsLabel.setText("Yellow Cards: " + selectedPlayer.getStats().getYellowCards());
//				redsLabel.setText("Red Cards: " + selectedPlayer.getStats().getRedCards());
//				winsLabel.setText("Wins: " + selectedPlayer.getStats().getWins());
//				drawsLabel.setText("Draws: " + selectedPlayer.getStats().getDraws());
//				lossesLabel.setText("Losses: " + selectedPlayer.getStats().getLosses());
			}
		});
		
	}
}
