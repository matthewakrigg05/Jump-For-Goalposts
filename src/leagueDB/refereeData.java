package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import accounts.RefereeAccount;
import leagueMembers.Referee;

public interface refereeData {
	
	public static RefereeAccount getRefereeAccount(Connection connection, Referee ref) {
		try {
	        PreparedStatement refAccStatement = connection.prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'referee';" );
	
	        refAccStatement.setInt(1, ref.getId());
	        ResultSet refAccResult = refAccStatement.executeQuery(); 
	        
	        RefereeAccount refAcc = new RefereeAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password"), 
	        		ref
	        		);
	        
	        return refAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Referee getReferee(Connection connection, RefereeAccount refereeAccount) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	
	        refStatement.setInt(1, refereeAccount.getId());
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		refereeAccount
	        		);
	        
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Referee getRefereeFromId(Connection connection, int id) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	
	        refStatement.setInt(1, id);
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		id
	        		);
	       
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Referee> getAllReferees(Connection connection) {
		List<Referee> referees = new ArrayList<Referee>();
		
		try {
			PreparedStatement refsStatement = (connection).prepareStatement(
			        "SELECT * FROM referees");
			ResultSet refResult = refsStatement.executeQuery();
			
			while(refResult.next()) {
				Referee ref = new Referee(
		        		refResult.getInt("refereeId"),
		        		refResult.getString("fname"),
		        		refResult.getString("lName"), 
		        		refResult.getString("preferredLocation"),
		        		refResult.getInt("userId")
		        		);
				
				referees.add(ref);
			}
			
			for(Referee ref : referees) { ref.setRefAcc(getRefereeAccount(connection, ref)); }
					
			return referees;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
}