package leagueDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import league.League;
import league.Season;
import league.Stadium;
import league.Team;
import leagueMembers.Manager;
import leagueMembers.Player;
import leagueMembers.Referee;

public class JFGPdb {
	
	private Connection connection;
	
	public JFGPdb() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:src/projectUtils/JFGP.db");
			initTables();
			exampleData();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// Gets database connection to interact with it.
	public Connection getConnection() { return connection; }
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Inserts some example data for testing application.
	public void exampleData() {
		String player1 = "INSERT OR IGNORE INTO players(playerId, fName, lName, positionType) VALUES(1, 'John', 'Smith', 'Attacker');";
		String player2 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (2, 'Michael', 'Johnson', 'midfielder');";
		String player3 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (3, 'David', 'Brown', 'defender');";
		String player4 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (4, 'Robert', 'Taylor', 'goalkeeper');";
		String player5 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (5, 'James', 'Anderson', 'attacker');";
		String player6 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (6, 'William', 'Jones', 'midfielder');";
		String player7 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (7, 'Joseph', 'Davis', 'defender');";
		String player8 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (8, 'Thomas', 'Wilson', 'goalkeeper');";
		String player9 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (9, 'Charles', 'Moore', 'attacker');";
		String player10 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (10, 'Daniel', 'Taylor', 'midfielder');";
		String player11 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (11, 'Matthew', 'White', 'defender');";
		String player12 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (12, 'Christopher', 'Martin', 'goalkeeper');";
		String player13 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (13, 'Andrew', 'Thompson', 'attacker');";
		String player14 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (14, 'Anthony', 'Garcia', 'midfielder');";
		String player15 = "INSERT OR IGNORE INTO players (playerId, fName, lName, positionType) VALUES (15, 'Mark', 'Martinez', 'defender');";
		String team1 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (2, 'Queens Park Rangers');";
		String team2 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (3, 'Leicester City');";
		String team3 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (4, 'Aston Villa');";
		String team4 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (5, 'Swansea City');";
		String team5 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (6, 'Stoke City');";
		String team6 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (7, 'Southampton');";
		String team7 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (8, 'West Bromwich Albion');";
		String team8 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (9, 'Crystal Palace');";
		String team9 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (10, 'Tottenham Hotspur');";
		String team10 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (11, 'Manchester United');";
		String team11 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (12, 'Manchester City');";
		String team12 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (13, 'Newcastle United');";
		String team13 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (14, 'Chelsea');";
		String team14 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (15, 'Burnley');";
		String team15 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (16, 'West Ham United');";
		String team16 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (17, 'Arsenal');";
		String team17 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (18, 'Hull City');";
		String team18 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (19, 'Everton');";
		String team19 = "INSERT OR IGNORE  INTO teams(teamId, teamName) VALUES (20, 'Liverpool');";
		String team20 = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (21, 'Sunderland');";
		
		String[] playerStatements = {player1, player2, player3, player4, player5, player6, player7, 
				player8, player9, player10, player11, player12, player13, player14, player15};
		
		String[] teamStatements = {team1, team2, team3, team4, team5, team6, team7, team8, 
				team9, team10, team11, team12, team13, team14, team15, team16, team17, team18, team19, team20}; 
		
		try {
	        for (String statement : playerStatements) {
	        	PreparedStatement DBstatement = connection.prepareStatement(statement);
	        	DBstatement.executeUpdate();
	        }
	        
	        for (String statement : teamStatements) {
	        	PreparedStatement DBstatement = connection.prepareStatement(statement);
	        	DBstatement.executeUpdate();
	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			}
	}
	
	public void initTables() {
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
            
            String createStadiumsTable = "CREATE TABLE IF NOT EXISTS stadiums(\r\n"
            		+ "	stadiumId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	stadiumName VARCHAR(100),\r\n"
            		+ "	capacity VARCHAR(6),\r\n"
            		+ "	stadiumLocation VARCHAR(250)\r\n"
            		+ "	);";
            
            
            String createTeamsTable = "CREATE TABLE IF NOT EXISTS teams(\r\n"
            		+ "	teamId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	teamName VARCHAR(100),\r\n"
            		+ " stadiumId INTEGER, \r\n"
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
            		+ " score VARCHAR(5), \r\n"
            		+ " matchOutcome VARCHAR(50), \r\n"
            		+ "	matchWeek INTEGER NOT NULL,\r\n"
            		+ "	seasonId INTEGER NOT NULL REFERENCES seasons(seasonId) ON DELETE CASCADE,\r\n"
            		+ "	refereeId INTEGER REFERENCES referees(refereeId) ON DELETE CASCADE,\r\n"
            		+ " homeTeamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE,\r\n"
            		+ " awayTeamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE \r\n"
            		+ "	);";
            
            String createMatchEventsTable = "CREATE TABLE IF NOT EXISTS matchEvents(\r\n"
            		+ "	eventId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	eventType VARCHAR(25),\r\n"
            		+ "	eventMinute TINYINT,\r\n"
            		+ "	teamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE, \r\n"
            		+ "	matchId INTEGER NOT NULL REFERENCES matches(matchId) ON DELETE CASCADE,\r\n"
            		+ "	playerId INTEGER NOT NULL REFERENCES players(playerId) ON DELETE CASCADE\r\n"
            		+ "	);";
            
            String createTeamEmployeeTable = "CREATE TABLE IF NOT EXISTS teamEmployee(\r\n"
            		+ "	employeeId INTEGER NOT NULL PRIMARY KEY, \r\n"
            		+ " teamId INTEGER NOT NULL REFERENCES teams(teamId) ON DELETE CASCADE,\r\n"
            		+ "	contractType VARCHAR(25)\r\n"
            		+ "	);";
            
            String createManagersTable = "CREATE TABLE IF NOT EXISTS managers(\r\n"
            		+ "	managerId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	preferredFormation VARCHAR(10),\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	teamEmployeeId INTEGER REFERENCES teamEmployee(employeeId) ON DELETE SET NULL,\r\n"
            		+ " userId INTEGER NOT NULL REFERENCES userAccounts(userId) ON DELETE CASCADE \r\n"
            		+ "	);";
            
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS players(\r\n"
            		+ "	playerId INTEGER NOT NULL PRIMARY KEY,\r\n"
            		+ "	teamEmployeeId INTEGER REFERENCES teamEmployee(employeeId) ON DELETE SET NULL,\r\n"
            		+ "	fName VARCHAR(100),\r\n"
            		+ "	lName VARCHAR(100),\r\n"
            		+ "	positionType VARCHAR(15),\r\n"
            		+ "	shirtNumber TINYINT,\r\n"
            		+ "	isSuspended BOOLEAN,\r\n"
            		+ "	isInjured BOOLEAN\r\n"
            		+ "	);";

            // The only two default instances of information in the application - the league and the admin account.
            // admin password set initially but is at liberty to be changed should the admin user deem it appropriate.
            String createLeague = "INSERT OR IGNORE INTO league (leagueId, leagueName) VALUES (1, 'Jump For Goalposts League');";
            
            String createAdmin = "INSERT OR IGNORE INTO userAccounts (userId, userType, emailAddress, password, leagueId) VALUES (1, 'admin', 'admin@jfgp.org', 'password', 1);";

            String byeWeek = "INSERT OR IGNORE INTO teams(teamId, teamName) VALUES (1, 'BYE');";
            
            String[] statements ={createleaguetable, createUserAccountTable, createSeasonsTable, createRefereesTable,
            		createStadiumsTable, createTeamsTable, teamsSeason, createMatchesTable,
    				createTeamEmployeeTable, createManagersTable,createPlayersTable, createMatchEventsTable, 
    				createLeague, createAdmin, byeWeek};
            
            for (String statement : statements) {
            	PreparedStatement DBstatement = connection.prepareStatement(statement);
            	DBstatement.executeUpdate();
            }

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/*
	 * Remaning methods are for more generic data retrieval from database, including both retrieval
	 * of single instances of classes and all instances of a class. For example, all the managers in the 
	 * database can be found at once and then have objects instantiated for each one using the data found.
	 */
	public Referee getRefereeFromId(int id) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	        refStatement.setInt(1, id);
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		id
	        		);
	       
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public List<Referee> getAllReferees() {
		List<Referee> referees = new ArrayList<Referee>();
		
		try {
			PreparedStatement refsStatement = (connection).prepareStatement(
			        "SELECT * FROM referees");
			ResultSet refResult = refsStatement.executeQuery();
			
			while(refResult.next()) {
				Referee ref = new Referee(
		        		refResult.getInt("refereeId"),
		        		refResult.getString("fname"),
		        		refResult.getString("lName"), 
		        		refResult.getString("preferredLocation"),
		        		refResult.getInt("userId")
		        		);
				
				referees.add(ref);
			}
			
			for(Referee ref : referees) { ref.setRefAcc(ref.getRefereeAccount(connection, ref.getUserId())); }
					
			return referees;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public Manager getManagerFromId(int id) {
		try {
	        PreparedStatement manStatement = connection.prepareStatement(
	                "SELECT * FROM managers WHERE userId = ?;" );
	
	        manStatement.setInt(1, id);
	        ResultSet manResult = manStatement.executeQuery(); 
	        
	        Manager man = new Manager(
	        		manResult.getInt("managerId"),
	        		manResult.getString("fname"),
	        		manResult.getString("lName"),
	        		id
	        		);
	       
			return man;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public List<Manager> getAllManagers() {
		List<Manager> managers = new ArrayList<Manager>();
		
		try {
			PreparedStatement manStatement = (connection).prepareStatement(
			        "SELECT * FROM managers;");
			ResultSet manResult = manStatement.executeQuery();
			
			while(manResult.next()) {
				Manager man = new Manager(
						manResult.getInt("managerId"),
						manResult.getString("fname"),
						manResult.getString("lName"), 
						manResult.getInt("userId")
		        		);
				
				managers.add(man);
			}
			
			for(Manager man : managers) { man.setManagerAcc(man.getManagerAccount(connection, man.getUserId())); }
					
			return managers;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM players;");
			ResultSet playerResult = playerStatement.executeQuery();
			
			while(playerResult.next()) {
				Player player = new Player(
						playerResult.getInt("playerId"),
						playerResult.getString("fname"),
						playerResult.getString("lName")
		        		);
				players.add(player);
			}
			return players;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public List<Team> getAllTeams() {
		List<Team> teams = new ArrayList<Team>();
		
		try {
			PreparedStatement teamsStatement = (connection).prepareStatement( "SELECT * FROM teams");
			ResultSet teamResult = teamsStatement.executeQuery();
			
			while(teamResult.next()) {
				if (teamResult.getInt("teamId") == 1) { continue; } 
				else {
				Team team = new Team(
						teamResult.getInt("teamId"),
						teamResult.getString("teamName")
		        		);
				teams.add(team);
				}
			}				
			return teams;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public Team getTeam(int id) { 
		Team team = null;
		
		try {
			PreparedStatement teamStatement = (connection).prepareStatement(
			        "SELECT * FROM teams WHERE teamId = ?;");
			
			teamStatement.setInt(1, id);
			ResultSet teamResult = teamStatement.executeQuery();
			team = new Team(teamResult.getInt("teamId"), 
					teamResult.getString("teamName")
					); 
					
			return team;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public Season findCurrentSeason() {
		try {
			PreparedStatement currentSeasonStatement = (connection).prepareStatement(
					"SELECT * FROM seasons WHERE isCurrent = true LIMIT 1;" );
			ResultSet seasonResult = currentSeasonStatement.executeQuery();
			
			Season currentSeason = new Season( 
					seasonResult.getInt("seasonId"),
					seasonResult.getString("seasonStart"),
					seasonResult.getString("seasonEnd"),
					seasonResult.getBoolean("isCurrent")
					);
			
			return currentSeason;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}
	
	public List<Stadium> getAllStadiums() {
		List<Stadium> stadiums = new ArrayList<Stadium>();
		
		try {
			PreparedStatement stadiumStatement = (connection).prepareStatement( "SELECT * FROM stadiums");
			ResultSet stadiumResult = stadiumStatement.executeQuery();
			
			while(stadiumResult.next()) {
				Stadium stadium = new Stadium(
						stadiumResult.getInt("stadiumId"),
						stadiumResult.getString("stadiumName"),
						stadiumResult.getString("capacity"),
						stadiumResult.getString("stadiumLocation")
		        		);
				stadiums.add(stadium);
			}				
			return stadiums;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public List<Manager> allTeamManagers() {
		List<Manager> teamManagers = new ArrayList<Manager>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM managers "
			        + "JOIN teamEmployee ON managers.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId IS NOT NULL;");
			
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Manager manager = new Manager(
						managers.getInt("managerId"),
						managers.getString("fname"),
						managers.getString("lName"),
						managers.getInt("userId")
		        		);
				teamManagers.add(manager);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamManagers;
	}
	
	public List<Player> allTeamPlayers() {
		List<Player> teamManagers = new ArrayList<Player>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM players "
			        + "JOIN teamEmployee ON players.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId IS NOT NULL;");
			
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Player manager = new Player(
						managers.getInt("playerId"),
						managers.getString("fname"),
						managers.getString("lName")
		        		);
				teamManagers.add(manager);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamManagers;
	}
	
	public League getLeague() {
		try {
			PreparedStatement leagueStatement = connection.prepareStatement(
			        "SELECT * FROM league WHERE leagueId = 1");
			ResultSet leagueResult = leagueStatement.executeQuery();
			
			League jfgpLeague = new League(
					leagueResult.getInt("leagueId"),
					leagueResult.getString("leagueName"));
					
			return jfgpLeague;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
}
