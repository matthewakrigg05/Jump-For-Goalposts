package gui.dialogs;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import league.Team;
import leagueDB.playerData;
import leagueMembers.*;

@SuppressWarnings("serial")
public class playersDialog extends JDialog implements playerData {
	
	Insets insets;
	
	List<Team> teams;
    List<String> teamSelection = new ArrayList<String>();
    
    String[] positions = {"Attacker", "Midfielder", "Defender", "Goalkeeper"};
    List<Attacker> attackers;
    List<Midfielder> midfielders;
    List<Defender> defenders;
    List<Goalkeeper> goalkeepers;
   
    List<Integer> players = new ArrayList<Integer>();
    List<String> playerSelection = new ArrayList<String>();
    
    public playersDialog() { 
    	initialise();
    	insets = new Insets(0, 0, 5, 5);
    	}
		
	public void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
	    setSize(450, 500);
	    setModal(true);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setTitle("Players");

        attackers = playerData.getAllAttackers(); 
        midfielders = playerData.getAllMidfielders();
        defenders = playerData.getAllDefenders();
        goalkeepers = playerData.getAllGoalkeepers();
        
        for(Attacker i : attackers) { players.add(i.getId());  playerSelection.add(i.getFullName()); }
        for(Midfielder i : midfielders) { players.add(i.getId()); playerSelection.add(i.getFullName()); }
        for(Defender i : defenders) { players.add(i.getId()); playerSelection.add(i.getFullName()); }
        for(Goalkeeper i : goalkeepers) { players.add(i.getId()); playerSelection.add(i.getFullName()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        getContentPane().setLayout(seasonDialogLayout);
        
        JLabel createPlayerLabel = new JLabel("Create Player");
        GridBagConstraints gbc_createPlayerLabel = new GridBagConstraints();
        gbc_createPlayerLabel.insets = insets;
        gbc_createPlayerLabel.gridx = 1;
        gbc_createPlayerLabel.gridy = 0;
        getContentPane().add(createPlayerLabel, gbc_createPlayerLabel);
        
        JLabel deletePlayerLabel = new JLabel("Delete Player");
        GridBagConstraints gbc_deletePlayerLabel = new GridBagConstraints();
        gbc_deletePlayerLabel.insets = insets;
        gbc_deletePlayerLabel.gridx = 3;
        gbc_deletePlayerLabel.gridy = 0;
        getContentPane().add(deletePlayerLabel, gbc_deletePlayerLabel);
        
        JLabel fNameLabel = new JLabel("First Name: ");
        GridBagConstraints gbc_fNameLabel = new GridBagConstraints();
        gbc_fNameLabel.insets = insets;
        gbc_fNameLabel.gridx = 1;
        gbc_fNameLabel.gridy = 1;
        getContentPane().add(fNameLabel, gbc_fNameLabel);
        
        JLabel playerToRemoveLabel = new JLabel("Player:");
        GridBagConstraints gbc_playerToRemoveLabel = new GridBagConstraints();
        gbc_playerToRemoveLabel.insets = insets;
        gbc_playerToRemoveLabel.gridx = 3;
        gbc_playerToRemoveLabel.gridy = 1;
        getContentPane().add(playerToRemoveLabel, gbc_playerToRemoveLabel);
        
        JTextField firstNameField = new JTextField();
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        getContentPane().add(firstNameField, gbc_fNameFileld);
        
        JComboBox playerSelect = new JComboBox(playerSelection.toArray());
	       
        GridBagConstraints gbc_playerSelect = new GridBagConstraints();
        gbc_playerSelect.insets = insets;
        gbc_playerSelect.gridx = 3;
        gbc_playerSelect.gridy = 2;
        getContentPane().add(playerSelect, gbc_playerSelect);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = insets;
        gbc_lastNameLabel.gridx = 1;
        gbc_lastNameLabel.gridy = 3;
        getContentPane().add(lastNameLabel, gbc_lastNameLabel);
        
        JButton delPlayerBut = new JButton("Delete");
        GridBagConstraints gbc_delPlayerBut = new GridBagConstraints();
        gbc_delPlayerBut.insets = insets;
        gbc_delPlayerBut.gridx = 3;
        gbc_delPlayerBut.gridy = 3;
        getContentPane().add(delPlayerBut, gbc_delPlayerBut);
        
        JTextField lastNameField = new JTextField();
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = insets;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 4;
        getContentPane().add(lastNameField, gbc_lastNameField);
        
        JLabel posSelectLabel = new JLabel("Position:");
        GridBagConstraints gbc_posSelectLabel = new GridBagConstraints();
        gbc_posSelectLabel.insets = insets;
        gbc_posSelectLabel.gridx = 1;
        gbc_posSelectLabel.gridy = 5;
        getContentPane().add(posSelectLabel, gbc_posSelectLabel);
        
        JComboBox positionSelect = new JComboBox(positions);
        GridBagConstraints gbc_positionSelect = new GridBagConstraints();
        gbc_positionSelect.insets = insets;
        gbc_positionSelect.fill = GridBagConstraints.HORIZONTAL;
        gbc_positionSelect.gridx = 1;
        gbc_positionSelect.gridy = 6;
        getContentPane().add(positionSelect, gbc_positionSelect);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        getContentPane().add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	playerData.createPlayer(firstNameField.getText(), lastNameField.getText(), positions[positionSelect.getSelectedIndex()].toLowerCase());
        	dispose();
        });
        
        delPlayerBut.addActionListener(e -> {
        	playerData.removePlayer(players.get(playerSelect.getSelectedIndex()));
        	dispose();
        });
	}
}