package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JFGPdb {
	
	private static Connection connection;
	
	public JFGPdb() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:./JFGP.db");
			initTables(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public Connection getConnection() { return connection; }
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void initTables(Connection conn) {
		/*
		 * Following set of statements are responsible for initialising the database,
		 * creating all the tables that should be necessary for the db to function as 
		 * expected. There are no other methods in this interface.
		 */
		
		try {
			String createleaguetable = "CREATE TABLE IF NOT EXISTS league(\r\n"
					+ "	leagueId INTEGER NOT NULL PRIMARY KEY,\r\n"
					+ "	leagueName VARCHAR(50) \r\n"
					+ "	);";
            
            String createUserAccountTable = "CREATE TABLE IF NOT EXISTS userAccounts(\r\n"
            		+ "	userId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	userType VARCHAR(25) NOT NULL,\r\n"
            		+ "	emailAddress VARCHAR(200) NOT NULL,\r\n"
            		+ "	password VARCHAR(50) NOT NULL,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            
            String createSeasonsTable = "CREATE TABLE IF NOT EXISTS seasons(\r\n"
            		+ "	seasonId INTEGER PRIMARY KEY,\r\n"
            		+ " isCurrent BOOLEAN, \r\n"
            		+ "	seasonStart VARCHAR(4) NOT NULL,\r\n"
            		+ "	seasonEnd VARCHAR(4) NOT NULL,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
		            
            String createRefereesTable = "CREATE TABLE IF NOT EXISTS referees (\r\n"
            		+ "	refereeId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	fName VARCHAR(50),\r\n"
            		+ "	lName VARCHAR(50),\r\n"
            		+ "	preferredLocation VARCHAR(50),\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	userId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId), \r\n"
            		+ " FOREIGN KEY (userId) REFERENCES userAccounts(userId) ON DELETE CASCADE\r\n"
            		+ "	);";
            
            String createStatsTable = "CREATE TABLE IF NOT EXISTS statsForPlayerOrTeam(\r\n"
            		+ "	statsId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	assists INT(4),\r\n"
            		+ "	goals INT(4),\r\n"
            		+ "	fouls INT(4),\r\n"
            		+ "	yellowCards INT(4),\r\n"
            		+ "	redCards INT(4),\r\n"
            		+ "	wins INT(4),\r\n"
            		+ "	draws INT(4),\r\n"
            		+ "	losses INT(4) \r\n"
            		+ "	);";
            
            String createStadiumsTable = "CREATE TABLE IF NOT EXISTS stadiums(\r\n"
            		+ "	stadiumId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	stadiumName VARCHAR(100),\r\n"
            		+ "	capacity VARCHAR(6),\r\n"
            		+ "	stadiumLocation VARCHAR(250)\r\n"
            		+ "	);";
            
            
            String createTeamsTable = "CREATE TABLE IF NOT EXISTS teams(\r\n"
            		+ "	teamId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	teamName VARCHAR(100),\r\n"
            		+ "	statsId INTEGER, \r\n"
            		+ " stadiumId INTEGER, \r\n"
            		+ "	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId) ON DELETE CASCADE, \r\n"
            		+ "	FOREIGN KEY (stadiumId) REFERENCES stadiums(stadiumId)"
            		+ "	);";
            
            String teamsSeason = "CREATE TABLE IF NOT EXISTS teamSeason (\r\n"
            		+ "teamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE, \r\n"
            		+ "seasonId INTEGER NOT NULL REFERENCES seasons(seasonId) ON DELETE CASCADE, \r\n"
            		+ "PRIMARY KEY (teamId, seasonId) \r\n"
            		+ ");";
            
            String createMatchesTable = "CREATE TABLE IF NOT EXISTS matches(\r\n"
            		+ "	matchId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	isComplete BOOLEAN NOT NULL,\r\n"
            		+ "	matchWeek INTEGER NOT NULL,\r\n"
            		+ "	seasonId INTEGER NOT NULL,\r\n"
            		+ "	refereeId INTEGER REFERENCES referees(refereeId) ON DELETE CASCADE,\r\n"
            		+ " homeTeamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE,\r\n"
            		+ " awayTeamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE, \r\n"
            		+ "	FOREIGN KEY (seasonId) REFERENCES seasons(seasonId) ON DELETE CASCADE, \r\n"
            		+ "	FOREIGN KEY (refereeId) REFERENCES referees(refereeId) ON DELETE SET NULL \r\n"
            		+ "	);";
            
            String createResultsTable = "CREATE TABLE IF NOT EXISTS results(\r\n"
            		+ "	resultId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	resultScore VARCHAR(5) NOT NULL,\r\n"
            		+ "	resultOutcome VARCHAR(100),\r\n"
            		+ "	matchId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (matchId) REFERENCES matches(matchId) ON DELETE CASCADE \r\n"
            		+ "	);";
            
            String createMatchEventsTable = "CREATE TABLE IF NOT EXISTS matchEvents(\r\n"
            		+ "	eventId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	eventType VARCHAR(25),\r\n"
            		+ "	eventMinute TINYINT,\r\n"
            		+ "	resultId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (resultId) REFERENCES results(resultId) ON DELETE CASCADE\r\n"
            		+ "	);";
            
            String createTeamEmployeeTable = "CREATE TABLE IF NOT EXISTS teamEmployee(\r\n"
            		+ "	employeeId INTEGER NOT NULL, \r\n"
            		+ " teamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE,\r\n"
            		+ "	contractType VARCHAR(25),\r\n"
            		+ " PRIMARY KEY (employeeId, teamId) \r\n"
            		+ "	);";
            
            String createManagersTable = "CREATE TABLE IF NOT EXISTS managers(\r\n"
            		+ "	managerId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	preferredFormation VARCHAR(10),\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	dob DATE,\r\n"
            		+ "	teamEmployeeId INTEGER,\r\n"
            		+ " userId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId) ON DELETE SET NULL,\r\n"
            		+ " FOREIGN KEY (userId) REFERENCES userAccounts(userId) ON DELETE CASCADE\r\n"
            		+ "	);";
            
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS players(\r\n"
            		+ "	playerId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	teamEmployeeId INTEGER,\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	dob DATE,\r\n"
            		+ "	positionType VARCHAR(15),\r\n"
            		+ "	shirtNumber TINYINT,\r\n"
            		+ "	isSuspended BOOLEAN,\r\n"
            		+ "	isInjured BOOLEAN,\r\n"
            		+ "	statsId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId) ON DELETE SET NULL,\r\n"
            		+ "	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId) ON DELETE CASCADE\r\n"
            		+ "	);";

            // The only two default instances of information in the application - the league and the admin account.
            // admin password set initially but is at liberty to be changed should the admin user deem it appropriate.
            String createLeague = "INSERT OR IGNORE INTO league (leagueId, leagueName) VALUES (1, 'Jump For Goalposts League');";
            
            String createAdmin = "INSERT OR IGNORE INTO userAccounts (userId, userType, emailAddress, password, leagueId) VALUES (1, 'admin', 'admin@jfgp.org', 'password', 1);";

            String byeWeek = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (1, 'BYE');";
            
            String[] statements ={createleaguetable, createUserAccountTable, createSeasonsTable, createRefereesTable,
    				createStatsTable, createStadiumsTable, createTeamsTable, teamsSeason, createMatchesTable,
    				createResultsTable, createMatchEventsTable, createTeamEmployeeTable, createManagersTable,
    				createPlayersTable, createLeague, createAdmin, byeWeek};
            
            for (String statement : statements) {
            	PreparedStatement DBstatement = (conn).prepareStatement(statement);
            	DBstatement.executeUpdate();
            }

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
