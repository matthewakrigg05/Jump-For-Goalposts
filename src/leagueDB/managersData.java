package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import accounts.ManagerAccount;
import leagueMembers.Manager;

public interface managersData {

	public static ManagerAccount getManagerAccount(int id) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement manAccStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'manager';" );
	
	        manAccStatement.setInt(1, id);
	        ResultSet refAccResult = manAccStatement.executeQuery(); 
	        
	        ManagerAccount manAcc = new ManagerAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password")
	        		);
	        
	        connection.closeConnection();
	        		
	        return manAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
		
	}
	
	public static void createManagerAccount(String fname, String lname) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement refAccStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO userAccounts(userType, emailAddress, password, leagueId) VALUES ('manager', ?, ?, 1);");
			
			refAccStatement.setString(1, lname + "@jfgp.org");
			refAccStatement.setString(2, lname + fname);
			refAccStatement.executeUpdate();
			
			PreparedStatement lastId = (connection.getConnection().prepareStatement(
					"SELECT userId FROM userAccounts ORDER BY ROWID DESC limit 1;"));
			
			ResultSet id = lastId.executeQuery();
			
			int manId = id.getInt("userId");
			
			connection.closeConnection();
			
			createManager(fname, lname, manId);
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static Manager getManager(ManagerAccount manAcc) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement manStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM manager WHERE userId = ?;" );
	
	        manStatement.setInt(1, manAcc.getId());
	        ResultSet manResult = manStatement.executeQuery(); 
	        
	        Manager man = new Manager(
	        		manResult.getInt("managerId"),
	        		manResult.getString("fname"),
	        		manResult.getString("lName"),
	        		manResult.getInt("userId")
	        		);
	        
	        connection.closeConnection();
			return man;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static void createManager(String fname, String lname, int id) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement manStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO managers(fName, lName, userId) VALUES (?, ?, ?);");
			
			manStatement.setString(1, fname);
			manStatement.setString(2, lname);
			manStatement.setInt(3, id);
			manStatement.executeUpdate();
			
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static List<Manager> getAllManagers() {
		JFGPdb connection = new JFGPdb();
		List<Manager> managers = new ArrayList<Manager>();
		
		try {
			PreparedStatement manStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM managers;");
			ResultSet manResult = manStatement.executeQuery();
			
			while(manResult.next()) {
				Manager man = new Manager(
						manResult.getInt("managerId"),
						manResult.getString("fname"),
						manResult.getString("lName"), 
						manResult.getInt("userId")
		        		);
				
				managers.add(man);
			}
			
			connection.closeConnection();
			
			for(Manager man : managers) { man.setManAcc(getManagerAccount(man.getUserId())); }
					
			return managers;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static void removeManager(Manager man) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			
			seasonStatement.setInt(1, man.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = (connection.getConnection()).prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			
			refAccDel.setInt(1, man.getUserId());
			refAccDel.executeUpdate();
			
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
}
