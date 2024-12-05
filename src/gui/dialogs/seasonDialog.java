package gui.dialogs;
import league.Season;
import leagueDB.seasonData;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Connection;

import javax.swing.*;

import gui.JfgpWindow;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class seasonDialog extends JDialog implements seasonData {
		
    List<Season> seasons;
    List<String> seasonSelection = new ArrayList<String>();
    
    Insets insets = new Insets(0, 0, 5, 5);
    JfgpWindow frame;
    
	public seasonDialog(JfgpWindow frame) {
		this.frame = frame;
		initialise();
		}
	
	private void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(450, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Seasons");

        seasons = seasonData.getSeasons(frame.getDbConnection());
        
        for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        getContentPane().setLayout(seasonDialogLayout);
        
        JLabel addSeasonsLabel = new JLabel("Add Seasons");
        GridBagConstraints gbc_addSeasonsLabel = new GridBagConstraints();
        gbc_addSeasonsLabel.insets = insets;
        gbc_addSeasonsLabel.gridx = 1;
        gbc_addSeasonsLabel.gridy = 0;
        getContentPane().add(addSeasonsLabel, gbc_addSeasonsLabel);
        
        JLabel removeSeasonsLabel = new JLabel("Remove Seasons");
        GridBagConstraints gbc_removeSeasonsLabel = new GridBagConstraints();
        gbc_removeSeasonsLabel.insets = insets;
        gbc_removeSeasonsLabel.gridx = 3;
        gbc_removeSeasonsLabel.gridy = 0;
        getContentPane().add(removeSeasonsLabel, gbc_removeSeasonsLabel);
        
        JLabel StartYearLabel = new JLabel("Season Start Year");
        GridBagConstraints gbc_StartYearLabel = new GridBagConstraints();
        gbc_StartYearLabel.insets = insets;
        gbc_StartYearLabel.gridx = 1;
        gbc_StartYearLabel.gridy = 1;
        getContentPane().add(StartYearLabel, gbc_StartYearLabel);
        
        JLabel seasonLabel = new JLabel("Season: ");
        GridBagConstraints gbc_seasonLabel = new GridBagConstraints();
        gbc_seasonLabel.insets = insets;
        gbc_seasonLabel.gridx = 3;
        gbc_seasonLabel.gridy = 1;
        getContentPane().add(seasonLabel, gbc_seasonLabel);
        
        JTextField seasonStartField = new JTextField();
        GridBagConstraints gbc_startYearField = new GridBagConstraints();
        gbc_startYearField.insets = insets;
        gbc_startYearField.gridx = 1;
        gbc_startYearField.gridy = 2;
        getContentPane().add(seasonStartField, gbc_startYearField);
        
        JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
       
        GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
        gbc_seasonSelect.insets = insets;
        gbc_seasonSelect.gridx = 3;
        gbc_seasonSelect.gridy = 2;
        getContentPane().add(seasonSelect, gbc_seasonSelect);

        JLabel endYearLabel = new JLabel("Season End Year");
        GridBagConstraints gbc_endYearLabel = new GridBagConstraints();
        gbc_endYearLabel.insets = insets;
        gbc_endYearLabel.gridx = 1;
        gbc_endYearLabel.gridy = 3;
        getContentPane().add(endYearLabel, gbc_endYearLabel);
        
        JButton deleteSeasonBut = new JButton("Delete");
        GridBagConstraints gbc_deleteSeasonBut = new GridBagConstraints();
        gbc_deleteSeasonBut.insets = insets;
        gbc_deleteSeasonBut.gridx = 3;
        gbc_deleteSeasonBut.gridy = 3;
        getContentPane().add(deleteSeasonBut, gbc_deleteSeasonBut);
        
        JTextField seasonEndField = new JTextField();
        GridBagConstraints gbc_seasonEndField = new GridBagConstraints();
        gbc_seasonEndField.insets = insets;
        gbc_seasonEndField.gridx = 1;
        gbc_seasonEndField.gridy = 4;
        getContentPane().add(seasonEndField, gbc_seasonEndField);
        
        JButton addBut = new JButton("Add");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 5;
        getContentPane().add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	seasonData.createSeason(frame.getDbConnection(), seasonStartField.getText(), seasonEndField.getText());
        	dispose();
        });
        
        deleteSeasonBut.addActionListener(e -> {
        	seasonData.removeSeason(frame.getDbConnection(), seasons.get(seasonSelect.getSelectedIndex()));
        	dispose();
        });
	}
}