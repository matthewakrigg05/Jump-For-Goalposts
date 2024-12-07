package gui.dialogs;
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

@SuppressWarnings("serial")
public class genFixturesDialog extends JDialog implements matchData, teamData, seasonData {
	
    List<Season> seasons = seasonData.getSeasons();
    List<String> seasonSelection = new ArrayList<String>();
    
    List<Team> teams = teamData.getAllTeams();
    List<String> teamSelection = new ArrayList<String>();

	public genFixturesDialog() { initialise(); }

	public void initialise() {
		setResizable(false);
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(760, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Generate Fixtures");
		
		for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
	    for(Team i : teams) { teamSelection.add(i.getName()); }
	        
		GridBagLayout gridBagLayout = new GridBagLayout();
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
		GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
		gbc_seasonSelect.insets = new Insets(0, 0, 5, 5);
		gbc_seasonSelect.anchor = GridBagConstraints.NORTH;
		gbc_seasonSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_seasonSelect.gridx = 1;
		gbc_seasonSelect.gridy = 2;
		getContentPane().add(seasonSelect, gbc_seasonSelect);
		
		JList teamSelectionList = new JList(teamSelection.toArray());
		
		// https://stackoverflow.com/questions/2404546/select-multiple-items-in-jlist-without-using-the-ctrl-command-key
		teamSelectionList.setSelectionModel(new DefaultListSelectionModel() {
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
		getContentPane().add(teamSelectionList, gbc_list);
		
		JButton genFixturesButton = new JButton("Generate Fixtures");
	
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = new Insets(0, 0, 5, 5);
		gbc_genFixturesButton.gridx = 2;
		gbc_genFixturesButton.gridy = 3;
		getContentPane().add(genFixturesButton, gbc_genFixturesButton);
		
		genFixturesButton.addActionListener(e -> {
				List<Team> selectedTeams = new ArrayList<Team>();
				for(int i : teamSelectionList.getSelectedIndices()) { selectedTeams.add(teams.get(i)); }
				matchData.createSeasonMatches(selectedTeams, seasons.get(seasonSelect.getSelectedIndex()));
				dispose();
				
			});
	}
}