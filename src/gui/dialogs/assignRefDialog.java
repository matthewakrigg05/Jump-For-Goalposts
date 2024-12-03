package gui.dialogs;
import javax.swing.*;
import league.Match;
import league.Season;
import leagueDB.matchData;
import leagueDB.refereeData;
import leagueDB.seasonData;
import leagueMembers.Referee;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class assignRefDialog extends JDialog implements matchData, refereeData, seasonData {
	
	List<Referee> referees;
    List<String> refSelection = new ArrayList<String>();
    List<Match> nextFiveGameWeeks;
    List<String> matches = new ArrayList<String>();
    Season currentSeason;
	
	public assignRefDialog() { initialise(); }
		
	public  void initialise() {
		setAlwaysOnTop(true);
		setFocusable(true);
        setSize(760, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Assign Referees");
		
		referees = refereeData.getAllReferees();
        for(Referee i : referees) { refSelection.add(i.getFullName()); }
        
        currentSeason = seasonData.getCurrentSeason();
        
        nextFiveGameWeeks = matchData.getNextFiveGameWeeks(currentSeason, 1);
        for(Match i : nextFiveGameWeeks) { matches.add(i.getMatchSummary()); }
        
        GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		
		JLabel refSelectLabel = new JLabel("Select a Referee");
		GridBagConstraints gbc_refSelectLabel = new GridBagConstraints();
		gbc_refSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_refSelectLabel.gridx = 0;
		gbc_refSelectLabel.gridy = 0;
		getContentPane().add(refSelectLabel, gbc_refSelectLabel);
		
		JComboBox refSelect = new JComboBox(refSelection.toArray());
		GridBagConstraints gbc_refSelect = new GridBagConstraints();
		gbc_refSelect.insets = new Insets(0, 0, 5, 0);
		gbc_refSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_refSelect.gridx = 0;
		gbc_refSelect.gridy = 1;
		getContentPane().add(refSelect, gbc_refSelect);
		
		JLabel matchSelectLabel = new JLabel("Select match to assign to");
		GridBagConstraints gbc_matchSelectLabel = new GridBagConstraints();
		gbc_matchSelectLabel.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelectLabel.gridx = 0;
		gbc_matchSelectLabel.gridy = 2;
		getContentPane().add(matchSelectLabel, gbc_matchSelectLabel);
		
		JComboBox matchSelect = new JComboBox(matches.toArray());
		GridBagConstraints gbc_matchSelect = new GridBagConstraints();
		gbc_matchSelect.insets = new Insets(0, 0, 5, 0);
		gbc_matchSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_matchSelect.gridx = 0;
		gbc_matchSelect.gridy = 3;
		getContentPane().add(matchSelect, gbc_matchSelect);
		
		JButton confirmationButton = new JButton("Confirm");
		GridBagConstraints gbc_confirmationButton = new GridBagConstraints();
		gbc_confirmationButton.gridx = 0;
		gbc_confirmationButton.gridy = 4;
		getContentPane().add(confirmationButton, gbc_confirmationButton);
		
		confirmationButton.addActionListener(e -> {
			if (matchData.checkRefAssigned(nextFiveGameWeeks.get(matchSelect.getSelectedIndex()))) {
				int areYouSure = JOptionPane.showConfirmDialog(this, "This match already has a referee assigned. Are you sure you want to overwrite this?", "", JOptionPane.YES_NO_OPTION);
				
				if(areYouSure == JOptionPane.YES_OPTION) { matchData.assignRef(nextFiveGameWeeks.get(matchSelect.getSelectedIndex()), referees.get(refSelect.getSelectedIndex())); }
			} else { matchData.assignRef(nextFiveGameWeeks.get(matchSelect.getSelectedIndex()), referees.get(refSelect.getSelectedIndex())); }
        	dispose();
		});
	}
}
