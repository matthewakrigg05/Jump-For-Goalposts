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
	
	public static void logIn(String email, String password) {
		JFGPdb db = new JFGPdb();
		connection = db.getConnection();
		
		try {
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                    "SELECT * FROM userAccounts WHERE emailAddress = ? AND password = ?"
	            );

	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                int userId = resultSet.getInt("userId");
	                String userType = resultSet.getString("userType");
	                db.closeConnection();
	                
	                switch (userType) {
	                	case "admin":
	                		new JfgpWindow(new AdminAccount(userId, email, password));
	                		break;
	                		
	                	case "referee":
	                		new JfgpWindow(new RefereeAccount(userId, email, password, refereeData.getRefereeFromId(userId)));
	                		break;
	                		
	                	case "manager":
	                		new JfgpWindow(new ManagerAccount(userId, email, password));
	                		break;
	                	
	                	default:
	                		JfgpWindow window = new JfgpWindow();
	                		JOptionPane.showMessageDialog(window, "Log In Failed");
	                }
	            }
	            else { 
		            JfgpWindow window = new JfgpWindow();
	        		JOptionPane.showMessageDialog(window, "Log In Failed");
	        		db.closeConnection();
	            }
	        } catch (SQLException e) { e.printStackTrace(); }
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
