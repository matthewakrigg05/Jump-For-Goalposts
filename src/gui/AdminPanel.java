package gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;
import java.util.List;

import league.*;
import leagueDB.JFGPdb;
import leagueMembers.*;

@SuppressWarnings("serial")
public class AdminPanel extends JPanel implements IPanel {
	
	List<String> adminButtons = new ArrayList<String>(List.of("League", "Generate Fixtures", "Season", 
			"Assign Match Referees", "Team", "Managers", "Players", "Referees", "Assign Players' Team", 
			"Assign Teams' Manager", "Stadiums", "Assign Teams' Stadium", "Unassign Team Members"));
	JButton[] panelButton = new JButton[adminButtons.size()];
	JList<String> matchesToRecordList;
	
	JfgpWindow frame;
	JFGPdb db;
	Insets insets;
	
	List<String> matches = new ArrayList<String>();
	List<Match> matchesToRecord;
	Season currentSeason;

	/*
	 * The admin panel is a panel that contains only dialog boxes that are used by an admin user.
	 */
	public AdminPanel(JfgpWindow frame) { 
		 this.frame = frame;
		 this.db = frame.getDb();
		 insets = new Insets(0, 0, 10, 25);
		 initialise();
		 }
	
	@Override
	public void initialise() {
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		setLayout(new GridBagLayout());
		addPanelComponents(this);
		addActionListeners();
	}
	
	// The main panel components
	@Override
	public void addPanelComponents(JPanel panel) {
		for(int i = 0; i < adminButtons.size(); i++) {
			panelButton[i] = new JButton(adminButtons.get(i));
			panelButton[i].setFont(getFont());
		}
		
		currentSeason = db.findCurrentSeason();
		
		int currentMatchWeek = currentSeason.getCurrentGameWeek(db.getConnection());
		
		matchesToRecord = currentSeason.getMatchWeekMatches(db, currentMatchWeek);
		for(Match match : matchesToRecord) { 
			matches.add(match.getMatchSummary()); }

		if (matches.isEmpty()) {
			matches.add("There are no matches to record");
		}
		
		JLabel leagueOptLabel = new JLabel("League Options:");
		leagueOptLabel.setFont(getFont());
		GridBagConstraints gbc_leagueOptLabel = new GridBagConstraints();
		gbc_leagueOptLabel.insets = insets;
		gbc_leagueOptLabel.gridx = 1;
		gbc_leagueOptLabel.gridy = 1;
		panel.add(leagueOptLabel, gbc_leagueOptLabel);
		
		JLabel matchOptLabel = new JLabel("Other Options:");
		matchOptLabel.setFont(getFont());
		GridBagConstraints gbc_matchOptLabel = new GridBagConstraints();
		gbc_matchOptLabel.insets = insets;
		gbc_matchOptLabel.gridx = 3;
		gbc_matchOptLabel.gridy = 1;
		panel.add(matchOptLabel, gbc_matchOptLabel);
		
		GridBagConstraints gbc_leagueButton = new GridBagConstraints();
		gbc_leagueButton.insets = insets;
		gbc_leagueButton.gridx = 1;
		gbc_leagueButton.gridy = 3;
		panel.add(panelButton[0], gbc_leagueButton);
		
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = insets;
		gbc_genFixturesButton.gridx = 3;
		gbc_genFixturesButton.gridy = 3;
		panel.add(panelButton[1], gbc_genFixturesButton);
		
		GridBagConstraints gbc_seasonButton = new GridBagConstraints();
		gbc_seasonButton.insets = insets;
		gbc_seasonButton.gridx = 1;
		gbc_seasonButton.gridy = 4;
		panel.add(panelButton[2], gbc_seasonButton);
		
		GridBagConstraints gbc_matchRefsButton = new GridBagConstraints();
		gbc_matchRefsButton.insets = insets;
		gbc_matchRefsButton.gridx = 3;
		gbc_matchRefsButton.gridy = 4;
		panel.add(panelButton[3], gbc_matchRefsButton);
		
		GridBagConstraints gbc_teamButton = new GridBagConstraints();
		gbc_teamButton.insets = insets;
		gbc_teamButton.gridx = 1;
		gbc_teamButton.gridy = 5;
		panel.add(panelButton[4], gbc_teamButton);
		
		GridBagConstraints gbc_managersButton = new GridBagConstraints();
		gbc_managersButton.insets = insets;
		gbc_managersButton.gridx = 1;
		gbc_managersButton.gridy = 6;
		panel.add(panelButton[5], gbc_managersButton);
		
		GridBagConstraints gbc_refereesButton = new GridBagConstraints();
		gbc_refereesButton.insets = insets;
		gbc_refereesButton.gridx = 1;
		gbc_refereesButton.gridy = 7;
		panel.add(panelButton[6], gbc_refereesButton);

		GridBagConstraints gbc_playersButton = new GridBagConstraints();
		gbc_playersButton.insets = insets;
		gbc_playersButton.gridx = 1;
		gbc_playersButton.gridy = 8;
		panel.add(panelButton[7], gbc_playersButton);
		
		GridBagConstraints gbc_assignPlayerButton = new GridBagConstraints();
		gbc_assignPlayerButton.insets = insets;
		gbc_assignPlayerButton.gridx = 3;
		gbc_assignPlayerButton.gridy = 5;
		panel.add(panelButton[8], gbc_assignPlayerButton);
		
		GridBagConstraints gbc_assignManagerButton = new GridBagConstraints();
		gbc_assignManagerButton.insets = insets;
		gbc_assignManagerButton.gridx = 3;
		gbc_assignManagerButton.gridy = 6;
		panel.add(panelButton[9], gbc_assignManagerButton);
		
		GridBagConstraints gbc_stadiumsButton = new GridBagConstraints();
		gbc_stadiumsButton.insets = insets;
		gbc_stadiumsButton.gridx = 3;
		gbc_stadiumsButton.gridy = 7;
		panel.add(panelButton[10], gbc_stadiumsButton);
		
		GridBagConstraints gbc_assignStadiumsButton = new GridBagConstraints();
		gbc_assignStadiumsButton.insets = insets;
		gbc_assignStadiumsButton.gridx = 3;
		gbc_assignStadiumsButton.gridy = 8;
		panel.add(panelButton[11], gbc_assignStadiumsButton);
		
		GridBagConstraints gbc_unassignPeopleButton = new GridBagConstraints();
		gbc_unassignPeopleButton.insets = insets;
		gbc_unassignPeopleButton.gridx = 3;
		gbc_unassignPeopleButton.gridy = 9;
		panel.add(panelButton[12], gbc_unassignPeopleButton);
		
		JLabel recMatchesLabel = new JLabel("Record A Match This Week: ");
		recMatchesLabel.setFont(getFont());
		GridBagConstraints gbc_recMatchesLabel = new GridBagConstraints();
		gbc_recMatchesLabel.insets = insets;
		gbc_recMatchesLabel.gridx = 5;
		gbc_recMatchesLabel.gridy = 1;
		panel.add(recMatchesLabel, gbc_recMatchesLabel);
		
		matchesToRecordList = new JList(matches.toArray());
		GridBagConstraints gbc_matchesToRecordList = new GridBagConstraints();
		gbc_matchesToRecordList.insets = insets;
		gbc_matchesToRecordList.gridx = 5;
		gbc_matchesToRecordList.gridy = 3;
		panel.add(matchesToRecordList, gbc_matchesToRecordList);
	}

