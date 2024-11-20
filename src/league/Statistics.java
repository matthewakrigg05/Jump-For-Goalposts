package league;

public class Statistics {
	private int assits;
	private int goalsScored;
	private int shotsTaken;
	private int foulsCommitted;
	private int yellowCards;
	private int redCards;
	private int wins;
	private int draws;
	private int losses;
	
	public Statistics(int assists, int goalsScored, int shotsTaken, int foulsCommitted,
			int yellowCards, int redCards, int wins, int draws, int losses) {
		this.setAssists(assists);
		this.setGoalsScored(goalsScored);
		this.setShotsTaken(shotsTaken);
		this.setFoulsCommitted(foulsCommitted);
		this.setYellowCards(yellowCards);
		this.setRedCards(redCards);
		this.setWins(wins);
		this.setDraws(draws);
		this.setLosses(losses);
	}
	
	public Statistics() {}
	
	public int getAssits() { return assits; }
	public void setAssists(int assits) { this.assits = assits; }
	
	public int getGoalsScored() { return goalsScored; }
	public void setGoalsScored(int goalsScored) { this.goalsScored = goalsScored; }
	
	public int getShotsTaken() { return shotsTaken; }
	public void setShotsTaken(int shotsTaken) { this.shotsTaken = shotsTaken; }
	
	public int getFoulsCommitted() { return foulsCommitted; }
	public void setFoulsCommitted(int foulsCommitted) { this.foulsCommitted = foulsCommitted; }
	
	public int getYellowCards() { return yellowCards; }
	public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }
	
	public int getRedCards() { return redCards; }
	public void setRedCards(int redCards) { this.redCards = redCards; }
	
	public int getWins() { return wins; }
	public void setWins(int wins) { this.wins = wins; }
	
	public int getDraws() { return draws; }
	public void setDraws(int draws) { this.draws = draws; }
	
	public int getLosses() { return losses; }
	public void setLosses(int losses) { this.losses = losses; }

}
