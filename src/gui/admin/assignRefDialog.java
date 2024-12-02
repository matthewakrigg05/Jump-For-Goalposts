package gui.admin;
import javax.swing.*;
import leagueDB.matchData;
import leagueDB.refereeData;
import leagueMembers.Referee;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class assignRefDialog extends JDialog implements matchData, refereeData {
	
	List<Referee> referees;
    List<String> refSelection = new ArrayList<String>();
	
	public assignRefDialog() { initialise(); }
		
	public  void initialise() {
		referees = refereeData.getAllReferees();
        for(Referee i : referees) { refSelection.add(i.getFullName()); }
        
		// check whether ref is already assigned to that gameweek - then offer confirmation option to the user
		// need a matchweek drop down, matches in matchweek drop down, refs can only be set for matches in current season
		// only show the next 5 game weeks
		
		// current season by default
        
        GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		
		JLabel refSelectLabel = new JLabel("Select a Referee");
		GridBagConstraints gbc_refSelectLabel = new GridBagConstraints();
		gbc_refSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_refSelectLabel.gridx = 0;
		gbc_refSelectLabel.gridy = 0;
		getContentPane().add(refSelectLabel, gbc_refSelectLabel);
		
		JComboBox refSelect = new JComboBox(refSelection.toArray());
		GridBagConstraints gbc_refSelect = new GridBagConstraints();
		gbc_refSelect.insets = new Insets(0, 0, 5, 0);
		gbc_refSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_refSelect.gridx = 0;
		gbc_refSelect.gridy = 1;
		getContentPane().add(refSelect, gbc_refSelect);
		
		JLabel matchSelectLabel = new JLabel("Select match to assign to");
		GridBagConstraints gbc_matchSelectLabel = new GridBagConstraints();
		gbc_matchSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelectLabel.gridx = 0;
		gbc_matchSelectLabel.gridy = 2;
		getContentPane().add(matchSelectLabel, gbc_matchSelectLabel);
		
		JComboBox matchSelect = new JComboBox();
		GridBagConstraints gbc_matchSelect = new GridBagConstraints();
		gbc_matchSelect.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_matchSelect.gridx = 0;
		gbc_matchSelect.gridy = 3;
		getContentPane().add(matchSelect, gbc_matchSelect);
		
		JButton confirmationButton = new JButton("Confirm");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		getContentPane().add(confirmationButton, gbc_confirmationButton);

	}
}
