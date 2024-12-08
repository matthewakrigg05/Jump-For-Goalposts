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