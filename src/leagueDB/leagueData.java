package leagueDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import accounts.ManagerAccount;
import accounts.RefereeAccount;
import league.*;
import leagueMembers.*;

public interface leagueData {
	
	public static RefereeAccount getRefereeAccount(Connection connection, Referee ref) {
		try {
	        PreparedStatement refAccStatement = connection.prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'referee';" );
	
	        refAccStatement.setInt(1, ref.getId());
	        ResultSet refAccResult = refAccStatement.executeQuery(); 
	        
	        RefereeAccount refAcc = new RefereeAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password"), 
	        		ref
	        		);
	        
	        return refAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Referee getReferee(Connection connection, RefereeAccount refereeAccount) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	
	        refStatement.setInt(1, refereeAccount.getId());
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		refereeAccount
	        		);
	        
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Referee getRefereeFromId(Connection connection, int id) {
		try {
	        PreparedStatement refStatement = connection.prepareStatement(
	                "SELECT * FROM referees WHERE userId = ?;" );
	
	        refStatement.setInt(1, id);
	        ResultSet refResult = refStatement.executeQuery(); 
	        
	        Referee ref = new Referee(
	        		refResult.getInt("refereeId"),
	        		refResult.getString("fname"),
	        		refResult.getString("lName"), 
	        		refResult.getString("preferredLocation"),
	        		id
	        		);
	       
			return ref;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Referee> getAllReferees(Connection connection) {
		List<Referee> referees = new ArrayList<Referee>();
		
		try {
			PreparedStatement refsStatement = (connection).prepareStatement(
			        "SELECT * FROM referees");
			ResultSet refResult = refsStatement.executeQuery();
			
			while(refResult.next()) {
				Referee ref = new Referee(
		        		refResult.getInt("refereeId"),
		        		refResult.getString("fname"),
		        		refResult.getString("lName"), 
		        		refResult.getString("preferredLocation"),
		        		refResult.getInt("userId")
		        		);
				
				referees.add(ref);
			}
			
			for(Referee ref : referees) { ref.setRefAcc(getRefereeAccount(connection, ref)); }
					
			return referees;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static ManagerAccount getManagerAccount(Connection connection, int id) {
		try {
	        PreparedStatement manAccStatement = connection.prepareStatement(
	                "SELECT * FROM userAccounts WHERE userId = ? AND userType = 'manager';" );
	
	        manAccStatement.setInt(1, id);
	        ResultSet refAccResult = manAccStatement.executeQuery(); 
	        
	        ManagerAccount manAcc = new ManagerAccount(
	        		refAccResult.getInt("userId"),
	        		refAccResult.getString("emailAddress"),
	        		refAccResult.getString("password")
	        		);
	        		
	        return manAcc;
	        
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Manager getManager(Connection connection, ManagerAccount manAcc) {
		try {
	        PreparedStatement manStatement = connection.prepareStatement(
	                "SELECT * FROM manager WHERE userId = ?;" );
	
	        manStatement.setInt(1, manAcc.getId());
	        ResultSet manResult = manStatement.executeQuery(); 
	        
	        Manager man = new Manager(
	        		manResult.getInt("managerId"),
	        		manResult.getString("fname"),
	        		manResult.getString("lName"),
	        		manResult.getInt("userId")
	        		);
	       
			return man;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Manager getManagerFromId(Connection connection, int id) {
		try {
	        PreparedStatement manStatement = connection.prepareStatement(
	                "SELECT * FROM managers WHERE userId = ?;" );
	
	        manStatement.setInt(1, id);
	        ResultSet manResult = manStatement.executeQuery(); 
	        
	        Manager man = new Manager(
	        		manResult.getInt("managerId"),
	        		manResult.getString("fname"),
	        		manResult.getString("lName"),
	        		id
	        		);
	       
			return man;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Manager> getAllManagers(Connection connection) {
		List<Manager> managers = new ArrayList<Manager>();
		
		try {
			PreparedStatement manStatement = (connection).prepareStatement(
			        "SELECT * FROM managers;");
			ResultSet manResult = manStatement.executeQuery();
			
			while(manResult.next()) {
				Manager man = new Manager(
						manResult.getInt("managerId"),
						manResult.getString("fname"),
						manResult.getString("lName"), 
						manResult.getInt("userId")
		        		);
				
				managers.add(man);
			}
			
			for(Manager man : managers) { man.setManagerAcc(getManagerAccount(connection, man.getUserId())); }
					
			return managers;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Player getPlayer(Connection connection, Player player) {
		try {
	        PreparedStatement playerStatement = connection.prepareStatement(
	                "SELECT * FROM player WHERE playerId = ?;" );

	        playerStatement.setInt(1, player.getId());
	        ResultSet playerResult = playerStatement.executeQuery(); 
	        
	        Player playerFound = new Player(
	        		playerResult.getInt("playerId"),
	        		playerResult.getString("fname"),
	        		playerResult.getString("lName")
	        		);
	        
			return playerFound;
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Player> getAllPlayers(Connection connection) {
		List<Player> players = new ArrayList<Player>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM players;");
			ResultSet playerResult = playerStatement.executeQuery();
			
			while(playerResult.next()) {
				Player player = new Player(
						playerResult.getInt("playerId"),
						playerResult.getString("fname"),
						playerResult.getString("lName")
		        		);
				players.add(player);
			}
			return players;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static League getLeague(Connection connection) {
		try {
			PreparedStatement leagueStatement = (connection).prepareStatement(
			        "SELECT * FROM league WHERE leagueId = 1");
			ResultSet leagueResult = leagueStatement.executeQuery();
			
			League jfgpLeague = new League(
					leagueResult.getInt("leagueId"),
					leagueResult.getString("leagueName"));
					
			return jfgpLeague;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Season> getSeasons(Connection connection) {
		List<Season> seasons = new ArrayList<Season>();
		
		try {
			PreparedStatement seasonStatement = (connection).prepareStatement(
			        "SELECT * FROM seasons");
			ResultSet seasonResult = seasonStatement.executeQuery();
			
			while(seasonResult.next()) {
				Season season = new Season(
						seasonResult.getInt("seasonId"),
						seasonResult.getString("seasonStart"),
						seasonResult.getString("seasonEnd"),
						seasonResult.getBoolean("isCurrent")
						);
				seasons.add(season);
			}

			return seasons;
			
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static int getCurrentGameWeek(Connection connection, int SeasonId) {
		try {
			PreparedStatement currentMatchWeekStatement = (connection).prepareStatement(
					"SELECT MIN(matchWeek) AS currentWeek FROM matches WHERE isComplete = 0 AND seasonId = ?;" );
			currentMatchWeekStatement.setInt(1, SeasonId);
			ResultSet matchweekResult = currentMatchWeekStatement.executeQuery();
			
			int matchWeek = matchweekResult.getInt("currentWeek");
			
			return matchWeek;
		} catch (SQLException e) { e.printStackTrace(); return 0; }	
	}
	
	public static List<Team> getAllTeams(Connection connection) {
		List<Team> teams = new ArrayList<Team>();
		
		try {
			PreparedStatement teamsStatement = (connection).prepareStatement( "SELECT * FROM teams");
			ResultSet teamResult = teamsStatement.executeQuery();
			
			while(teamResult.next()) {
				if (teamResult.getInt("teamId") == 1) { continue; } 
				else {
				Team team = new Team(
						teamResult.getInt("teamId"),
						teamResult.getString("teamName")
		        		);
				teams.add(team);
				}
			}				
			return teams;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Team getTeam(Connection connection, int id) { 
		Team team = null;
		
		try {
			PreparedStatement teamStatement = (connection).prepareStatement(
			        "SELECT * FROM teams WHERE teamId = ?;");
			
			teamStatement.setInt(1, id);
			ResultSet teamResult = teamStatement.executeQuery();
			team = new Team(teamResult.getInt("teamId"), 
					teamResult.getString("teamName")
					); 
					
			return team;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static List<Match> getNextFiveGameWeeks(Connection connection, Season currentSeason, int currentGameWeek) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND matchWeek < ? + 5 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			gameWeeksStatement.setInt(2, currentGameWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						getTeam(connection, gameWeeks.getInt("homeTeamId")),
						getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		return matches;
	}
	
	public static List<Match> getSeasonMatches(Connection connection, Season currentSeason) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						getTeam(connection, gameWeeks.getInt("homeTeamId")),
						getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
	
	public static List<Match> getSeasonFixtures(Connection connection, Season currentSeason) {
		List<Match> fixtures = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND isComplete = 0 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						getTeam(connection, gameWeeks.getInt("homeTeamId")),
						getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				fixtures.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return fixtures;
	}
	
	public static List<Match> getSeasonResults(Connection connection, Season currentSeason) {
		List<Match> results = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE seasonId = ? AND isComplete = 1 ORDER BY matchWeek ASC;");
			
			gameWeeksStatement.setInt(1, currentSeason.getId());
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						getTeam(connection, gameWeeks.getInt("homeTeamId")),
						getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				results.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return results;
	}
	
	public static boolean checkRefAssigned(Connection connection, Match match) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT refereeId FROM matches WHERE matchId = ? AND refereeId IS NOT NULL;");
			
			matchStatement.setInt(1, match.getMatchId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static List<Match> getNextFiveRefMatches(Connection connection, Referee referee) {
		List<Match> matchesToAttend = new ArrayList<Match>();
		
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE refereeId = ? AND isComplete = false ORDER BY matchWeek ASC LIMIT 5;");
			
			matchStatement.setInt(1, referee.getId());
			ResultSet res = matchStatement.executeQuery();
			
			while(res.next()) {
				Match match= new Match (
						res.getInt("matchId"),
						getTeam(connection, res.getInt("homeTeamId")),
						getTeam(connection, res.getInt("awayTeamId") ),
						res.getInt("matchWeek")
						);
				matchesToAttend.add(match);
			}
					
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matchesToAttend;
	}
	
	public static List<Match> getMatchWeekMatches( Connection connection, int matchWeek) {
		List<Match> matches = new ArrayList<Match>();
		
		try {
			PreparedStatement gameWeeksStatement = (connection).prepareStatement(
			        "SELECT * FROM matches WHERE matchweek = ? AND isComplete = 0 AND (homeTeamId <> 1 AND awayTeamId <> 1);");
			
			gameWeeksStatement.setInt(1, matchWeek);
			ResultSet gameWeeks = gameWeeksStatement.executeQuery();
			
			while (gameWeeks.next()) {
				Match match= new Match (
						gameWeeks.getInt("matchId"),
						getTeam(connection, gameWeeks.getInt("homeTeamId")),
						getTeam(connection, gameWeeks.getInt("awayTeamId") ),
						gameWeeks.getInt("matchWeek")
						);
				matches.add(match);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return matches;
	}
	
	public static Season getCurrentSeason(Connection connection) {
		try {
			PreparedStatement currentSeasonStatement = (connection).prepareStatement(
					"SELECT * FROM seasons WHERE isCurrent = true LIMIT 1;" );
			ResultSet seasonResult = currentSeasonStatement.executeQuery();
			
			Season currentSeason = new Season( 
					seasonResult.getInt("seasonId"),
					seasonResult.getString("seasonStart"),
					seasonResult.getString("seasonEnd"),
					seasonResult.getBoolean("isCurrent")
					);
			
			return currentSeason;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}
	
	public static List<Stadium> getAllStadiums(Connection connection) {
		List<Stadium> stadiums = new ArrayList<Stadium>();
		
		try {
			PreparedStatement stadiumStatement = (connection).prepareStatement( "SELECT * FROM stadiums");
			ResultSet stadiumResult = stadiumStatement.executeQuery();
			
			while(stadiumResult.next()) {
				Stadium stadium = new Stadium(
						stadiumResult.getInt("stadiumId"),
						stadiumResult.getString("stadiumName"),
						stadiumResult.getString("capacity"),
						stadiumResult.getString("stadiumLocation")
		        		);
				stadiums.add(stadium);
			}				
			return stadiums;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static boolean checkStadiumAssigned(Connection connection, Team team) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT teamId FROM teams WHERE teamId = ? AND stadiumId IS NOT NULL;");
			
			matchStatement.setInt(1, team.getTeamId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static boolean checkPlayerAssigned(Connection connection, Player player) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT teamEmployeeId FROM players WHERE playerId = ? AND teamEmployeeId IS NOT NULL;");
			
			matchStatement.setInt(1, player.getId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static boolean checkManagerAssigned(Connection connection, Manager manager) {
		try {
			PreparedStatement matchStatement = (connection).prepareStatement(
			        "SELECT teamId FROM teams WHERE teamId = ? AND stadiumId IS NOT NULL;");
			
			matchStatement.setInt(1, manager.getId());
			ResultSet res = matchStatement.executeQuery();
			return res.next();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static List<Player> getTeamPlayers(Connection connection, Team team) {
		List<Player> teamPlayers = new ArrayList<Player>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM players "
			        + "JOIN teamEmployee ON players.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId = ?;");
			
			playerStatement.setInt(1, team.getTeamId());
			ResultSet players = playerStatement.executeQuery();
			
			while(players.next()) {
				Player player = new Player(
						players.getInt("playerId"),
						players.getString("fname"),
						players.getString("lName")
		        		);
				teamPlayers.add(player);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamPlayers;
	}
	
	public static Manager getTeamManager(Connection connection, Team team) {
		Manager teamManager = null;
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM managers "
			        + "JOIN teamEmployee ON managers.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId = ?;");
			
			playerStatement.setInt(1, team.getTeamId());
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Manager manager = new Manager(
						managers.getInt("playerId"),
						managers.getString("fname"),
						managers.getString("lName"),
						managers.getInt("userId")
		        		);
				teamManager = manager;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamManager;
	}
	
	public static Team getPlayerTeam(Connection connection, Player player) {
		Team playerTeam = null;
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM teams "
			        + "JOIN teamEmployee ON teams.teamId = teamEmployee.teamId "
			        + "JOIN players ON players.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE playerId = ?;");
			
			playerStatement.setInt(1, player.getId());
			ResultSet players = playerStatement.executeQuery();
			
			while(players.next()) {
				Team team = new Team(
						players.getInt("playerId"),
						players.getString("teamName")
		        		);
				playerTeam = team;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return playerTeam;
	}
	
	public static Team getManagerTeam(Connection connection, Manager manager) {
		Team managerTeam = null;
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM teams "
			        + "JOIN teamEmployee ON teams.teamId = teamEmployee.teamId "
			        + "JOIN managers ON managers.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE managerId = ?;");
			
			playerStatement.setInt(1, manager.getId());
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Team team = new Team(
						managers.getInt("playerId"),
						managers.getString("teamName")
		        		);
				managerTeam = team;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return managerTeam;
	}
	
	
	public static String[][] getLeagueTableData(Connection connection, List<Team> teams) {
		String[][] leagueTableData = new String[teams.size()][6];
		
		for(int i = 0; i < teams.size(); i++) {
			leagueTableData[i][0] = teams.get(i).getName(); 
			leagueTableData[i][1] = String.valueOf(ComputeTeamStatistics.getGamesPlayed(connection, teams.get(i)));
			leagueTableData[i][2] = String.valueOf(ComputeTeamStatistics.getTeamWins(connection, teams.get(i)));
			leagueTableData[i][3] = String.valueOf(ComputeTeamStatistics.getTeamDraws(connection, teams.get(i)));
			leagueTableData[i][4] = String.valueOf(ComputeTeamStatistics.getTeamLosses(connection, teams.get(i)));
			leagueTableData[i][5] = String.valueOf(ComputeTeamStatistics.getTeamPoints(connection, teams.get(i)));
		}
		return leagueTableData;
	}
	
	public static List<Team> getSeasonTeams(Connection connection, int currentSeason) {
		List<Team> teams = new ArrayList<Team>();
		try {
			PreparedStatement teamsStatement = (connection).prepareStatement( "SELECT * FROM teams WHERE teamId IN (SELECT teamId FROM teamSeason WHERE seasonId = ?);");
			teamsStatement.setInt(1, currentSeason);
			ResultSet teamResult = teamsStatement.executeQuery();
			
			while(teamResult.next()) {
				if (teamResult.getInt("teamId") == 1) { continue; } 
				else {
				Team team = new Team(
						teamResult.getInt("teamId"),
						teamResult.getString("teamName")
		        		);
				teams.add(team);
					}
				}	
			} catch (SQLException e) { e.printStackTrace(); }
		
		return teams;
	}
	
	public static List<Manager> allTeamManagers(Connection connection) {
		List<Manager> teamManagers = new ArrayList<Manager>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM managers "
			        + "JOIN teamEmployee ON managers.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId IS NOT NULL;");
			
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Manager manager = new Manager(
						managers.getInt("playerId"),
						managers.getString("fname"),
						managers.getString("lName"),
						managers.getInt("userId")
		        		);
				teamManagers.add(manager);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamManagers;
	}
	
	public static List<Player> allTeamPlayers(Connection connection) {
		List<Player> teamManagers = new ArrayList<Player>();
		
		try {
			PreparedStatement playerStatement = connection.prepareStatement(
			        "SELECT * FROM players "
			        + "JOIN teamEmployee ON players.teamEmployeeId = teamEmployee.employeeId "
			        + "WHERE teamId IS NOT NULL;");
			
			ResultSet managers = playerStatement.executeQuery();
			
			while(managers.next()) {
				Player manager = new Player(
						managers.getInt("playerId"),
						managers.getString("fname"),
						managers.getString("lName")
		        		);
				teamManagers.add(manager);
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return teamManagers;
	}
}