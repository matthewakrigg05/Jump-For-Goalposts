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
	
	// assigns account as admin
	public AdminAccount(int id, String emailAddress, String password) { super(id, emailAddress, password, true); }
	
	/*
	 * Assign and remove referee methods allows referees to be assigned to matches,
	 * this is important as the referee accounts can only record matches that they are 
	 * assigned to. This same idea applies to the methods for assigning managers,
	 * players and stadiums to teams.
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
	
	public void assignStadium(Connection connection, Team team, Stadium stadium) {
		try {
			PreparedStatement assignStadiumStatement = connection.prepareStatement(
			        "UPDATE teams SET stadiumId = ? WHERE teamId = ?;");
			assignStadiumStatement.setInt(1, team.getTeamId());
			assignStadiumStatement.setInt(2, stadium.getStadiumId());
			assignStadiumStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
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
	
	public void unassignManagerFromTeam(Connection connection, Manager manager) {
		try {
			PreparedStatement removeManagerEmpStatement = connection.prepareStatement(
			        "DELETE FROM teamEmployee WHERE employeeId IN (SELECT teamEmployeeId FROM managers WHERE managerId = ?);");
			removeManagerEmpStatement.setInt(2, manager.getId());
			removeManagerEmpStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void unassignPlayerFromTeam(Connection connection, Player player) {
		try {
			PreparedStatement removePlayerEmpStatement = connection.prepareStatement(
			        "DELETE FROM teamEmployee WHERE employeeId IN (SELECT teamEmployeeId FROM players WHERE playerId = ?);");
			removePlayerEmpStatement.setInt(1, player.getId());
			removePlayerEmpStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
    /*
     * Following method is the logic used to create alternating home and away games for a team, as well as
     * creating the second fixture between two teams in the second half of the season when the teams
     * swap being home and away.
     */
	public void createSeasonMatches(JFGPdb db, List<Team> teams, Season season) {
		if (teams.size() % 2 != 0) {teams.add(db.getTeam(1)); } // in case there are an odd number of teams, add the BYE team - meaning bye week where team doesnt play
	
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
	
	public void changeLeagueName(Connection connection, String newName) {
		try {
			PreparedStatement leagueStatement = connection.prepareStatement(
			        "UPDATE league SET leagueName = ? WHERE leagueId = 1;");
			leagueStatement.setString(1, newName);
			leagueStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
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

	/*
	 * Remaining methods are responisble for removing and adding instances of objects into the SQLite db,
	 * they pass the details from the objects into the db, and if they are a referee or manager, these methods
	 * also create an account for them, automatically generating an email address and password.
	 */
	public void removeManager(Connection connection, Manager manager) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			seasonStatement.setInt(1, manager.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = connection.prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			refAccDel.setInt(1, manager.getUserId());
			refAccDel.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
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

	public void createTeam(Connection connection, String teamName) {
		try {
			PreparedStatement teamStatement = connection.prepareStatement(
			        "INSERT INTO teams(teamName) VALUES (?);");
			teamStatement.setString(1, teamName);
			teamStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void removeTeam(Connection connection, Team team) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "DELETE FROM teams WHERE teamId = ?;");
			seasonStatement.setInt(1, team.getTeamId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
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
	
	public void removePlayer(Connection connection, Player player) {
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "DELETE FROM players WHERE playerId = ?;");
			
			playerStatement.setInt(1, player.getId());
			playerStatement.executeUpdate();

			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
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
	
	public void removeSeason(Connection connection, Season season) {
		try {
			PreparedStatement seasonStatement = connection.prepareStatement(
			        "DELETE FROM seasons WHERE seasonId = ?;");
			
			seasonStatement.setInt(1, season.getId());
			seasonStatement.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void createMatch(Connection connection, Team homeTeam, Team awayTeam, Season season, int matchWeek) {
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
	
	public void removeStadium(Connection connection, Stadium stadium) {
		try {
			PreparedStatement stadiumStatement = connection.prepareStatement(
			        "DELETE FROM stadiums WHERE stadiumId = ?;");
			stadiumStatement.setInt(1, stadium.getStadiumId());
			stadiumStatement.executeUpdate();
		
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
