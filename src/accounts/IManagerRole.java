package accounts;
import leagueMembers.Player;

public interface IManagerRole {

	public static void assignShirtNumDialog(Player player, int shirtNum) {
		 player.setShirtNum(shirtNum); 
	}
}
