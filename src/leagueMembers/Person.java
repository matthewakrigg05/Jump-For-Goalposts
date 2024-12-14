package leagueMembers;

/*
 * A template for the other classes that are in this package, handling basic functionality and 
 * holding generic data that all people within the system are likely to have
 */

abstract class Person {

	private int id;
	private String fName;
	private String lName;
	private String contractType;
	private int userId;
	
	public Person(int id, String fName, String lName, int userId) {
		setId(id);
		setFName(fName);
		setLName(lName);
		setUserId(userId);
	}
	
	public Person(int id, String fName, String lName) {
		setId(id);
		setFName(fName);
		setLName(lName);
	}

	public String getFullName() { return String.format("%1$s %2$s", getFName(), getLName()); }
	
	// Gets and sets first name of person
	public String getFName() { return this.fName; }
	public void setFName(String fName) { this.fName = fName; }
	
	// Gets and sets last name of person
	public String getLName() { return this.lName; }
	public void setLName(String lName) { this.lName = lName; }
	
	// Gets and sets persons contract type
	public String getContractType() { return this.contractType; }
	public void setContractType(String type) { this.contractType = type; }

	// Gets and sets id of person
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	// Gets and sets userId of person
	public int getUserId() { return userId; }
	public void setUserId(int id) { this.userId = id; }
}
