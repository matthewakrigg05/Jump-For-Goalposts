package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JFGPdb {
	
	public JFGPdb() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:./JFGP.db");
			// https://www.sqliz.com/posts/java-basic-sqlite/
			String createleaguetable = "CREATE TABLE IF NOT EXISTS league(\r\n"
					+ "	leagueId INTEGER NOT NULL,\r\n"
					+ "	leagueName VARCHAR(50), \r\n"
					+ "	PRIMARY KEY (leagueId)\r\n"
					+ "	);";
            PreparedStatement leaguepreparedStatement = connection.prepareStatement(createleaguetable);
            leaguepreparedStatement.executeUpdate();
            
            String createUserAccountTable = "CREATE TABLE IF NOT EXISTS userAccounts(\r\n"
            		+ "	userId INTEGER NOT NULL,\r\n"
            		+ "	userType VARCHAR(25) NOT NULL,\r\n"
            		+ "	emailAddress VARCHAR(200) NOT NULL,\r\n"
            		+ "	password VARCHAR(50) NOT NULL,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (userId),\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            PreparedStatement preparedStatement = connection.prepareStatement(createUserAccountTable);
            preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
