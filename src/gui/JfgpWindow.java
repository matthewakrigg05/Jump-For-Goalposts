package gui;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import accounts.*;
import leagueDB.JFGPdb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.sql.Connection;

@SuppressWarnings("serial")
public class JfgpWindow extends JFrame {

	private boolean isLoggedIn;
	private String userType;
	private ManagerAccount managerAccount;
	private RefereeAccount refereeAccount;
	private AdminAccount admin;
	private Connection connection;
	
	public JfgpWindow() {
		JFGPdb db = new JFGPdb();
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(false);
		setUserType("User");
		setDbConnection(db.getConnection());
		initialise();
	}
	
	public JfgpWindow(AdminAccount admin) {
		JFGPdb db = new JFGPdb();
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setUserType("Admin");
		setDbConnection(db.getConnection());
		setAdminUser(admin);
		initialise();
	}
	
	public JfgpWindow(ManagerAccount manager) {	
		JFGPdb db = new JFGPdb();
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setUserType("Manager");
		setManagerUser(manager);
		setDbConnection(db.getConnection());
		initialise();
	}
	
	public JfgpWindow(RefereeAccount referee, Connection connection) {	
		JFGPdb db = new JFGPdb();
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setUserType("Referee");
		setRefereeUser(referee);
		setDbConnection(db.getConnection());
		initialise();
	}
	 
	private void initialise() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setBackground(new Color(192, 192, 192));
		setMinimumSize(new Dimension(600, 450));
		setBounds(100, 100, 450, 300);
		getContentPane().add(new toolBar(this, this.connection), BorderLayout.WEST);
		getContentPane().add(new HomePanel(), BorderLayout.CENTER);
		setVisible(true);
	}
	
	public Connection getDbConnection() { return this.connection;	}
	public void setDbConnection(Connection conn) { this.connection = conn;	}
	
	public boolean isLoggedIn() { return isLoggedIn; }
	public void setLoggedIn(boolean isLoggedIn) { this.isLoggedIn = isLoggedIn; }
	
	public String getUserType() { return userType; }
	public void setUserType(String userType) { this.userType = userType; }
	
	public void setManagerUser(ManagerAccount manager) { this.managerAccount = manager; }
	public ManagerAccount getManagerAccount() { return this.managerAccount; }
	
	public void setRefereeUser(RefereeAccount referee) { this.refereeAccount = referee; }
	public RefereeAccount getRefereeAccount() { return this.refereeAccount; }
	
	public void setAdminUser(AdminAccount admin) { this.admin = admin; }
	public AdminAccount getAdminAccount() { return this.admin; }
}
