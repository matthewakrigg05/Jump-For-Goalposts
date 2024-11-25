package gui.admin;
import league.Season;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class seasonDialog extends JDialog {
		
		private JPanel panel;
		
		public seasonDialog(JPanel panel) {
			
		}
		
		public void initialise() {
			setFocusable(true);
	        setSize(650, 375);
	        setModal(true);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setResizable(false);
	        setVisible(true);
	        
	        // need league name, season year, create new seasons - start date, teams in this season
	        // dont allow duplicate teams, season is a set of team ids
	        // edit button, save button
		}
	}
