package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import league.Match;
import leagueDB.JFGPdb;
import leagueMembers.Referee;
import java.awt.GridBagConstraints;

@SuppressWarnings("serial")
public class RefereePanel extends JPanel implements IPanel {

	JList<String> toAttendList;
	JList<String> toRecordList;
	Referee referee;
	JfgpWindow frame;
	JFGPdb db;
	
	List<String> refereeButtons = new ArrayList<String>(List.of("Record Matches", "View My Upcoming Fixtures"));
	List<Match> matchesToAttend = new ArrayList<Match>(); 
	List<String> matchSummaries = new ArrayList<String>();
	
	public RefereePanel(JfgpWindow frame) { 
		this.frame = frame;
		this.db = frame.getDb();
		this.referee = frame.getRefereeAccount().getReferee(db.getConnection());
		initialise();
		}
	
	@Override
	public void initialise() {
		setLayout(new GridBagLayout());
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		setLayout(new GridBagLayout());
		
		matchesToAttend = referee.getNextFiveRefMatches(db);
		
		if (matchesToAttend.size() == 0) { this.matchSummaries.add("You have no matches to attend..."); }
		else { for (Match match : matchesToAttend) { matchSummaries.add(match.getMatchSummary()); } }
		
		addPanelComponents(this);
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
		
		toAttendList = new JList<String>((String[]) matchSummaries.toArray());
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
		
		toRecordList = new JList<String>((String[]) matchSummaries.toArray());
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
			public void valueChanged(ListSelectionEvent e) { toAttendList.getSelectedIndex(); }
		});
		
		toRecordList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				new recordMatchPanel(frame,matchesToAttend.get(toAttendList.getSelectedIndex()), frame.getRefereeAccount()).setVisible(true);
			}
		});
	}
}
