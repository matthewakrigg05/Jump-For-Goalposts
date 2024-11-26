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
import league.League;
import leagueMembers.Referee;
import leagueMembers.Manager;


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
	
	public void logIn(String email, String password) {
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
	                		
	                		PreparedStatement refStatement = connection.prepareStatement(
	        	                    "SELECT * FROM referees WHERE userId = ?"
	        	            );

	                		refStatement.setInt(1, userId);
	        	            ResultSet refResult = refStatement.executeQuery();
	        	            
	                		RefereeAccount refLogIn = new RefereeAccount(userId, email, password);
	                		
	                		Referee ref = new Referee(
	        	            		refResult.getInt("refereeId"),
	        	            		refResult.getString("fname"),
	        	            		refResult.getString("lName"), 
	        	            		refResult.getString("preferredLocation"),
	        	            		refLogIn
	        	            		); 
	                		
	                		new JfgpWindow(refLogIn).setVisible(true);
	                		break;
	                		
	                	case "manager":
	                		PreparedStatement managerStatement = connection.prepareStatement(
	        	                    "SELECT * FROM managers WHERE userId = ?");

	                		managerStatement.setInt(1, userId);
	        	            ResultSet managerResult = managerStatement.executeQuery();
	        	            
	        	            Manager manager = new Manager(
	        	            		managerResult.getInt("managerId"),
	        	            		managerResult.getString("fname"),
	        	            		managerResult.getString("lName"), 
	        	            		userId); 
	        	            
//	                		ManagerAccount managerLogIn = new ManagerAccount(userId, email, password, manager);
//	                		new JfgpWindow(managerLogIn).setVisible(true);
	                		break;
	                	
	                	default:
	                		JfgpWindow window = new JfgpWindow();
	                		window.setVisible(true);
	                		JOptionPane.showMessageDialog(window, "Log In Failed");
	                
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
	
	public Connection getConnection() {
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
