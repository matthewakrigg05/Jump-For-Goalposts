import gui.JfgpWindow;
import leagueDB.JFGPdb;
 
public class JumpForGoalposts {

	public static void main(String[] args) {

		JFGPdb dbConnection = new JFGPdb();
		
		try {
			JfgpWindow window = new JfgpWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
