package league;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import leagueDB.RetrieveGeneralStatistics;
import leagueMembers.Manager;
import leagueMembers.Player;

public class Team implements RetrieveGeneralStatistics {
	
	private int teamId;
	private String name;
	private Stadium stadium;
	private Manager currentManager;
	private List<Player> players;
	
	public Team(int id, String name) {
		setTeamId(id);
		setName(name);
	}
	
	public int getTeamId() { return teamId; }
	public void setTeamId(int teamId) { this.teamId = teamId; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public Stadium getStadium() { return stadium; }
	public void setStadium(Stadium stadium) { this.stadium = stadium; }
	
	public Manager getManager() { return currentManager; }
	public void setManager(Manager manager) { this.currentManager = manager; }
	
	public List<Player> getPlayers() { return this.players; }
	public void setPlayers(List<Player> teamPlayers) { this.players = teamPlayers; }

	@Override
	public int getYellows(Connection connection) {
		int yellows = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE teamId = ? AND eventType = 'Yellow Card';");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			yellows = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return yellows;
	}

	@Override
	public int getReds(Connection connection) {
		int reds = 0;
		
	try {
		PreparedStatement goalsStatement = connection.prepareStatement( 
				"SELECT COUNT(*) AS fouls FROM matchEvents WHERE teamId = ? AND eventType = 'Red Card';");
		goalsStatement.setInt(1, getTeamId());
		ResultSet res = goalsStatement.executeQuery();
		reds = res.getInt(1);
		 
	} catch (SQLException e) { e.printStackTrace(); }
	
	return reds;
	}

	@Override
	public int getFouls(Connection connection) {
		int fouls = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE teamId = ? AND eventType = 'Foul';");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 fouls = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return fouls;
	}

	@Override
	public int getGoals(Connection connection) {
		int goals = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS goals FROM matchEvents WHERE teamId = ? AND eventType = 'Goal';");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 goals = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	return goals;
	}

	@Override
	public int getAssists(Connection connection) {
		int assists = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS assists FROM matchEvents WHERE playerId = ? AND eventType = 'Assist';");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			assists = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
	
	return assists;
	}
	
	public int getGamesPlayed(Connection connection) {
		int games = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS games FROM matches WHERE (homeTeamId = ? OR awayTeamId = ?) AND isComplete = 1;");
			goalsStatement.setInt(1, getTeamId());
			goalsStatement.setInt(2, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 games = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return games;
	}
	
	
	public int getTeamWins(Connection connection) {
		int wins = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matches WHERE (homeTeamId = ? AND matchOutcome = 'Home Win') OR (awayTeamId = ? AND matchOutcome = 'Away Win');");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			wins = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return wins;
	}
	
	public int getTeamDraws(Connection connection) {
		int draws = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matches WHERE (homeTeamId = ? OR awayTeamId = ?) AND  matchOutcome = 'Draw';");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			draws = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return draws;
	}
	
	public int getTeamLosses(Connection connection) {
		int losses = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matches WHERE (homeTeamId = ? AND matchOutcome = 'Away Win') OR (awayTeamId = ? AND matchOutcome = 'Home Win');");
			goalsStatement.setInt(1, getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			losses = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return losses;
	}
	
	public int getTeamPoints(Connection connection) {
		int points = 0;
		points += getTeamWins(connection) * 3;
		points += getTeamDraws(connection);
		return points;
	}
	
	public Manager getTeamManager(Connection connection) {
		Manager teamManager = null;
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM managers "
			        + "JOIN teamEmployee ON managers.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId = ?;");
			
			playerStatement.setInt(1, getTeamId());
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Manager manager = new Manager(
						managers.getInt("playerId"),
						managers.getString("fname"),
						managers.getString("lName"),
						managers.getInt("userId")
		        		);
				teamManager = manager;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamManager;
	}
	
	// gets players assigned to sepcific instance of a team
	public List<Player> getTeamPlayers(Connection connection) {
		List<Player> teamPlayers = new ArrayList<Player>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM players "
			        + "JOIN teamEmployee ON players.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId = ?;");
			
			playerStatement.setInt(1, getTeamId());
			ResultSet players = playerStatement.executeQuery();
			
			while(players.next()) {
				Player player = new Player(
						players.getInt("playerId"),
						players.getString("fname"),
						players.getString("lName")
		        		);
				teamPlayers.add(player);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		setPlayers(teamPlayers);
		return teamPlayers;
	}

	public boolean checkStadiumAssigned(Connection connection) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT teamId FROM teams WHERE teamId = ? AND stadiumId IS NOT NULL;");
			
			matchStatement.setInt(1, getTeamId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
}
