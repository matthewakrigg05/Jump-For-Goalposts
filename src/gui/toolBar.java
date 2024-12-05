package gui;
import java.awt.*;
import java.sql.Connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class toolBar extends JToolBar {
	
	private JButton[] toolBarButton;
	private JPanel rolePanel;
	
	public toolBar(JfgpWindow frame, Connection connection) {
		
		setBackground(new Color(0, 128, 128));
		setFloatable(false);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new EmptyBorder(0, 0, 10, 20));
		setOrientation(SwingConstants.VERTICAL);
		
		// toolbar buttons are indexed in this list
		final String[] toolBarButtonNames = {"Home", "Teams", "Players", "Fixtures", "Results",
				frame.getUserType() + " View", "Log In", "Log Out"};
		toolBarButton = new JButton[toolBarButtonNames.length];
		
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
		
		switch (frame.getUserType()) {
		case "Admin":
			AdminPanel admin = new AdminPanel(frame);
			rolePanel = admin.getPanel();
			break;
		
		case "Referee":
			RefereePanel refPanel = new RefereePanel(frame.getRefereeAccount());
			rolePanel = refPanel.getPanel();
			break;
			
		case "Manager":
			ManagerPanel manPanel = new ManagerPanel(frame.getManagerAccount());
			rolePanel = manPanel.getPanel();
			break;
		}
		
		if (frame.isLoggedIn()) { 
			add(toolBarButton[5]);
			add(toolBarButton[7]); 
			}
		else { 
			add(toolBarButton[6]);
		
			toolBarButton[6].addActionListener(e -> {
				new logInWindow(connection).setVisible(true);
				frame.dispose();
			});
		}
		
		toolBarButton[0].addActionListener(e -> { updateFrame(frame, new HomePanel()); });
		toolBarButton[1].addActionListener(e -> { updateFrame(frame, new TeamsPanel()); });
		toolBarButton[2].addActionListener(e -> { updateFrame(frame, new PlayersPanel()); });
		toolBarButton[3].addActionListener(e -> { updateFrame(frame, new FixturesPanel()); });
		toolBarButton[4].addActionListener(e -> { updateFrame(frame, new ResultsPanel()); });
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
