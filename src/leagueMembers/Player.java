package leagueMembers;
import java.sql.Date;
import leagueDB.Statistics;

abstract class Player extends Person {
	
	private String preferredFoot;
	private Statistics stats;
	private boolean isInjured;
	private boolean isSuspended;
	private int shirtNum;
	private String positionType;
	

	public Player(int id, String fName, String lName, Date DoB, String contractType) {
		super(id, fName, lName, DoB, contractType);
	}


	// Standard getters and setters
	public String getPreferredFoot() {
		return preferredFoot;
	}

	public void setPreferredFoot(String preferredFoot) {
		this.preferredFoot = preferredFoot;
	}

	public Statistics getStats() {
		return stats;
	}

	public void setStats(Statistics stats) {
		this.stats = stats;
	}

	public boolean isInjured() {
		return isInjured;
	}

	public void setInjured(boolean isInjured) {
		this.isInjured = isInjured;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public int getShirtNum() {
		return shirtNum;
	}

	public void setShirtNum(int shirtNum) {
		this.shirtNum = shirtNum;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

}
