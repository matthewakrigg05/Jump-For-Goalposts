package leagueDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import league.League;
import league.Season;

public interface seasonData {

	public static void createSeason(String start, String end) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO seasons(seasonStart, seasonEnd, leagueId) "
			        + "VALUES (?, ?, 1");
			
			seasonStatement.setString(1, start);
			seasonStatement.setString(2, end);
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
						seasonResult.getString("seasonEnd")
						);
				seasons.add(season);
			}
			
			connection.closeConnection();
					
			return seasons;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
}
