package leagueMembers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import accounts.RefereeAccount;
import league.Match;
import leagueDB.JFGPdb;

public class Referee extends Person {
	
	private String preferredLocation;
	private RefereeAccount refereesAccount;
	private int refAccId;
	private String[] gamesOfficiated;
	private String[] matchesToAttend;

	public Referee(int id, String fName, String lName, String location, RefereeAccount refAcc) {
		super(id, fName, lName);
		this.refereesAccount = refAcc;
		setPreferredLocation(location);
	}
	
	public Referee(int id, String fName, String lName, String location, int refAccId) {
		super(id, fName, lName);
		this.refAccId = refAccId;
		setPreferredLocation(location);
	}

	// Standard getters and setters
	public String getPreferredLocation() { return preferredLocation; }
	public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }

	public RefereeAccount getRefereesAccount() { return refereesAccount; }
	public void setRefereesAccount(RefereeAccount refereesAccount) { this.refereesAccount = refereesAccount; }

	public String[] getGamesOfficiated() { return gamesOfficiated; }
	public void setGamesOfficiated(String[] gamesOfficiated) { this.gamesOfficiated = gamesOfficiated; }

	public String[] getMatchesToAttend() { return matchesToAttend; }
	public void setMatchesToAttend(String[] matchesToAttend) { this.matchesToAttend = matchesToAttend; }
	
	public int getUserId() { return this.refAccId; }
	
	public void setRefAcc(RefereeAccount refAcc) { this.refereesAccount = refAcc; }
	
	public RefereeAccount getRefereeAccount(Connection connection, int id) {
		try {
	        PreparedStatement refAccStatement = connection.prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'referee';" );
	
	        refAccStatement.setInt(1, getId());
	        ResultSet refAccResult = refAccStatement.executeQuery(); 
	        
	        RefereeAccount refAcc = new RefereeAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password"), 
	        		this
	        		);
	        
	        return refAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public List<Match> getNextFiveRefMatches(JFGPdb db) {
		List<Match> matchesToAttend = new ArrayList<Match>();
		
		try {
			PreparedStatement matchStatement = (db.getConnection()).prepareStatement(
			        "SELECT * FROM matches WHERE refereeId = ? AND isComplete = false ORDER BY matchWeek ASC LIMIT 5;");
			
			matchStatement.setInt(1, getId());
			ResultSet res = matchStatement.executeQuery();
			
			while(res.next()) {
				Match match= new Match (
						res.getInt("matchId"),
						db.getTeam(res.getInt("homeTeamId")),
						db.getTeam(res.getInt("awayTeamId")),
						res.getInt("matchWeek")
						);
				matchesToAttend.add(match);
			}
					
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matchesToAttend;
	}
}
