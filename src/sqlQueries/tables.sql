CREATE TABLE IF NOT EXISTS userAccounts(
	userId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	userType VARCHAR(25) NOT NULL,
	emailAddress VARCHAR(200) NOT NULL,
	password VARCHAR(50) NOT NULL,
	FOREIGN KEY (leagueId) REFERENCES league(leagueId) 
	);
	
CREATE TABLE IF NOT EXISTS league(
	leagueId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	leagueName VARCHAR(50) 
	);

CREATE TABLE IF NOT EXISTS seasons(
	seasonId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	seasonStart DATE,
	seasonEnd DATE
	FOREIGN KEY leagueId REFERENCES league(leagueId) 
	);
	
CREATE TABLE IF NOT EXISTS referees (
	refereeId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	fName VARCHAR(50),
	lName VARCHAR(50),
	preferredLocation VARCHAR(50)
	FOREIGN KEY leagueId REFERENCES league(leagueId) 
	);
	
CREATE TABLE IF NOT EXISTS teams (
	teamId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	teamName VARCHAR(100) );
	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId) NOT NULL
	);
	
CREATE TABLE IF NOT EXISTS stadiums(
	stadiumId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	stadiumName VARCHAR(100),
	capacity VARCHAR(6),
	stadiumLocation VARCHAR(250) 
	);
	
CREATE TABLE IF NOT EXISTS matches(
	matchId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	isComplete BOOLEAN NOT NULL,
	matchDate DATETIME
	FOREIGN KEY (seasonId) REFERENCES seasons(seasonId) 
	FOREIGN KEY (refereeId) REFERENCES referees(refereeId) 
	);
	
CREATE TABLE IF NOT EXISTS results(
	resultId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	resultScore VARCHAR(5) NOT NULL,
	resultOutcome VARCHAR(100) 
	FOREIGN KEY (matchId) REFERENCES matches(matchId) 
	);
	
CREATE TABLE IF NOT EXISTS matchEvents(
	eventId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	eventType VARCHAR(25),
	eventMinute TINYINT
	FOREIGN KEY (resultId) REFERENCES results(resultId) NOT NULL
	);
	
CREATE TABLE IF NOT EXISTS teamEmployee(
	teamEmployeeId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	contractType VARCHAR(25)
	FOREIGN KEY (teamId) REFERENCES teams(teamId)
	);

CREATE TABLE IF NOT EXISTS managers(
	managerId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	preferredFormation VARCHAR(10),
	fName VARCHAR(100),
	lName VARCHAR(100),
	dob DATE
	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId)
	);

CREATE TABLE IF NOT EXISTS players(
	playerId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	fName VARCHAR(100),
	lName VARCHAR(100),
	dob DATE,
	positionType VARCHAR(15),
	shirtNumber TINYINT,
	isSuspended BOOLEAN,
	isInjured BOOLEAN
	FOREIGN KEY (teamEmployeeId) REFERENCES teamEmployee(teamEmployeeId)
	FOREIGN KEY (statsId) REFERENCES statsForPlayerOrTeam(statsId)
	);

CREATE TABLE IF NOT EXISTS statsForPlayerOrTeam(
	statsId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	assits INT(4),
	goals INT(4),
	fouls INT(4),
	yellowCards INT(4),
	redCards INT(4),
	wins INT(4),
	draws INT(4),
	losses INT(4)
	);



