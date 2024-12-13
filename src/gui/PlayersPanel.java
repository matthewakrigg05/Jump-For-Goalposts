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
import leagueMembers.Player;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel {
	
	List<String> playerSelection = new ArrayList<String>();
	List<Player> players;
	Player selectedPlayer;

	Insets insets;
	JfgpWindow frame;
	
	JList playerList;
	JLabel playerNameLabel;
	JLabel teamLabel;
	JLabel gamesPlayedLabel;
	JLabel goalsLabel;
	JLabel assitsLabel;
	JLabel foulsLabel;
	JLabel yellowsLabel;
	JLabel redsLabel;

	public PlayersPanel(JfgpWindow frame) {
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
		players = new ArrayList<Player>(frame.getDb().getAllPlayers());
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
		goalsLabel = new JLabel("Goals: ");
		assitsLabel = new JLabel("Assits: ");
		foulsLabel = new JLabel("Fouls: ");
		yellowsLabel = new JLabel("Yellow Cards: ");
		redsLabel = new JLabel("Red Cards: ");
		
		playerProfile.add(playerNameLabel);
		playerProfile.add(teamLabel);
		playerProfile.add(goalsLabel);
		playerProfile.add(assitsLabel);
		playerProfile.add(foulsLabel);
		playerProfile.add(yellowsLabel);
		playerProfile.add(redsLabel);

		panel.add(playerProfile, gbc_recMatchesLabel);
	}
	
	public void addActionListeners() {
		
		playerList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedPlayer = players.get(playerList.getSelectedIndex());
				playerNameLabel.setText("Player: " + selectedPlayer.getFullName());
				teamLabel.setText("Plays for: " + selectedPlayer.getPlayerTeam(frame.getDb().getConnection()));
				goalsLabel.setText("Goals: " + selectedPlayer.getGoals(frame.getDb().getConnection()));
				assitsLabel.setText("Assits: " + selectedPlayer.getAssists(frame.getDb().getConnection()));
				foulsLabel.setText("Fouls: " + selectedPlayer.getFouls(frame.getDb().getConnection()));
				yellowsLabel.setText("Yellow Cards: " + selectedPlayer.getYellows(frame.getDb().getConnection()));
				redsLabel.setText("Red Cards: " + selectedPlayer.getReds(frame.getDb().getConnection()));
			}
		});
	}
}
