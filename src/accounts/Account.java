package accounts;

// Abstract class to be used by subclasses - utilising inheritance of general attributes and methods
abstract class Account {
	private int id;
	private String emailAddress;
	private String password;
	
	public Account(int id, String emailAddress, String password) {
		setId(id);
		setEmail(emailAddress);
		setPassword(password);
	}
	
	// Retrive Id, used most often for interaction with database
	protected int getId() { return this.id; }
	protected void setId(int id) { this.id = id; }
	
	protected String getEmail() { return this.emailAddress; }
	protected void setEmail(String emailAddress) { this.emailAddress = emailAddress; }

	protected String getPassword() { return password; }
	protected void setPassword(String password) { this.password = password; }
}
