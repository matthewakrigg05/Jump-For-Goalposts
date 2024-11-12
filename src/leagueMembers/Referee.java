package leagueMembers;
import java.sql.Date;

import accounts.RefereeAccount;

public class Referee extends Person {
	
	private String preferredLocation;
	private RefereeAccount refereesAccount;
	private String[] gamesOfficiated;
	private String[] matchesToAttend;
	
	public Referee(int id, String fName, String lName, Date DoB, String contractType, RefereeAccount refAcc) {
		super(id, fName, lName, DoB, contractType);
		this.setRefereesAccount(refAcc);
	}

	
	// Standard getters and setters
	public String getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

	public RefereeAccount getRefereesAccount() {
		return refereesAccount;
	}

	public void setRefereesAccount(RefereeAccount refereesAccount) {
		this.refereesAccount = refereesAccount;
	}

	public String[] getGamesOfficiated() {
		return gamesOfficiated;
	}

	public void setGamesOfficiated(String[] gamesOfficiated) {
		this.gamesOfficiated = gamesOfficiated;
	}


	public String[] getMatchesToAttend() {
		return matchesToAttend;
	}

	public void setMatchesToAttend(String[] matchesToAttend) {
		this.matchesToAttend = matchesToAttend;
	}

}
