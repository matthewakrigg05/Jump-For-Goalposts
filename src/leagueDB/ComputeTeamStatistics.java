package leagueDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import league.Team;

public interface ComputeTeamStatistics {

	public static int getGamesPlayed(Connection connection, Team team) {
		int games = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS games FROM matches WHERE (homeTeamId = ? OR awayTeamId = ?) AND isComplete = 1;");
			goalsStatement.setInt(1, team.getTeamId());
			goalsStatement.setInt(2, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 games = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return games;
	}

	public static int getTeamGoals(Connection connection, Team team) {
		int goals = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS goals FROM matchEvents WHERE teamId = ? AND eventType = 'Goal';");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 goals = res.getInt(1);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goals;
	}
	
	public static int getTeamYellows(Connection connection, Team team) {
		int yellows = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE teamId = ? AND eventType = 'Yellow Card';");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			yellows = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return yellows;
	}
	
	public static int getTeamRed(Connection connection, Team team) {
		int reds = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE teamId = ? AND eventType = 'Red Card';");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			reds = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return reds;
	}
	
	public static int getTeamFouls(Connection connection, Team team) {
		int fouls = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE teamId = ? AND eventType = 'Foul';");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			 fouls = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return fouls;
	}
	
	public static int getTeamWins(Connection connection, Team team) {
		int wins = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matches WHERE (homeTeamId = ? AND matchOutcome = 'Home Win') OR (awayTeamId = ? AND matchOutcome = 'Away Win');");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			wins = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return wins;
	}
	
	public static int getTeamDraws(Connection connection, Team team) {
		int draws = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matches WHERE (homeTeamId = ? OR awayTeamId = ?) AND  matchOutcome = 'Draw';");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			draws = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return draws;
	}
	
	public static int getTeamLosses(Connection connection, Team team) {
		int losses = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matches WHERE (homeTeamId = ? AND matchOutcome = 'Away Win') OR (awayTeamId = ? AND matchOutcome = 'Home Win');");
			goalsStatement.setInt(1, team.getTeamId());
			ResultSet res = goalsStatement.executeQuery();
			losses = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return losses;
	}
}
