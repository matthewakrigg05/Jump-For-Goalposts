package gui;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import accounts.AdminAccount;
import accounts.ManagerAccount;
import accounts.RefereeAccount;
import leagueDB.JFGPdb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.sql.Connection;

@SuppressWarnings("serial")
public class JfgpWindow extends JFrame {

	private JPanel currentPanel  = new HomePanel();
	private boolean isLoggedIn;
	private String userType;
	
	public JfgpWindow() {	
		setLoggedIn(false);
		setUserType("User");
		initialise();
	}
	
	public JfgpWindow(AdminAccount admin) {	
		setLoggedIn(true);
		setUserType("Admin");
		initialise();
	}
	
	public JfgpWindow(ManagerAccount manager) {	
		setLoggedIn(true);
		setUserType("Manager");
		initialise();
	}
	
	public JfgpWindow(RefereeAccount referee) {	
		setLoggedIn(true);
		setUserType("Referee");
		initialise();
	}
	 
	private void initialise() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setBackground(new Color(192, 192, 192));
		setResizable(true);
		setMinimumSize(new Dimension(600, 450));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(new toolBar(this, userType), BorderLayout.WEST);
		getContentPane().add(currentPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	// standard getters and setters
	public JPanel getCurrentPanel() {
		return this.currentPanel;
	}
	
	public void setCurrentPanel(JPanel panel) {
		this.currentPanel = panel;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
