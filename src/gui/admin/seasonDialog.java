package gui.admin;
import league.League;
import league.Season;
import leagueDB.leagueData;
import leagueDB.seasonData;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.List;


public class seasonDialog extends JDialog implements seasonData {
		private List<String> seasonYears;
		
		public seasonDialog() { initialise(); }
		
		public void initialise() {
//			League league = leagueData.getLeague();
			setAlwaysOnTop(true);
			setFocusable(true);
	        setSize(450, 500);
	        setModal(true);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//	        setTitle(league.getLeagueName());
	        List<Season> seasons = seasonData.getSeasons();
	        List<String> seasonSelection = new ArrayList<String>();
	        
	        for(Season i : seasons) {
	        	seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd());
	        }
	       
	        GridBagLayout seasonDialogLayout = new GridBagLayout();
	        seasonDialogLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
	        seasonDialogLayout.columnWidths = new int[] {50, 50, 100, 100, 50};
	        seasonDialogLayout.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 50};
	        getContentPane().setLayout(seasonDialogLayout);
	        
	        JLabel addSeasonsLabel = new JLabel("Add Seasons");
	        GridBagConstraints gbc_addSeasonsLabel = new GridBagConstraints();
	        gbc_addSeasonsLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_addSeasonsLabel.gridx = 1;
	        gbc_addSeasonsLabel.gridy = 0;
	        getContentPane().add(addSeasonsLabel, gbc_addSeasonsLabel);
	        
	        JLabel removeSeasonsLabel = new JLabel("Remove Seasons");
	        GridBagConstraints gbc_removeSeasonsLabel = new GridBagConstraints();
	        gbc_removeSeasonsLabel.insets = new Insets(0, 0, 5, 0);
	        gbc_removeSeasonsLabel.fill = GridBagConstraints.BOTH;
	        gbc_removeSeasonsLabel.gridx = 3;
	        gbc_removeSeasonsLabel.gridy = 0;
	        getContentPane().add(removeSeasonsLabel, gbc_removeSeasonsLabel);
	        
	        JLabel StartYearLabel = new JLabel("Season Start Year");
	        GridBagConstraints gbc_StartYearLabel = new GridBagConstraints();
	        gbc_StartYearLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_StartYearLabel.gridx = 1;
	        gbc_StartYearLabel.gridy = 1;
	        getContentPane().add(StartYearLabel, gbc_StartYearLabel);
	        
	        JLabel seasonLabel = new JLabel("Season: ");
	        GridBagConstraints gbc_seasonLabel = new GridBagConstraints();
	        gbc_seasonLabel.insets = new Insets(0, 0, 5, 0);
	        gbc_seasonLabel.gridx = 3;
	        gbc_seasonLabel.gridy = 1;
	        getContentPane().add(seasonLabel, gbc_seasonLabel);
	        
	        JTextField seasonStartField = new JTextField();
	        GridBagConstraints gbc_startYearField = new GridBagConstraints();
	        gbc_startYearField.insets = new Insets(0, 0, 5, 5);
	        gbc_startYearField.fill = GridBagConstraints.HORIZONTAL;
	        gbc_startYearField.gridx = 1;
	        gbc_startYearField.gridy = 2;
	        getContentPane().add(seasonStartField, gbc_startYearField);
	        seasonStartField.setColumns(10);
	        
	        JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
	       
	        GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
	        gbc_seasonSelect.insets = new Insets(0, 0, 5, 0);
	        gbc_seasonSelect.fill = GridBagConstraints.HORIZONTAL;
	        gbc_seasonSelect.gridx = 3;
	        gbc_seasonSelect.gridy = 2;
	        getContentPane().add(seasonSelect, gbc_seasonSelect);

	        JLabel endYearLabel = new JLabel("Season End Year");
	        GridBagConstraints gbc_endYearLabel = new GridBagConstraints();
	        gbc_endYearLabel.insets = new Insets(0, 0, 5, 5);
	        gbc_endYearLabel.gridx = 1;
	        gbc_endYearLabel.gridy = 3;
	        getContentPane().add(endYearLabel, gbc_endYearLabel);
	        
	        JButton deleteSeasonBut = new JButton("Delete");
	        GridBagConstraints gbc_deleteSeasonBut = new GridBagConstraints();
	        gbc_deleteSeasonBut.insets = new Insets(0, 0, 5, 0);
	        gbc_deleteSeasonBut.gridx = 3;
	        gbc_deleteSeasonBut.gridy = 3;
	        getContentPane().add(deleteSeasonBut, gbc_deleteSeasonBut);
	        
	        JTextField seasonEndField = new JTextField();
	        GridBagConstraints gbc_seasonEndField = new GridBagConstraints();
	        gbc_seasonEndField.insets = new Insets(0, 0, 5, 5);
	        gbc_seasonEndField.fill = GridBagConstraints.HORIZONTAL;
	        gbc_seasonEndField.gridx = 1;
	        gbc_seasonEndField.gridy = 4;
	        getContentPane().add(seasonEndField, gbc_seasonEndField);
	        seasonEndField.setColumns(10);
	        
	        JButton addBut = new JButton("Add");
	        GridBagConstraints gbc_addBut = new GridBagConstraints();
	        gbc_addBut.insets = new Insets(0, 0, 5, 5);
	        gbc_addBut.gridx = 1;
	        gbc_addBut.gridy = 5;
	        getContentPane().add(addBut, gbc_addBut);
	        
	        addBut.addActionListener(e -> {
	        	seasonData.createSeason(seasonStartField.getText(), seasonEndField.getText());
	        	dispose();
	        });
	        
	        
	        deleteSeasonBut.addActionListener(e -> {
	        	seasonData.removeSeason(seasons.get(seasonSelect.getSelectedIndex()));
	        	dispose();
	        });
	        
	        // need league name, season year, create new seasons - start date, teams in this season
	        // dont allow duplicate teams, season is a set of team ids
	        // edit button, save button
		}
	}
