package accounts;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import gui.JfgpWindow;
import league.Match;
import league.MatchEvent;
import leagueDB.leagueData;
import leagueMembers.Player;

public interface IRefereeRole {
	
	public static Match  matchToResult(Connection connection, Match match, int homeScore, int awayScore) {
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
	
	public static void recordMatchEvents(Connection connection, List<MatchEvent> matchEvents, Match match) {
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
	
	public static MatchEvent getMatchEventDialog(JfgpWindow frame, Match match) {
		JDialog matchEventDialog = new JDialog();
		MatchEvent newEvent = new MatchEvent();
		
		String[] eventTypes = {"Goal", "Assist", "Foul", "Yellow Card", "Red Card"};
		List<String> playersList = new ArrayList<String>();
		List<Player> players = new ArrayList<Player>();
		
		List<Player> homeTeamPlayers = leagueData.getTeamPlayers(frame.getDbConnection(), match.getHomeTeam()); 
        for(Player i : homeTeamPlayers) { 
        	playersList.add(match.getHomeTeam().getName() + " " + i.getFullName());
        	players.add(i); }
        
        List<Player> awayTeamPlayers = leagueData.getTeamPlayers(frame.getDbConnection(), match.getAwayTeam()); 
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
		
		// type, minute, team & player, add event
		
		JLabel newEventLabel = new JLabel("Add New Match Event");
		
		JLabel eventTypeLabel = new JLabel("Choose a type of match event: ");
		JComboBox eventSelect = new JComboBox(eventTypes);
		
		JLabel addEventMinuteLabel = new JLabel("Minute of event: ");
		JTextArea minuteArea = new JTextArea();
		
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
		});
		
		matchEventDialog.setVisible(true);
		return newEvent;
	}
}
