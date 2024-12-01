package leagueDB;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import league.Season;
import league.Team;

public interface matchData {

	public static void createMatch(Team homeTeam, Team awayTeam, Season season, int matchWeek) {
		JFGPdb connection = new JFGPdb();
		try {
			PreparedStatement seasonStatement = (connection.getConnection()).prepareStatement(
			        "INSERT INTO matches(isComplete, matchWeek, seasonId, homeTeamId, awayTeamId) VALUES (FALSE, ?, ?, ?, ?);");
			
			seasonStatement.setInt(1 , matchWeek);
			seasonStatement.setInt(2, season.getId());
			seasonStatement.setInt(3, homeTeam.getTeamId());
			seasonStatement.setInt(4, awayTeam.getTeamId());
			seasonStatement.executeUpdate();
			connection.closeConnection();
			
		} catch (SQLException e) { e.printStackTrace(); connection.closeConnection(); }
	}
	
	public static void createSeasonMatches(List<Team> teams, Season season) {
	if (teams.size() % 2 != 0) {teams.add(teamData.getTeam(1)); }
		
		for(int i = 0; i < teams.size(); i++) {
			for(int j = 0; j < teams.size(); j++) {
				if(i != j) { createMatch(teams.get(i), teams.get(j), season, j);; }
			}
		}
	}
}

