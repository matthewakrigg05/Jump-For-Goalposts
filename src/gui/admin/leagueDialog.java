package gui.admin;
import league.League;
import leagueDB.JFGPdb;
import leagueDB.leagueData;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

@SuppressWarnings("serial")
public class leagueDialog extends JDialog implements leagueData {

	private League league;
	
	public leagueDialog() {
		initialise();
	}
	
	public void initialise() {
		league = leagueData.getLeague(new JFGPdb());
        
		setFocusable(true);
        setSize(650, 375);
        setTitle(league.getLeagueName());
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JLabel lblNewLabel = new JLabel("Hello + ");
        lblNewLabel.setBounds(0, 0, 45, 13);
        getContentPane().add(lblNewLabel);
        setVisible(true);

        
        // need league name, edit button, save button
        // league stores all teams that ever featured in this league, not just ones in current season
	}
}
