package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import league.League;
import league.Match;
import league.Season;
import league.Team;
import leagueDB.leagueMemberData;
import leagueDB.matchData;
import leagueDB.seasonData;
import leagueDB.teamData;
import leagueMembers.Manager;
import leagueMembers.Player;
import leagueMembers.Referee;

public interface IAdminPanel {
	
	public static JDialog getAssignRefDialog(JfgpWindow frame) {
		JDialog assignRefDialog = new JDialog();
		
	    List<String> refSelection = new ArrayList<String>();
	    List<String> matches = new ArrayList<String>();
		
		assignRefDialog.setModal(true);
		assignRefDialog.setAlwaysOnTop(true);
		assignRefDialog.setFocusable(true);
		assignRefDialog.setSize(760, 500);
		assignRefDialog.setTitle("Assign Referees");
		
		List<Referee> referees = leagueMemberData.getAllReferees(frame.getDbConnection());
        for(Referee i : referees) { refSelection.add(i.getFullName()); }
        
        List<Match> nextFiveGameWeeks = matchData.getNextFiveGameWeeks(frame.getDbConnection(), 
        		seasonData.getCurrentSeason(frame.getDbConnection()), 1);
        
        for(Match i : nextFiveGameWeeks) { matches.add(i.getMatchSummary()); }
        
        GridBagLayout gridBagLayout = new GridBagLayout();
		assignRefDialog.setLayout(gridBagLayout);
		
		JLabel refSelectLabel = new JLabel("Select a Referee");
		GridBagConstraints gbc_refSelectLabel = new GridBagConstraints();
		gbc_refSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_refSelectLabel.gridx = 0;
		gbc_refSelectLabel.gridy = 0;
		assignRefDialog.add(refSelectLabel, gbc_refSelectLabel);
		
		JComboBox refSelect = new JComboBox(refSelection.toArray());
		GridBagConstraints gbc_refSelect = new GridBagConstraints();
		gbc_refSelect.insets = new Insets(0, 0, 5, 0);
		gbc_refSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_refSelect.gridx = 0;
		gbc_refSelect.gridy = 1;
		assignRefDialog.add(refSelect, gbc_refSelect);
		
		JLabel matchSelectLabel = new JLabel("Select match to assign to");
		GridBagConstraints gbc_matchSelectLabel = new GridBagConstraints();
		gbc_matchSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelectLabel.gridx = 0;
		gbc_matchSelectLabel.gridy = 2;
		assignRefDialog.add(matchSelectLabel, gbc_matchSelectLabel);
		
		JComboBox matchSelect = new JComboBox(matches.toArray());
		GridBagConstraints gbc_matchSelect = new GridBagConstraints();
		gbc_matchSelect.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_matchSelect.gridx = 0;
		gbc_matchSelect.gridy = 3;
		assignRefDialog.add(matchSelect, gbc_matchSelect);
		
		JButton confirmationButton = new JButton("Confirm");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		assignRefDialog.add(confirmationButton, gbc_confirmationButton);
		
		confirmationButton.addActionListener(e -> {
			if (matchData.checkRefAssigned(frame.getDbConnection(), nextFiveGameWeeks.get(matchSelect.getSelectedIndex()))) {
				int areYouSure = JOptionPane.showConfirmDialog(frame, "This match already has a referee assigned. Are you sure you want to overwrite this?", "", JOptionPane.YES_NO_OPTION);
				
				if(areYouSure == JOptionPane.YES_OPTION) { 
					frame.getAdminAccount().assignRef(frame.getDbConnection(), 
							nextFiveGameWeeks.get(matchSelect.getSelectedIndex()), 
							referees.get(refSelect.getSelectedIndex())); }
			} else { 
				frame.getAdminAccount().assignRef(frame.getDbConnection(), 
						nextFiveGameWeeks.get(matchSelect.getSelectedIndex()), 
						referees.get(refSelect.getSelectedIndex())); }
        	assignRefDialog.dispose();
		});
		
		assignRefDialog.setVisible(true);
		return assignRefDialog;
	}
	
