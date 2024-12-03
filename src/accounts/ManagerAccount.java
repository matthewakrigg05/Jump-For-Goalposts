package accounts;

public class ManagerAccount extends Account implements IManagerRole {

	public ManagerAccount(int id, String emailAddress,  String password) {
		super(id, emailAddress, password, false);
	}
}
