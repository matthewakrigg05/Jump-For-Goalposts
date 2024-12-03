import gui.JfgpWindow;
import leagueDB.JFGPdb;

public class JumpForGoalposts {

	public static void main(String[] args) {

		JFGPdb dbConnection = new JFGPdb();
		dbConnection.closeConnection();
		
		try { new JfgpWindow().setVisible(true); } catch (Exception e) { e.printStackTrace(); }
	}
}
