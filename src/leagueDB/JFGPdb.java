package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;


public class JFGPdb implements dbInitMethods {
	
	public JFGPdb() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:./JFGP.db");
			
			dbInitMethods.initTables(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