	public static JDialog getGenFixturesDialog(JfgpWindow frame) {
		JDialog genFixturesDialog = new JDialog();
		
	     List<String> seasonSelection = new ArrayList<String>();

	     List<String> teamSelection = new ArrayList<String>();
		
		genFixturesDialog.setResizable(false);
		genFixturesDialog.setAlwaysOnTop(true);
		genFixturesDialog.setFocusable(true);
		genFixturesDialog.setSize(760, 500);
		genFixturesDialog.setModal(true);
		genFixturesDialog.setTitle("Generate Fixtures");
        
		List<Season> seasons = seasonData.getSeasons(frame.getDbConnection());
        List<Team> teams = teamData.getAllTeams(frame.getDbConnection());
		
		for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
	    for(Team i : teams) { teamSelection.add(i.getName()); }
	        
		GridBagLayout gridBagLayout = new GridBagLayout();
		genFixturesDialog.setLayout(gridBagLayout);
		
		JLabel selectSeaeson = new JLabel("Select Season To Generate Fixtures For");
		selectSeaeson.setHorizontalAlignment(SwingConstants.LEFT);
		
		GridBagConstraints gbc_selectSeaeson = new GridBagConstraints();
		gbc_selectSeaeson.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectSeaeson.insets = new Insets(0, 0, 5, 5);
		gbc_selectSeaeson.gridx = 1;
		gbc_selectSeaeson.gridy = 1;
		genFixturesDialog.add(selectSeaeson, gbc_selectSeaeson);
		
		JLabel teamsToAdd = new JLabel("Select Teams to Include in the Season");
		teamsToAdd.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_teamsToAdd = new GridBagConstraints();
		gbc_teamsToAdd.insets = new Insets(0, 0, 5, 5);
		gbc_teamsToAdd.gridx = 3;
		gbc_teamsToAdd.gridy = 1;
		genFixturesDialog.add(teamsToAdd, gbc_teamsToAdd);
		
		JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
		GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
		gbc_seasonSelect.insets = new Insets(0, 0, 5, 5);
		gbc_seasonSelect.anchor = GridBagConstraints.NORTH;
		gbc_seasonSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_seasonSelect.gridx = 1;
		gbc_seasonSelect.gridy = 2;
		genFixturesDialog.add(seasonSelect, gbc_seasonSelect);
		
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
		genFixturesDialog.add(teamSelectionList, gbc_list);
		
		JButton genFixturesButton = new JButton("Generate Fixtures");
	
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = new Insets(0, 0, 5, 5);
		gbc_genFixturesButton.gridx = 2;
		gbc_genFixturesButton.gridy = 3;
		genFixturesDialog.add(genFixturesButton, gbc_genFixturesButton);
		
		genFixturesButton.addActionListener(e -> {
				List<Team> selectedTeams = new ArrayList<Team>();
				for(int i : teamSelectionList.getSelectedIndices()) { selectedTeams.add(teams.get(i)); }
				matchData.createSeasonMatches(frame.getDbConnection(), selectedTeams, seasons.get(seasonSelect.getSelectedIndex()));
				genFixturesDialog.dispose();
			});
		
		genFixturesDialog.setVisible(true);
		return genFixturesDialog;
	}