	@Override
	public void addActionListeners() {
		panelButton[0].addActionListener(e -> { getLeagueDialog(); });
		panelButton[1].addActionListener(e -> { getGenFixturesDialog(); });
		panelButton[2].addActionListener(e -> { getSeasonDialog(); });
		panelButton[3].addActionListener(e -> { getAssignRefDialog(); });
		panelButton[4].addActionListener(e -> { getTeamDialog(); });
		panelButton[5].addActionListener(e -> { getManagersDialog(); });
		panelButton[6].addActionListener(e -> { getPlayerDialog(); });
		panelButton[7].addActionListener(e -> { getRefereeDialog(); });
		panelButton[8].addActionListener(e -> { getAssignPlayerDialog(); });
		panelButton[9].addActionListener(e -> { getAssignManagerDialog(); });
		panelButton[10].addActionListener(e -> { getStadiumDialog(); });
		panelButton[11].addActionListener(e -> { getAssignStadiumDialog(); });
		panelButton[12].addActionListener(e -> { getRemoveFromTeamDialog(); });
		
		matchesToRecordList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Match selectedMatch = matchesToRecord.get(matchesToRecordList.getSelectedIndex());
				
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new toolBar(frame), BorderLayout.WEST);
				frame.getContentPane().add(new recordMatchPanel(frame, selectedMatch, frame.getAdminAccount()), BorderLayout.CENTER);
				frame.revalidate();
				frame.repaint();
			}
		});
		
	}	
	
	// Dialog used to assign referees to matches.
	public JDialog getAssignRefDialog() {
		JDialog assignRefDialog = new JDialog();
	    List<String> refSelection = new ArrayList<String>();
	    List<String> matches = new ArrayList<String>();
		
		assignRefDialog.setModal(true);
		assignRefDialog.setAlwaysOnTop(true);
		assignRefDialog.setFocusable(true);
		assignRefDialog.setSize(760, 500);
		assignRefDialog.setTitle("Assign Referees");
		assignRefDialog.setLayout(new GridBagLayout());
		
		List<Referee> referees = db.getAllReferees();
        for(Referee i : referees) { refSelection.add(i.getFullName()); }
        
        List<Match> nextFiveGameWeeks = currentSeason.getNextFiveGameWeeks(db);
        for(Match i : nextFiveGameWeeks) { matches.add(i.getMatchSummary()); }

		JLabel refSelectLabel = new JLabel("Select a Referee");
		GridBagConstraints gbc_refSelectLabel = new GridBagConstraints();
		gbc_refSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_refSelectLabel.gridx = 0;
		gbc_refSelectLabel.gridy = 0;
		assignRefDialog.add(refSelectLabel, gbc_refSelectLabel);
		
		JComboBox<String> refSelect = new JComboBox(refSelection.toArray());
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
		
		JComboBox<String> matchSelect = new JComboBox(matches.toArray());
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
			if (nextFiveGameWeeks.get(matchSelect.getSelectedIndex()).checkRefAssigned(db.getConnection())) {
				int areYouSure = JOptionPane.showConfirmDialog(frame, "This match already has a referee assigned. Are you sure you want to overwrite this?", "", JOptionPane.YES_NO_OPTION);
				
				if(areYouSure == JOptionPane.YES_OPTION) { 
					frame.getAdminAccount().assignRef(db.getConnection(), 
							nextFiveGameWeeks.get(matchSelect.getSelectedIndex()), 
							referees.get(refSelect.getSelectedIndex())); }
			} else { 
				frame.getAdminAccount().assignRef(db.getConnection(), 
						nextFiveGameWeeks.get(matchSelect.getSelectedIndex()), 
						referees.get(refSelect.getSelectedIndex())); }
        	assignRefDialog.dispose();
		});
		
		assignRefDialog.setVisible(true);
		return assignRefDialog;
	}
	
	// The dialog box responsible for allowing the admin to generate the season fixtures.
	public JDialog getGenFixturesDialog() {
		JDialog genFixturesDialog = new JDialog();
		
	     List<String> seasonSelection = new ArrayList<String>();
	     List<String> teamSelection = new ArrayList<String>();
		
		genFixturesDialog.setResizable(false);
		genFixturesDialog.setAlwaysOnTop(true);
		genFixturesDialog.setFocusable(true);
		genFixturesDialog.setSize(760, 500);
		genFixturesDialog.setModal(true);
		genFixturesDialog.setTitle("Generate Fixtures");
		genFixturesDialog.setLayout(new GridBagLayout());
        
		List<Season> seasons = db.getLeague().getSeasons(db.getConnection());
        List<Team> teams = db.getAllTeams();
		
		for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
	    for(Team i : teams) { teamSelection.add(i.getName()); }

		JLabel selectSeaeson = new JLabel("Select Season To Generate Fixtures For");
		selectSeaeson.setHorizontalAlignment(SwingConstants.LEFT);
		
		GridBagConstraints gbc_selectSeaeson = new GridBagConstraints();
		gbc_selectSeaeson.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectSeaeson.insets = new Insets(0, 0, 5, 5);
		gbc_selectSeaeson.gridx = 1;
		gbc_selectSeaeson.gridy = 1;
		genFixturesDialog.add(selectSeaeson, gbc_selectSeaeson);
		
		JLabel teamsToAdd = new JLabel("Select Teams to Include in the Season");
		GridBagConstraints gbc_teamsToAdd = new GridBagConstraints();
		gbc_teamsToAdd.insets = new Insets(0, 0, 5, 5);
		gbc_teamsToAdd.gridx = 3;
		gbc_teamsToAdd.gridy = 1;
		genFixturesDialog.add(teamsToAdd, gbc_teamsToAdd);
		
		JComboBox<String> seasonSelect = new JComboBox(seasonSelection.toArray());
		GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
		gbc_seasonSelect.insets = new Insets(0, 0, 5, 5);
		gbc_seasonSelect.anchor = GridBagConstraints.NORTH;
		gbc_seasonSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_seasonSelect.gridx = 1;
		gbc_seasonSelect.gridy = 2;
		genFixturesDialog.add(seasonSelect, gbc_seasonSelect);
		
		JList<String> teamSelectionList = new JList(teamSelection.toArray());
		
		// Overwritten method from the list selection model, allows the user to select multiple indices
		// from the list without them being adjacent.
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
				frame.getAdminAccount().createSeasonMatches(db, selectedTeams, seasons.get(seasonSelect.getSelectedIndex()));
				genFixturesDialog.dispose();
			});
		
		genFixturesDialog.setVisible(true);
		return genFixturesDialog;
	}

	// The dialog box that allows the admin to change the name of the league, as well
	// as setting the current season being played.
	public JDialog getLeagueDialog() {
		JDialog leagueDialog = new JDialog();
		
		List<String> seasonSelection = new ArrayList<String>();
		League league = db.getLeague();
		
		leagueDialog.setFocusable(true);
		leagueDialog.setModal(true);
		leagueDialog.setResizable(false);
		leagueDialog.setSize(500, 250);
		leagueDialog.setTitle(league.getLeagueName());
		leagueDialog.setLayout(new GridBagLayout());
		
		List<Season> seasons = league.getSeasons(db.getConnection());
        for(Season i : seasons) { seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
        
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
        newName.setColumns(15);
        newName.getText();
        
        GridBagConstraints gbc_newName = new GridBagConstraints();
        gbc_newName.fill = GridBagConstraints.HORIZONTAL;
        gbc_newName.anchor = GridBagConstraints.NORTH;
        gbc_newName.insets = new Insets(0, 0, 5, 5);
        gbc_newName.gridx = 1;
        gbc_newName.gridy = 3;
        leagueDialog.add(newName, gbc_newName);
        
        JComboBox<String> seasonSelect = new JComboBox(seasonSelection.toArray());
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
        	frame.getAdminAccount().changeLeagueName(db.getConnection(), newName.getText());
        	leagueDialog.dispose();
        });
        
        updateButton.addActionListener(e -> {
        	frame.getAdminAccount().setCurrentSeason(db.getConnection(), seasons.get(seasonSelect.getSelectedIndex()).getId());
        	leagueDialog.dispose();
        });
        
        leagueDialog.setVisible(true);
        return leagueDialog;
	}

	// Dialog box that enables the admin to create and remove managers from the system.
	public JDialog getManagersDialog() {
		JDialog managerDialog = new JDialog();
		List<Manager> managers;
	    List<String> managerSelection = new ArrayList<String>();
	    Insets insets = frame.getInsets();
	    
	    managerDialog.setAlwaysOnTop(true);
	    managerDialog.setFocusable(true);
	    managerDialog.setSize(450, 500);
	    managerDialog.setModal(true);
	    managerDialog.setTitle("Managers");
	    managerDialog.setLayout(new GridBagLayout());
        
        managers = db.getAllManagers();
        for(Manager i : managers) { managerSelection.add(i.getFullName()); }
        
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
        firstNameField.setColumns(15);
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        managerDialog.add(firstNameField, gbc_fNameFileld);
        
        JComboBox<String> refSelect = new JComboBox(managerSelection.toArray());
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
        lastNameField.setColumns(15);
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
        	frame.getAdminAccount().createManagerAccount(db.getConnection(), 
        			firstNameField.getText(), lastNameField.getText());
        	managerDialog.dispose();
        });
        
        delManBut.addActionListener(e -> {
        	frame.getAdminAccount().removeManager(db.getConnection(), 
        			managers.get(refSelect.getSelectedIndex()));
        	managerDialog.dispose();
        });
        
        managerDialog.setVisible(true);
        return managerDialog;
	}
	
	// Same as manager dialog but for players
	public JDialog getPlayerDialog() {
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
        playerDialog.setLayout(new GridBagLayout());

	    List<Player> players = db.getAllPlayers(); 
        for(Player i : players) { playerIds.add(i.getId());  playerSelection.add(i.getFullName()); }
       
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
        firstNameField.setColumns(15);
        GridBagConstraints gbc_fNameFileld = new GridBagConstraints();
        gbc_fNameFileld.insets = insets;
        gbc_fNameFileld.gridx = 1;
        gbc_fNameFileld.gridy = 2;
        playerDialog.add(firstNameField, gbc_fNameFileld);
        
        JComboBox<String> playerSelect = new JComboBox(playerSelection.toArray());
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
        lastNameField.setColumns(15);
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
        
        JComboBox<String> positionSelect = new JComboBox<String>(positions);
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
        	frame.getAdminAccount().createPlayer(db.getConnection(), firstNameField.getText(), lastNameField.getText(), positions[positionSelect.getSelectedIndex()].toLowerCase());
        	playerDialog.dispose();
        });
        
        delPlayerBut.addActionListener(e -> {
        	frame.getAdminAccount().removePlayer(db.getConnection(), players.get(playerSelect.getSelectedIndex()));
        	playerDialog.dispose();
        });
        
        playerDialog.setVisible(true);
        return playerDialog;
	}
	
	// Same as manager dialog but for referees
	public JDialog getRefereeDialog() {
		JDialog refereeDialog = new JDialog();
	    Insets insets = new Insets(0, 0, 5, 5);
	    
	    refereeDialog.setAlwaysOnTop(true);
	    refereeDialog.setFocusable(true);
	    refereeDialog.setSize(450, 500);
	    refereeDialog.setModal(true);
	    refereeDialog.setTitle("Referees");
	    refereeDialog.setLayout(new GridBagLayout());

	    List<String> refSelection = new ArrayList<String>();
        List<Referee> referees = db.getAllReferees();
        for(Referee i : referees) { refSelection.add(i.getFullName()); }
     
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
        
        JLabel firstNameLabel = new JLabel("First Name: ");
        GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
        gbc_firstNameLabel.insets = insets;
        gbc_firstNameLabel.gridx = 1;
        gbc_firstNameLabel.gridy = 1;
        refereeDialog.add(firstNameLabel, gbc_firstNameLabel);
        
        JLabel refToRemoveLabel = new JLabel("Referee:");
        GridBagConstraints gbc_refToRemoveLabel = new GridBagConstraints();
        gbc_refToRemoveLabel.insets = insets;
        gbc_refToRemoveLabel.gridx = 3;
        gbc_refToRemoveLabel.gridy = 1;
        refereeDialog.add(refToRemoveLabel, gbc_refToRemoveLabel);
        
        JTextField firstNameField = new JTextField();
        firstNameField.setColumns(15);
        GridBagConstraints gbc_firstNameField = new GridBagConstraints();
        gbc_firstNameField.insets = insets;
        gbc_firstNameField.gridx = 1;
        gbc_firstNameField.gridy = 2;
        refereeDialog.add(firstNameField, gbc_firstNameField);
        
		JComboBox<String> refSelect = new JComboBox(refSelection.toArray());
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
        lastNameField.setColumns(15);
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = insets;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 4;
        refereeDialog.add(lastNameField, gbc_lastNameField);

        JLabel cityLabel = new JLabel("City: ");
        GridBagConstraints gbc_cityLabel = new GridBagConstraints();
        gbc_cityLabel.insets = insets;
        gbc_cityLabel.gridx = 1;
        gbc_cityLabel.gridy = 5;
        refereeDialog.add(cityLabel, gbc_cityLabel);
        
        JTextField cityField = new JTextField();
        cityField.setColumns(15);
        GridBagConstraints gbc_cityField = new GridBagConstraints();
        gbc_cityField.insets = insets;
        gbc_cityField.gridx = 1;
        gbc_cityField.gridy = 6;
        refereeDialog.add(cityField, gbc_cityField);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = insets;
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        refereeDialog.add(addBut, gbc_addBut);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createRefereeAccount(db.getConnection(), 
        			firstNameField.getText(), lastNameField.getText(), cityField.getText());
        	refereeDialog.dispose();
        });
        
        delRefBut.addActionListener(e -> {
        	frame.getAdminAccount().removeReferee(db.getConnection(), referees.get(refSelect.getSelectedIndex()));
        	refereeDialog.dispose();
        });
        
        refereeDialog.setVisible(true);
        return refereeDialog;
	}
	
	// Dialog box for creating seasons by inputting the start and end year of the season.
	public JDialog getSeasonDialog() {
		JDialog seasonDialog = new JDialog();
		List<Season> seasons;
	    List<String> seasonSelection = new ArrayList<String>();
	    
	    Insets insets = frame.getInsets();
	    seasonDialog.setAlwaysOnTop(true);
	    seasonDialog.setFocusable(true);
	    seasonDialog.setSize(450, 500);
	    seasonDialog.setModal(true);
	    seasonDialog.setTitle("Seasons");
	    seasonDialog.setLayout(new GridBagLayout());

        seasons = db.getLeague().getSeasons(db.getConnection());
        for(Season i : seasons) { 
        	seasonSelection.add("Season ID: " + i.getId() + " Season Years: " + i.getSeasonStartEnd()); }
        
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
        seasonStartField.setColumns(10);
        GridBagConstraints gbc_startYearField = new GridBagConstraints();
        gbc_startYearField.insets = insets;
        gbc_startYearField.gridx = 1;
        gbc_startYearField.gridy = 2;
        seasonDialog.add(seasonStartField, gbc_startYearField);
        
        JComboBox<String> seasonSelect = new JComboBox(seasonSelection.toArray());
        GridBagConstraints gbc_seasonSelect = new GridBagConstraints();
        gbc_seasonSelect.insets = insets;
        gbc_seasonSelect.gridx = 3;
        gbc_seasonSelect.gridy = 2;
        seasonDialog.add(seasonSelect, gbc_seasonSelect);

        JLabel seasonEndLabel = new JLabel("Season End Year");
        GridBagConstraints gbc_seasonEndLabel = new GridBagConstraints();
        gbc_seasonEndLabel.insets = insets;
        gbc_seasonEndLabel.gridx = 1;
        gbc_seasonEndLabel.gridy = 3;
        seasonDialog.add(seasonEndLabel, gbc_seasonEndLabel);
        
        JButton deleteSeasonBut = new JButton("Delete");
        GridBagConstraints gbc_deleteSeasonBut = new GridBagConstraints();
        gbc_deleteSeasonBut.insets = insets;
        gbc_deleteSeasonBut.gridx = 3;
        gbc_deleteSeasonBut.gridy = 3;
        seasonDialog.add(deleteSeasonBut, gbc_deleteSeasonBut);
        
        JTextField seasonEndField = new JTextField();
        seasonEndField.setColumns(10);
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
        	frame.getAdminAccount().createSeason(db.getConnection(), seasonStartField.getText(), seasonEndField.getText());
        	seasonDialog.dispose();
        });
        
        deleteSeasonBut.addActionListener(e -> {
        	frame.getAdminAccount().removeSeason(db.getConnection(), seasons.get(seasonSelect.getSelectedIndex()));
        	seasonDialog.dispose();
        });
		
        seasonDialog.setVisible(true);
		return seasonDialog;
	}
	
	// Same as manager dialog but for teams, using a team name
	public JDialog getTeamDialog() {
		JDialog teamDialog = new JDialog();
		
		List<Team> teams;
	    List<String> teamSelection = new ArrayList<String>();
	    Insets insets = frame.getInsets();
		
	    teamDialog.setAlwaysOnTop(true);
	    teamDialog.setFocusable(true);
	    teamDialog.setSize(450, 500);
	    teamDialog.setModal(true);
	    teamDialog.setTitle("Teams");
	    teamDialog.setLayout(new GridBagLayout());
        
        teams = db.getAllTeams();
        for(Team i : db.getAllTeams()) { 
        	teamSelection.add(i.getName()); }
       
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
        teamNameField.setColumns(15);
        GridBagConstraints gbc_teamNameField = new GridBagConstraints();
        gbc_teamNameField.insets = insets;
        gbc_teamNameField.gridx = 1;
        gbc_teamNameField.gridy = 2;
        teamDialog.add(teamNameField, gbc_teamNameField);
        
        JComboBox<String> teamSelect = new JComboBox(teamSelection.toArray());
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
        	frame.getAdminAccount().createTeam(db.getConnection(), teamNameField.getText());
        	teamDialog.dispose();
        });
        
        delTeamBut.addActionListener(e -> {
        	frame.getAdminAccount().removeTeam(db.getConnection(), teams.get(teamSelect.getSelectedIndex()));
        	teamDialog.dispose();
        });
		
        teamDialog.setVisible(true);
		return teamDialog;
	}
	
	// Assign players to teams and give them a contract type
	public JDialog getAssignPlayerDialog() {
		JDialog playerDialog = new JDialog();
		String[] contractTypes = {"Full-Time", "Part-Time"};
		List<String> playerSelection = new ArrayList<String>();
	    List<String> teamsSelect = new ArrayList<String>();
	   
	    playerDialog.setModal(true);
	    playerDialog.setAlwaysOnTop(true);
	    playerDialog.setFocusable(true);
	    playerDialog.setSize(760, 500);
	    playerDialog.setTitle("Assign Player");
	    playerDialog.setLayout(new GridBagLayout());
		
		List<Player> players = db.getAllPlayers();
        for(Player i : players) { playerSelection.add(i.getFullName()); }
        
        List<Team> teams = db.getAllTeams();
        for(Team i : teams) { teamsSelect.add(i.getName()); }
        
		JLabel playerSelectLabel = new JLabel("Select a Player");
		GridBagConstraints gbc_playerSelectLabel = new GridBagConstraints();
		gbc_playerSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_playerSelectLabel.gridx = 0;
		gbc_playerSelectLabel.gridy = 0;
		playerDialog.add(playerSelectLabel, gbc_playerSelectLabel);
		
		JComboBox<String> playerSelect = new JComboBox(playerSelection.toArray());
		GridBagConstraints gbc_playerSelect = new GridBagConstraints();
		gbc_playerSelect.insets = new Insets(0, 0, 5, 0);
		gbc_playerSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_playerSelect.gridx = 0;
		gbc_playerSelect.gridy = 1;
		playerDialog.add(playerSelect, gbc_playerSelect);
		
		JLabel matchSelectLabel = new JLabel("Select team to assign to");
		GridBagConstraints gbc_matchSelectLabel = new GridBagConstraints();
		gbc_matchSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelectLabel.gridx = 0;
		gbc_matchSelectLabel.gridy = 2;
		playerDialog.add(matchSelectLabel, gbc_matchSelectLabel);
		
		JComboBox<String> teamSelect = new JComboBox(teamsSelect.toArray());
		GridBagConstraints gbc_teamSelect = new GridBagConstraints();
		gbc_teamSelect.insets = new Insets(0, 0, 5, 0);
		gbc_teamSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamSelect.gridx = 0;
		gbc_teamSelect.gridy = 3;
		playerDialog.add(teamSelect, gbc_teamSelect);
		
		JLabel conTypeLabel = new JLabel("Contract Type: ");
		
		JComboBox<String> contractSelect = new JComboBox(contractTypes);
		GridBagConstraints gbc_contractSelect = new GridBagConstraints();
		gbc_contractSelect.insets = new Insets(0, 0, 5, 0);
		gbc_contractSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_contractSelect.gridx = 0;
		gbc_contractSelect.gridy = 3;
		playerDialog.add(contractSelect, gbc_contractSelect);
		
		JButton confirmationButton = new JButton("Confirm");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		playerDialog.add(confirmationButton, gbc_confirmationButton);
		
		confirmationButton.addActionListener(e -> {
			if (players.get(playerSelect.getSelectedIndex()).checkPlayerAssigned(db.getConnection() )) {
				int areYouSure = JOptionPane.showConfirmDialog(playerDialog, 
						"This player is already assigned to a team. Are you sure you want to overwrite this?",
						"", JOptionPane.YES_NO_OPTION);
				
				if(areYouSure == JOptionPane.YES_OPTION) { 
					frame.getAdminAccount().assignPlayerToTeam(db.getConnection(), 
							teams.get(teamSelect.getSelectedIndex()), 
							players.get(playerSelect.getSelectedIndex()),
							contractTypes[contractSelect.getSelectedIndex()]); }
			} else { 
				frame.getAdminAccount().assignPlayerToTeam(db.getConnection(), 
						teams.get(teamSelect.getSelectedIndex()), 
						players.get(playerSelect.getSelectedIndex()),
						contractTypes[contractSelect.getSelectedIndex()]); }
			playerDialog.dispose();
		});
		
		playerDialog.setVisible(true);
		return playerDialog;
	}
	
	// Assign managers to team and give them a contract type
	public JDialog getAssignManagerDialog() {
		JDialog managerDialog = new JDialog();
		List<String> managerSelection = new ArrayList<String>();
	    List<String> teamsSelect = new ArrayList<String>();
	    String[] contractTypes = {"Full-Time", "Part-Time"};
		
	    managerDialog.setModal(true);
	    managerDialog.setAlwaysOnTop(true);
	    managerDialog.setFocusable(true);
	    managerDialog.setSize(760, 500);
	    managerDialog.setTitle("Assign Manager");
        managerDialog.setLayout(new GridBagLayout());
		
		List<Manager> managers = db.getAllManagers();
        for(Manager i : managers) { managerSelection.add(i.getFullName()); }
        
        List<Team> teams = db.getAllTeams();
        for(Team i : teams) { teamsSelect.add(i.getName()); }
		
		JLabel managerSelectLabel = new JLabel("Select a Manager");
		GridBagConstraints gbc_managerSelectLabel = new GridBagConstraints();
		gbc_managerSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_managerSelectLabel.gridx = 0;
		gbc_managerSelectLabel.gridy = 0;
		managerDialog.add(managerSelectLabel, gbc_managerSelectLabel);
		
		JComboBox<String> managerSelect = new JComboBox(managerSelection.toArray());
		GridBagConstraints gbc_managerSelect = new GridBagConstraints();
		gbc_managerSelect.insets = new Insets(0, 0, 5, 0);
		gbc_managerSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_managerSelect.gridx = 0;
		gbc_managerSelect.gridy = 1;
		managerDialog.add(managerSelect, gbc_managerSelect);
		
		JLabel matchSelectLabel = new JLabel("Select team to assign to");
		GridBagConstraints gbc_matchSelectLabel = new GridBagConstraints();
		gbc_matchSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelectLabel.gridx = 0;
		gbc_matchSelectLabel.gridy = 2;
		managerDialog.add(matchSelectLabel, gbc_matchSelectLabel);
		
		JComboBox<String> teamSelect = new JComboBox(teamsSelect.toArray());
		GridBagConstraints gbc_teamSelect = new GridBagConstraints();
		gbc_teamSelect.insets = new Insets(0, 0, 5, 0);
		gbc_teamSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamSelect.gridx = 0;
		gbc_teamSelect.gridy = 3;
		managerDialog.add(teamSelect, gbc_teamSelect);
		
		JLabel conTypeLabel = new JLabel("Contract Type: ");
		
		JComboBox<String> contractSelect = new JComboBox(contractTypes);
		GridBagConstraints gbc_contractSelect = new GridBagConstraints();
		gbc_contractSelect.insets = new Insets(0, 0, 5, 0);
		gbc_contractSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_contractSelect.gridx = 0;
		gbc_contractSelect.gridy = 3;
		managerDialog.add(contractSelect, gbc_contractSelect);
		
		JButton confirmationButton = new JButton("Confirm");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		managerDialog.add(confirmationButton, gbc_confirmationButton);
		
		confirmationButton.addActionListener(e -> {
			if (managers.get(managerSelect.getSelectedIndex()).checkManagerAssigned(db.getConnection())) {
				int areYouSure = JOptionPane.showConfirmDialog(managerDialog, 
						"This manager is already assigned to a team. Are you sure you want to overwrite this?",
						"", JOptionPane.YES_NO_OPTION);
				
				if(areYouSure == JOptionPane.YES_OPTION) { 
					frame.getAdminAccount().assignManagerToTeam(db.getConnection(), 
							teams.get(teamSelect.getSelectedIndex()), 
							managers.get(managerSelect.getSelectedIndex()),
							contractTypes[contractSelect.getSelectedIndex()]); }
			} else { 
				frame.getAdminAccount().assignManagerToTeam(db.getConnection(), 
						teams.get(teamSelect.getSelectedIndex()), 
						managers.get(managerSelect.getSelectedIndex()),
						contractTypes[contractSelect.getSelectedIndex()]); }
			managerDialog.dispose();
		});
		
		managerDialog.setVisible(true);
		return managerDialog;
	}
	
	// Assign stadium to a team as their home ground
	public JDialog getAssignStadiumDialog() {
		JDialog assignStadiumDialog = new JDialog();
		List<String> stadiumSelection = new ArrayList<String>();
	    List<String> teamsSelect = new ArrayList<String>();
		
	    assignStadiumDialog.setModal(true);
	    assignStadiumDialog.setAlwaysOnTop(true);
		assignStadiumDialog.setFocusable(true);
		assignStadiumDialog.setSize(760, 500);
		assignStadiumDialog.setTitle("Assign Stadium");
        assignStadiumDialog.setLayout(new GridBagLayout());
		
		List<Stadium> stadiums = db.getAllStadiums();
        for(Stadium i : stadiums) { stadiumSelection.add(i.getStadiumName()); }
        
        List<Team> teams = db.getAllTeams();
        for(Team i : teams) { teamsSelect.add(i.getName()); }
		
		JLabel refSelectLabel = new JLabel("Select a stadium: ");
		GridBagConstraints gbc_refSelectLabel = new GridBagConstraints();
		gbc_refSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_refSelectLabel.gridx = 0;
		gbc_refSelectLabel.gridy = 0;
		assignStadiumDialog.add(refSelectLabel, gbc_refSelectLabel);
		
		JComboBox<String> stadiumSelect = new JComboBox(stadiumSelection.toArray());
		GridBagConstraints gbc_refSelect = new GridBagConstraints();
		gbc_refSelect.insets = new Insets(0, 0, 5, 0);
		gbc_refSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_refSelect.gridx = 0;
		gbc_refSelect.gridy = 1;
		assignStadiumDialog.add(stadiumSelect, gbc_refSelect);
		
		JLabel matchSelectLabel = new JLabel("Select team to assign to: ");
		GridBagConstraints gbc_matchSelectLabel = new GridBagConstraints();
		gbc_matchSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelectLabel.gridx = 0;
		gbc_matchSelectLabel.gridy = 2;
		assignStadiumDialog.add(matchSelectLabel, gbc_matchSelectLabel);
		
		JComboBox<String> matchSelect = new JComboBox(teamsSelect.toArray());
		GridBagConstraints gbc_matchSelect = new GridBagConstraints();
		gbc_matchSelect.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_matchSelect.gridx = 0;
		gbc_matchSelect.gridy = 3;
		assignStadiumDialog.add(matchSelect, gbc_matchSelect);
		
		JButton confirmationButton = new JButton("Confirm");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		assignStadiumDialog.add(confirmationButton, gbc_confirmationButton);
		
		confirmationButton.addActionListener(e -> {
			if (teams.get(matchSelect.getSelectedIndex()).checkStadiumAssigned(db.getConnection())) {
				int areYouSure = JOptionPane.showConfirmDialog(assignStadiumDialog, 
						"This team already has a stadium assigned. Are you sure you want to overwrite this?",
						"", JOptionPane.YES_NO_OPTION);
				
				if(areYouSure == JOptionPane.YES_OPTION) { 
					frame.getAdminAccount().assignStadium(db.getConnection(), 
							teams.get(matchSelect.getSelectedIndex()), 
							stadiums.get(stadiumSelect.getSelectedIndex())); }
			} else { 
				frame.getAdminAccount().assignStadium(db.getConnection(), 
						teams.get(matchSelect.getSelectedIndex()), 
						stadiums.get(stadiumSelect.getSelectedIndex())); }
        	assignStadiumDialog.dispose();
		});
		
		assignStadiumDialog.setVisible(true);
		return assignStadiumDialog;
	}
	
	// Dialog for creating stadiums
	public JDialog getStadiumDialog() {
		JDialog stadiumDialog = new JDialog();
		
		List<Stadium> stadiums;
	    List<String> stadiumSelection = new ArrayList<String>();
	    Insets insets = frame.getInsets();
	    
	    stadiumDialog.setAlwaysOnTop(true);
	    stadiumDialog.setFocusable(true);
	    stadiumDialog.setSize(450, 500);
	    stadiumDialog.setModal(true);
	    stadiumDialog.setTitle("Stadiums");
        
	    stadiums = db.getAllStadiums();
        for(Stadium i : stadiums) { stadiumSelection.add(i.getStadiumName()); }
       
        GridBagLayout StadiumDialogLayout = new GridBagLayout();
        stadiumDialog.setLayout(StadiumDialogLayout);
        
        JLabel createStadiumLabel = new JLabel("Create Stadium");
        GridBagConstraints gbc_createStadiumLabel = new GridBagConstraints();
        gbc_createStadiumLabel.insets = insets;
        gbc_createStadiumLabel.gridx = 1;
        gbc_createStadiumLabel.gridy = 0;
        stadiumDialog.add(createStadiumLabel, gbc_createStadiumLabel);
        
        JLabel stadiumNameLabel = new JLabel("Stadium Name: ");
        GridBagConstraints gbc_stadiumNameLabel = new GridBagConstraints();
        gbc_stadiumNameLabel.insets = insets;
        gbc_stadiumNameLabel.gridx = 1;
        gbc_stadiumNameLabel.gridy = 1;
        stadiumDialog.add(stadiumNameLabel, gbc_stadiumNameLabel);;
        
        JTextField stadiumNameField = new JTextField();
        GridBagConstraints gbc_stadiumNameField = new GridBagConstraints();
        gbc_stadiumNameField.insets = insets;
        gbc_stadiumNameField.gridx = 1;
        gbc_stadiumNameField.gridy = 2;
        stadiumDialog.add(stadiumNameField, gbc_stadiumNameField);
        stadiumNameField.setColumns(15);
        
        JComboBox<String> stadiumSelect = new JComboBox(stadiumSelection.toArray());
        GridBagConstraints gbc_stadiumSelect = new GridBagConstraints();
        gbc_stadiumSelect.insets = insets;
        gbc_stadiumSelect.gridx = 3;
        gbc_stadiumSelect.gridy = 2;
        stadiumDialog.add(stadiumSelect, gbc_stadiumSelect);

        JLabel capacityLabel = new JLabel("Capacity: ");
        GridBagConstraints gbc_capacityLabel = new GridBagConstraints();
        gbc_capacityLabel.insets = insets;
        gbc_capacityLabel.gridx = 1;
        gbc_capacityLabel.gridy = 3;
        stadiumDialog.add(capacityLabel, gbc_capacityLabel);
        
        JTextField capacityField = new JTextField();
        GridBagConstraints gbc_capacityField = new GridBagConstraints();
        gbc_capacityField.insets = insets;
        gbc_capacityField.gridx = 1;
        gbc_capacityField.gridy = 4;
        stadiumDialog.add(capacityField, gbc_capacityField);
        capacityField.setColumns(15);
        
        JLabel locationLabel = new JLabel("Location: ");
        GridBagConstraints gbc_locationLabel = new GridBagConstraints();
        gbc_locationLabel.insets = insets;
        gbc_locationLabel.gridx = 1;
        gbc_locationLabel.gridy = 5;
        stadiumDialog.add(locationLabel, gbc_locationLabel);
        
        JTextField locationField = new JTextField();
        GridBagConstraints gbc_locationField= new GridBagConstraints();
        gbc_locationField.insets = insets;
        gbc_locationField.gridx = 1;
        gbc_locationField.gridy = 6;
        stadiumDialog.add(locationField, gbc_locationField);
        locationField.setColumns(15);
        
        JButton addBut = new JButton("Create");
        GridBagConstraints gbc_addBut = new GridBagConstraints();
        gbc_addBut.insets = new Insets(0, 0, 0, 5);
        gbc_addBut.gridx = 1;
        gbc_addBut.gridy = 8;
        stadiumDialog.add(addBut, gbc_addBut);
        
        JLabel deleteManagerLabel = new JLabel("Delete Stadium");
        GridBagConstraints gbc_deleteManagerLabel = new GridBagConstraints();
        gbc_deleteManagerLabel.insets = insets;
        gbc_deleteManagerLabel.gridx = 3;
        gbc_deleteManagerLabel.gridy = 0;
        stadiumDialog.add(deleteManagerLabel, gbc_deleteManagerLabel);
        
        JLabel stadiumToRemoveLabel = new JLabel("Stadium:");
        GridBagConstraints gbc_stadiumToRemoveLabel = new GridBagConstraints();
        gbc_stadiumToRemoveLabel.insets = insets;
        gbc_stadiumToRemoveLabel.gridx = 3;
        gbc_stadiumToRemoveLabel.gridy = 1;
        stadiumDialog.add(stadiumToRemoveLabel, gbc_stadiumToRemoveLabel);
        
        JButton delStadiumButton = new JButton("Delete");
        GridBagConstraints gbc_delStadiumButton = new GridBagConstraints();
        gbc_delStadiumButton.insets = insets;
        gbc_delStadiumButton.gridx = 3;
        gbc_delStadiumButton.gridy = 3;
        stadiumDialog.add(delStadiumButton, gbc_delStadiumButton);
        
        addBut.addActionListener(e -> {
        	frame.getAdminAccount().createStadium(db.getConnection(), 
        			stadiumNameField.getText(), capacityField.getText(), locationField.getText());
        	stadiumDialog.dispose();
        });
        
        delStadiumButton.addActionListener(e -> {
        	frame.getAdminAccount().removeStadium(db.getConnection(), 
        			stadiums.get(stadiumSelect.getSelectedIndex()));
        	stadiumDialog.dispose();
        });
		
		stadiumDialog.setVisible(true);
		return stadiumDialog;
	}
	
	// Dialog for removing players and managers from teams
	public JDialog getRemoveFromTeamDialog() {
		JDialog dialog = new JDialog();
		List<String> managerSelect = new ArrayList<String>();
		List<String> playerSelect = new ArrayList<String>();
		
		dialog.setModal(true);
		dialog.setAlwaysOnTop(true);
		dialog.setFocusable(true);
		dialog.setSize(760, 500);
		dialog.setTitle("Unassign");
		dialog.setLayout(new GridBagLayout());
			
		List<Manager> managers = db.allTeamManagers();
        for(Manager i : managers) { managerSelect.add(i.getFullName()); }
        
        List<Player> players = db.allTeamPlayers();
        for(Player i : players) { playerSelect.add(i.getFullName()); }
		
		JLabel managerSelectLabel = new JLabel("Select a Manager");
		GridBagConstraints gbc_managerSelectLabel = new GridBagConstraints();
		gbc_managerSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_managerSelectLabel.gridx = 0;
		gbc_managerSelectLabel.gridy = 0;
		dialog.add(managerSelectLabel, gbc_managerSelectLabel);
		
		JComboBox<String> managerSelected = new JComboBox(managerSelect.toArray());
		GridBagConstraints gbc_managerSelect = new GridBagConstraints();
		gbc_managerSelect.insets = new Insets(0, 0, 5, 0);
		gbc_managerSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_managerSelect.gridx = 0;
		gbc_managerSelect.gridy = 1;
		dialog.add(managerSelected, gbc_managerSelect);
		
		JButton confirmationButton = new JButton("Remove Manager");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		dialog.add(confirmationButton, gbc_confirmationButton);
		
		JLabel playerSelectLabel = new JLabel("Select a Player");
		GridBagConstraints gbc_playerSelectLabel = new GridBagConstraints();
		gbc_playerSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_playerSelectLabel.gridx = 1;
		gbc_playerSelectLabel.gridy = 0;
		dialog.add(playerSelectLabel, gbc_playerSelectLabel);
		
		JComboBox<String> playerSelected = new JComboBox(playerSelect.toArray());
		GridBagConstraints gbc_playerSelected = new GridBagConstraints();
		gbc_playerSelected.insets = new Insets(0, 0, 5, 0);
		gbc_playerSelected.fill = GridBagConstraints.HORIZONTAL;
		gbc_playerSelected.gridx = 1;
		gbc_playerSelected.gridy = 1;
		dialog.add(playerSelected, gbc_playerSelected);
		
		JButton playerConfirmationButton = new JButton("Remove Player");
		GridBagConstraints gbc_playerConfirmationButton = new GridBagConstraints();
		gbc_playerConfirmationButton.insets = new Insets(0, 0, 5, 0);
		gbc_playerConfirmationButton.gridx = 1;
		gbc_playerConfirmationButton.gridy = 4;
		dialog.add(playerConfirmationButton, gbc_playerConfirmationButton);
		
		confirmationButton.addActionListener(e -> {
				int areYouSure = JOptionPane.showConfirmDialog(dialog, 
						"Are you sure you want to overwrite this?",
						"", JOptionPane.YES_NO_OPTION);
				
			if(areYouSure == JOptionPane.YES_OPTION) { 
				frame.getAdminAccount().unassignManagerFromTeam(db.getConnection(), 
						managers.get(managerSelected.getSelectedIndex()));
				dialog.dispose();}
		});
		
		playerConfirmationButton.addActionListener(e -> {
			int areYouSure = JOptionPane.showConfirmDialog(dialog, 
					"Are you sure you want to overwrite this?",
					"", JOptionPane.YES_NO_OPTION);
			
			if(areYouSure == JOptionPane.YES_OPTION) { 
				frame.getAdminAccount().unassignPlayerFromTeam(db.getConnection(), 
						players.get(playerSelected.getSelectedIndex())); 
				dialog.dispose();}
			});
		
		dialog.setVisible(true);
		return dialog;
	}
	
}
