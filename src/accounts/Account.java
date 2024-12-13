package accounts;

abstract class Account {
	
	// template to be used by subclasses - utilising inheritance of general attributes and methods
	
	private int id;
	private String emailAddress;
	private String password;
	private boolean isAdmin;
	
	public Account(int id, String emailAddress, String password, boolean isAdmin) {
		this.id = id;
		setEmail(emailAddress);
		setPassword(password);
		this.isAdmin = isAdmin;
	}
	
	public void changeUserPassword(String password) { this.setPassword(password); }
	
	// Standard getter and setter methods
	public boolean isUserAdmin() { return this.isAdmin; }
	
	public int getId() { return this.id; }
	
	public String getEmail() { return this.emailAddress; }
	public void setEmail(String emailAddress) { this.emailAddress = emailAddress; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
}
