package accounts;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import gui.JfgpWindow;
import league.Match;
import league.MatchEvent;
import leagueMembers.Player;
import leagueMembers.Referee;

public class RefereeAccount extends Account {
	
	public RefereeAccount(int id, String emailAddress, String password) { super(id, emailAddress, password); }
	
	/*
	 * Retrieves the referee from the database that is associated with the instance of the referee
	 * using the user id of the account and returns the referee.
	 */
	public Referee getReferee(Connection connection) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	        refStatement.setInt(1, getId());
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		this
	        		);
	        
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	/*
	 * Updates information in the matches table so that the match can be recognised as a result
	 * rather than a fixture, as well as automatically deciding the outcome of the match using the 
	 * score.
	 * 
	 * @param connection 	Connection to the database.
	 * 
	 * @param match			The match which is being updated in order to register as a result.
	 * 
	 * @param homeScore		Score for the home team.
	 * 
	 * @param awayScore		Score for the away team.
	 */
	public Match matchToResult(Connection connection, Match match, int homeScore, int awayScore) {
		String matchOutcome;
		
		if (homeScore > awayScore) { matchOutcome = "Home Win"; }
		else if (homeScore == awayScore) { matchOutcome = "Draw"; }
		else { matchOutcome = "Away Win"; }
		
		try {			
			PreparedStatement isCompleteUpdate = connection.prepareStatement(
					"UPDATE matches SET isComplete = 1, score = ?, matchOutcome = ? WHERE matchId = ?");
			isCompleteUpdate.setString(1, String.valueOf(homeScore) + "-" + String.valueOf(awayScore));
			isCompleteUpdate.setString(2, matchOutcome);
			isCompleteUpdate.setInt(3, match.getMatchId());
			isCompleteUpdate.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }	
		
		return match;
	}
	
	/*
	 * Takes a list of match events and uses the instances to add them to the database.
	 * 
	 * @param connection 	Connection to the database permitting interaction with it.
	 * 
	 * @param matchEvents	The list of events that occurred in the match being added to the database.
	 * 
	 * @param match			The match that is having the event added to it.
	 */
	public void recordMatchEvents(Connection connection, List<MatchEvent> matchEvents, Match match) {
		for (MatchEvent event : matchEvents) {
			try {
				PreparedStatement eventStatement = connection.prepareStatement(
						"INSERT INTO matchEvents(eventType, eventMinute, teamId, playerId, matchId) VALUES (?, ?, ?, ?, ?)");
				eventStatement.setString(1, event.getEventType());
				eventStatement.setInt(2, event.getEventMinute());
				eventStatement.setInt(3, event.getTeam().getTeamId());
				eventStatement.setInt(4, event.getPlayerInvolved().getId());
				eventStatement.setInt(5, match.getMatchId());
				eventStatement.executeUpdate();
				
			} catch (SQLException e) { e.printStackTrace(); }	
		}
	}
	
	/*
	 * Generates the dialog box to add the details of the match event and returns the instance of match event
	 * that has been created using the dialog box. This can then be used when adding the match and match events
	 * to the database upon recording the match.
	 * 
	 * @param frame 	The main window holding the database and account information.
	 * 
	 * @param match		The match that is having the event added to it.
	 */
	public MatchEvent matchEventDialog(JfgpWindow frame, Match match) {
		JDialog matchEventDialog = new JDialog();
		MatchEvent newEvent = new MatchEvent();
		
		// types of event in the match
		String[] eventTypes = {"Goal", "Assist", "Foul", "Yellow Card", "Red Card"};
		List<String> playersList = new ArrayList<String>();
		
		// needs separate list so that the player can be found in the home and away teams afterwards
		List<Player> players = new ArrayList<Player>(); 
															
		
		List<Player> homeTeamPlayers = match.getHomeTeam().getTeamPlayers(frame.getDb().getConnection()); 
        for(Player i : homeTeamPlayers) { 
        	playersList.add(match.getHomeTeam().getName() + " " + i.getFullName());
        	players.add(i); }
        
        List<Player> awayTeamPlayers = match.getAwayTeam().getTeamPlayers(frame.getDb().getConnection()); 
        for(Player i : awayTeamPlayers) { 
        	playersList.add(match.getAwayTeam().getName() + " " + i.getFullName());
        	players.add(i);
        }
		
		matchEventDialog.setModal(true);
		matchEventDialog.setAlwaysOnTop(true);
		matchEventDialog.setFocusable(true);
		matchEventDialog.setSize(760, 500);
		matchEventDialog.setTitle("Match Event");
		matchEventDialog.setLayout(new GridBagLayout());
		
		JLabel newEventLabel = new JLabel("Add New Match Event");
		
		JLabel eventTypeLabel = new JLabel("Choose a type of match event: ");
		JComboBox eventSelect = new JComboBox(eventTypes);
		
		JLabel addEventMinuteLabel = new JLabel("Minute of event: ");
		JTextArea minuteArea = new JTextArea();
		minuteArea.setColumns(10);
		
		JLabel playerLabel = new JLabel("Player Involved: ");
		JComboBox playerSelect = new JComboBox(playersList.toArray());
		
		JButton addButton = new JButton("Add");
		
		matchEventDialog.add(newEventLabel);
		matchEventDialog.add(eventTypeLabel);
		matchEventDialog.add(eventSelect);
		matchEventDialog.add(addEventMinuteLabel);
		matchEventDialog.add(minuteArea);
		matchEventDialog.add(playerLabel);
		matchEventDialog.add(playerSelect);
		matchEventDialog.add(addButton);

		addButton.addActionListener(e -> {
			
			try 
			{ 
				Integer.parseInt(minuteArea.getText()); 
				
				if (homeTeamPlayers.contains(players.get(playerSelect.getSelectedIndex()))) {
					newEvent.setTeam(match.getHomeTeam());
				}
				else {
					newEvent.setTeam(match.getAwayTeam());
				}
				
				newEvent.setEventType(eventTypes[eventSelect.getSelectedIndex()]);
				newEvent.setEventMinute(Integer.parseInt(minuteArea.getText()));
				newEvent.setPlayerInvolved(players.get(playerSelect.getSelectedIndex()));
				matchEventDialog.dispose();
				
				JOptionPane.showMessageDialog(frame, "Match Event recorded!");
			}  
			catch (NumberFormatException notInt)  
			{ 
				JOptionPane.showMessageDialog(frame, "Please make sure the minute is a valid integer!");
			} 
		});
		
		matchEventDialog.setVisible(true);
		return newEvent;
	}
}
