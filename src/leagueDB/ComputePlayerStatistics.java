package leagueDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
