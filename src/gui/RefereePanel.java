package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import accounts.RefereeAccount;
import league.Match;
import leagueDB.matchData;
import leagueDB.refereeData;
import leagueMembers.Referee;
import java.awt.GridBagConstraints;

@SuppressWarnings("serial")
public class RefereePanel extends panel {

	private JList toAttendList;
	private JList toRecordList;
	private Referee referee;
	Connection connection;
	private List<String> refereeButtons = new ArrayList<String>(List.of("Record Matches", "View My Upcoming Fixtures"));
	private List<Match> matchesToAttend = new ArrayList<Match>(); 
	private List<String> matchSummaries = new ArrayList<String>();
	
	public RefereePanel(JfgpWindow frame) { 
		this.connection = frame.getDbConnection();
		this.referee = refereeData.getReferee(frame.getDbConnection(), frame.getRefereeAccount());
		initialise();
		}
	
	@Override
	public void initialise() {
		setButtonNames(refereeButtons);
		setPanel(new JPanel());
		getPanel().setLayout(new GridBagLayout());
		setInsets(new Insets(0, 0, 10, 25));
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		setLayout(new GridBagLayout());
		
		panelButton = new JButton[getButtonNames().size()];
		matchesToAttend = matchData.getNextFiveRefMatches(connection, referee);
		
		if (matchesToAttend.size() == 0) { this.matchSummaries.add("You have no matches to attend..."); }
		else { for (Match match : matchesToAttend) { matchSummaries.add(match.getMatchSummary()); } }
		
		addPanelComponents(getPanel());
		addActionListeners();
	}
	
	@Override
	public void addPanelComponents(JPanel panel) {
		
		JLabel nextFiveGamesLabel = new JLabel("Your next five matches to attend:");
		GridBagConstraints gbc_nextFiveGamesLabel = new GridBagConstraints();
		gbc_nextFiveGamesLabel.insets = getInsets();
		gbc_nextFiveGamesLabel.gridx = 0;
		gbc_nextFiveGamesLabel.gridy = 0;
		panel.add(nextFiveGamesLabel, gbc_nextFiveGamesLabel);
		
		toAttendList = new JList(matchSummaries.toArray());
		GridBagConstraints gbc_toAttendList = new GridBagConstraints();
		gbc_toAttendList.insets = getInsets();
		gbc_toAttendList.fill = GridBagConstraints.BOTH;
		gbc_toAttendList.gridx = 0;
		gbc_toAttendList.gridy = 1;
		panel.add(toAttendList, gbc_toAttendList);
		
		JLabel matchesToRecordLabel = new JLabel("Matches to be recorded: ");
		GridBagConstraints gbc_matchesToRecordLabel = new GridBagConstraints();
		gbc_matchesToRecordLabel.insets = getInsets();
		gbc_matchesToRecordLabel.gridx = 0;
		gbc_matchesToRecordLabel.gridy = 2;
		panel.add(matchesToRecordLabel, gbc_matchesToRecordLabel);
		
		toRecordList = new JList(matchSummaries.toArray());
		GridBagConstraints gbc_toRecordList = new GridBagConstraints();
		gbc_toRecordList.fill = GridBagConstraints.BOTH;
		gbc_toRecordList.insets = getInsets();
		gbc_toRecordList.gridx = 0;
		gbc_toRecordList.gridy = 3;
		panel.add(toRecordList, gbc_toRecordList);
		
	}
	
	@Override
	public void addActionListeners() {	
		toAttendList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				toAttendList.getSelectedIndex();
				
			}
		});
		
		toRecordList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				new recordMatchPanel(matchesToAttend.get(toAttendList.getSelectedIndex())).setVisible(true);
			}
		});
	}
}
