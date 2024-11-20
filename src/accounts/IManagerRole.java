package accounts;
import leagueMembers.Attacker;
import leagueMembers.Defender;
import leagueMembers.Goalkeeper;
import leagueMembers.Midfielder;

public interface IManagerRole {

	public static void assignShirtNum(Object player, int shirtNum) {
		if (player instanceof Attacker) {
			((Attacker) player).setShirtNum(shirtNum);
		}
		
		else if (player instanceof Midfielder) {
			((Midfielder) player).setShirtNum(shirtNum);
		}
		
		else if (player instanceof Defender) {
			((Defender) player).setShirtNum(shirtNum);
		}
		
		else if (player instanceof Goalkeeper) {
			((Goalkeeper) player).setShirtNum(shirtNum);
		}
	}
}
