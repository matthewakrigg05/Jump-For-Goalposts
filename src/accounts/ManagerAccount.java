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
	
	public void assignShirtNum(Connection connection, Player player, int shirtNum) {
		 player.setShirtNum(shirtNum); 
		 
		 try {
			 PreparedStatement shirtNumStatement = connection.prepareStatement(
					 "UPDATE players SET shirtNumber = ? WHERE playerId = ?;");
			 shirtNumStatement.setInt(1, shirtNum);
			 shirtNumStatement.setInt(2, player.getId());
			 shirtNumStatement.executeUpdate();
		 } catch (SQLException e) { e.printStackTrace(); }
	}
	
	public Manager getManager(Connection connection) {
		try {
	        PreparedStatement manStatement = connection.prepareStatement(
	                "SELECT * FROM managers WHERE userId = ?;" );
	
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
