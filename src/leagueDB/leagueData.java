package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import league.League;

public interface leagueData {
	
	public static League getLeague(JFGPdb connection) {
		try {
			PreparedStatement leagueStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM league WHERE leagueId = 1");
			ResultSet leagueResult = leagueStatement.executeQuery();
			
			League jfgpLeague = new League(
					leagueResult.getInt("leagueId"),
					leagueResult.getString("leagueName"));
			
			connection.closeConnection();
					
			return jfgpLeague;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
		
	}
	
	public static void changeLeagueName(JFGPdb connection, String newName) {
		try {
			PreparedStatement leagueStatement = (connection.getConnection()).prepareStatement(
			        "UPDATE league SET leagueName = ? WHERE leagueId = 1;");
			
			leagueStatement.setString(1, newName);
			leagueStatement.executeUpdate();
			
			connection.closeConnection();

			
		} catch (SQLException e) { e.printStackTrace(); }
		
	}

}
