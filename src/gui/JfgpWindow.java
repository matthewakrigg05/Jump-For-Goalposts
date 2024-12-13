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
	private ManagerAccount managerAccount;
	private RefereeAccount refereeAccount;
	private AdminAccount admin;
	private JFGPdb db;
	
	public JfgpWindow() {
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(false);
		setDb(new JFGPdb());
		initialise();
	}
	
	public JfgpWindow(AdminAccount admin) {
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setDb(new JFGPdb());
		setAdminUser(admin);
		initialise();
	}
	
	public JfgpWindow(ManagerAccount manager) {	
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setManagerUser(manager);
		setDb(new JFGPdb());
		initialise();
	}
	
	public JfgpWindow(RefereeAccount referee, Connection connection) {	
		setTitle("Jump For Goalposts - League Manager");
		setLoggedIn(true);
		setRefereeUser(referee);
		setDb(new JFGPdb());
		initialise();
	}
	 
	private void initialise() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setBackground(new Color(192, 192, 192));
		setMinimumSize(new Dimension(600, 450));
		getContentPane().add(new toolBar(this), BorderLayout.WEST);
		getContentPane().add(new HomePanel(this), BorderLayout.CENTER);
		setVisible(true);
	}
	
	public JFGPdb getDb() { return this.db;	}
	public void setDb(JFGPdb db) { this.db = db;	}
	
	public boolean isLoggedIn() { return isLoggedIn; }
	public void setLoggedIn(boolean isLoggedIn) { this.isLoggedIn = isLoggedIn; }
	
	public void setManagerUser(ManagerAccount manager) { this.managerAccount = manager; }
	public ManagerAccount getManagerAccount() { return this.managerAccount; }
	
	public void setRefereeUser(RefereeAccount referee) { this.refereeAccount = referee; }
	public RefereeAccount getRefereeAccount() { return this.refereeAccount; }
	
	public void setAdminUser(AdminAccount admin) { this.admin = admin; }
	public AdminAccount getAdminAccount() { return this.admin; }
}
