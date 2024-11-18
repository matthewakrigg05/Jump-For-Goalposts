package leagueMembers;
import java.sql.Date;
import accounts.ManagerAccount;

public class Manager extends Person {
	
	private String preferredFormation;
	private ManagerAccount managerAcc;

	public Manager(int id, String fName, String lName, Date DoB, String contractType, ManagerAccount managerAcc) {
		super(id, fName, lName, DoB, contractType);
		this.setManagerAcc(managerAcc);

	}

	public String getPreferredFormation() { return preferredFormation; }
	public void setPreferredFormation(String preferredFormation) { this.preferredFormation = preferredFormation; }

	public ManagerAccount getManagerAcc() { return managerAcc; }
	public void setManagerAcc(ManagerAccount managerAcc) { this.managerAcc = managerAcc; }

}
