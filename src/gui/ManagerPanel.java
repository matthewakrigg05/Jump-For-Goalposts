package gui;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import accounts.ManagerAccount;
import league.Team;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class ManagerPanel extends JPanel {

	List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));
	List<String> playerSelection = new ArrayList<String>();
	List<Player> players;
	JfgpWindow frame;
	JList playerList;
	JLabel shirtNumLabel;
	JTextArea shirtNum;
	JButton assign;
	ManagerAccount manager;
	Insets insets;
	Team team;
	
	public ManagerPanel(JfgpWindow frame, ManagerAccount manager) { 
		this.frame = frame; 
		this.manager = manager;
		this.team = manager.getManager(frame.getDb().getConnection()).getManagerTeam(frame.getDb().getConnection());
		initialise(); }
	

	public void initialise() {
		insets = new Insets(0, 0, 10, 25);
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JButton[] panelButton = new JButton[managerButtons.size()];
		addPanelComponents(this);
		addActionListeners();
	}
	
	public void addPanelComponents(JPanel panel) {
		// need to be able to pick a player in their team and change their shirt number, have a squad lineup and view next 5 games
		
		if (team != null){
			players = new ArrayList<Player>(team.getTeamPlayers(frame.getDb().getConnection()));
			for(Player player : players) { playerSelection.add(player.getFullName()); }
		}
		
		playerList = new JList(playerSelection.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = insets;
		gbc_matchesToRecordList.gridx = 1;
		gbc_matchesToRecordList.gridy = 1;
		panel.add(playerList, gbc_matchesToRecordList);
		
		shirtNumLabel = new JLabel("Assign a shirt number to selected player: ");
		
		shirtNum = new JTextArea();
		
		assign = new JButton("Assign");
	}
	
	public void addActionListeners() {
		
		assign.addActionListener(e -> {
			manager.assignShirtNum(frame.getDb().getConnection(), players.get(playerList.getSelectedIndex()), Integer.parseInt(shirtNum.getText()));
		});
	}
}
