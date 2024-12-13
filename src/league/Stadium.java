package league;

public class Stadium {
	
	private int stadiumId;
	private String stadiumName;
	private String capacity;
	private String location;
	
	public Stadium(int stadiumId, String name, String capacity, String location) {
		setStadiumId(stadiumId);
		setStadiumName(name);
		setCapacity(capacity);
		setLocation(location);
	}
	
	// Standard getters and setters
	public int getStadiumId() {return this.stadiumId;}
	public void setStadiumId(int id) { this.stadiumId = id; }
	
	public String getStadiumName() { return stadiumName; }
	public void setStadiumName(String stadiumName) { this.stadiumName = stadiumName; }

	public String getCapacity() { return capacity; }
	public void setCapacity(String capacity) { this.capacity = capacity; }

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
}