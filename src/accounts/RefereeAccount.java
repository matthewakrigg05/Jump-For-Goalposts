package accounts;
import leagueMembers.Referee;

public class RefereeAccount extends Account implements IRefereeRole {
	
	Referee referee;

	public RefereeAccount(int id, String emailAddress, String password) {
		super(id, emailAddress, password, false);
	}
	
	// Standard getter and setter methods
	public Referee getReferee() {
		return this.referee;
	}
	
	public void setReferee(Referee referee) {
		this.referee = referee;
	}

}
