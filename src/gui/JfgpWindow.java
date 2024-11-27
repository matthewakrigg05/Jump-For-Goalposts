package gui;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import accounts.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

@SuppressWarnings("serial")
public class JfgpWindow extends JFrame {

	private boolean isLoggedIn;
	private String userType;
	
	public JfgpWindow() {
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(false);
		setUserType("User");
		initialise();
	}
	
	public JfgpWindow(AdminAccount admin) {	
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setUserType("Admin");
		initialise();
	}
	
	public JfgpWindow(ManagerAccount manager) {	
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setUserType("Manager");
		initialise();
	}
	
	public JfgpWindow(RefereeAccount referee) {	
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setUserType("Referee");
		initialise();
	}
	 
	private void initialise() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setBackground(new Color(192, 192, 192));
		setMinimumSize(new Dimension(600, 450));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(new toolBar(this), BorderLayout.WEST);
		getContentPane().add(new HomePanel(), BorderLayout.CENTER);
	}
	
	public boolean isLoggedIn() { return isLoggedIn; }
	public void setLoggedIn(boolean isLoggedIn) { this.isLoggedIn = isLoggedIn; }

	public String getUserType() { return userType; }
	public void setUserType(String userType) { this.userType = userType; }
}
