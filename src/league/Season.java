package league;

public class Season {

	private int seasonId;
	private String seasonStart;
	private String seasonEnd;
	private Team[] teams;
	private Match[] fixtures;
	private Result[] results;
	
	public Season(int id, String seasonStart, String seasonEnd) {
		setId(id);
		setSeasonStart(seasonStart);
		setSeasonEnd(seasonEnd);
	}

	// Standard getters and setters
	public int getId() { return this.seasonId; }
	
	public void setId(int id) { this.seasonId = id; }

	public String getSeasonStart() {
		return seasonStart;
	}

	public void setSeasonStart(String seasonYear) {
		this.seasonStart = seasonYear;
	}
	
	public String getSeasonStartEnd() {
		return this.seasonStart + "/" + this.seasonEnd;
	}

	public Team[] getTeams() {
		return teams;
	}

	public void setTeams(Team[] teams) {
		this.teams = teams;
	}

	public Match[] getFixtures() {
		return fixtures;
	}

	public void setFixtures(Match[] fixtures) {
		this.fixtures = fixtures;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

	public String getSeasonEnd() {
		return seasonEnd;
	}

	public void setSeasonEnd(String seasonEnd) {
		this.seasonEnd = seasonEnd;
	}

}
