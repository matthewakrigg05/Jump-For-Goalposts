package accounts;

public class AdminAccount extends Account implements IManagerRole, IRefereeRole {
	
	public AdminAccount(int id, String emailAddress, String password) {
		super(id, emailAddress, password, true);
	}

}
