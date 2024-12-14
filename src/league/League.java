package league;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class League {
	
	private int leagueId;
	private String leagueName;
	
	public League(int id, String name) {
		setLeagueId(id);
		setLeagueName(name);
	}
	
	// Gets and sets league id
	public void setLeagueId(int Id) { this.leagueId = Id; }
	public int getLeagueId() { return leagueId; }

	// Gets and sets league name
	public String getLeagueName() { return leagueName; }
	public void setLeagueName(String leagueName) { this.leagueName = leagueName; }
	
	// Gets all the seasons of the league
	public List<Season> getSeasons(Connection connection) {
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
}