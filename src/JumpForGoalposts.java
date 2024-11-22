import gui.JfgpWindow;
import gui.logInWindow;
import leagueDB.JFGPdb;
 
public class JumpForGoalposts {

	public static void main(String[] args) {

		JFGPdb dbConnection = new JFGPdb();
		
		try {
			new JfgpWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
