package gui.admin;
import javax.swing.*;
import leagueDB.matchData;
import leagueDB.refereeData;

public class assignRefDialog extends JDialog implements matchData, refereeData {
	
	public assignRefDialog() {}

	public static void initialise() {
		// select season, match, referee and check whether ref is already assigned to that gameweek - then offer confirmation option to the user
		
		// need a matchweek drop down, matches in matchweek drop down, refs can only be set for matches in current season
	}
}
