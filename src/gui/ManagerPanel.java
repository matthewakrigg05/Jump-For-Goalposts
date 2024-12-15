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
public class ManagerPanel extends JPanel implements IPanel {

	List<String> managerButtons = new ArrayList<String>(List.of("Assign Player Shirt Numbers", "View My Upcoming Fixtures", "Update Current Lineup"));
	List<String> playerSelection = new ArrayList<String>();
	List<Player> players;
	ManagerAccount manager;
	Team team;
	
	JfgpWindow frame;
	Insets insets;
	
	JList playerList;
	JLabel shirtNumLabel;
	JTextArea shirtNum;
	JButton assign;
	
	public ManagerPanel(JfgpWindow frame, ManagerAccount manager) { 
		this.frame = frame; 
		this.manager = manager;
		this.team = manager.getManager(frame.getDb().getConnection()).getManagerTeam(frame.getDb().getConnection());
		initialise(); }
	
	@Override
	public void initialise() {
		insets = new Insets(0, 0, 10, 25);
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		addPanelComponents(this);
		addActionListeners();
	}
	
	// Allows the manager to assign a shirt number to player in their team
	@Override
	public void addPanelComponents(JPanel panel) {
		
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
		shirtNum.setColumns(10);
		
		assign = new JButton("Assign");
		
		panel.add(shirtNumLabel);
		panel.add(shirtNum);
		panel.add(assign);
	}
	
	@Override
	public void addActionListeners() {
		assign.addActionListener(e -> {
			
			try 
			{ 
				Integer.parseInt(shirtNum.getText()); 
				manager.assignShirtNum(frame.getDb().getConnection(), players.get(playerList.getSelectedIndex()), Integer.parseInt(shirtNum.getText()));
				JOptionPane.showMessageDialog(frame, "Player Shirt number assigned!");
			}  
			catch (NumberFormatException notInt)  
			{ 
				JOptionPane.showMessageDialog(frame, "Please make sure the shirt number is a valid integer!");
			} 
		});
	}
}
