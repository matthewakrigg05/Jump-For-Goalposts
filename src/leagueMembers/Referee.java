package leagueMembers;
import accounts.RefereeAccount;

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

	public int getUserId() { return refereesAccount.getId(); }
	
	public int getRefUserId() { return this.refAccId; }
	
	public void setRefAcc(RefereeAccount refAcc) { this.refereesAccount = refAcc; }
}
