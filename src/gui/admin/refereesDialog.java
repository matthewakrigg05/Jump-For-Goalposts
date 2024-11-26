package gui.admin;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import leagueDB.refereeData;
import leagueMembers.Referee;

@SuppressWarnings("serial")
public class refereesDialog extends JDialog implements refereeData {

	List<Referee> referees;
    List<String> refSelection = new ArrayList<String>();
    
    private JTextField cityField;
    
		public refereesDialog() { initialise(); }
		
		public void initialise() {
			setAlwaysOnTop(true);
			setFocusable(true);
	        setSize(450, 500);
	        setModal(true);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setTitle("Referees");

	        referees = refereeData.getAllReferees();
	        for(Referee i : referees) { refSelection.add(i.getFullName()); }
	       
	        GridBagLayout seasonDialogLayout = new GridBagLayout();
	        seasonDialogLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
	        seasonDialogLayout.columnWidths = new int[] {50, 50, 100, 100, 50};
	        seasonDialogLayout.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 0, 0, 50};
	        getContentPane().setLayout(seasonDialogLayout);
	        
	        JLabel createRefereeLabel = new JLabel("Create Referees");
	        GridBagConstraints gbc_createRefereeLabel = new GridBagConstraints();
	        gbc_createRefereeLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_createRefereeLabel.gridx = 1;
	        gbc_createRefereeLabel.gridy = 0;
	        getContentPane().add(createRefereeLabel, gbc_createRefereeLabel);
	        
	        JLabel deleteRefereeLabel = new JLabel("Delete Referee");
	        GridBagConstraints gbc_deleteRefereeLabel = new GridBagConstraints();
	        gbc_deleteRefereeLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_deleteRefereeLabel.fill = GridBagConstraints.BOTH;
	        gbc_deleteRefereeLabel.gridx = 3;
	        gbc_deleteRefereeLabel.gridy = 0;
	        getContentPane().add(deleteRefereeLabel, gbc_deleteRefereeLabel);
	        
	        JLabel fNameLabel = new JLabel("First Name: ");
	        GridBagConstraints gbc_fNameLabel = new GridBagConstraints();
	        gbc_fNameLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_fNameLabel.gridx = 1;
	        gbc_fNameLabel.gridy = 1;
	        getContentPane().add(fNameLabel, gbc_fNameLabel);
	        
	        JLabel refToRemoveLabel = new JLabel("Referee:");
	        GridBagConstraints gbc_refToRemoveLabel = new GridBagConstraints();
	        gbc_refToRemoveLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_refToRemoveLabel.gridx = 3;
	        gbc_refToRemoveLabel.gridy = 1;
	        getContentPane().add(refToRemoveLabel, gbc_refToRemoveLabel);
	        
	        JTextField firstNameField = new JTextField();
	        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
	        gbc_fNameFileld.insets = new Insets(0, 0, 5, 5);
	        gbc_fNameFileld.fill = GridBagConstraints.HORIZONTAL;
	        gbc_fNameFileld.gridx = 1;
	        gbc_fNameFileld.gridy = 2;
	        getContentPane().add(firstNameField, gbc_fNameFileld);
	        firstNameField.setColumns(10);
	        
	        JComboBox refSelect = new JComboBox(refSelection.toArray());
	       
	        GridBagConstraints gbc_refSelect = new GridBagConstraints();
	        gbc_refSelect.insets = new Insets(0, 0, 5, 0);
	        gbc_refSelect.fill = GridBagConstraints.HORIZONTAL;
	        gbc_refSelect.gridx = 3;
	        gbc_refSelect.gridy = 2;
	        getContentPane().add(refSelect, gbc_refSelect);

	        JLabel lastNameLabel = new JLabel("Last Name: ");
	        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
	        gbc_lastNameLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_lastNameLabel.gridx = 1;
	        gbc_lastNameLabel.gridy = 3;
	        getContentPane().add(lastNameLabel, gbc_lastNameLabel);
	        
	        JButton delRefBut = new JButton("Delete");
	        GridBagConstraints gbc_delRefBut = new GridBagConstraints();
	        gbc_delRefBut.insets = new Insets(0, 0, 5, 5);
	        gbc_delRefBut.gridx = 3;
	        gbc_delRefBut.gridy = 3;
	        getContentPane().add(delRefBut, gbc_delRefBut);
	        
	        JTextField lastNameField = new JTextField();
	        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
	        gbc_lastNameField.insets = new Insets(0, 0, 5, 5);
	        gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
	        gbc_lastNameField.gridx = 1;
	        gbc_lastNameField.gridy = 4;
	        getContentPane().add(lastNameField, gbc_lastNameField);
	        lastNameField.setColumns(10);
	        
	        JLabel cityLabel = new JLabel("City: ");
	        GridBagConstraints gbc_cityLabel = new GridBagConstraints();
	        gbc_cityLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_cityLabel.gridx = 1;
	        gbc_cityLabel.gridy = 5;
	        getContentPane().add(cityLabel, gbc_cityLabel);
	        
	        cityField = new JTextField();
	        GridBagConstraints gbc_cityField = new GridBagConstraints();
	        gbc_cityField.insets = new Insets(0, 0, 5, 5);
	        gbc_cityField.fill = GridBagConstraints.HORIZONTAL;
	        gbc_cityField.gridx = 1;
	        gbc_cityField.gridy = 6;
	        getContentPane().add(cityField, gbc_cityField);
	        cityField.setColumns(10);
	        
	        JButton addBut = new JButton("Create");
	        GridBagConstraints gbc_addBut = new GridBagConstraints();
	        gbc_addBut.insets = new Insets(0, 0, 0, 5);
	        gbc_addBut.gridx = 1;
	        gbc_addBut.gridy = 8;
	        getContentPane().add(addBut, gbc_addBut);
	        
	        addBut.addActionListener(e -> {
	        	refereeData.createRefereeAccount(firstNameField.getText(), firstNameField.getText(), cityField.getText());
	        	dispose();
	        });
	        
	        
	        delRefBut.addActionListener(e -> {
	        	refereeData.removeReferee(referees.get(refSelect.getSelectedIndex()));
	        	dispose();
	        });
		}	        
}
