package gui.admin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import leagueDB.teamData;

public class teamDialog extends JDialog implements teamData {
	
	public teamDialog() { initialise(); }

	public void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(450, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Teams");
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        seasonDialogLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
        seasonDialogLayout.columnWidths = new int[] {50, 50, 100, 100, 50};
        seasonDialogLayout.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 0, 0, 50};
        getContentPane().setLayout(seasonDialogLayout);
        
        JLabel createTeamLabel = new JLabel("Create Team");
        GridBagConstraints gbc_createTeamLabel = new GridBagConstraints();
        gbc_createTeamLabel.insets = new Insets(0, 0, 5, 5);
        gbc_createTeamLabel.gridx = 1;
        gbc_createTeamLabel.gridy = 0;
        getContentPane().add(createTeamLabel, gbc_createTeamLabel);
        
        JLabel deleteTeamLabel = new JLabel("Delete Team");
        deleteTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_deleteTeamLabel = new GridBagConstraints();
        gbc_deleteTeamLabel.insets = new Insets(0, 0, 5, 5);
        gbc_deleteTeamLabel.fill = GridBagConstraints.BOTH;
        gbc_deleteTeamLabel.gridx = 3;
        gbc_deleteTeamLabel.gridy = 0;
        getContentPane().add(deleteTeamLabel, gbc_deleteTeamLabel);
        
        JLabel teamNameLabel = new JLabel("Team Name:");
        GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
        gbc_teamNameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_teamNameLabel.gridx = 1;
        gbc_teamNameLabel.gridy = 1;
        getContentPane().add(teamNameLabel, gbc_teamNameLabel);
        
        JLabel teamToRemoveLabel = new JLabel("Team:");
        GridBagConstraints gbc_teamToRemoveLabel = new GridBagConstraints();
        gbc_teamToRemoveLabel.insets = new Insets(0, 0, 5, 5);
        gbc_teamToRemoveLabel.gridx = 3;
        gbc_teamToRemoveLabel.gridy = 1;
        getContentPane().add(teamToRemoveLabel, gbc_teamToRemoveLabel);
        
        JTextField teamNameField = new JTextField();
        GridBagConstraints gbc_teamNameField = new GridBagConstraints();
        gbc_teamNameField.insets = new Insets(0, 0, 5, 5);
        gbc_teamNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_teamNameField.gridx = 1;
        gbc_teamNameField.gridy = 2;
        getContentPane().add(teamNameField, gbc_teamNameField);
        teamNameField.setColumns(10);
        
        JComboBox teamSelect = new JComboBox();
       
        GridBagConstraints gbc_teamSelect = new GridBagConstraints();
        gbc_teamSelect.insets = new Insets(0, 0, 5, 5);
        gbc_teamSelect.fill = GridBagConstraints.HORIZONTAL;
        gbc_teamSelect.gridx = 3;
        gbc_teamSelect.gridy = 2;
        getContentPane().add(teamSelect, gbc_teamSelect);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = new Insets(0, 0, 5, 5);
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 3;
        getContentPane().add(addBut, gbc_addBut);
        
        JButton delRefBut = new JButton("Delete");
        GridBagConstraints gbc_delRefBut = new GridBagConstraints();
        gbc_delRefBut.insets = new Insets(0, 0, 5, 5);
        gbc_delRefBut.gridx = 3;
        gbc_delRefBut.gridy = 3;
        getContentPane().add(delRefBut, gbc_delRefBut);
        
        addBut.addActionListener(e -> {
        	teamData.createTeam(teamNameField.getText());
        	dispose();
        });
//        
//        
//        delRefBut.addActionListener(e -> {
//        	refereeData.removeReferee(referees.get(refSelect.getSelectedIndex()));
//        	dispose();
//        });
	}
}
