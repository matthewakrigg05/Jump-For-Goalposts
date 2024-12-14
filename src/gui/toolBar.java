package gui;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class toolBar extends JToolBar {
	
	private JButton[] toolBarButton;
	private JPanel rolePanel;
	private String[] toolBarButtonNames = {"Home", "Teams", "Players", "Fixtures", "Results",
			"Your View", "Log In", "Log Out"};
	
	/*
	 * Class responsible for the JToolBar that appears on the west side of the UI, allows for navigation
	 * between the different panels, as well as giving logged in users accses to their role specific
	 * panels.
	 */
	public toolBar(JfgpWindow frame) {
		setBackground(new Color(0, 128, 128));
		setFloatable(false);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new EmptyBorder(0, 0, 10, 20));
		setOrientation(SwingConstants.VERTICAL);
		
		toolBarButton = new JButton[toolBarButtonNames.length];
		
		// sets the common attributes of the buttons that are all going to be the same in the toolbar
		for (int i = 0; i < toolBarButtonNames.length; i++) {
			toolBarButton[i] = new JButton(toolBarButtonNames[i]);
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
		
		if (frame.isLoggedIn()) { 
			// dependant on the type of user that is signed in, a panel dedicated to be used by them is made accessible
			if (frame.getAdminAccount() != null) {
				AdminPanel admin = new AdminPanel(frame);
				rolePanel = admin;
			}
			
			else if (frame.getRefereeAccount() != null) {
				RefereePanel refPanel = new RefereePanel(frame);
				rolePanel = refPanel;
			}
			else if (frame.getManagerAccount() != null) {
				ManagerPanel manPanel = new ManagerPanel(frame, frame.getManagerAccount());
				rolePanel = manPanel;
			}
			
			add(toolBarButton[5]);
			add(toolBarButton[7]); 
			}
		else { 
			add(toolBarButton[6]);
		
			toolBarButton[6].addActionListener(e -> {
				new logInWindow(frame.getDb()).setVisible(true);
				frame.dispose();
			});
		}
		
		toolBarButton[0].addActionListener(e -> { updateFrame(frame, new HomePanel(frame)); });
		toolBarButton[1].addActionListener(e -> { updateFrame(frame, new TeamsPanel(frame)); });
		toolBarButton[2].addActionListener(e -> { updateFrame(frame, new PlayersPanel(frame)); });
		toolBarButton[3].addActionListener(e -> { updateFrame(frame, new FixturesPanel(frame)); });
		toolBarButton[4].addActionListener(e -> { updateFrame(frame, new ResultsPanel(frame)); });
		toolBarButton[5].addActionListener(e -> { updateFrame(frame, rolePanel); });
		
		toolBarButton[7].addActionListener(e -> {
			int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", 
					"Log Out?",  JOptionPane.YES_NO_OPTION);
			if(response == 0) {
				frame.dispose();
				new JfgpWindow();
			}
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
