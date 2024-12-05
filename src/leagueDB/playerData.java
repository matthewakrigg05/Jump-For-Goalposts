package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import leagueMembers.*;

public interface playerData {
	
	public static Player getPlayer(Player player) {
		JFGPdb connection = new JFGPdb();
		try {
	        PreparedStatement playerStatement = connection.getConnection().prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        playerStatement.setInt(1, player.getId());
	        ResultSet playerResult = playerStatement.executeQuery(); 
	        
	        Player playerFound = new Player(
	        		playerResult.getInt("playerId"),
	        		playerResult.getString("fname"),
	        		playerResult.getString("lName")
	        		);
	        
	        connection.closeConnection();
			return playerFound;
		
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
	
	public static List<Player> getAllPlayers() {
		JFGPdb connection = new JFGPdb();
		List<Player> goalkeepers = new ArrayList<Player>();
		
		try {
			PreparedStatement goalkeeperStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM players WHERE positionType = 'goalkeeper';");
			ResultSet goalkeeperResult = goalkeeperStatement.executeQuery();
			
			while(goalkeeperResult.next()) {
				Player goalkeeper = new Player(
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
	
	public static void removePlayer(Player player) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement attackerStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM players WHERE playerId = ?;");
			
			attackerStatement.setInt(1, player.getId());
			attackerStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
}
