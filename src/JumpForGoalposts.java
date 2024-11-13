import java.sql.Connection;
import java.sql.DriverManager;
 

public class JumpForGoalposts {

	public static void main(String[] args) {

		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:./sample.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	
		
		System.out.println("db");

	}

}
