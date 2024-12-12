package leagueMembers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import league.Team;
import leagueDB.RetrieveGeneralStatistics;

public class Player extends Person implements RetrieveGeneralStatistics {
	
	private String preferredFoot;
	private boolean isInjured;
	private boolean isSuspended;
	private int shirtNum;
	private String positionType;
	

	public Player(int id, String fName, String lName) {
		super(id, fName, lName);
	}

	// Standard getters and setters
	public String getPreferredFoot() { return preferredFoot; }
	public void setPreferredFoot(String preferredFoot) { this.preferredFoot = preferredFoot; }
	
	public boolean isInjured() { return isInjured; }
	public void setInjured(boolean isInjured) { this.isInjured = isInjured; }

	public boolean isSuspended() { return isSuspended; }
	public void setSuspended(boolean isSuspended) { this.isSuspended = isSuspended;	}

	public int getShirtNum() { return shirtNum;	}
	public void setShirtNum(int shirtNum) { this.shirtNum = shirtNum; }

	public String getPositionType() { return positionType; }
	public void setPositionType(String positionType) { this.positionType = positionType; }

	@Override
	public int getGoals(Connection connection) {
		int goals = 0;
			
			try {
				PreparedStatement goalsStatement = connection.prepareStatement( 
						"SELECT COUNT(*) AS goals FROM matchEvents WHERE playerId = ? AND eventType = 'Goal';");
				goalsStatement.setInt(1, getId());
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
				goalsStatement.setInt(1, getId());
				ResultSet res = goalsStatement.executeQuery();
				assists = res.getInt(1);
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return assists;
	}

	@Override
	public int getFouls(Connection connection) {
		int fouls = 0;
				
			try {
				PreparedStatement goalsStatement = connection.prepareStatement( 
						"SELECT COUNT(*) AS fouls FROM matchEvents WHERE playerId = ? AND eventType = 'Foul';");
				goalsStatement.setInt(1, getId());
				ResultSet res = goalsStatement.executeQuery();
				 fouls = res.getInt(1);
				 
			} catch (SQLException e) { e.printStackTrace(); }
			
			return fouls;
	}

	@Override
	public int getYellows(Connection connection) {
		int yellows = 0;
		
		try {
			PreparedStatement goalsStatement = connection.prepareStatement( 
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE playerId = ? AND eventType = 'Yellow Card';");
			goalsStatement.setInt(1, getId());
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
					"SELECT COUNT(*) AS fouls FROM matchEvents WHERE playerId = ? AND eventType = 'Red Card';");
			goalsStatement.setInt(1, getId());
			ResultSet res = goalsStatement.executeQuery();
			reds = res.getInt(1);
			 
		} catch (SQLException e) { e.printStackTrace(); }
		
		return reds;
	}

	public Team getPlayerTeam(Connection connection) {
		Team playerTeam = null;
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM teams "
			        + "JOIN teamEmployee ON teams.teamId = teamEmployee.teamId "
			        + "JOIN players ON players.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE playerId = ?;");
			
			playerStatement.setInt(1, getId());
			ResultSet players = playerStatement.executeQuery();
			
			while(players.next()) {
				Team team = new Team(
						players.getInt("playerId"),
						players.getString("teamName")
		        		);
				playerTeam = team;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return playerTeam;
	}
	
	public Player getPlayer(Connection connection) {
		try {
	        PreparedStatement playerStatement = connection.prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        playerStatement.setInt(1, getId());
	        ResultSet playerResult = playerStatement.executeQuery(); 
	        
	        Player playerFound = new Player(
	        		playerResult.getInt("playerId"),
	        		playerResult.getString("fname"),
	        		playerResult.getString("lName")
	        		);
	        
			return playerFound;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public boolean checkPlayerAssigned(Connection connection) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT teamEmployeeId FROM players WHERE playerId = ? AND teamEmployeeId IS NOT NULL;");
			
			matchStatement.setInt(1, getId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
}
