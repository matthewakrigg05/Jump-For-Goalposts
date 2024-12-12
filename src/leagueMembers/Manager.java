package leagueMembers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import accounts.ManagerAccount;
import league.Team;

public class Manager extends Person {
	
	private String preferredFormation;
	private int managerAccId;
	private ManagerAccount managerAcc;

	public Manager(int id, String fName, String lName, int userId) { super(id, fName, lName, userId); }

	public String getPreferredFormation() { return preferredFormation; }
	public void setPreferredFormation(String preferredFormation) { this.preferredFormation = preferredFormation; }

	public ManagerAccount getManagerAcc() { return managerAcc; }
	public void setManagerAcc(ManagerAccount managerAcc) { this.managerAcc = managerAcc; }
	
	public void setUserId(int id) { this.managerAccId = id; }
	public int getUserId() { return this.managerAccId; }
	
	public Team getManagerTeam(Connection connection) {
		Team managerTeam = null;
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM teams "
			        + "JOIN teamEmployee ON teams.teamId = teamEmployee.teamId "
			        + "JOIN managers ON managers.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE managerId = ?;");
			
			playerStatement.setInt(1, getId());
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Team team = new Team(
						managers.getInt("teamId"),
						managers.getString("teamName")
		        		);
				managerTeam = team;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return managerTeam;
	}
	
	public ManagerAccount getManagerAccount(Connection connection, int id) {
		try {
	        PreparedStatement manAccStatement = connection.prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'manager';" );
	
	        manAccStatement.setInt(1, id);
	        ResultSet refAccResult = manAccStatement.executeQuery(); 
	        
	        ManagerAccount manAcc = new ManagerAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password")
	        		);
	        		
	        return manAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public boolean checkManagerAssigned(Connection connection) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT teamId FROM teams WHERE teamId = ? AND stadiumId IS NOT NULL;");
			
			matchStatement.setInt(1, getId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
}

