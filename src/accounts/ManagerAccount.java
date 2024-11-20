package accounts;
import leagueMembers.Manager;

public class ManagerAccount extends Account implements IManagerRole {
	
	Manager manager;

	public ManagerAccount(int id, String emailAddress,  String password, Manager manager) {
		super(id, emailAddress, password, false);
		this.manager = manager;
	}
	
	
	// Standard getter and setter methods
	public Manager getManager() {
		return this.manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
