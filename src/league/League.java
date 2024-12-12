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
	private List<Season> seasons;
	
	public League(int id, String name) {
		setLeagueId(id);
		setLeagueName(name);
	}
	
	public void setLeagueId(int Id) { this.leagueId = Id; }
	public int getLeagueId() { return leagueId; }

	public String getLeagueName() { return leagueName; }
	public void setLeagueName(String leagueName) { this.leagueName = leagueName; }
	
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