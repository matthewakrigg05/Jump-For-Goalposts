package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;

public class JFGPdb {
	
	public JFGPdb() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:./sample.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}


