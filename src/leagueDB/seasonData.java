package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import league.Season;

public interface seasonData {

	public static void createSeason(String start, String end) {
		JFGPdb connection = new JFGPdb();
		try {
			
			PreparedStatement isCurrentSeason = (connection.getConnection()).prepareStatement(
					"SELECT isCurrent FROM seasons WHERE isCurrent = true;");
			ResultSet isCurrent = isCurrentSeason.executeQuery();
			
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO seasons(seasonStart, seasonEnd, isCurrent, leagueId) VALUES (?, ?, ?, 1);");
			
			seasonStatement.setString(1, start);
			seasonStatement.setString(2, end);
			
			if (!isCurrent.next()) { seasonStatement.setBoolean(3, true); }
			else { seasonStatement.setBoolean(3, false); }
			
			seasonStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static void removeSeason(Season season) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "DELETE FROM seasons WHERE seasonId = ?;");
			
			seasonStatement.setInt(1, season.getId());
			seasonStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static List<Season> getSeasons() {
		JFGPdb connection = new JFGPdb();
		List<Season> seasons = new ArrayList<Season>();
		
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "SELECT * FROM seasons");
			ResultSet seasonResult = seasonStatement.executeQuery();
			
			while(seasonResult.next()) {
				Season season = new Season(
						seasonResult.getInt("seasonId"),
						seasonResult.getString("seasonStart"),
						seasonResult.getString("seasonEnd"),
						seasonResult.getBoolean("isCurrent")
						);
				seasons.add(season);
			}
			
			connection.closeConnection();
			return seasons;
			
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static void setCurrentSeason(int seasonId) {
		JFGPdb connection = new JFGPdb();
		
		try {
			PreparedStatement deselectCurrSeasonStatement = (connection.getConnection()).prepareStatement(
					"UPDATE seasons SET isCurrent = false WHERE isCurrent = true;" );
			deselectCurrSeasonStatement.executeUpdate();
			
			
			PreparedStatement setCurrSeasonStatement = (connection.getConnection()).prepareStatement(
					"UPDATE seasons SET isCurrent = true WHERE seasonId = ?;" );
			
			setCurrSeasonStatement.setInt(1, seasonId);
			setCurrSeasonStatement.executeUpdate();
			
			connection.closeConnection();
		} catch (SQLException e) { e.printStackTrace(); }	
	}
	
	public static Season getCurrentSeason() {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement currentSeasonStatement = (connection.getConnection()).prepareStatement(
					"SELECT * FROM seasons WHERE isCurrent = true LIMIT 1;" );
			ResultSet seasonResult = currentSeasonStatement.executeQuery();
			
			Season currentSeason = new Season( 
					seasonResult.getInt("seasonId"),
					seasonResult.getString("seasonStart"),
					seasonResult.getString("seasonEnd"),
					seasonResult.getBoolean("isCurrent")
					);
			
			connection.closeConnection();
			
			return currentSeason;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}
}