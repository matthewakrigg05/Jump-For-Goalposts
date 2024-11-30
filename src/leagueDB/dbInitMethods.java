package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

public interface dbInitMethods {
	
	public static void initTables(Connection conn) {
		// https://www.sqliz.com/posts/java-basic-sqlite/ - ref 
		
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
            PreparedStatement leaguePS = conn.prepareStatement(createleaguetable);
            leaguePS.executeUpdate();
            
            String createUserAccountTable = "CREATE TABLE IF NOT EXISTS userAccounts(\r\n"
            		+ "	userId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	userType VARCHAR(25) NOT NULL,\r\n"
            		+ "	emailAddress VARCHAR(200) NOT NULL,\r\n"
            		+ "	password VARCHAR(50) NOT NULL,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            PreparedStatement userAccPS = conn.prepareStatement(createUserAccountTable);
            userAccPS.executeUpdate();
            
            String createSeasonsTable = "CREATE TABLE IF NOT EXISTS seasons(\r\n"
            		+ "	seasonId INTEGER PRIMARY KEY,\r\n"
            		+ "	seasonStart VARCHAR(4) NOT NULL,\r\n"
            		+ "	seasonEnd VARCHAR(4) NOT NULL,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            		
            PreparedStatement seasonsPS = conn.prepareStatement(createSeasonsTable);
            seasonsPS.executeUpdate();
		            
            String createRefereesTable = "CREATE TABLE IF NOT EXISTS referees (\r\n"
            		+ "	refereeId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	fName VARCHAR(50),\r\n"
            		+ "	lName VARCHAR(50),\r\n"
            		+ "	preferredLocation VARCHAR(50),\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	userId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId), \r\n"
            		+ " FOREIGN KEY (userId) REFERENCES userAccounts(userId) \r\n"
            		+ "	);";
            PreparedStatement refereePS = conn.prepareStatement(createRefereesTable);
            refereePS.executeUpdate();
            
            String createStatsTable = "CREATE TABLE IF NOT EXISTS statsForPlayerOrTeam(\r\n"
            		+ "	statsId INTEGER NOT NULL,\r\n"
            		+ "	assists INT(4),\r\n"
            		+ "	goals INT(4),\r\n"
            		+ "	fouls INT(4),\r\n"
            		+ "	yellowCards INT(4),\r\n"
            		+ "	redCards INT(4),\r\n"
            		+ "	wins INT(4),\r\n"
            		+ "	draws INT(4),\r\n"
            		+ "	losses INT(4), \r\n"
            		+ " seasonId INTEGER NOT NULL, \r\n"
            		+ " FOREIGN KEY (seasonId) REFERENCES seasons(seasonId), \r\n"
            		+ " PRIMARY KEY (statsId, seasonId) \r\n"
            		+ "	);";
            
            PreparedStatement statsPS = conn.prepareStatement(createStatsTable);
            statsPS.executeUpdate();
            
            String createStadiumsTable = "CREATE TABLE IF NOT EXISTS stadiums(\r\n"
            		+ "	stadiumId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	stadiumName VARCHAR(100),\r\n"
            		+ "	capacity VARCHAR(6),\r\n"
            		+ "	stadiumLocation VARCHAR(250)\r\n"
            		+ "	);";
            
            PreparedStatement stadiumsPS = conn.prepareStatement(createStadiumsTable);
            stadiumsPS.executeUpdate();
            
            String createTeamsTable = "CREATE TABLE IF NOT EXISTS teams(\r\n"
            		+ "	teamId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	teamName VARCHAR(100),\r\n"
            		+ "	statsId INTEGER NOT NULL, \r\n"
            		+ " stadiumId INTEGER, \r\n"
            		+ "	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId)\r\n"
            		+ "	FOREIGN KEY (stadiumId) REFERENCES stadiums(stadiumId)"
            		+ "	);";
            
            PreparedStatement teamsPS = conn.prepareStatement(createTeamsTable);
            teamsPS.executeUpdate();
            
            String teamsSeason = "CREATE TABLE IF NOT EXISTS teamSeason (\r\n"
            		+ "teamId INTEGER NOT NULL REFERENCES teams(teamId), \r\n"
            		+ "seasonId INTEGER NOT NULL REFERENCES seasons(seasonId), \r\n"
            		+ "PRIMARY KEY (teamId, seasonId) \r\n"
            		+ ");";
            
            PreparedStatement teamSeasonPS = conn.prepareStatement(teamsSeason);
            teamSeasonPS.executeUpdate();
            
            String createMatchesTable = "CREATE TABLE IF NOT EXISTS matches(\r\n"
            		+ "	matchId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	isComplete BOOLEAN NOT NULL,\r\n"
            		+ "	matchDate DATETIME,\r\n"
            		+ "	seasonId INTEGER NOT NULL,\r\n"
            		+ "	refereeId INTEGER,\r\n"
            		+ " homeTeamId INTEGER NOT NULL,\r\n"
            		+ " awayTeamId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (seasonId) REFERENCES seasons(seasonId), \r\n"
            		+ "	FOREIGN KEY (refereeId) REFERENCES referees(refereeId), \r\n"
            		+ " FOREIGN KEY (homeTeamId) REFERENCES teams(teamId), \r\n"
            		+ " FOREIGN KEY (awayTeamId) REFERENCES teams(teamId)"
            		+ "	);";
            
            PreparedStatement matchesPS = conn.prepareStatement(createMatchesTable);
            matchesPS.executeUpdate();
            
            String createResultsTable = "CREATE TABLE IF NOT EXISTS results(\r\n"
            		+ "	resultId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	resultScore VARCHAR(5) NOT NULL,\r\n"
            		+ "	resultOutcome VARCHAR(100),\r\n"
            		+ "	matchId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (matchId) REFERENCES matches(matchId) \r\n"
            		+ "	);";
            
            PreparedStatement resultsPS = conn.prepareStatement(createResultsTable);
            resultsPS.executeUpdate();
            
            String createMatchEventsTable = "CREATE TABLE IF NOT EXISTS matchEvents(\r\n"
            		+ "	eventId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	eventType VARCHAR(25),\r\n"
            		+ "	eventMinute TINYINT,\r\n"
            		+ "	resultId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (resultId) REFERENCES results(resultId)\r\n"
            		+ "	);";
            
            PreparedStatement matchEventsPS = conn.prepareStatement(createMatchEventsTable);
            matchEventsPS.executeUpdate();
            
            String createTeamEmployeeTable = "CREATE TABLE IF NOT EXISTS teamEmployee(\r\n"
            		+ "	teamId INTEGER NOT NULL,\r\n"
            		+ "	playerId INTEGER NOT NULL,\r\n"
            		+ "	contractType VARCHAR(25),\r\n"
            		+ " PRIMARY KEY (teamId, playerId), \r\n"
            		+ "	FOREIGN KEY (teamId) REFERENCES teams(teamId)\r\n"
            		+ " FOREIGN KEY (playerId) REFERENCES players(playerId)"
            		+ "	);";
            
            PreparedStatement teamEmployeePS = conn.prepareStatement(createTeamEmployeeTable);
            teamEmployeePS.executeUpdate();
            
            String createManagersTable = "CREATE TABLE IF NOT EXISTS managers(\r\n"
            		+ "	managerId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	preferredFormation VARCHAR(10),\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	dob DATE,\r\n"
            		+ "	teamEmployeeId INTEGER,\r\n"
            		+ " userId INTEGER NOT NULL, \r\n"
            		+ "	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId),\r\n"
            		+ " FOREIGN KEY (userId) REFERENCES userAccounts(userId) \r\n"
            		+ "	);";
            
            PreparedStatement managersPS = conn.prepareStatement(createManagersTable);
            managersPS.executeUpdate();
            
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS players(\r\n"
            		+ "	playerId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	dob DATE,\r\n"
            		+ "	positionType VARCHAR(15),\r\n"
            		+ "	shirtNumber TINYINT,\r\n"
            		+ "	isSuspended BOOLEAN,\r\n"
            		+ "	isInjured BOOLEAN,\r\n"
            		+ "	statsId INTEGER NOT NULL,\r\n"
            		+ "	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId)\r\n"
            		+ "	);";
            
            PreparedStatement playersPS = conn.prepareStatement(createPlayersTable);
            playersPS.executeUpdate();
            
            // The only two default instances of information in the application - the league and the admin account.
            // admin password set initially but is at liberty to be changed should the admin user deem it appropriate.
            String createLeague = "INSERT OR IGNORE INTO league (leagueId, leagueName) VALUES (1, 'Jump For Goalposts League');";

            PreparedStatement leagueDataPS = conn.prepareStatement(createLeague);
            leagueDataPS.executeUpdate();
            
            String createAdmin = "INSERT OR IGNORE INTO userAccounts (userId, userType, emailAddress, password, leagueId) VALUES (1, 'admin', 'admin@jfgp.org', 'password', 1);";

            PreparedStatement adminPS = conn.prepareStatement(createAdmin);
            adminPS.executeUpdate();
            
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
