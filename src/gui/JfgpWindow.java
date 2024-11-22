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
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Point;
import javax.swing.border.EmptyBorder;
import java.awt.Frame;
import javax.swing.JLabel;

public class JfgpWindow {
	private JLabel windowTitle;
	private String currentPanel;

	//Create the application.
	public JfgpWindow(String panel) {
		this.currentPanel = panel;
		initialize();
	}

	 // Initialize the contents of the frame.	 
	private void initialize() {
		JFrame applicationFrame = new JFrame();
		applicationFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		applicationFrame.setBackground(new Color(192, 192, 192));
		applicationFrame.setResizable(false);
		applicationFrame.setBounds(100, 100, 450, 300);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.setBorder(new EmptyBorder(0, 0, 0, 20));
		toolBar.setForeground(new Color(192, 192, 192));
		toolBar.setBackground(new Color(0, 128, 128));
		toolBar.setOrientation(SwingConstants.VERTICAL);
		applicationFrame.getContentPane().add(toolBar, BorderLayout.WEST);
		
		JButton toolBarHome = new JButton("Home");
		toolBarHome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarHome.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarHome.setForeground(new Color(192, 192, 192));
		toolBarHome.setOpaque(false);
		toolBarHome.setBackground(new Color(240, 240, 240));
		
		toolBar.add(toolBarHome);
		JButton toolBarTeams = new JButton("Teams");
		toolBarTeams.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarTeams.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarTeams.setForeground(new Color(192, 192, 192));
		toolBarTeams.setOpaque(false);
		toolBarTeams.setHorizontalAlignment(SwingConstants.LEFT);
		
		toolBar.add(toolBarTeams);
		
		JButton toolBarPlayers = new JButton("Players");
		toolBarPlayers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarPlayers.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarPlayers.setForeground(new Color(192, 192, 192));
		toolBarPlayers.setOpaque(false);
		toolBar.add(toolBarPlayers);
		
		JButton toolBarFixtures = new JButton("Fixtures");
		toolBarFixtures.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarFixtures.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarFixtures.setForeground(new Color(192, 192, 192));
		toolBarFixtures.setOpaque(false);
		toolBar.add(toolBarFixtures);
		
		JButton toolBarResults = new JButton("Results");
		toolBarResults.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarResults.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarResults.setForeground(new Color(192, 192, 192));
		toolBarResults.setOpaque(false);
		toolBar.add(toolBarResults);
		
		JButton toolBarTable = new JButton("Table");
		toolBarTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarTable.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarTable.setForeground(new Color(192, 192, 192));
		toolBarTable.setOpaque(false);
		toolBar.add(toolBarTable);
		
		JSeparator toolBarSep = new JSeparator();
		toolBarSep.setOrientation(SwingConstants.VERTICAL);
		toolBarSep.setBounds(new Rectangle(0, 0, 0, 35));
		toolBar.add(toolBarSep);
		
		JButton toolBarRoleDependant = new JButton("Role");
		toolBarRoleDependant.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarRoleDependant.setActionCommand("");
		toolBarRoleDependant.setBorder(new EmptyBorder(4, 5, 0, 0));
		toolBarRoleDependant.setForeground(new Color(192, 192, 192));
		toolBarRoleDependant.setOpaque(false);
		toolBar.add(toolBarRoleDependant);
		
		JButton toolBarLogInOrOut = new JButton("Log In");
		toolBarLogInOrOut.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toolBarLogInOrOut.setBorder(new EmptyBorder(4, 5, 4, 0));
		toolBarLogInOrOut.setForeground(new Color(192, 192, 192));
		toolBarLogInOrOut.setOpaque(false);
		toolBar.add(toolBarLogInOrOut);
		toolBarLogInOrOut.setVerticalAlignment(SwingConstants.BOTTOM);
		
		windowTitle = new JLabel("Jump For Goalposts - " + this.currentPanel);
		windowTitle.setHorizontalAlignment(SwingConstants.CENTER);
		windowTitle.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		applicationFrame.getContentPane().add(windowTitle, BorderLayout.NORTH);
		
		JPanel homePanel = new JPanel();
		applicationFrame.getContentPane().add(homePanel, BorderLayout.CENTER);
		applicationFrame.setVisible(true);
	}

}
