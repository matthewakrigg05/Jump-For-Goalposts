package leagueMembers;
import accounts.ManagerAccount;

public class Manager extends Person {
	
	private String preferredFormation;
	private ManagerAccount managerAcc;
	
	public Manager(int id, String fName, String lName, String location, ManagerAccount manAcc) {
		super(id, fName, lName);
		this.managerAcc = manAcc;
	}

	public Manager(int id, String fName, String lName, int userId) { super(id, fName, lName, userId); }

	public String getPreferredFormation() { return preferredFormation; }
	public void setPreferredFormation(String preferredFormation) { this.preferredFormation = preferredFormation; }

	public ManagerAccount getManagerAcc() { return managerAcc; }
	public void setManagerAcc(ManagerAccount managerAcc) { this.managerAcc = managerAcc; }
	
	public void setManAcc(ManagerAccount manAcc) { this.managerAcc = manAcc; }
}

