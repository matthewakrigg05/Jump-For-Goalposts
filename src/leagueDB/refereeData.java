package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import accounts.RefereeAccount;
import leagueMembers.Referee;

public interface refereeData {
	
	public static RefereeAccount getRefereeAccount(Referee ref) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement refAccStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'referee';" );
	
	        refAccStatement.setInt(1, ref.getId());
	        ResultSet refAccResult = refAccStatement.executeQuery(); 
	        
	        RefereeAccount refAcc = new RefereeAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password"), 
	        		ref
	        		);
	        
	        connection.closeConnection();
	        return refAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static void createRefereeAccount(String fname, String lname, String city) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement refAccStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO userAccounts(userType, emailAddress, password, leagueId) VALUES ('referee', ?, ?, 1);");
			
			refAccStatement.setString(1, lname + "@jfgp.org");
			refAccStatement.setString(2, lname + city);
			refAccStatement.executeUpdate();
			
			PreparedStatement lastId = (connection.getConnection().prepareStatement(
					"SELECT userId FROM userAccounts ORDER BY ROWID DESC limit 1;"));
			
			ResultSet id = lastId.executeQuery();
			
			int refId = id.getInt("userId");
			
			connection.closeConnection();
			
			createReferee(fname, lname, city, refId);
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static Referee getReferee(RefereeAccount refereeAccount) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement refStatement = connection.getConnection().prepareStatement(
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
	        
	        connection.closeConnection();
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static Referee getRefereeFromId(int id) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement refStatement = connection.getConnection().prepareStatement(
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
	        
	        connection.closeConnection();
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static  void createReferee(String fname, String lname, String city, int id) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement refStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO referees(fName, lName, preferredLocation, leagueId, userId) VALUES (?, ?, ?, 1, ?);");
			
			refStatement.setString(1, fname);
			refStatement.setString(2, lname);
			refStatement.setString(3, city);
			refStatement.setInt(4, id);
			refStatement.executeUpdate();
			
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static List<Referee> getAllReferees() {
		JFGPdb connection = new JFGPdb();
		List<Referee> referees = new ArrayList<Referee>();
		
		try {
			PreparedStatement refsStatement = (connection.getConnection()).prepareStatement(
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
			
			connection.closeConnection();
			
			for(Referee ref : referees) { ref.setRefAcc(getRefereeAccount(ref)); }
					
			return referees;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static void removeReferee(Referee ref) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			
			seasonStatement.setInt(1, ref.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = (connection.getConnection()).prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			
			refAccDel.setInt(1, ref.getUserId());
			refAccDel.executeUpdate();
			
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
}