package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import accounts.RefereeAccount;
import leagueMembers.Referee;
import java.awt.GridBagConstraints;

@SuppressWarnings("serial")
public class RefereePanel extends panel {

	List<String> refereeButtons = new ArrayList<String>(List.of("Record Matches", "View My Upcoming Fixtures"));
	Referee referee;

	public RefereePanel() { initialise(); }
	
	public RefereePanel(RefereeAccount referee) { initialise(); }
	
	@Override
	public void initialise() {
		setButtonNames(refereeButtons);
		setPanel(new JPanel());
		getPanel().setLayout(new GridBagLayout());
		setInsets(new Insets(0, 0, 10, 25));
		setFont(new Font("Tahoma", Font.PLAIN, 25));
		setLayout(new GridBagLayout());
		
		panelButton = new JButton[getButtonNames().size()];
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
		
		JList toAttendList = new JList();
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
		
		JList toRecordList = new JList();
		GridBagConstraints gbc_toRecordList = new GridBagConstraints();
		gbc_toRecordList.fill = GridBagConstraints.BOTH;
		gbc_toRecordList.insets = getInsets();
		gbc_toRecordList.gridx = 0;
		gbc_toRecordList.gridy = 3;
		panel.add(toRecordList, gbc_toRecordList);
		
	}
	
	@Override
	public void addActionListeners() {
		
		
		
	}
}
