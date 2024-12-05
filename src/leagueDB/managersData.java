package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import accounts.ManagerAccount;
import leagueMembers.Manager;

public interface managersData {

	public static ManagerAccount getManagerAccount(Connection connection, int id) {
		try {
	        PreparedStatement manAccStatement = connection.prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'manager';" );
	
	        manAccStatement.setInt(1, id);
	        ResultSet refAccResult = manAccStatement.executeQuery(); 
	        
	        ManagerAccount manAcc = new ManagerAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password")
	        		);
	        		
	        return manAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Manager getManager(Connection connection, ManagerAccount manAcc) {
		try {
	        PreparedStatement manStatement = connection.prepareStatement(
	                "SELECT * FROM manager WHERE userId = ?;" );
	
	        manStatement.setInt(1, manAcc.getId());
	        ResultSet manResult = manStatement.executeQuery(); 
	        
	        Manager man = new Manager(
	        		manResult.getInt("managerId"),
	        		manResult.getString("fname"),
	        		manResult.getString("lName"),
	        		manResult.getInt("userId")
	        		);
	       
			return man;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Manager> getAllManagers(Connection connection) {
		List<Manager> managers = new ArrayList<Manager>();
		
		try {
			PreparedStatement manStatement = (connection).prepareStatement(
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
			
			for(Manager man : managers) { man.setManAcc(getManagerAccount(connection, man.getUserId())); }
					
			return managers;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
}