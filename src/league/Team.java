package league;

import leagueMembers.Manager;

public class Team {
	
	private int teamId;
	private String name;
	private Stadium stadium;
	private Manager currentManager;
	private Object[] players;
	private Object[] currentLineup;
	private Team rivalTeam;
	
	public Team(int id, String name, Stadium stadium, Manager manager, Object[] players) {
		setTeamId(id);
		setName(name);
		setStadium(stadium);
		setManager(manager);
		setPlayers(players);
	}
	
	public Team(int id, String name) {
		setTeamId(id);
		setName(name);
	}
	
	public int getTeamId() { return teamId; }
	public void setTeamId(int teamId) { this.teamId = teamId; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public Stadium getStadium() { return stadium; }
	public void setStadium(Stadium stadium) { this.stadium = stadium; }
	
	public Manager getManager() { return currentManager; }
	public void setManager(Manager manager) { this.currentManager = manager; }
	
	public Object[] getPlayers() { return players; }
	public void setPlayers(Object[] players) { this.players = players; }
	
	public Object[] getCurrentLineup() { return currentLineup; }
	public void setCurrentLineup(Object[] currentLineup) { this.currentLineup = currentLineup; }
	
	public Team getRivalTeam() { return rivalTeam; }
	public void setRivalTeam(Team rivalTeam) { this.rivalTeam = rivalTeam; }
}
