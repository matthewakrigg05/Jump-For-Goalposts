package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class toolBar extends JToolBar {
	
	
	private JButton[] toolBarButton;
	private JPanel rolePanel;
	
	public toolBar(JfgpWindow frame, String userType) {
		
		String user = userType.substring(0, 1).toUpperCase() + userType.substring(1); 
		final String[] toolBarButtonNames = {"Home", "Teams", "Players", "Fixtures", "Results", user + " View", "Log In"};
		
		switch (userType) {
		case "admin":
			rolePanel =  new AdminPanel();
			break;
		
		case "referee":
			rolePanel = new RefereePanel();
			break;
			
		case "manager":
			rolePanel = new ManagerPanel();
			break;
		}
		
		toolBarButton = new JButton[7];
		for (int i = 0; i < 7; i++) {
			toolBarButton[i] = new JButton(toolBarButtonNames[i]);
		}
		
		setBackground(new Color(0, 128, 128));
		setFloatable(false);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new EmptyBorder(0, 0, 0, 20));
		setOrientation(SwingConstants.VERTICAL);

		// For loop loops through the different tool bar buttons and applies the same settings to them all in the order that appears in the toolBarButtonNames list.
		for (int i = 0; i < 7; i++) {
			toolBarButton[i].setFont(new Font("Tahoma", Font.PLAIN, 24));
			toolBarButton[i].setForeground(new Color(192, 192, 192));
			toolBarButton[i].setFocusPainted(false);
			toolBarButton[i].setBorderPainted(false);
			toolBarButton[i].setOpaque(false);
			toolBarButton[i].setContentAreaFilled(false);
			toolBarButton[i].setHorizontalAlignment(SwingConstants.LEFT);
			toolBarButton[i].setBorder(new EmptyBorder(4, 5, 0, 0));
			toolBarButton[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		JSeparator toolBarSep = new JSeparator();
		toolBarSep.setOrientation(SwingConstants.VERTICAL);
		toolBarSep.setBounds(new Rectangle(0, 0, 0, 35));
		
		add(toolBarButton[0]);
		add(toolBarButton[1]);
		add(toolBarButton[2]);
		add(toolBarButton[3]);
		add(toolBarButton[4]);
		add(toolBarSep);
		
		if (userType != "user") {
			add(toolBarButton[5]);
		}
		
		add(toolBarButton[6]);
		
		toolBarButton[0].addActionListener(e -> {
			this.updateFrame(frame, new HomePanel());
		});
		
		toolBarButton[1].addActionListener(e -> {
			this.updateFrame(frame, new TeamsPanel());
		});
		
		toolBarButton[2].addActionListener(e -> {
			this.updateFrame(frame, new PlayersPanel());
		});
		
		toolBarButton[3].addActionListener(e -> {
			this.updateFrame(frame, new FixturesPanel());
		});
		
		toolBarButton[4].addActionListener(e -> {
			this.updateFrame(frame, new ResultsPanel());
		});
		
		toolBarButton[5].addActionListener(e -> {
			this.updateFrame(frame, rolePanel);
		});
		
		toolBarButton[6].addActionListener(e -> {
			new logInWindow().setVisible(true);
			frame.dispose();
		});
	}
	
	private void updateFrame(JfgpWindow frame, JPanel panel) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(this, BorderLayout.WEST);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
}
