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
		this.setId(id);
		this.fName = fName;
		this.lName = lName;
		this.userId = userId;
	}
	
	public Person(int id, String fName, String lName) {
		this.setId(id);
		this.fName = fName;
		this.lName = lName;
	}
	
	public void updateStatistics() {}
	
	public String getFullName() { return String.format("%1$s %2$s", getFName(), getLName()); }
	
	//Standard getters and setters
	public String getFName() { return this.fName; }
	public void setFName(String fName) { this.fName = fName; }
	
	public String getLName() { return this.lName; }
	public void setLName(String lName) { this.lName = lName; }
	
	public String getContractType() { return this.contractType; }
	public void setContractType(String type) { this.contractType = type; }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getUserId() { return userId; }
	public void setUserId(int id) { this.userId = id; }
}
