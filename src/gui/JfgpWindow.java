package gui;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class JfgpWindow {
	private JTextField windowTitle;
	private String currentPanel;

	//Create the application.
	public JfgpWindow(String panel) {
		this.currentPanel = panel;
		initialize();
	}

	 // Initialize the contents of the frame.	 
	private void initialize() {
		JFrame applicationFrame = new JFrame();
		applicationFrame.setBounds(100, 100, 450, 300);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		applicationFrame.getContentPane().add(toolBar, BorderLayout.WEST);
		
		JButton toolBarHome = new JButton("Home");
		toolBarHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		toolBar.add(toolBarHome);
		JButton toolBarTeams = new JButton("Teams");
		toolBarTeams.setHorizontalAlignment(SwingConstants.LEFT);
		
		toolBar.add(toolBarTeams);
		
		JButton toolBarPlayers = new JButton("Players");
		toolBar.add(toolBarPlayers);
		
		JButton toolBarFixtures = new JButton("Fixtures");
		toolBar.add(toolBarFixtures);
		
		JButton toolBarResults = new JButton("Results");
		toolBar.add(toolBarResults);
		
		JButton toolBarTable = new JButton("Table");
		toolBar.add(toolBarTable);
		
		JSeparator toolBarSep = new JSeparator();
		toolBarSep.setOrientation(SwingConstants.VERTICAL);
		toolBarSep.setBounds(new Rectangle(0, 0, 0, 35));
		toolBar.add(toolBarSep);
		
		JButton toolBarRoleDependant = new JButton("Role Dependant");
		toolBar.add(toolBarRoleDependant);
		
		JButton toolBarLogInOrOut = new JButton("Log In");
		toolBarLogInOrOut.setVerticalAlignment(SwingConstants.BOTTOM);
		toolBar.add(toolBarLogInOrOut);
		
		windowTitle = new JTextField("Jump For Goalposts - " + this.currentPanel);
		windowTitle.setEditable(false);
		windowTitle.setHorizontalAlignment(SwingConstants.CENTER);
		windowTitle.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		applicationFrame.getContentPane().add(windowTitle, BorderLayout.NORTH);
		windowTitle.setColumns(10);
		
		JPanel homePanel = new JPanel();
		applicationFrame.getContentPane().add(homePanel, BorderLayout.CENTER);
		applicationFrame.setVisible(true);
	}

}
