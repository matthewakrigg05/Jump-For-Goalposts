package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import leagueMembers.*;

public interface playerData {
	
	public static Player getPlayer(Connection connection, Player player) {
		try {
	        PreparedStatement playerStatement = connection.prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        playerStatement.setInt(1, player.getId());
	        ResultSet playerResult = playerStatement.executeQuery(); 
	        
	        Player playerFound = new Player(
	        		playerResult.getInt("playerId"),
	        		playerResult.getString("fname"),
	        		playerResult.getString("lName")
	        		);
	       
			return playerFound;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Player> getAllPlayers(Connection connection) {
		List<Player> goalkeepers = new ArrayList<Player>();
		
		try {
			PreparedStatement goalkeeperStatement = connection.prepareStatement(
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
			return goalkeepers;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
}