	public static JDialog getLeagueDialog(JfgpWindow frame) {
		JDialog leagueDialog = new JDialog();
		
		List<String> seasonSelection = new ArrayList<String>();
		League league = seasonData.getLeague(frame.getDbConnection());
		
		leagueDialog.setFocusable(true);
		leagueDialog.setModal(true);
		leagueDialog.setResizable(false);
		leagueDialog.setSize(500, 250);
		leagueDialog.setTitle(league.getLeagueName());
		
		List<Season> seasons = seasonData.getSeasons(frame.getDbConnection());
		
        for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        leagueDialog.setLayout(gridBagLayout);
        
        JLabel changeLeagueName = new JLabel("Change League Name: ");
        changeLeagueName.setHorizontalAlignment(SwingConstants.CENTER);
        
        GridBagConstraints gbc_changeLeagueName = new GridBagConstraints();
        gbc_changeLeagueName.insets = new Insets(0, 0, 5, 5);
        gbc_changeLeagueName.gridx = 1;
        gbc_changeLeagueName.gridy = 2;
        leagueDialog.add(changeLeagueName, gbc_changeLeagueName);
        
        JLabel currSeasonLabel = new JLabel("Set Current Season");
        GridBagConstraints gbc_currSeasonLabel = new GridBagConstraints();
        gbc_currSeasonLabel.insets = new Insets(0, 0, 5, 5);
        gbc_currSeasonLabel.gridx = 3;
        gbc_currSeasonLabel.gridy = 2;
        leagueDialog.add(currSeasonLabel, gbc_currSeasonLabel);
        
        JTextArea newName = new JTextArea();
        newName.setMinimumSize(new Dimension(50, 22));
        newName.getText();
        
        GridBagConstraints gbc_newName = new GridBagConstraints();
        gbc_newName.fill = GridBagConstraints.HORIZONTAL;
        gbc_newName.anchor = GridBagConstraints.NORTH;
        gbc_newName.insets = new Insets(0, 0, 5, 5);
        gbc_newName.gridx = 1;
        gbc_newName.gridy = 3;
        leagueDialog.add(newName, gbc_newName);
        
        JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 3;
        gbc_comboBox.gridy = 3;
        leagueDialog.add(seasonSelect, gbc_comboBox);
        
        JButton saveButton = new JButton("Save");
        GridBagConstraints gbc_saveButton = new GridBagConstraints();
        gbc_saveButton.insets = new Insets(0, 0, 0, 5);
        gbc_saveButton.anchor = GridBagConstraints.NORTH;
        gbc_saveButton.gridx = 1;
        gbc_saveButton.gridy = 4;
        leagueDialog.add(saveButton, gbc_saveButton);
        
        JButton updateButton = new JButton("Update");
        GridBagConstraints gbc_updateButton = new GridBagConstraints();
        gbc_updateButton.insets = new Insets(0, 0, 0, 5);
        gbc_updateButton.gridx = 3;
        gbc_updateButton.gridy = 4;
        leagueDialog.add(updateButton, gbc_updateButton);
        
        saveButton.addActionListener(e -> {
        	seasonData.changeLeagueName(frame.getDbConnection(), newName.getText());
        	leagueDialog.dispose();
        });
        
        updateButton.addActionListener(e -> {
        	seasonData.setCurrentSeason(frame.getDbConnection(), seasons.get(seasonSelect.getSelectedIndex()).getId());
        	leagueDialog.dispose();
        });
        
        leagueDialog.setVisible(true);
        return leagueDialog;
	}

