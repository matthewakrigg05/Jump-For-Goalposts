CREATE TABLE IF NOT EXISTS league(
	leagueId INTEGER NOT NULL,
	leagueName VARCHAR(50), 
	PRIMARY KEY (leagueId)
	);

CREATE TABLE IF NOT EXISTS userAccounts(
	userId INTEGER NOT NULL,
	userType VARCHAR(25) NOT NULL,
	emailAddress VARCHAR(200) NOT NULL,
	password VARCHAR(50) NOT NULL,
	leagueId INTEGER NOT NULL,
	PRIMARY KEY (userId),
	FOREIGN KEY (leagueId) REFERENCES league(leagueId) 
	);

CREATE TABLE IF NOT EXISTS seasons(
	seasonId INTEGER NOT NULL,
	seasonStart DATE,
	seasonEnd DATE,
	leagueId INTEGER NOT NULL,
	PRIMARY KEY (seasonId),
	FOREIGN KEY leagueId REFERENCES league(leagueId) 
	);
	
CREATE TABLE IF NOT EXISTS referees (
	refereeId INTEGER NOT NULL,
	fName VARCHAR(50),
	lName VARCHAR(50),
	preferredLocation VARCHAR(50),
	leagueId INTEGER NOT NULL,
	PRIMARY KEY (refereeId),
	FOREIGN KEY leagueId REFERENCES league(leagueId) 
	);
	
CREATE TABLE IF NOT EXISTS statsForPlayerOrTeam(
	statsId INTEGER NOT NULL,
	assits INT(4),
	goals INT(4),
	fouls INT(4),
	yellowCards INT(4),
	redCards INT(4),
	wins INT(4),
	draws INT(4),
	losses INT(4),
	PRIMARY KEY (statsId)
	);

CREATE TABLE IF NOT EXISTS teams (
	teamId INTEGER NOT NULL,
	teamName VARCHAR(100),
	statsId INTEGER NOT NULL,
	PRIMARY KEY (teamId),
	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId) NOT NULL
	);
	
CREATE TABLE IF NOT EXISTS stadiums(
	stadiumId INTEGER NOT NULL,
	stadiumName VARCHAR(100),
	capacity VARCHAR(6),
	stadiumLocation VARCHAR(250),
	PRIMARY KEY (stadiumId) 
	);
	
CREATE TABLE IF NOT EXISTS matches(
	matchId INTEGER NOT NULL,
	isComplete BOOLEAN NOT NULL,
	matchDate DATETIME,
	seasonId INTEGER NOT NULL,
	refereeId INTEGER,
	PRIMARY KEY (matchId),
	FOREIGN KEY (seasonId) REFERENCES seasons(seasonId), 
	FOREIGN KEY (refereeId) REFERENCES referees(refereeId) 
	);
	
CREATE TABLE IF NOT EXISTS results(
	resultId INTEGER NOT NULL,
	resultScore VARCHAR(5) NOT NULL,
	resultOutcome VARCHAR(100),
	matchId INTEGER NOT NULL, 
	PRIMARY KEY (resultId),
	FOREIGN KEY (matchId) REFERENCES matches(matchId) 
	);
	
CREATE TABLE IF NOT EXISTS matchEvents(
	eventId INTEGER NOT NULL,
	eventType VARCHAR(25),
	eventMinute TINYINT,
	resultId INTEGER NOT NULL,
	PRIMARY KEY (eventId),
	FOREIGN KEY (resultId) REFERENCES results(resultId) NOT NULL
	);
	
CREATE TABLE IF NOT EXISTS teamEmployee(
	teamEmployeeId INTEGER NOT NULL,
	contractType VARCHAR(25),
	teamId INTEGER NOT NULL,
	PRIMARY KEY (teamEmployeeId),
	FOREIGN KEY (teamId) REFERENCES teams(teamId)
	);

CREATE TABLE IF NOT EXISTS managers(
	managerId INTEGER NOT NULL,
	preferredFormation VARCHAR(10),
	fName VARCHAR(100),
	lName VARCHAR(100),
	dob DATE,
	teamEmployeeId INTEGER,
	PRIMARY KEY (managerId),
	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId)
	);

CREATE TABLE IF NOT EXISTS players(
	playerId INTEGER NOT NULL,
	fName VARCHAR(100),
	lName VARCHAR(100),
	dob DATE,
	positionType VARCHAR(15),
	shirtNumber TINYINT,
	isSuspended BOOLEAN,
	isInjured BOOLEAN,
	teamEmployeeId INTEGER,
	seasonId INTEGER NOT NULL,
	PRIMARY KEY (playerId),
	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId),
	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId)
	);


