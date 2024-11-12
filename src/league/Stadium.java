package league;

public class Stadium {
	
	private String stadiumName;
	private int capacity;
	private String location;
	
	public Stadium(String name, int capacity, String location) {
		this.setStadiumName(name);
		this.setCapacity(capacity);
		this.setLocation(location);
	}

	
	// Standard getters and setters
	public String getStadiumName() {
		return stadiumName;
	}

	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
