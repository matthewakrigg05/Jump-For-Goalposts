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
					+ "	leagueId INTEGER NOT NULL,\r\n"
					+ "	leagueName VARCHAR(50), \r\n"
					+ "	PRIMARY KEY (leagueId)\r\n"
					+ "	);";
            PreparedStatement leaguePS = conn.prepareStatement(createleaguetable);
            leaguePS.executeUpdate();
            
            String createUserAccountTable = "CREATE TABLE IF NOT EXISTS userAccounts(\r\n"
            		+ "	userId INTEGER NOT NULL,\r\n"
            		+ "	userType VARCHAR(25) NOT NULL,\r\n"
            		+ "	emailAddress VARCHAR(200) NOT NULL,\r\n"
            		+ "	password VARCHAR(50) NOT NULL,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (userId),\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            PreparedStatement userAccPS = conn.prepareStatement(createUserAccountTable);
            userAccPS.executeUpdate();
            
            String createSeasonsTable = "CREATE TABLE IF NOT EXISTS seasons(\r\n"
            		+ "	seasonId INTEGER NOT NULL,\r\n"
            		+ "	seasonStart DATE,\r\n"
            		+ "	seasonEnd DATE,\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (seasonId),\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            		
            PreparedStatement seasonsPS = conn.prepareStatement(createSeasonsTable);
            seasonsPS.executeUpdate();
		            
            String createRefereesTable = "CREATE TABLE IF NOT EXISTS referees (\r\n"
            		+ "	refereeId INTEGER NOT NULL,\r\n"
            		+ "	fName VARCHAR(50),\r\n"
            		+ "	lName VARCHAR(50),\r\n"
            		+ "	preferredLocation VARCHAR(50),\r\n"
            		+ "	leagueId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (refereeId),\r\n"
            		+ "	FOREIGN KEY (leagueId) REFERENCES league(leagueId) \r\n"
            		+ "	);";
            PreparedStatement refereePS = conn.prepareStatement(createRefereesTable);
            refereePS.executeUpdate();
            
            String createStatsTable = "CREATE TABLE IF NOT EXISTS statsForPlayerOrTeam(\r\n"
            		+ "	statsId INTEGER NOT NULL,\r\n"
            		+ "	assits INT(4),\r\n"
            		+ "	goals INT(4),\r\n"
            		+ "	fouls INT(4),\r\n"
            		+ "	yellowCards INT(4),\r\n"
            		+ "	redCards INT(4),\r\n"
            		+ "	wins INT(4),\r\n"
            		+ "	draws INT(4),\r\n"
            		+ "	losses INT(4),\r\n"
            		+ "	PRIMARY KEY (statsId)\r\n"
            		+ "	);";
            
            PreparedStatement statsPS = conn.prepareStatement(createStatsTable);
            statsPS.executeUpdate();
            
            String createTeamsTable = "CREATE TABLE IF NOT EXISTS teams (\r\n"
            		+ "	teamId INTEGER NOT NULL,\r\n"
            		+ "	teamName VARCHAR(100),\r\n"
            		+ "	statsId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (teamId),\r\n"
            		+ "	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId)\r\n"
            		+ "	);";
            
            PreparedStatement teamsPS = conn.prepareStatement(createTeamsTable);
            teamsPS.executeUpdate();
            
            String createStadiumsTable = "CREATE TABLE IF NOT EXISTS stadiums(\r\n"
            		+ "	stadiumId INTEGER NOT NULL,\r\n"
            		+ "	stadiumName VARCHAR(100),\r\n"
            		+ "	capacity VARCHAR(6),\r\n"
            		+ "	stadiumLocation VARCHAR(250),\r\n"
            		+ "	PRIMARY KEY (stadiumId) \r\n"
            		+ "	);";
            
            PreparedStatement stadiumsPS = conn.prepareStatement(createStadiumsTable);
            stadiumsPS.executeUpdate();
            
            String createMatchesTable = "CREATE TABLE IF NOT EXISTS matches(\r\n"
            		+ "	matchId INTEGER NOT NULL,\r\n"
            		+ "	isComplete BOOLEAN NOT NULL,\r\n"
            		+ "	matchDate DATETIME,\r\n"
            		+ "	seasonId INTEGER NOT NULL,\r\n"
            		+ "	refereeId INTEGER,\r\n"
            		+ "	PRIMARY KEY (matchId),\r\n"
            		+ "	FOREIGN KEY (seasonId) REFERENCES seasons(seasonId), \r\n"
            		+ "	FOREIGN KEY (refereeId) REFERENCES referees(refereeId) \r\n"
            		+ "	);";
            
            PreparedStatement matchesPS = conn.prepareStatement(createMatchesTable);
            matchesPS.executeUpdate();
            
            String createResultsTable = "CREATE TABLE IF NOT EXISTS results(\r\n"
            		+ "	resultId INTEGER NOT NULL,\r\n"
            		+ "	resultScore VARCHAR(5) NOT NULL,\r\n"
            		+ "	resultOutcome VARCHAR(100),\r\n"
            		+ "	matchId INTEGER NOT NULL, \r\n"
            		+ "	PRIMARY KEY (resultId),\r\n"
            		+ "	FOREIGN KEY (matchId) REFERENCES matches(matchId) \r\n"
            		+ "	);";
            
            PreparedStatement resultsPS = conn.prepareStatement(createResultsTable);
            resultsPS.executeUpdate();
            
            String createMatchEventsTable = "CREATE TABLE IF NOT EXISTS matchEvents(\r\n"
            		+ "	eventId INTEGER NOT NULL,\r\n"
            		+ "	eventType VARCHAR(25),\r\n"
            		+ "	eventMinute TINYINT,\r\n"
            		+ "	resultId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (eventId),\r\n"
            		+ "	FOREIGN KEY (resultId) REFERENCES results(resultId)\r\n"
            		+ "	);";
            
            PreparedStatement matchEventsPS = conn.prepareStatement(createMatchEventsTable);
            matchEventsPS.executeUpdate();
            
            String createTeamEmployeeTable = "CREATE TABLE IF NOT EXISTS teamEmployee(\r\n"
            		+ "	teamEmployeeId INTEGER NOT NULL,\r\n"
            		+ "	contractType VARCHAR(25),\r\n"
            		+ "	teamId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (teamEmployeeId),\r\n"
            		+ "	FOREIGN KEY (teamId) REFERENCES teams(teamId)\r\n"
            		+ "	);";
            
            PreparedStatement teamEmployeePS = conn.prepareStatement(createTeamEmployeeTable);
            teamEmployeePS.executeUpdate();
            
            String createManagersTable = "CREATE TABLE IF NOT EXISTS managers(\r\n"
            		+ "	managerId INTEGER NOT NULL,\r\n"
            		+ "	preferredFormation VARCHAR(10),\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	dob DATE,\r\n"
            		+ "	teamEmployeeId INTEGER,\r\n"
            		+ "	PRIMARY KEY (managerId),\r\n"
            		+ "	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId)\r\n"
            		+ "	);";
            
            PreparedStatement managersPS = conn.prepareStatement(createManagersTable);
            managersPS.executeUpdate();
            
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS players(\r\n"
            		+ "	playerId INTEGER NOT NULL,\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	dob DATE,\r\n"
            		+ "	positionType VARCHAR(15),\r\n"
            		+ "	shirtNumber TINYINT,\r\n"
            		+ "	isSuspended BOOLEAN,\r\n"
            		+ "	isInjured BOOLEAN,\r\n"
            		+ "	teamEmployeeId INTEGER,\r\n"
            		+ "	statsId INTEGER NOT NULL,\r\n"
            		+ "	PRIMARY KEY (playerId),\r\n"
            		+ "	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId),\r\n"
            		+ "	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId)\r\n"
            		+ "	);";
            
            PreparedStatement playersPS = conn.prepareStatement(createPlayersTable);
            playersPS.executeUpdate();
            
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}