package accounts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import league.*;
import leagueDB.JFGPdb;
import leagueMembers.*;

public class AdminAccount extends RefereeAccount {
	
	/**
	 * The assign referee method allows referees to be assigned to matches,
	 * this is important as the referee accounts can only record matches that they are 
	 * assigned to.
	 * 
	 * @param id  			The ID of the user account in the user accounts table of the database.
	 * 
	 * @param emailAddress	The email address associated with the instance account.
	 * 
	 * @param password		The password associated with the instance account.	
	 */
	public AdminAccount(int id, String emailAddress, String password) { super(id, emailAddress, password); }
	
	/**
	 * The assign referee method allows referees to be assigned to matches,
	 * this is important as the referee accounts can only record matches that they are 
	 * assigned to.
	 * 
	 * @param connection  The connection to the database must be passed on in order
	 * 					  for the methods to interact safely with the database.
	 * 
	 * @param match		  Match that is having a referee assigned to it.	
	 * 
	 * @param ref		  Referee being assigned to the match.
	 */
	public void assignRef(Connection connection, Match match, Referee ref) {
		try {
			PreparedStatement assignRefStatement = connection.prepareStatement(
			        "UPDATE matches SET refereeId = ? WHERE matchId = ?;");
			assignRefStatement.setInt(1, ref.getId());
			assignRefStatement.setInt(2, match.getMatchId());
			assignRefStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * The assign stadium method allows referees to be assigned to teams. This is then the
	 * stadium that is referred to when the team is playing a match at their home ground as
	 * the match location.
	 * 
	 * @param connection  The connection to the database must be passed on in order
	 * 					  for the methods to interact safely with the database.
	 * 
	 * @param team		  Team that is having a stadium assigned to it.	
	 * 
	 * @param stadium	  Stadium being assigned as the home ground for the team.
	 */
	
	public void assignStadium(Connection connection, Team team, Stadium stadium) {
		try {
			PreparedStatement assignStadiumStatement = connection.prepareStatement(
			        "UPDATE teams SET stadiumId = ? WHERE teamId = ?;");
			assignStadiumStatement.setInt(1, team.getTeamId());
			assignStadiumStatement.setInt(2, stadium.getStadiumId());
			assignStadiumStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * The assign player to team method enables the player to be registered as one of 
	 * the teams employees and can record goals and other match events for both teams 
	 * and the players with ease.
	 * 
	 * @param connection  The connection to the database must be passed on in order
	 * 					  for the methods to interact safely with the database.
	 * 
	 * @param team		  The team that is having the player assigned to it.	
	 * 
	 * @param player	  Player being assigned to a team.
	 * 
	 * @param contract 	  The type of contract that the player has at the team (full-time/
	 * 					  part-time).
	 */
	public void assignPlayerToTeam(Connection connection, Team team, Player player, String contract) {
		try {
			PreparedStatement assignPlayerToTeamStatement = connection.prepareStatement(
			        "INSERT INTO teamEmployee(teamId, contractType) VALUES (?, ?);");
			assignPlayerToTeamStatement.setInt(1, team.getTeamId());
			assignPlayerToTeamStatement.setString(2, contract);
			assignPlayerToTeamStatement.executeUpdate();
		 
			PreparedStatement lastId = connection.prepareStatement(
					"SELECT employeeId FROM teamEmployee ORDER BY ROWID DESC limit 1;");
			ResultSet res = lastId.executeQuery();	
			int playerEmpId = res.getInt("employeeId");
			
			PreparedStatement playerEmpIdStatement = connection.prepareStatement(
			        "UPDATE players SET teamEmployeeId = ? WHERE playerId = ?;");
			playerEmpIdStatement.setInt(1, playerEmpId);
			playerEmpIdStatement.setInt(2, player.getId());
			playerEmpIdStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * The assign manager to team method enables the player to be registered as one of 
	 * the teams employees and enables that manager to access the players of that team
	 * through their manager panel in the 'Your View' section of the application where
	 * the manager can change the players shirt number.
	 * 
	 * @param connection  The connection to the database must be passed on in order
	 * 					  for the methods to interact safely with the database.
	 * 
	 * @param team		  The team that is having the manager assigned to it.	
	 * 
	 * @param manager	  Manager being assigned to a team.
	 * 
	 * @param contract 	  The type of contract that the player has at the team (full-time/
	 * 					  part-time).
	 */
	public void assignManagerToTeam(Connection connection, Team team, Manager manager, String contract) {
		try {
			PreparedStatement newEmpStatement = connection.prepareStatement(
			        "INSERT INTO teamEmployee(teamId, contractType) VALUES (?, ?);");
			newEmpStatement.setInt(1, team.getTeamId());
			newEmpStatement.setString(2, contract);
			newEmpStatement.executeUpdate();
		
			PreparedStatement lastId = connection.prepareStatement(
					"SELECT employeeId FROM teamEmployee ORDER BY ROWID DESC limit 1;");
			ResultSet res = lastId.executeQuery();	
			int playerEmpId = res.getInt("employeeId");
			
			PreparedStatement managerEmpIdStatement = connection.prepareStatement(
			        "UPDATE managers SET teamEmployeeId = ? WHERE managerId = ?;");
			managerEmpIdStatement.setInt(1, playerEmpId);
			managerEmpIdStatement.setInt(2, manager.getId());
			managerEmpIdStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Unassign manager from team method refers to the team employee table, locates the manager by ID
	 * and consequently deletes that row from the teamEmployee table; the teamEmployeeId in the respective 
	 * managers table is then set to NULL as a result of an ON DELETE clause in the database table initialisation.
	 * 
	 * @param connection 	Connection to the database in order to safely interact with it.
	 * 
	 * @param manager 		The manager being unassigned from the team that they are currently assigned to.
	 */
	public void unassignManagerFromTeam(Connection connection, Manager manager) {
		try {
			PreparedStatement removeManagerEmpStatement = connection.prepareStatement(
			        "DELETE FROM teamEmployee WHERE employeeId IN (SELECT teamEmployeeId FROM managers WHERE managerId = ?);");
			removeManagerEmpStatement.setInt(2, manager.getId());
			removeManagerEmpStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Unassign player from team method refers to the team employee table, locates the manager by ID
	 * and consequently deletes that row from the teamEmployee table; the teamEmployeeId in the respective 
	 * players table is then set to NULL as a result of an ON DELETE clause in the database table initialisation.
	 * 
	 * @param connection 	Connection to the database in order to safely interact with it.
	 * 
	 * @param player 		The player being unassigned from the team that they are currently assigned to.
	 */
	public void unassignPlayerFromTeam(Connection connection, Player player) {
		try {
			PreparedStatement removePlayerEmpStatement = connection.prepareStatement(
			        "DELETE FROM teamEmployee WHERE employeeId IN (SELECT teamEmployeeId FROM players WHERE playerId = ?);");
			removePlayerEmpStatement.setInt(1, player.getId());
			removePlayerEmpStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
    /**
     * Following method is the logic used to create alternating home and away games for a team, as well as
     * creating the second fixture between two teams in the second half of the season when the teams
     * swap being home and away.
     * 
     * @param db		The database object itself rather than the connection, this is because the db class
     * 					contains a method to retrieve teams by their team ID and this is necessary to 
     * 					add the bye-week team to the teams list if there are an odd number of teams.
     * 
     * @param teams		The teams selected to participate in the season and therefore need to have matches
     * 					scheduled for them.
     * 
     * @param season	The season that the selected teams will be playing in.
     */
	
	public void createSeasonMatches(JFGPdb db, List<Team> teams, Season season) {
		if (teams.size() % 2 != 0) {teams.add(db.getTeam(1)); } // in the event that there are an odd number of teams, add BYE team - meaning bye week where a team doesnt play
	
	    int numMatchesPerRound = teams.size() / 2;
	    Connection connection = db.getConnection();
	    List<Team> rotatingTeams = new ArrayList<Team>(teams.subList(1, teams.size()));
	

	    for (int round = 0; round < teams.size() - 1; round++) {
	        for (int i = 0; i < numMatchesPerRound; i++) {
	            Team homeTeam = (i == 0) ? teams.get(0) : rotatingTeams.get(i - 1);
	            Team awayTeam = rotatingTeams.get(rotatingTeams.size() - i - 1);
	            
	            createMatch(connection, homeTeam, awayTeam, season, round + 1);
	            createMatch(connection, awayTeam, homeTeam, season,  teams.size() + round);
	        }
	        Collections.rotate(rotatingTeams, 1);
	    }
	   
	    // inserts team and season id into junction table to handle what teams are playing in which season
		try {
			for (Team team : teams) {
				PreparedStatement teamSeasonStatement = connection.prepareStatement(
					"INSERT INTO teamSeason(teamId, seasonId) VALUES (?, ?);");
				teamSeasonStatement.setInt(1, team.getTeamId()); 
				teamSeasonStatement.setInt(2, season.getId()); 
				teamSeasonStatement.executeUpdate();
				}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Change league name method allows for customisation of the application and the league 
	 * that the admin is managing.
	 * 
	 * @param connection 	Connection to the database in order to safely interact with it.
	 * 
	 * @param newName 		The new inputted name for the league
	 */
	public void changeLeagueName(Connection connection, String newName) {
		try {
			PreparedStatement leagueStatement = connection.prepareStatement(
			        "UPDATE league SET leagueName = ? WHERE leagueId = 1;");
			leagueStatement.setString(1, newName);
			leagueStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Allows the admin to tell the system which season is the season that is currently being 
	 * played or the season that is due to be started.
	 * 
	 * @param connection 	Connection to the database in order to safely interact with it.
	 * 
	 * @param seasonId 		The season being set as active/current
	 */
	public void setCurrentSeason(Connection connection, int seasonId) {
		try {
			PreparedStatement deselectCurrSeasonStatement = connection.prepareStatement(
					"UPDATE seasons SET isCurrent = false WHERE isCurrent = true;" );
			deselectCurrSeasonStatement.executeUpdate();
			
			PreparedStatement setCurrSeasonStatement = connection.prepareStatement(
					"UPDATE seasons SET isCurrent = true WHERE seasonId = ?;" );
			
			setCurrSeasonStatement.setInt(1, seasonId);
			setCurrSeasonStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }	
	}

	/**
	 * Removes manager as a person from the database and then goes on to remove their account 
	 * from the database too, this ensures that the manager no longer has access to the system
	 * with manager access.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param manager		Manager to be removed.
	 */
	public void removeManager(Connection connection, Manager manager) {
		try {
			PreparedStatement delManStatement = connection.prepareStatement(
			        "DELETE FROM managers WHERE managerId = ?;");
			delManStatement.setInt(1, manager.getId());
			delManStatement.executeUpdate();
			
			PreparedStatement delManAccStatement = connection.prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			delManAccStatement.setInt(1, manager.getUserId());
			delManAccStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Adds manager person into the managers table, along with their userId when their account
	 * is created.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param fname			Managers first name.
	 * 
	 * @param lname 		Managers last name.
	 * 
	 * @param id			User ID associated with managers account for accessing the system.
	 */
	public void createManager(Connection connection, String fname, String lname, int id) {
		try {
			PreparedStatement manStatement = connection.prepareStatement(
			        "INSERT INTO managers(fName, lName, userId) VALUES (?, ?, ?);");
			manStatement.setString(1, fname);
			manStatement.setString(2, lname);
			manStatement.setInt(3, id);
			manStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Adds manager account into the userAccounts table, then goes on to create a new manager
	 * in the managers table using the createManager method.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param fname			Managers first name.
	 * 
	 * @param lname 		Managers last name.
	 */
	public void createManagerAccount(Connection connection, String fname, String lname) {
		try {
			PreparedStatement refAccStatement = connection.prepareStatement(
			        "INSERT INTO userAccounts(userType, emailAddress, password, leagueId) VALUES ('manager', ?, ?, 1);");
			refAccStatement.setString(1, lname + "@jfgp.org");
			refAccStatement.setString(2, lname + fname);
			refAccStatement.executeUpdate();
			
			PreparedStatement lastId = connection.prepareStatement(
					"SELECT userId FROM userAccounts ORDER BY ROWID DESC limit 1;");
			ResultSet id = lastId.executeQuery();
			int manId = id.getInt("userId");
	
			createManager(connection, fname, lname, manId);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}

	/**
	 * Creates a row in teams table of database for new team.
	 * 
	 * @param connection	Connection to the database.
	 * 
	 * @param teamName		Name of team being added.
	 */
	public void createTeam(Connection connection, String teamName) {
		try {
			PreparedStatement teamStatement = connection.prepareStatement(
			        "INSERT INTO teams(teamName) VALUES (?);");
			teamStatement.setString(1, teamName);
			teamStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Removes a row in teams table of database associated with a team.
	 * 
	 * @param connection	Connection to the database.
	 * 
	 * @param team			Team being removed.
	 */
	public void removeTeam(Connection connection, Team team) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "DELETE FROM teams WHERE teamId = ?;");
			seasonStatement.setInt(1, team.getTeamId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Adds player person into the players table.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param fname			Players first name.
	 * 
	 * @param lname 		Players last name.
	 * 
	 * @param positionType  The position that the player plays in most often.
	 */
	public void createPlayer(Connection connection, String fname, String lname, String positionType) {
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "INSERT INTO players(fName, lName, positionType) VALUES (?, ?, ?);");
			playerStatement.setString(1, fname);
			playerStatement.setString(2, lname);
			playerStatement.setString(3, positionType);
			playerStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Removes player person from the players table.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param player		Instance of the player that is to be removed from the database.
	 */
	public void removePlayer(Connection connection, Player player) {
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "DELETE FROM players WHERE playerId = ?;");
			playerStatement.setInt(1, player.getId());
			playerStatement.executeUpdate();

			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Removes referee person from the referees table.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param ref			Instance of the referee that is to be removed from the database.
	 */
	public void removeReferee(Connection connection, Referee ref) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			seasonStatement.setInt(1, ref.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = connection.prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			refAccDel.setInt(1, ref.getUserId());
			refAccDel.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Adds referee person into the referees table.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param fname			Referees first name.
	 * 
	 * @param lname 		Referees last name.
	 * 
	 * @param city		    The city that the referee is based in - would be used for deciding 
	 * 						which matches they officiate.
	 * 
	 * @param id			UserID associated with the referee being created.
	 */
	public void createReferee(Connection connection, String fname, String lname, String city, int id) {
		try {
			PreparedStatement refStatement = connection.prepareStatement(
			        "INSERT INTO referees(fName, lName, preferredLocation, leagueId, userId) VALUES (?, ?, ?, 1, ?);");
			refStatement.setString(1, fname);
			refStatement.setString(2, lname);
			refStatement.setString(3, city);
			refStatement.setInt(4, id);
			refStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Adds referee account into the userAccounts table, then goes on to create a new referee
	 * in the referees table using the createReferee method.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param fname			Referees first name.
	 * 
	 * @param lname 		Referees last name.
	 * 
	 * @param city			The city that the referee is based in - would be used for deciding 
	 * 						which matches they officiate.
	 */
	public void createRefereeAccount(Connection connection, String fname, String lname, String city) {
		try {
			PreparedStatement refAccStatement = connection.prepareStatement(
			        "INSERT INTO userAccounts(userType, emailAddress, password, leagueId) VALUES ('referee', ?, ?, 1);");
			refAccStatement.setString(1, lname + "@jfgp.org");
			refAccStatement.setString(2, lname + city);
			refAccStatement.executeUpdate();
			
			PreparedStatement lastId = connection.prepareStatement(
					"SELECT userId FROM userAccounts ORDER BY ROWID DESC limit 1;");
			ResultSet id = lastId.executeQuery();
			int refId = id.getInt("userId");
			createReferee(connection, fname, lname, city, refId);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Searches database for a season that has been assigned as the current season, if
	 * there is a season assigned as the current one, the new season is created and sets
	 * isCurrent to false, however if there is no current season already, this season
	 * being created is assigned as current.
	 * 
	 * @param connection	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param start			Start year for the season.
	 * 
	 * @param end			End year for the season.
	 */
	public void createSeason(Connection connection, String start, String end) {
		try {
			PreparedStatement isCurrentSeason = connection.prepareStatement(
					"SELECT isCurrent FROM seasons WHERE isCurrent = true;");
			ResultSet isCurrent = isCurrentSeason.executeQuery();
			
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "INSERT INTO seasons(seasonStart, seasonEnd, isCurrent, leagueId) VALUES (?, ?, ?, 1);");
			seasonStatement.setString(1, start);
			seasonStatement.setString(2, end);
			
			if (!isCurrent.next()) { seasonStatement.setBoolean(3, true); }
			else { seasonStatement.setBoolean(3, false); }
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Removes instance of a stadium from the database.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param season		Instance of a season that is to be removed from the database.
	 */
	public void removeSeason(Connection connection, Season season) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "DELETE FROM seasons WHERE seasonId = ?;");
			seasonStatement.setInt(1, season.getId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Creates a match row to be added to the matches table of the database.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param homeTeam		Home team for the match.
	 * 
	 * @param awayTeam		Away team for the match.
	 * 
	 * @param season		Season that the match is going to be played in.
	 * 
	 * @param matchWeek		The week that the match will be played.
	 */
	public void createMatch(Connection connection, Team homeTeam, Team awayTeam, Season season, int matchWeek) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "INSERT INTO matches(isComplete, matchWeek, seasonId, homeTeamId, awayTeamId) VALUES (FALSE, ?, ?, ?, ?);");
			seasonStatement.setInt(1 , matchWeek);
			seasonStatement.setInt(2, season.getId());
			seasonStatement.setInt(3, homeTeam.getTeamId());
			seasonStatement.setInt(4, awayTeam.getTeamId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Creates stadium to be added to the stadiums table of the database.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param name			Name of the stadium being added to the database.
	 * 
	 * @param cap			Capacity of the stadium being added.
	 * 
	 * @param loc			Location of the stadium/city that stadium is based in.
	 */
	public void createStadium(Connection connection, String name, String cap, String loc) {
		try {
			PreparedStatement stadiumStatement = connection.prepareStatement(
			        "INSERT INTO stadiums(stadiumName, capacity, stadiumLocation) VALUES (?, ?, ?);");
			stadiumStatement.setString(1, name);
			stadiumStatement.setString(2, cap);
			stadiumStatement.setString(3, loc);
			stadiumStatement.executeUpdate();
		
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Removes stadium from the stadiums table.
	 * 
	 * @param connection  	Connection to the database ensuring safe interaction with it.
	 * 
	 * @param stadium		Instance of a stadium that is to be removed from the database.
	 */
	public void removeStadium(Connection connection, Stadium stadium) {
		try {
			PreparedStatement stadiumStatement = connection.prepareStatement(
			        "DELETE FROM stadiums WHERE stadiumId = ?;");
			stadiumStatement.setInt(1, stadium.getStadiumId());
			stadiumStatement.executeUpdate();
		
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
