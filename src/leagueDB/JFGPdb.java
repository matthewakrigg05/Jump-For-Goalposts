package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import accounts.*;
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
	                
	                // log in as the correct kind of user - different views in the application depending on the type of user
	                switch (userType) {
	                	case "admin":
	                		new JfgpWindow(new AdminAccount(userId, email, password));
	                		break;
	                		
	                	case "referee":	        	            
	                		RefereeAccount refLogIn = new RefereeAccount(userId, email, password);	                		
	                		new JfgpWindow(refLogIn).setVisible(true);
	                		break;
	                		
	                	case "manager":	        	       
	                		ManagerAccount managerLogIn = new ManagerAccount(userId, email, password);
	                		new JfgpWindow(managerLogIn).setVisible(true);
	                		break;
	                	
	                	default:
	                		break;
	                }
	            }
	            else { 
		            JfgpWindow window = new JfgpWindow();
	        		window.setVisible(true);
	        		JOptionPane.showMessageDialog(window, "Log In Failed");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
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
