package league;

import leagueMembers.Manager;

public class Team {
	
	private int teamId;
	private String name;
	private Stadium stadium;
	private Manager manager;
	private Object[] players;
	private Object[] currentLineup;
	private Team rivalTeam;
	
	public Team(int id, String name, Stadium stadium, Manager manager, Object[] players) {
		this.setTeamId(id);
		this.setName(name);
		this.setStadium(stadium);
		this.setManager(manager);
		this.setPlayers(players);
	}
	
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Stadium getStadium() {
		return stadium;
	}
	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public Object[] getPlayers() {
		return players;
	}
	public void setPlayers(Object[] players) {
		this.players = players;
	}
	public Object[] getCurrentLineup() {
		return currentLineup;
	}
	public void setCurrentLineup(Object[] currentLineup) {
		this.currentLineup = currentLineup;
	}
	public Team getRivalTeam() {
		return rivalTeam;
	}
	public void setRivalTeam(Team rivalTeam) {
		this.rivalTeam = rivalTeam;
	}

}
