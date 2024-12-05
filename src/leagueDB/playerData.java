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
}