	public static JDialog getManagersDialog(JfgpWindow frame) {
		
		JDialog managerDialog = new JDialog();
		List<Manager> managers;
	    List<String> managerSelection = new ArrayList<String>();
	    Insets insets = new Insets(0, 0, 5, 5);
	    
	    managerDialog.setAlwaysOnTop(true);
	    managerDialog.setFocusable(true);
	    managerDialog.setSize(450, 500);
	    managerDialog.setModal(true);
	    managerDialog.setTitle("Managers");
        
        managers = leagueMemberData.getAllManagers(frame.getDbConnection());
        for(Manager i : managers) { managerSelection.add(i.getFullName()); }
       
        GridBagLayout managerDialogLayout = new GridBagLayout();
        managerDialog.setLayout(managerDialogLayout);
        
        JLabel createManagerLabel = new JLabel("Create Manager");
        GridBagConstraints gbc_createManagerLabel = new GridBagConstraints();
        gbc_createManagerLabel.insets = insets;
        gbc_createManagerLabel.gridx = 1;
        gbc_createManagerLabel.gridy = 0;
        managerDialog.add(createManagerLabel, gbc_createManagerLabel);
        
        JLabel deleteManagerLabel = new JLabel("Delete Manager");
        GridBagConstraints gbc_deleteManagerLabel = new GridBagConstraints();
        gbc_deleteManagerLabel.insets = insets;
        gbc_deleteManagerLabel.gridx = 3;
        gbc_deleteManagerLabel.gridy = 0;
        managerDialog.add(deleteManagerLabel, gbc_deleteManagerLabel);
        
        JLabel fNameLabel = new JLabel("First Name: ");
        GridBagConstraints gbc_fNameLabel = new GridBagConstraints();
        gbc_fNameLabel.insets = insets;
        gbc_fNameLabel.gridx = 1;
        gbc_fNameLabel.gridy = 1;
        managerDialog.add(fNameLabel, gbc_fNameLabel);
        
        JLabel managerToRemoveLabel = new JLabel("Manager:");
        GridBagConstraints gbc_managerToRemoveLabel = new GridBagConstraints();
        gbc_managerToRemoveLabel.insets = insets;
        gbc_managerToRemoveLabel.gridx = 3;
        gbc_managerToRemoveLabel.gridy = 1;
        managerDialog.add(managerToRemoveLabel, gbc_managerToRemoveLabel);
        
        JTextField firstNameField = new JTextField();
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        managerDialog.add(firstNameField, gbc_fNameFileld);
        
        JComboBox refSelect = new JComboBox(managerSelection.toArray());
       
        GridBagConstraints gbc_refSelect = new GridBagConstraints();
        gbc_refSelect.insets = insets;
        gbc_refSelect.gridx = 3;
        gbc_refSelect.gridy = 2;
        managerDialog.add(refSelect, gbc_refSelect);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = insets;
        gbc_lastNameLabel.gridx = 1;
        gbc_lastNameLabel.gridy = 3;
        managerDialog.add(lastNameLabel, gbc_lastNameLabel);
        
        JButton delManBut = new JButton("Delete");
        GridBagConstraints gbc_delManBut = new GridBagConstraints();
        gbc_delManBut.insets = insets;
        gbc_delManBut.gridx = 3;
        gbc_delManBut.gridy = 3;
        managerDialog.add(delManBut, gbc_delManBut);
        
        JTextField lastNameField = new JTextField();
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = insets;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 4;
        managerDialog.add(lastNameField, gbc_lastNameField);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = new Insets(0, 0, 0, 5);
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        managerDialog.add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createManagerAccount(frame.getDbConnection(), 
        			firstNameField.getText(), firstNameField.getText());
        	managerDialog.dispose();
        });
        
        delManBut.addActionListener(e -> {
        	frame.getAdminAccount().removeManager(frame.getDbConnection(), 
        			managers.get(refSelect.getSelectedIndex()));
        	managerDialog.dispose();
        });
        
        managerDialog.setVisible(true);
        return managerDialog;
	}
	
	public static JDialog getDispMatchDialog(JfgpWindow frame) {
		JDialog dispMatchDialog = new JDialog();
		
		return dispMatchDialog;
	}
	
	public static JDialog getMatchEventDialog(JfgpWindow frame) {
		JDialog matchEventDialog = new JDialog();
		
		return matchEventDialog;
	}
	
	public static JDialog getPlayerDialog(JfgpWindow frame) {
		JDialog playerDialog = new JDialog();
		
		Insets insets = new Insets(0, 0, 5, 5);
	    String[] positions = {"Attacker", "Midfielder", "Defender", "Goalkeeper"};
	   
	    List<Integer> playerIds = new ArrayList<Integer>();
	    List<String> playerSelection = new ArrayList<String>();
		
		playerDialog.setAlwaysOnTop(true);
		playerDialog.setFocusable(true);
		playerDialog.setSize(450, 500);
		playerDialog.setModal(true);
	    playerDialog.setTitle("Players");

	    List<Player> players = leagueMemberData.getAllPlayers(frame.getDbConnection()); 
        
        for(Player i : players) { playerIds.add(i.getId());  playerSelection.add(i.getFullName()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        playerDialog.setLayout(seasonDialogLayout);
        
        JLabel createPlayerLabel = new JLabel("Create Player");
        GridBagConstraints gbc_createPlayerLabel = new GridBagConstraints();
        gbc_createPlayerLabel.insets = insets;
        gbc_createPlayerLabel.gridx = 1;
        gbc_createPlayerLabel.gridy = 0;
        playerDialog.add(createPlayerLabel, gbc_createPlayerLabel);
        
        JLabel deletePlayerLabel = new JLabel("Delete Player");
        GridBagConstraints gbc_deletePlayerLabel = new GridBagConstraints();
        gbc_deletePlayerLabel.insets = insets;
        gbc_deletePlayerLabel.gridx = 3;
        gbc_deletePlayerLabel.gridy = 0;
        playerDialog.add(deletePlayerLabel, gbc_deletePlayerLabel);
        
        JLabel fNameLabel = new JLabel("First Name: ");
        GridBagConstraints gbc_fNameLabel = new GridBagConstraints();
        gbc_fNameLabel.insets = insets;
        gbc_fNameLabel.gridx = 1;
        gbc_fNameLabel.gridy = 1;
        playerDialog.add(fNameLabel, gbc_fNameLabel);
        
        JLabel playerToRemoveLabel = new JLabel("Player:");
        GridBagConstraints gbc_playerToRemoveLabel = new GridBagConstraints();
        gbc_playerToRemoveLabel.insets = insets;
        gbc_playerToRemoveLabel.gridx = 3;
        gbc_playerToRemoveLabel.gridy = 1;
        playerDialog.add(playerToRemoveLabel, gbc_playerToRemoveLabel);
        
        JTextField firstNameField = new JTextField();
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        playerDialog.add(firstNameField, gbc_fNameFileld);
        
        JComboBox playerSelect = new JComboBox(playerSelection.toArray());
	       
        GridBagConstraints gbc_playerSelect = new GridBagConstraints();
        gbc_playerSelect.insets = insets;
        gbc_playerSelect.gridx = 3;
        gbc_playerSelect.gridy = 2;
        playerDialog.add(playerSelect, gbc_playerSelect);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = insets;
        gbc_lastNameLabel.gridx = 1;
        gbc_lastNameLabel.gridy = 3;
        playerDialog.add(lastNameLabel, gbc_lastNameLabel);
        
        JButton delPlayerBut = new JButton("Delete");
        GridBagConstraints gbc_delPlayerBut = new GridBagConstraints();
        gbc_delPlayerBut.insets = insets;
        gbc_delPlayerBut.gridx = 3;
        gbc_delPlayerBut.gridy = 3;
        playerDialog.add(delPlayerBut, gbc_delPlayerBut);
        
        JTextField lastNameField = new JTextField();
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = insets;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 4;
        playerDialog.add(lastNameField, gbc_lastNameField);
        
        JLabel posSelectLabel = new JLabel("Position:");
        GridBagConstraints gbc_posSelectLabel = new GridBagConstraints();
        gbc_posSelectLabel.insets = insets;
        gbc_posSelectLabel.gridx = 1;
        gbc_posSelectLabel.gridy = 5;
        playerDialog.add(posSelectLabel, gbc_posSelectLabel);
        
        JComboBox positionSelect = new JComboBox(positions);
        GridBagConstraints gbc_positionSelect = new GridBagConstraints();
        gbc_positionSelect.insets = insets;
        gbc_positionSelect.fill = GridBagConstraints.HORIZONTAL;
        gbc_positionSelect.gridx = 1;
        gbc_positionSelect.gridy = 6;
        playerDialog.add(positionSelect, gbc_positionSelect);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        playerDialog.add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createPlayer(frame.getDbConnection(), firstNameField.getText(), lastNameField.getText(), positions[positionSelect.getSelectedIndex()].toLowerCase());
        	playerDialog.dispose();
        });
        
        delPlayerBut.addActionListener(e -> {
        	frame.getAdminAccount().removePlayer(frame.getDbConnection(), players.get(playerSelect.getSelectedIndex()));
        	playerDialog.dispose();
        });
        
        playerDialog.setVisible(true);
        return playerDialog;
	}
	
	public static JDialog getRefereeDialog(JfgpWindow frame) {
		JDialog refereeDialog = new JDialog();
	    Insets insets = new Insets(0, 0, 5, 5);;
	    
	    refereeDialog.setAlwaysOnTop(true);
	    refereeDialog.setFocusable(true);
	    refereeDialog.setSize(450, 500);
	    refereeDialog.setModal(true);
	    refereeDialog.setTitle("Referees");

	    List<String> refSelection = new ArrayList<String>();
        List<Referee> referees = leagueMemberData.getAllReferees(frame.getDbConnection());
        for(Referee i : referees) { refSelection.add(i.getFullName()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        refereeDialog.setLayout(seasonDialogLayout);
        
        JLabel createRefereeLabel = new JLabel("Create Referees");
        GridBagConstraints gbc_createRefereeLabel = new GridBagConstraints();
        gbc_createRefereeLabel.insets = insets;
        gbc_createRefereeLabel.gridx = 1;
        gbc_createRefereeLabel.gridy = 0;
        refereeDialog.add(createRefereeLabel, gbc_createRefereeLabel);
        
        JLabel deleteRefereeLabel = new JLabel("Delete Referee");
        GridBagConstraints gbc_deleteRefereeLabel = new GridBagConstraints();
        gbc_deleteRefereeLabel.insets = insets;
        gbc_deleteRefereeLabel.gridx = 3;
        gbc_deleteRefereeLabel.gridy = 0;
        refereeDialog.add(deleteRefereeLabel, gbc_deleteRefereeLabel);
        
        JLabel fNameLabel = new JLabel("First Name: ");
        GridBagConstraints gbc_fNameLabel = new GridBagConstraints();
        gbc_fNameLabel.insets = insets;
        gbc_fNameLabel.gridx = 1;
        gbc_fNameLabel.gridy = 1;
        refereeDialog.add(fNameLabel, gbc_fNameLabel);
        
        JLabel refToRemoveLabel = new JLabel("Referee:");
        GridBagConstraints gbc_refToRemoveLabel = new GridBagConstraints();
        gbc_refToRemoveLabel.insets = insets;
        gbc_refToRemoveLabel.gridx = 3;
        gbc_refToRemoveLabel.gridy = 1;
        refereeDialog.add(refToRemoveLabel, gbc_refToRemoveLabel);
        
        JTextField firstNameField = new JTextField();
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        refereeDialog.add(firstNameField, gbc_fNameFileld);
        firstNameField.setColumns(15);
        
		JComboBox refSelect = new JComboBox(refSelection.toArray());
       
        GridBagConstraints gbc_refSelect = new GridBagConstraints();
        gbc_refSelect.insets = insets;
        gbc_refSelect.gridx = 3;
        gbc_refSelect.gridy = 2;
        refereeDialog.add(refSelect, gbc_refSelect);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = insets;
        gbc_lastNameLabel.gridx = 1;
        gbc_lastNameLabel.gridy = 3;
        refereeDialog.add(lastNameLabel, gbc_lastNameLabel);
        
        JButton delRefBut = new JButton("Delete");
        GridBagConstraints gbc_delRefBut = new GridBagConstraints();
        gbc_delRefBut.insets = insets;
        gbc_delRefBut.gridx = 3;
        gbc_delRefBut.gridy = 3;
        refereeDialog.add(delRefBut, gbc_delRefBut);
        
        JTextField lastNameField = new JTextField();
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = insets;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 4;
        refereeDialog.add(lastNameField, gbc_lastNameField);
        lastNameField.setColumns(15);

        JLabel cityLabel = new JLabel("City: ");
        GridBagConstraints gbc_cityLabel = new GridBagConstraints();
        gbc_cityLabel.insets = insets;
        gbc_cityLabel.gridx = 1;
        gbc_cityLabel.gridy = 5;
        refereeDialog.add(cityLabel, gbc_cityLabel);
        
        JTextField cityField = new JTextField();
        GridBagConstraints gbc_cityField = new GridBagConstraints();
        gbc_cityField.insets = insets;
        gbc_cityField.gridx = 1;
        gbc_cityField.gridy = 6;
        refereeDialog.add(cityField, gbc_cityField);
        cityField.setColumns(15);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        refereeDialog.add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createRefereeAccount(frame.getDbConnection(), 
        			firstNameField.getText(), lastNameField.getText(), cityField.getText());
        	refereeDialog.dispose();
        });
        
        delRefBut.addActionListener(e -> {
        	frame.getAdminAccount().removeReferee(frame.getDbConnection(), referees.get(refSelect.getSelectedIndex()));
        	refereeDialog.dispose();
        });
        
        refereeDialog.setVisible(true);
        return refereeDialog;
	}
	
	public static JDialog getSeasonDialog(JfgpWindow frame) {
		JDialog seasonDialog = new JDialog();
		
		List<Season> seasons;
	    List<String> seasonSelection = new ArrayList<String>();
	    
	    Insets insets = new Insets(0, 0, 5, 5);
	    seasonDialog.setAlwaysOnTop(true);
	    seasonDialog.setFocusable(true);
	    seasonDialog.setSize(450, 500);
	    seasonDialog.setModal(true);
	    seasonDialog.setTitle("Seasons");

        seasons = seasonData.getSeasons(frame.getDbConnection());
        
        for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        seasonDialog.setLayout(seasonDialogLayout);
        
        JLabel addSeasonsLabel = new JLabel("Add Seasons");
        GridBagConstraints gbc_addSeasonsLabel = new GridBagConstraints();
        gbc_addSeasonsLabel.insets = insets;
        gbc_addSeasonsLabel.gridx = 1;
        gbc_addSeasonsLabel.gridy = 0;
        seasonDialog.add(addSeasonsLabel, gbc_addSeasonsLabel);
        
        JLabel removeSeasonsLabel = new JLabel("Remove Seasons");
        GridBagConstraints gbc_removeSeasonsLabel = new GridBagConstraints();
        gbc_removeSeasonsLabel.insets = insets;
        gbc_removeSeasonsLabel.gridx = 3;
        gbc_removeSeasonsLabel.gridy = 0;
        seasonDialog.add(removeSeasonsLabel, gbc_removeSeasonsLabel);
        
        JLabel StartYearLabel = new JLabel("Season Start Year");
        GridBagConstraints gbc_StartYearLabel = new GridBagConstraints();
        gbc_StartYearLabel.insets = insets;
        gbc_StartYearLabel.gridx = 1;
        gbc_StartYearLabel.gridy = 1;
        seasonDialog.add(StartYearLabel, gbc_StartYearLabel);
        
        JLabel seasonLabel = new JLabel("Season: ");
        GridBagConstraints gbc_seasonLabel = new GridBagConstraints();
        gbc_seasonLabel.insets = insets;
        gbc_seasonLabel.gridx = 3;
        gbc_seasonLabel.gridy = 1;
        seasonDialog.add(seasonLabel, gbc_seasonLabel);
        
        JTextField seasonStartField = new JTextField();
        GridBagConstraints gbc_startYearField = new GridBagConstraints();
        gbc_startYearField.insets = insets;
        gbc_startYearField.gridx = 1;
        gbc_startYearField.gridy = 2;
        seasonDialog.add(seasonStartField, gbc_startYearField);
        
        JComboBox seasonSelect = new JComboBox(seasonSelection.toArray());
       
        GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
        gbc_seasonSelect.insets = insets;
        gbc_seasonSelect.gridx = 3;
        gbc_seasonSelect.gridy = 2;
        seasonDialog.add(seasonSelect, gbc_seasonSelect);

        JLabel endYearLabel = new JLabel("Season End Year");
        GridBagConstraints gbc_endYearLabel = new GridBagConstraints();
        gbc_endYearLabel.insets = insets;
        gbc_endYearLabel.gridx = 1;
        gbc_endYearLabel.gridy = 3;
        seasonDialog.add(endYearLabel, gbc_endYearLabel);
        
        JButton deleteSeasonBut = new JButton("Delete");
        GridBagConstraints gbc_deleteSeasonBut = new GridBagConstraints();
        gbc_deleteSeasonBut.insets = insets;
        gbc_deleteSeasonBut.gridx = 3;
        gbc_deleteSeasonBut.gridy = 3;
        seasonDialog.add(deleteSeasonBut, gbc_deleteSeasonBut);
        
        JTextField seasonEndField = new JTextField();
        GridBagConstraints gbc_seasonEndField = new GridBagConstraints();
        gbc_seasonEndField.insets = insets;
        gbc_seasonEndField.gridx = 1;
        gbc_seasonEndField.gridy = 4;
        seasonDialog.add(seasonEndField, gbc_seasonEndField);
        
        JButton addBut = new JButton("Add");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 5;
        seasonDialog.add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	seasonData.createSeason(frame.getDbConnection(), seasonStartField.getText(), seasonEndField.getText());
        	seasonDialog.dispose();
        });
        
        deleteSeasonBut.addActionListener(e -> {
        	seasonData.removeSeason(frame.getDbConnection(), seasons.get(seasonSelect.getSelectedIndex()));
        	seasonDialog.dispose();
        });
		
		
		seasonDialog.setVisible(true);
		return seasonDialog;
	}
	
	public static JDialog getTeamDialog(JfgpWindow frame) {
		JDialog teamDialog = new JDialog();
		
		List<Team> teams;
	    List<String> teamSelection = new ArrayList<String>();
	    Insets insets = new Insets(0, 0, 5, 5);
		
	    teamDialog.setAlwaysOnTop(true);
	    teamDialog.setFocusable(true);
	    teamDialog.setSize(450, 500);
	    teamDialog.setModal(true);
	    teamDialog.setTitle("Teams");
        
        teams = teamData.getAllTeams(frame.getDbConnection());
        for(Team i : teams) { teamSelection.add(i.getName()); }
       
        GridBagLayout seasonDialogLayout = new GridBagLayout();
        teamDialog.setLayout(seasonDialogLayout);
        
        JLabel createTeamLabel = new JLabel("Create Team");
        GridBagConstraints gbc_createTeamLabel = new GridBagConstraints();
        gbc_createTeamLabel.insets = insets;
        gbc_createTeamLabel.gridx = 1;
        gbc_createTeamLabel.gridy = 0;
        teamDialog.add(createTeamLabel, gbc_createTeamLabel);
        
        JLabel deleteTeamLabel = new JLabel("Delete Team");
        deleteTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        GridBagConstraints gbc_deleteTeamLabel = new GridBagConstraints();
        gbc_deleteTeamLabel.insets = insets;
        gbc_deleteTeamLabel.gridx = 3;
        gbc_deleteTeamLabel.gridy = 0;
        teamDialog.add(deleteTeamLabel, gbc_deleteTeamLabel);
        
        JLabel teamNameLabel = new JLabel("Team Name:");
        GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
        gbc_teamNameLabel.insets = insets;
        gbc_teamNameLabel.gridx = 1;
        gbc_teamNameLabel.gridy = 1;
        teamDialog.add(teamNameLabel, gbc_teamNameLabel);
        
        JLabel teamToRemoveLabel = new JLabel("Team:");
        GridBagConstraints gbc_teamToRemoveLabel = new GridBagConstraints();
        gbc_teamToRemoveLabel.insets = insets;
        gbc_teamToRemoveLabel.gridx = 3;
        gbc_teamToRemoveLabel.gridy = 1;
        teamDialog.add(teamToRemoveLabel, gbc_teamToRemoveLabel);
        
        JTextField teamNameField = new JTextField();
        GridBagConstraints gbc_teamNameField = new GridBagConstraints();
        gbc_teamNameField.insets = insets;
        gbc_teamNameField.gridx = 1;
        gbc_teamNameField.gridy = 2;
        teamDialog.add(teamNameField, gbc_teamNameField);
        
        JComboBox teamSelect = new JComboBox(teamSelection.toArray());
       
        GridBagConstraints gbc_teamSelect = new GridBagConstraints();
        gbc_teamSelect.insets = insets;
        gbc_teamSelect.gridx = 3;
        gbc_teamSelect.gridy = 2;
        teamDialog.add(teamSelect, gbc_teamSelect);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 3;
        teamDialog.add(addBut, gbc_addBut);
        
        JButton delTeamBut = new JButton("Delete");
        GridBagConstraints gbc_delTeamBut = new GridBagConstraints();
        gbc_delTeamBut.insets = insets;
        gbc_delTeamBut.gridx = 3;
        gbc_delTeamBut.gridy = 3;
        teamDialog.add(delTeamBut, gbc_delTeamBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createTeam(frame.getDbConnection(), teamNameField.getText());
        	teamDialog.dispose();
        });
        
        delTeamBut.addActionListener(e -> {
        	frame.getAdminAccount().removeTeam(frame.getDbConnection(), teams.get(teamSelect.getSelectedIndex()));
        	teamDialog.dispose();
        });
		
		return teamDialog;
	}
	
	public static JDialog getUpdateDialog(JfgpWindow frame) {
		JDialog updateDialog = new JDialog();
		
		updateDialog.setVisible(true);
		return updateDialog;
	}
}