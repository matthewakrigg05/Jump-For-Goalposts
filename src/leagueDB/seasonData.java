package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import league.League;
import league.Season;

public interface seasonData {

	public static League getLeague(Connection connection) {
		try {
			PreparedStatement leagueStatement = (connection).prepareStatement(
			        "SELECT * FROM league WHERE leagueId = 1");
			ResultSet leagueResult = leagueStatement.executeQuery();
			
			League jfgpLeague = new League(
					leagueResult.getInt("leagueId"),
					leagueResult.getString("leagueName"));
					
			return jfgpLeague;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static void changeLeagueName(Connection connection, String newName) {
		try {
			PreparedStatement leagueStatement = (connection).prepareStatement(
			        "UPDATE league SET leagueName = ? WHERE leagueId = 1;");
			
			leagueStatement.setString(1, newName);
			leagueStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public static void createSeason(Connection connection, String start, String end) {
		try {
			PreparedStatement isCurrentSeason = (connection).prepareStatement(
					"SELECT isCurrent FROM seasons WHERE isCurrent = true;");
			ResultSet isCurrent = isCurrentSeason.executeQuery();
			
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "INSERT INTO seasons(seasonStart, seasonEnd, isCurrent, leagueId) VALUES (?, ?, ?, 1);");
			seasonStatement.setString(1, start);
			seasonStatement.setString(2, end);
			
			if (!isCurrent.next()) { seasonStatement.setBoolean(3, true); }
			else { seasonStatement.setBoolean(3, false); }
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void removeSeason(Connection connection,Season season) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "DELETE FROM seasons WHERE seasonId = ?;");
			
			seasonStatement.setInt(1, season.getId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static List<Season> getSeasons(Connection connection) {

		List<Season> seasons = new ArrayList<Season>();
		
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
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

			return seasons;
			
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static void setCurrentSeason(Connection connection, int seasonId) {
		
		try {
			PreparedStatement deselectCurrSeasonStatement = (connection).prepareStatement(
					"UPDATE seasons SET isCurrent = false WHERE isCurrent = true;" );
			deselectCurrSeasonStatement.executeUpdate();
			
			PreparedStatement setCurrSeasonStatement = (connection).prepareStatement(
					"UPDATE seasons SET isCurrent = true WHERE seasonId = ?;" );
			
			setCurrSeasonStatement.setInt(1, seasonId);
			setCurrSeasonStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }	
	}
	
	public static Season getCurrentSeason(Connection connection) {
		try {
			PreparedStatement currentSeasonStatement = (connection).prepareStatement(
					"SELECT * FROM seasons WHERE isCurrent = true LIMIT 1;" );
			ResultSet seasonResult = currentSeasonStatement.executeQuery();
			
			Season currentSeason = new Season( 
					seasonResult.getInt("seasonId"),
					seasonResult.getString("seasonStart"),
					seasonResult.getString("seasonEnd"),
					seasonResult.getBoolean("isCurrent")
					);
			
			return currentSeason;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}
	
	public static int getCurrentGameWeek(Connection connection, int SeasonId) {
		try {
			PreparedStatement currentMatchWeekStatement = (connection).prepareStatement(
					"SELECT MIN(matchWeek) AS currentWeek FROM matches WHERE isComplete = 0 AND seasonId = ?;" );
			currentMatchWeekStatement.setInt(1, SeasonId);
			ResultSet matchweekResult = currentMatchWeekStatement.executeQuery();
			
			int matchWeek = matchweekResult.getInt("currentWeek");
			
			return matchWeek;
		} catch (SQLException e) { e.printStackTrace(); return 0; }	
	}
}