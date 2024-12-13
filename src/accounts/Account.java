package accounts;

// Abstract class to be used by subclasses - utilising inheritance of general attributes and methods
abstract class Account {
	private int id;
	private String emailAddress;
	private String password;
	private boolean isAdmin;
	
	public Account(int id, String emailAddress, String password, boolean isAdmin) {
		setId(id);
		setEmail(emailAddress);
		setPassword(password);
		setIsUserAdmin(isAdmin);
	}
	
	protected void changeUserPassword(String password) { this.setPassword(password); }
	
	// Standard getter and setter methods
	protected boolean isUserAdmin() { return this.isAdmin; }
	protected void setIsUserAdmin(boolean admin) { this.isAdmin = admin; }
	
	protected int getId() { return this.id; }
	protected void setId(int id) { this.id = id; }
	
	protected String getEmail() { return this.emailAddress; }
	protected void setEmail(String emailAddress) { this.emailAddress = emailAddress; }

	protected String getPassword() { return password; }
	protected void setPassword(String password) { this.password = password; }
}
