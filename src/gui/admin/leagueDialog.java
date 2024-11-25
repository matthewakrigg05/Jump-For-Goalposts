package gui.admin;
import league.League;
import leagueDB.JFGPdb;
import leagueDB.leagueData;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class leagueDialog extends JDialog implements leagueData {

	private League league;
	private League league2;
	
	public leagueDialog() {
		initialise();
	}
	
	public void initialise() {
		league = leagueData.getLeague(new JFGPdb());
        
		setFocusable(true);
        setSize(650, 375);
        setTitle(league.getLeagueName());
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());

        
        JLabel lblNewLabel = new JLabel(league.getLeagueName());
        lblNewLabel.setBounds(0, 0, 45, 13);
        getContentPane().add(lblNewLabel);
        
        JLabel changeLeagueName = new JLabel("Change League Name: ");
        JTextArea newName = new JTextArea();
        JButton saveButton = new JButton("Save");
        
        getContentPane().add(changeLeagueName);
        getContentPane().add(newName);
        getContentPane().add(saveButton);
        
        saveButton.addActionListener(e -> {
        	leagueData.changeLeagueName(new JFGPdb(), newName.getText());
        	newName.getText();
        	dispose();
        });
     
        
        // need league name, edit button, save button
        // league stores all teams that ever featured in this league, not just ones in current season
	}
}
