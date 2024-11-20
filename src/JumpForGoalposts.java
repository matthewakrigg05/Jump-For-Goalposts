import gui.JfgpWindow;
import leagueDB.JFGPdb;
 
public class JumpForGoalposts {

	public static void main(String[] args) {

		JFGPdb dbConnection = new JFGPdb();
		
		try {
			JfgpWindow window = new JfgpWindow("Home");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
