package gui.dialogs;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import accounts.AdminAccount;
import gui.JfgpWindow;
import leagueDB.managersData;
import leagueMembers.Manager;

@SuppressWarnings("serial")
public class managersDialog extends JDialog implements managersData {

	List<Manager> managers;
    List<String> managerSelection = new ArrayList<String>();
    Insets insets = new Insets(0, 0, 5, 5);
    JfgpWindow frame;
    
	public managersDialog(JfgpWindow frame) { 
		this.frame = frame;
		initialise();
        }
	
	private void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(450, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Managers");
        
        managers = managersData.getAllManagers();

        for(Manager i : managers) { managerSelection.add(i.getFullName()); }
       
        GridBagLayout managerDialogLayout = new GridBagLayout();
        getContentPane().setLayout(managerDialogLayout);
        
        JLabel createManagerLabel = new JLabel("Create Manager");
        GridBagConstraints gbc_createManagerLabel = new GridBagConstraints();
        gbc_createManagerLabel.insets = insets;
        gbc_createManagerLabel.gridx = 1;
        gbc_createManagerLabel.gridy = 0;
        getContentPane().add(createManagerLabel, gbc_createManagerLabel);
        
        JLabel deleteManagerLabel = new JLabel("Delete Manager");
        GridBagConstraints gbc_deleteManagerLabel = new GridBagConstraints();
        gbc_deleteManagerLabel.insets = insets;
        gbc_deleteManagerLabel.gridx = 3;
        gbc_deleteManagerLabel.gridy = 0;
        getContentPane().add(deleteManagerLabel, gbc_deleteManagerLabel);
        
        JLabel fNameLabel = new JLabel("First Name: ");
        GridBagConstraints gbc_fNameLabel = new GridBagConstraints();
        gbc_fNameLabel.insets = insets;
        gbc_fNameLabel.gridx = 1;
        gbc_fNameLabel.gridy = 1;
        getContentPane().add(fNameLabel, gbc_fNameLabel);
        
        JLabel managerToRemoveLabel = new JLabel("Manager:");
        GridBagConstraints gbc_managerToRemoveLabel = new GridBagConstraints();
        gbc_managerToRemoveLabel.insets = insets;
        gbc_managerToRemoveLabel.gridx = 3;
        gbc_managerToRemoveLabel.gridy = 1;
        getContentPane().add(managerToRemoveLabel, gbc_managerToRemoveLabel);
        
        JTextField firstNameField = new JTextField();
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        getContentPane().add(firstNameField, gbc_fNameFileld);
        
        JComboBox refSelect = new JComboBox(managerSelection.toArray());
       
        GridBagConstraints gbc_refSelect = new GridBagConstraints();
        gbc_refSelect.insets = insets;
        gbc_refSelect.gridx = 3;
        gbc_refSelect.gridy = 2;
        getContentPane().add(refSelect, gbc_refSelect);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = insets;
        gbc_lastNameLabel.gridx = 1;
        gbc_lastNameLabel.gridy = 3;
        getContentPane().add(lastNameLabel, gbc_lastNameLabel);
        
        JButton delManBut = new JButton("Delete");
        GridBagConstraints gbc_delManBut = new GridBagConstraints();
        gbc_delManBut.insets = insets;
        gbc_delManBut.gridx = 3;
        gbc_delManBut.gridy = 3;
        getContentPane().add(delManBut, gbc_delManBut);
        
        JTextField lastNameField = new JTextField();
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = insets;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 4;
        getContentPane().add(lastNameField, gbc_lastNameField);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = new Insets(0, 0, 0, 5);
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        getContentPane().add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createManagerAccount(frame.getDbConnection(), 
        			firstNameField.getText(), firstNameField.getText());
        	dispose();
        });
        
        delManBut.addActionListener(e -> {
        	frame.getAdminAccount().removeManager(frame.getDbConnection(), 
        			managers.get(refSelect.getSelectedIndex()));
        	dispose();
        });
	}	        
}