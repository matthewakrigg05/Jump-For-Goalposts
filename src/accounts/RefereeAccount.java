package accounts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import leagueMembers.Referee;

public class RefereeAccount extends Account implements IRefereeRole {
	
	Referee referee;

	public RefereeAccount(int id, String emailAddress, String password, Referee referee) {
		super(id, emailAddress, password, false);
		setReferee(referee);
	}
	
	// Standard getter and setter methods
	public Referee getReferee() { return this.referee; }
	public void setReferee(Referee referee) { this.referee = referee; }
	
	public Referee getReferee(Connection connection) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	
	        refStatement.setInt(1, getId());
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		this
	        		);
	        
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
}
