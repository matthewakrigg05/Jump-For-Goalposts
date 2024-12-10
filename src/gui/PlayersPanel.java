package gui;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import leagueDB.leagueData;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel implements leagueData {
	
	List<String> playerSelection = new ArrayList<String>();
	JfgpWindow frame;

	public PlayersPanel(JfgpWindow frame) {
		this.frame = frame;
		initialise(frame);
		}

	public void initialise(JfgpWindow frame) {
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		addPanelComponents(this, frame);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel, JfgpWindow frame) {
		List<Player> players = new ArrayList<Player>(leagueData.getAllPlayers(frame.getDbConnection()));
		for(Player player : players) { playerSelection.add(player.getFullName()); }
		
		JLabel label = new JLabel("test");
		label.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = getInsets();
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		panel.add(label, gbc_recMatchesLabel);
		
		JList playerList = new JList(playerSelection.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = getInsets();
		gbc_matchesToRecordList.gridx = 5;
		gbc_matchesToRecordList.gridy = 3;
		panel.add(playerList, gbc_matchesToRecordList);
		
	}
	
	public void addActionListeners() {
		
	}
}
