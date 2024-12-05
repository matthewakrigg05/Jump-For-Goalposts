package gui.dialogs;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import gui.JfgpWindow;
import league.Team;
import leagueDB.teamData;

@SuppressWarnings("serial")
public class teamDialog extends JDialog implements teamData {
	
	List<Team> teams;
    List<String> teamSelection = new ArrayList<String>();
    Insets insets = new Insets(0, 0, 5, 5);
    JfgpWindow frame;
	
	public teamDialog(JfgpWindow frame) { 
		this.frame = frame;
		initialise(); 
	}

	private void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(450, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Teams");
        
        teams = teamData.getAllTeams(frame.getDbConnection());
        for(Team i : teams) { teamSelection.add(i.getName()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        getContentPane().setLayout(seasonDialogLayout);
        
        JLabel createTeamLabel = new JLabel("Create Team");
        GridBagConstraints gbc_createTeamLabel = new GridBagConstraints();
        gbc_createTeamLabel.insets = insets;
        gbc_createTeamLabel.gridx = 1;
        gbc_createTeamLabel.gridy = 0;
        getContentPane().add(createTeamLabel, gbc_createTeamLabel);
        
        JLabel deleteTeamLabel = new JLabel("Delete Team");
        deleteTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        GridBagConstraints gbc_deleteTeamLabel = new GridBagConstraints();
        gbc_deleteTeamLabel.insets = insets;
        gbc_deleteTeamLabel.gridx = 3;
        gbc_deleteTeamLabel.gridy = 0;
        getContentPane().add(deleteTeamLabel, gbc_deleteTeamLabel);
        
        JLabel teamNameLabel = new JLabel("Team Name:");
        GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
        gbc_teamNameLabel.insets = insets;
        gbc_teamNameLabel.gridx = 1;
        gbc_teamNameLabel.gridy = 1;
        getContentPane().add(teamNameLabel, gbc_teamNameLabel);
        
        JLabel teamToRemoveLabel = new JLabel("Team:");
        GridBagConstraints gbc_teamToRemoveLabel = new GridBagConstraints();
        gbc_teamToRemoveLabel.insets = insets;
        gbc_teamToRemoveLabel.gridx = 3;
        gbc_teamToRemoveLabel.gridy = 1;
        getContentPane().add(teamToRemoveLabel, gbc_teamToRemoveLabel);
        
        JTextField teamNameField = new JTextField();
        GridBagConstraints gbc_teamNameField = new GridBagConstraints();
        gbc_teamNameField.insets = insets;
        gbc_teamNameField.gridx = 1;
        gbc_teamNameField.gridy = 2;
        getContentPane().add(teamNameField, gbc_teamNameField);
        
        JComboBox teamSelect = new JComboBox(teamSelection.toArray());
       
        GridBagConstraints gbc_teamSelect = new GridBagConstraints();
        gbc_teamSelect.insets = insets;
        gbc_teamSelect.gridx = 3;
        gbc_teamSelect.gridy = 2;
        getContentPane().add(teamSelect, gbc_teamSelect);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 3;
        getContentPane().add(addBut, gbc_addBut);
        
        JButton delTeamBut = new JButton("Delete");
        GridBagConstraints gbc_delTeamBut = new GridBagConstraints();
        gbc_delTeamBut.insets = insets;
        gbc_delTeamBut.gridx = 3;
        gbc_delTeamBut.gridy = 3;
        getContentPane().add(delTeamBut, gbc_delTeamBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createTeam(frame.getDbConnection(), teamNameField.getText());
        	dispose();
        });
        
        delTeamBut.addActionListener(e -> {
        	frame.getAdminAccount().removeTeam(frame.getDbConnection(), teams.get(teamSelect.getSelectedIndex()));
        	dispose();
        });
	}
}