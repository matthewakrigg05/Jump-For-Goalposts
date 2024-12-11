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
import league.Result;
import leagueDB.leagueData;
import leagueMembers.Player;

public interface IRefereeRole {
	
	public static Result matchToResult(Connection connection, Match match, int homeScore, int awayScore) {
		Result result = new Result(match.getMatchId(), match.getHomeTeam(), match.getAwayTeam(), match.getMatchWeek());
		String matchOutcome;
		
		if (homeScore > awayScore) {
			matchOutcome = "Home Win";
		}
		else if (homeScore == awayScore) {
			matchOutcome = "Draw";
		}
		else {
			matchOutcome = "Away Win";
		}
		
		try {
			PreparedStatement resultStatement = connection.prepareStatement(
					"INSERT INTO results(resultScore, resultOutcome, matchId) VALUES (?, ?, ?)");
			
			resultStatement.setString(1, String.valueOf(homeScore) + "-" + String.valueOf(awayScore));
			resultStatement.setString(2, matchOutcome);
			resultStatement.setInt(3, match.getMatchId());
			resultStatement.executeUpdate();
			
			PreparedStatement isCompleteUpdate = connection.prepareStatement(
					"UPDATE matches SET isComplete = 1 WHERE matchId = ?");
			isCompleteUpdate.setInt(1, match.getMatchId());
			isCompleteUpdate.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return result;
	}
	
	public static void recordMatchEvents(Connection connection, List<MatchEvent> matchEvents, Result result) {
		for (MatchEvent event : matchEvents) {
			try {
				PreparedStatement eventStatement = connection.prepareStatement(
						"INSERT INTO matchEvents(eventType, eventMinute, playerId, resultId) VALUES (?, ?, ?, ?)");
				
				eventStatement.setString(1, event.getEventType());
				eventStatement.setInt(2, event.getEventMinute());
				eventStatement.setInt(3, event.getPlayerInvolved().getId());
				eventStatement.setInt(4, result.getResultId());
				eventStatement.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
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
			newEvent.setEventType(eventTypes[eventSelect.getSelectedIndex()]);
			newEvent.setEventMinute(Integer.parseInt(minuteArea.getText()));
			newEvent.setPlayerInvolved(players.get(playerSelect.getSelectedIndex()));
			matchEventDialog.dispose();
		});
		
		matchEventDialog.setVisible(true);
		return newEvent;
	}
}
