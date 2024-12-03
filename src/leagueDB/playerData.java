package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import leagueMembers.*;

public interface playerData {
	
	public static Attacker getAttacker(int id) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement attackerStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        attackerStatement.setInt(1, id);
	        ResultSet attackerResult = attackerStatement.executeQuery(); 
	        
	        Attacker attacker = new Attacker(
	        		attackerResult.getInt("playerId"),
	        		attackerResult.getString("fname"),
	        		attackerResult.getString("lName")
	        		);
	        
	        connection.closeConnection();
			return attacker;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static Midfielder getMidfielder(int id) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement midfielderStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        midfielderStatement.setInt(1, id);
	        ResultSet midfielderResult = midfielderStatement.executeQuery(); 
	        
	        Midfielder midfielder = new Midfielder(
	        		midfielderResult.getInt("playerId"),
	        		midfielderResult.getString("fname"),
	        		midfielderResult.getString("lName")
	        		);
	        
	        connection.closeConnection();
			return midfielder;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static Defender getDefender(int id) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement defenderStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM player WHERE playerId = ? AND positionType = 'defender';" );

	        defenderStatement.setInt(1, id);
	        ResultSet defenderResult = defenderStatement.executeQuery(); 
	        
	        Defender defender = new Defender(
	        		defenderResult.getInt("playerId"),
	        		defenderResult.getString("fname"),
	        		defenderResult.getString("lName")
	        		);
	        
	        connection.closeConnection();
			return defender;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static Goalkeeper getGoalkeeper(int id) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement goalkeeperStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        goalkeeperStatement.setInt(1, id);
	        ResultSet goalkeeperResult = goalkeeperStatement.executeQuery(); 
	        
	        Goalkeeper goalkeeper = new Goalkeeper(
	        		goalkeeperResult.getInt("playerId"),
	        		goalkeeperResult.getString("fname"),
	        		goalkeeperResult.getString("lName")
	        		);
	        
	        connection.closeConnection();
			return goalkeeper;
		
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
		
		return null;
	}
	
	public static void createPlayer(String fname, String lname, String positionType) {
		JFGPdb connection = new JFGPdb();
		try {
			int statsId = teamData.createStats(connection.getConnection());
			PreparedStatement playerStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO players(fName, lName, positionType, statsId) VALUES (?, ?, ?, ?);");
			
			playerStatement.setString(1, fname);
			playerStatement.setString(2, lname);
			playerStatement.setString(3, positionType);
			playerStatement.setInt(4, statsId);
			playerStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static List<Attacker> getAllAttackers() {
		JFGPdb connection = new JFGPdb();
		List<Attacker> attackers = new ArrayList<Attacker>();
		
		try {
			PreparedStatement attackerStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM players WHERE positionType = 'attacker';");
			ResultSet attackerResult = attackerStatement.executeQuery();
			
			while(attackerResult.next()) {
				Attacker attacker = new Attacker(
						attackerResult.getInt("playerId"),
						attackerResult.getString("fname"),
		        		attackerResult.getString("lName")
		        		);
				
				attackers.add(attacker);
			}
			connection.closeConnection();
			return attackers;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Midfielder> getAllMidfielders() {
		JFGPdb connection = new JFGPdb();
		List<Midfielder> midfielders = new ArrayList<Midfielder>();
		
		try {
			PreparedStatement attackerStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM players WHERE positionType = 'midfielder';");
			ResultSet midfielderResult = attackerStatement.executeQuery();
			
			while(midfielderResult.next()) {
				Midfielder midfielder = new Midfielder(
						midfielderResult.getInt("playerId"),
						midfielderResult.getString("fname"),
						midfielderResult.getString("lName")
		        		);
				
				midfielders.add(midfielder);
			}
			connection.closeConnection();
			return midfielders;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}

	public static List<Defender> getAllDefenders() {
		JFGPdb connection = new JFGPdb();
		List<Defender> defenders = new ArrayList<Defender>();
		
		try {
			PreparedStatement defenderStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM players WHERE positionType = 'defender';");
			ResultSet defenderResult = defenderStatement.executeQuery();
			
			while(defenderResult.next()) {
				Defender defender = new Defender(
						defenderResult.getInt("playerId"),
						defenderResult.getString("fname"),
						defenderResult.getString("lName")
		        		);
				defenders.add(defender);
			}
			connection.closeConnection();
			return defenders;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static List<Goalkeeper> getAllGoalkeepers() {
		JFGPdb connection = new JFGPdb();
		List<Goalkeeper> goalkeepers = new ArrayList<Goalkeeper>();
		
		try {
			PreparedStatement goalkeeperStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM players WHERE positionType = 'goalkeeper';");
			ResultSet goalkeeperResult = goalkeeperStatement.executeQuery();
			
			while(goalkeeperResult.next()) {
				Goalkeeper goalkeeper = new Goalkeeper(
						goalkeeperResult.getInt("playerId"),
						goalkeeperResult.getString("fname"),
						goalkeeperResult.getString("lName")
		        		);
				goalkeepers.add(goalkeeper);
			}
			connection.closeConnection();
			return goalkeepers;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static List<Attacker> getTeamAttackers() {
		JFGPdb connection = new JFGPdb();
		List<Attacker> attackers = new ArrayList<Attacker>();
		
		try {
			PreparedStatement attackerStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM players WHERE positionType = 'attacker';");
			ResultSet attackerResult = attackerStatement.executeQuery();
			
			while(attackerResult.next()) {
				Attacker attacker = new Attacker(
						attackerResult.getInt("playerId"),
						attackerResult.getString("fname"),
		        		attackerResult.getString("lName")
		        		);
				
				attackers.add(attacker);
			}
			connection.closeConnection();
			return attackers;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static void removePlayer(int id) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement attackerStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM players WHERE playerId = ?;");
			
			attackerStatement.setInt(1, id);
			attackerStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
}
