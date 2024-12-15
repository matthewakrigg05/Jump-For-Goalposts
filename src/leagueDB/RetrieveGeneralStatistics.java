package leagueDB;
import java.sql.Connection;

/**
 * Interface implemented by players and teams that are for retrieval of generic data
 * and must be implemented differently between the two
 */

public interface RetrieveGeneralStatistics {
	
	public int getYellows(Connection connection);
	
	public int getReds(Connection connection);
	
	public int getFouls(Connection connection);

	public int getGoals(Connection connection);
	
	public int getAssists(Connection connection);
}
