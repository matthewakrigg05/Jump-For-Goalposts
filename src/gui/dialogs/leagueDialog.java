package gui.dialogs;
import league.League;
import league.Season;
import leagueDB.seasonData;
import javax.swing.*;
import gui.panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class leagueDialog extends JDialog implements seasonData {

	private League league;
	
	List<Season> seasons;
    List<String> seasonSelection = new ArrayList<String>();
    Connection connection;
    
	public leagueDialog(Connection connection) { 
		this.connection = connection;
		initialise();
		}
	
	public void initialise() {
		setFocusable(true);
        setSize(500, 250);
        setModal(true);
        setTitle(league.getLeagueName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
		league = seasonData.getLeague(connection);
		seasons = seasonData.getSeasons(connection);
		
        for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        getContentPane().setLayout(gridBagLayout);
        
        JLabel changeLeagueName = new JLabel("Change League Name: ");
        changeLeagueName.setHorizontalAlignment(SwingConstants.CENTER);
        
        GridBagConstraints gbc_changeLeagueName = new GridBagConstraints();
        gbc_changeLeagueName.insets = new Insets(0, 0, 5, 5);
        gbc_changeLeagueName.gridx = 1;
        gbc_changeLeagueName.gridy = 2;
        getContentPane().add(changeLeagueName, gbc_changeLeagueName);
        
        JLabel currSeasonLabel = new JLabel("Set Current Season");
        GridBagConstraints gbc_currSeasonLabel = new GridBagConstraints();
        gbc_currSeasonLabel.insets = new Insets(0, 0, 5, 5);
        gbc_currSeasonLabel.gridx = 3;
        gbc_currSeasonLabel.gridy = 2;
        getContentPane().add(currSeasonLabel, gbc_currSeasonLabel);
        
        JTextArea newName = new JTextArea();
        newName.setMinimumSize(new Dimension(50, 22));
        newName.getText();
        
        GridBagConstraints gbc_newName = new GridBagConstraints();
        gbc_newName.fill = GridBagConstraints.HORIZONTAL;
        gbc_newName.anchor = GridBagConstraints.NORTH;
        gbc_newName.insets = new Insets(0, 0, 5, 5);
        gbc_newName.gridx = 1;
        gbc_newName.gridy = 3;
        getContentPane().add(newName, gbc_newName);
        
        JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 3;
        gbc_comboBox.gridy = 3;
        getContentPane().add(seasonSelect, gbc_comboBox);
        
        JButton saveButton = new JButton("Save");
        GridBagConstraints gbc_saveButton = new GridBagConstraints();
        gbc_saveButton.insets = new Insets(0, 0, 0, 5);
        gbc_saveButton.anchor = GridBagConstraints.NORTH;
        gbc_saveButton.gridx = 1;
        gbc_saveButton.gridy = 4;
        getContentPane().add(saveButton, gbc_saveButton);
        
        JButton updateButton = new JButton("Update");
        GridBagConstraints gbc_updateButton = new GridBagConstraints();
        gbc_updateButton.insets = new Insets(0, 0, 0, 5);
        gbc_updateButton.gridx = 3;
        gbc_updateButton.gridy = 4;
        getContentPane().add(updateButton, gbc_updateButton);
        
        saveButton.addActionListener(e -> {
        	seasonData.changeLeagueName(connection, newName.getText());
        	dispose();
        });
        
        updateButton.addActionListener(e -> {
        	seasonData.setCurrentSeason(connection, seasons.get(seasonSelect.getSelectedIndex()).getId());
        	dispose();
        });
	}
}
