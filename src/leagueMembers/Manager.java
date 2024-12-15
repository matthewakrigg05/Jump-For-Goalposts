package leagueMembers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import accounts.ManagerAccount;
import league.Team;

public class Manager extends Person {
	
	private ManagerAccount managerAcc;

	public Manager(int id, String fName, String lName, int userId) { super(id, fName, lName, userId); }

	// Gets and sets for managers associated account.
	public void setManagerAcc(ManagerAccount managerAcc) { this.managerAcc = managerAcc; }
	public ManagerAccount getManagerAcc() { return this.managerAcc; }
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
	
	// Gets the team that the manager is assigned to.
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
	
	/**
	 * Searches through employees of teams to see if manager appears, this is so that when
	 * assigning managers, appropriate dialog can be displayed asking the user if they are
	 * sure that they want to carry out the assignment.
	 * 
	 * @return whether the manager is assigned to a team
	 */
	public boolean checkManagerAssigned(Connection connection) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
					"SELECT teamEmployeeId FROM managers WHERE managerId = ? AND teamEmployeeId IS NOT NULL;");
			
			matchStatement.setInt(1, getId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
}

