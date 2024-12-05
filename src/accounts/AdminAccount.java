package accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import leagueDB.JFGPdb;
import leagueMembers.Manager;

public class AdminAccount extends Account implements IManagerRole, IRefereeRole {
	
	public AdminAccount(int id, String emailAddress, String password) {
		super(id, emailAddress, password, true);
	}

	public void removeManager(Connection connection, Manager man) {
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "DELETE FROM referees WHERE refereeId = ?;");
			
			seasonStatement.setInt(1, man.getId());
			seasonStatement.executeUpdate();
			
			PreparedStatement refAccDel = (connection).prepareStatement(
			        "DELETE FROM userAccounts WHERE userId = ?;");
			
			refAccDel.setInt(1, man.getUserId());
			refAccDel.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void createManager(Connection connection, String fname, String lname, int id) {
		try {
			PreparedStatement manStatement = (connection).prepareStatement(
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
}
