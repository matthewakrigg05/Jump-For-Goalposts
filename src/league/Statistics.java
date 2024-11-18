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
	
	public Statistics() {}
	
	public int getAssits() { return assits; }
	public void setAssits(int assits) { this.assits = assits; }
	
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
