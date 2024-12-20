package accounts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import leagueMembers.Manager;
import leagueMembers.Player;

public class ManagerAccount extends Account {

	public ManagerAccount(int id, String emailAddress,  String password) { super(id, emailAddress, password); }
	
	// previous methods ensure that the manager is only able to update the shirt numbers of players
	// who play for the team that they manage
	public void assignShirtNum(Connection connection, Player player, int shirtNum) {
		 player.setShirtNum(shirtNum); // assign the shirt number to local instance of the player
		 
		 try {
			 PreparedStatement shirtNumStatement = connection.prepareStatement(
					 "UPDATE players SET shirtNumber = ? WHERE playerId = ?;");
			 shirtNumStatement.setInt(1, shirtNum);
			 shirtNumStatement.setInt(2, player.getId());
			 shirtNumStatement.executeUpdate();
		 } catch (SQLException e) { e.printStackTrace(); }
	}
	
	/*
	 * Retrieves the manager from the database that is associated with the instance of the manager
	 * using the user id of the account and returns the manager.
	 */
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
