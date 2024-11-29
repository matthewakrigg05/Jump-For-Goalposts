package leagueDB;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import league.Match;
import league.Season;
import league.Team;

public interface matchData {

	public static void createMatch(Team homeTeam, Team awayTeam, Season season) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO matches(isComplete, seasonId, homeTeamId, awayTeamId) VALUES (FALSE, ?, ?, ?);");
			
			seasonStatement.setInt(1, season.getId());
			seasonStatement.setInt(2, homeTeam.getTeamId());
			seasonStatement.setInt(3, awayTeam.getTeamId());
			seasonStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static void createSeasonMatches(List<Team> teams, Season season) {
		for(int i = 0; i < teams.size(); i++) {
			for(int j = 0; j < teams.size(); j++) {
				if(i == j) { continue; }
				else {
					createMatch(teams.get(i), teams.get(j), season);
				}
			}
		}
	}
	
	public static Match getMatch(int matchId) {
		return null;
	}
}
