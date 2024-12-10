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
	
	public static void recordMatchScore(Match match, String score) {
		
	}
	
	public static void matchToResult(Match match, MatchEvent[] events) {
		
	}
	
	public static void recordMatchEvents() {
		
	}
	
	public static JDialog getMatchEventDialog(JfgpWindow frame, Match match) {
		JDialog matchEventDialog = new JDialog();
		String[] eventTypes = {"Goal", "Assist", "Foul", "Yellow Card", "Red Card"};
		List<String> playersList = new ArrayList<String>();
		
		List<Player> homeTeamPlayers = leagueData.getTeamPlayers(frame.getDbConnection(), match.getHomeTeam()); 
        for(Player i : homeTeamPlayers) { playersList.add(match.getHomeTeam().getName() + " " + i.getFullName()); }
        
        List<Player> awayTeamPlayers = leagueData.getTeamPlayers(frame.getDbConnection(), match.getAwayTeam()); 
        for(Player i : awayTeamPlayers) { playersList.add(match.getAwayTeam().getName() + " " + i.getFullName()); }
		
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
		
		
		matchEventDialog.setVisible(true);
		return matchEventDialog;
	}
}
