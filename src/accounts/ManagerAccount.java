package accounts;

import leagueMembers.Player;

public class ManagerAccount extends Account {

	public ManagerAccount(int id, String emailAddress,  String password) {
		super(id, emailAddress, password, false);
	}
	
	public static void assignShirtNumDialog(Player player, int shirtNum) {
		 player.setShirtNum(shirtNum); 
	}
}
