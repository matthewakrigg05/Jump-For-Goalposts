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
import leagueDB.leagueData;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class ManagerPanel extends JPanel {

	List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));
	List<String> playerSelection = new ArrayList<String>();
	List<Player> players;
	JfgpWindow frame;
	JList playerList;
	ManagerAccount manager;
	Insets insets;
	Team team;
	
	public ManagerPanel(JfgpWindow frame, ManagerAccount manager) { 
		this.frame = frame; 
		this.manager = manager;
		this.team = leagueData.getManagerTeam(frame.getDbConnection(), leagueData.getManagerFromId(frame.getDbConnection(), manager.getId()));
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
			players = new ArrayList<Player>(leagueData.getTeamPlayers(frame.getDbConnection(), team));
			for(Player player : players) { playerSelection.add(player.getFullName()); }
		}
		
		
		
		
		playerList = new JList(playerSelection.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = insets;
		gbc_matchesToRecordList.gridx = 1;
		gbc_matchesToRecordList.gridy = 1;
		panel.add(playerList, gbc_matchesToRecordList);
	}
	

	public void addActionListeners() {
		
	}
}
