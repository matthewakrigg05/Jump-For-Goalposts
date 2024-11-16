package leagueDB;
import java.io.FileReader;
import java.sql.Connection;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.sql.DriverManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class JFGPdb {
	
	public JFGPdb() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:./JFGP.db");
			
			try {
				// Initialize object for ScripRunner
				ScriptRunner sr = new ScriptRunner(connection);

				// Give the input file to Reader
				Reader reader = new BufferedReader(
	                               new FileReader("E:/CW1_CIS2002/Jump-For-Goalposts/src/sqlQueries/tables.sql/"));

				// Exctute script
				sr.runScript(reader);

			} catch (Exception e) {
				System.err.println("Failed to Execute sqlQueries/tables.sql"
						+ " The error is " + e.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
