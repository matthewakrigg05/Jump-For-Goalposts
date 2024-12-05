package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import accounts.AdminAccount;
import accounts.RefereeAccount;
import accounts.ManagerAccount;
import gui.JfgpWindow;


public class JFGPdb implements dbInitMethods {
	
	private static Connection connection;
	
	public JFGPdb() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:./JFGP.db");
			dbInitMethods.initTables(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public Connection getConnection() { return connection; }
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
