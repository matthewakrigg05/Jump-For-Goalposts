package leagueDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import league.Season;
import league.Team;
import leagueMembers.Player;

public interface ComputeGeneralStatistics {
	
	public int getYellows(Connection connection);
	
	public int getReds(Connection connection);
	
	public int getFouls(Connection connection);

	public int getGoals(Connection connection);
	
	public int getAssists(Connection connection);
}
