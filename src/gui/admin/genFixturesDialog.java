package gui.admin;
import javax.swing.*;

import league.Season;
import league.Team;
import leagueDB.matchData;
import leagueDB.seasonData;
import leagueDB.teamData;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class genFixturesDialog extends JDialog implements matchData, teamData, seasonData {
	
    List<Season> seasons = seasonData.getSeasons();
    List<String> seasonSelection = new ArrayList<String>();
    
    List<Team> teams = teamData.getAllTeams();
    List<String> teamSelection = new ArrayList<String>();

	public genFixturesDialog() {
		setResizable(false); initialise(); }

	
	public void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(760, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Generate Fixtures");
		// need: season drop down of seasons with no matches -> most recent first, add teams to season, gen fixtures button
		
		for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
	    for(Team i : teams) { teamSelection.add(i.getName()); }
	        
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{14, 100, 22, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel selectSeaeson = new JLabel("Select Season To Generate Fixtures For");
		selectSeaeson.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_selectSeaeson = new GridBagConstraints();
		gbc_selectSeaeson.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectSeaeson.insets = new Insets(0, 0, 5, 5);
		gbc_selectSeaeson.gridx = 1;
		gbc_selectSeaeson.gridy = 1;
		getContentPane().add(selectSeaeson, gbc_selectSeaeson);
		
		JLabel teamsToAdd = new JLabel("Select Teams to Include in the Season");
		teamsToAdd.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_teamsToAdd = new GridBagConstraints();
		gbc_teamsToAdd.insets = new Insets(0, 0, 5, 5);
		gbc_teamsToAdd.gridx = 3;
		gbc_teamsToAdd.gridy = 1;
		getContentPane().add(teamsToAdd, gbc_teamsToAdd);
		
		JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		getContentPane().add(seasonSelect, gbc_comboBox);
		
		JList list = new JList(teamSelection.toArray());
		
		// https://stackoverflow.com/questions/2404546/select-multiple-items-in-jlist-without-using-the-ctrl-command-key
		list.setSelectionModel(new DefaultListSelectionModel() {
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(super.isSelectedIndex(index0)) { super.removeSelectionInterval(index0, index1); }
		        else { super.addSelectionInterval(index0, index1); }
		    }
		});
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 3;
		gbc_list.gridy = 2;
		getContentPane().add(list, gbc_list);
		
		JButton genFixturesButton = new JButton("Generate Fixtures");
	
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = new Insets(0, 0, 5, 5);
		gbc_genFixturesButton.gridx = 2;
		gbc_genFixturesButton.gridy = 3;
		getContentPane().add(genFixturesButton, gbc_genFixturesButton);
		
		genFixturesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.getSelectedIndices();
				List<Team> selectedTeams = new ArrayList<Team>();
				for(int i : list.getSelectedIndices()) { selectedTeams.add(teams.get(i)); }
				matchData.createSeasonMatches(selectedTeams, seasons.get(seasonSelect.getSelectedIndex()));
				dispose();
			}
		});

	}
	
}
