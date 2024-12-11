package leagueDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import league.Team;

public interface ComputeTeamStatistics {

	public static int getTeamGoals(Connection connection, Team team) {
		int goals = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS goals FROM matchEvents WHERE teamId = ? AND eventType = 'Goal';");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 goals = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goals;
	}
}
