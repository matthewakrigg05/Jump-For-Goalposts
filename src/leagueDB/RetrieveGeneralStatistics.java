package leagueDB;
import java.sql.Connection;

public interface RetrieveGeneralStatistics {
	
	public int getYellows(Connection connection);
	
	public int getReds(Connection connection);
	
	public int getFouls(Connection connection);

	public int getGoals(Connection connection);
	
	public int getAssists(Connection connection);
}
