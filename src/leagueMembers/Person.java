package leagueMembers;
import java.sql.Date;

abstract class Person {

	private int id;
	private String fName;
	private String lName;
	private Date DoB;
	private String contractType;
	
	public Person(int id, String fName, String lName, Date DoB, String contractType) {
		this.setId(id);
		this.fName = fName;
		this.lName = lName;
		this.DoB = DoB;
		this.contractType = contractType;
	}
	
	public void updateStatistics() {
		
	}
	
	public String getFullName() {
		return String.format(this.getFName(), this.getLName());
	}
	
	//Standard getters and setters
	public String getFName() {
		return this.fName;
	}
	
	public void setFName(String fName) {
		this.fName = fName;
	}
	
	public String getLName() {
		return this.lName;
	}
	
	public void setLName(String lName) {
		this.lName = lName;
	}
	
	public Date getDoB() {
		return this.DoB;
	}
	
	public void setDoB(Date DoB) {
		this.DoB = DoB;
	}
	
	public String getContractType() {
		return this.contractType;
	}
	
	public void setContractType(String type) {
		this.contractType = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
