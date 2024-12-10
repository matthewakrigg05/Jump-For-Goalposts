package accounts;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import gui.JfgpWindow;
import league.Match;
import league.MatchEvent;
import leagueDB.leagueData;
import leagueMembers.Player;

public interface IRefereeRole {
	
	public static void matchToResult(Match match, MatchEvent[] events) {
		
	}
	
	public static void recordMatchEvents() {
		
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
