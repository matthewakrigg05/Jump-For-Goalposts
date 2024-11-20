package league;

public class League {
	
	private int leagueId;
	private String leagueName;
	
	public League(int id, String name) {
		this.leagueId = id;
		this.setLeagueName(name);
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
