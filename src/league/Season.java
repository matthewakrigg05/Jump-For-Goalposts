package league;

public class Season {

	private int seasonId;
	private String seasonStart;
	private String seasonEnd;
	private Team[] teams;
	private Match[] fixtures;
	private Match[] results;
	private boolean isCurrent;
	
	public Season(int id, String seasonStart, String seasonEnd, boolean isCurrent) {
		setId(id);
		setSeasonStart(seasonStart);
		setSeasonEnd(seasonEnd);
		setIsCurrent(isCurrent);
	}

	// Standard getters and setters
	public int getId() { return this.seasonId; }
	public void setId(int id) { this.seasonId = id; }

	public String getSeasonStart() { return seasonStart; }
	public void setSeasonStart(String seasonYear) { this.seasonStart = seasonYear; }
	
	public String getSeasonEnd() { return seasonEnd; }
	public void setSeasonEnd(String seasonEnd) { this.seasonEnd = seasonEnd; }
	
	public String getSeasonStartEnd() { return this.seasonStart + "/" + this.seasonEnd; }
	
	public Team[] getTeams() { return teams; }
	public void setTeams(Team[] teams) { this.teams = teams; }

	public Match[] getFixtures() { return fixtures; }
	public void setFixtures(Match[] fixtures) { this.fixtures = fixtures; }

	public Match[] getResults() { return results; }
	public void setResults(Match[] results) { this.results = results; }

	public boolean getIsCurrent() { return this.isCurrent; }
	public void setIsCurrent(boolean current) { this.isCurrent = current; }
	
}
