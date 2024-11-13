package leagueDB;
import java.sql.Connection;
import java.sql.SQLException;
import org.sqlite.SQLiteDataSource;

public class DBinit {
	
	SQLiteDataSource ds;
	
	public DBinit() {
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:jfgp.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			Connection connection = ds.getConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}


