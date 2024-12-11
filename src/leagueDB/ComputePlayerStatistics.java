package leagueDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import league.Season;
import league.Team;
import leagueMembers.Player;

public interface ComputePlayerStatistics {

	public static int getPlayerGoals(Connection connection, Player player) {
		int goals = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS goals FROM matchEvents WHERE playerId = ? AND eventType = 'Goal';");
			goalsStatement.setInt(1, player.getId());
			ResultSet res = goalsStatement.executeQuery();
			 goals = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goals;
	}
	
	public static int getPlayerAssists(Connection connection, Player player) {
		int assists = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS assists FROM matchEvents WHERE playerId = ? AND eventType = 'Assist';");
			goalsStatement.setInt(1, player.getId());
			ResultSet res = goalsStatement.executeQuery();
			assists = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return assists;
	}
	
	public static int getPlayerYellows(Connection connection, Player player) {
		int yellows = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE playerId = ? AND eventType = 'Yellow Card';");
			goalsStatement.setInt(1, player.getId());
			ResultSet res = goalsStatement.executeQuery();
			yellows = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return yellows;
	}
	
	public static int getPlayerRed(Connection connection, Player player) {
		int reds = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE playerId = ? AND eventType = 'Red Card';");
			goalsStatement.setInt(1, player.getId());
			ResultSet res = goalsStatement.executeQuery();
			reds = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return reds;
	}
	
	public static int getPlayerFouls(Connection connection, Player player) {
		int fouls = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE playerId = ? AND eventType = 'Foul';");
			goalsStatement.setInt(1, player.getId());
			ResultSet res = goalsStatement.executeQuery();
			 fouls = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return fouls;
	}
	
	public static Player getTopScorer(Connection connection, Season season) {
		List<Player> players = leagueData.getAllPlayers(connection);
		Player topScorer = new Player(0, "No", "Player");
		int highestGoals = 0;
		
		for(Player player : players) {
			if (highestGoals < getPlayerGoals(connection, player)) {
				topScorer = player;
				highestGoals = getPlayerGoals(connection, player);
			}
		}
		
		return topScorer;
	}
}
