package accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import leagueMembers.Manager;
import leagueMembers.Player;

public class ManagerAccount extends Account {

	public ManagerAccount(int id, String emailAddress,  String password) {
		super(id, emailAddress, password, false);
	}
	
	public static void assignShirtNumDialog(Player player, int shirtNum) {
		 player.setShirtNum(shirtNum); 
	}
	
	public Manager getManager(Connection connection) {
		try {
	        PreparedStatement manStatement = connection.prepareStatement(
	                "SELECT * FROM manager WHERE userId = ?;" );
	
	        manStatement.setInt(1, getId());
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
}
