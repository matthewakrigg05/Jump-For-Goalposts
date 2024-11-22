package gui;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import accounts.AdminAccount;
import accounts.ManagerAccount;
import accounts.RefereeAccount;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

public class JfgpWindow extends JFrame {

	private JPanel currentPanel;
	private boolean isLoggedIn;
	private String userType;
	
	//Create the application.
	public JfgpWindow() {	
		this.setLoggedIn(false);
		initialize();
	}
	
	public JfgpWindow(AdminAccount admin) {	
		this.setLoggedIn(false);
		this.setUserType("admin");
		initialize();
	}
	
	public JfgpWindow(ManagerAccount manager) {	
		this.setLoggedIn(false);
		this.setUserType("manager");
		initialize();
	}
	
	public JfgpWindow(RefereeAccount referee) {	
		this.setLoggedIn(false);
		this.setUserType("referee");
		initialize();
	}

	 // Initialize the contents of the window.	 
	private void initialize() {
		
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setBackground(new Color(192, 192, 192));
		setResizable(true);
		setMinimumSize(new Dimension(600, 450));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		currentPanel = new AdminPanel();
		
		getContentPane().add(new toolBar(this), BorderLayout.WEST);
		getContentPane().add(currentPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
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
