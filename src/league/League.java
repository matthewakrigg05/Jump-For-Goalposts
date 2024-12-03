package league;

public class League {
	
	private int leagueId;
	private String leagueName;
	
	public League(int id, String name) {
		setLeagueId(id);
		setLeagueName(name);
	}
	
	public void setLeagueId(int Id) {
		this.leagueId = Id;
	}
	
	public int getLeagueId() {
		return leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	
}
