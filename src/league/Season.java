package league;

public class Season extends League {

	private int seasonId;
	private String seasonYear;
	private Team[] teams;
	private Match[] fixtures;
	private Result[] results;
	
	public Season(int id, String name, String seasonYear) {
		super(id, name);
		setSeasonYear(seasonYear);
	}

	// Standard getters and setters
	public int getId() {
		return this.seasonId;
	}

	public String getSeasonYear() {
		return seasonYear;
	}

	public void setSeasonYear(String seasonYear) {
		this.seasonYear = seasonYear;
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

}
