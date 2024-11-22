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
import java.awt.Cursor;

public class JfgpWindow {
	private JLabel windowTitle;
	private String currentPanel;
	Font toolBarFont = new Font("Tahoma", Font.PLAIN, 24);
	Color fgColour = new Color(192, 192, 192);
	EmptyBorder buttonBorder = new EmptyBorder(4, 5, 0, 0);
	private final String[] toolBarButtonNames = {"Home", "Teams", "Players", "Fixtures", "Results", "Role", "Log In"};
	private JButton[] toolBarButton;

	//Create the application.
	public JfgpWindow(String panel) {
		this.currentPanel = panel;
		
		toolBarButton = new JButton[7];
		for (int i = 0; i < 7; i++) {
			toolBarButton[i] = new JButton(toolBarButtonNames[i]);
		}
		initialize();
	}

	 // Initialize the contents of the window.	 
	private void initialize() {
		
		JFrame applicationFrame = new JFrame();
		applicationFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		applicationFrame.setBackground(new Color(192, 192, 192));
		applicationFrame.setResizable(false);
		applicationFrame.setBounds(100, 100, 450, 300);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(new Color(0, 128, 128));
		toolBar.setFloatable(false);
		toolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.setBorder(new EmptyBorder(0, 0, 0, 20));
		toolBar.setForeground(fgColour);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBar.setFont(toolBarFont);
		applicationFrame.getContentPane().add(toolBar, BorderLayout.WEST);
		
		for (int i = 0; i < 7; i++) {
			toolBarButton[i].setFont(toolBarFont);
			toolBarButton[i].setForeground(fgColour);
			toolBarButton[i].setFocusPainted(false);
			toolBarButton[i].setBorderPainted(false);
			toolBarButton[i].setHorizontalAlignment(SwingConstants.LEFT);
			toolBarButton[i].setBorder(buttonBorder);
			toolBarButton[i].setOpaque(false);
			toolBarButton[i].setContentAreaFilled(false);
			toolBarButton[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		JSeparator toolBarSep = new JSeparator();
		toolBarSep.setOrientation(SwingConstants.VERTICAL);
		toolBarSep.setBounds(new Rectangle(0, 0, 0, 35));
		
		toolBar.add(toolBarButton[0]);
		toolBar.add(toolBarButton[1]);
		toolBar.add(toolBarButton[2]);
		toolBar.add(toolBarButton[3]);
		toolBar.add(toolBarButton[4]);
		toolBar.add(toolBarSep);
		toolBar.add(toolBarButton[5]);
		toolBar.add(toolBarButton[6]);
		
		windowTitle = new JLabel("Jump For Goalposts - " + this.currentPanel);
		windowTitle.setHorizontalAlignment(SwingConstants.CENTER);
		windowTitle.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		applicationFrame.getContentPane().add(windowTitle, BorderLayout.NORTH);
		
		JPanel homePanel = new JPanel();
		applicationFrame.getContentPane().add(homePanel, BorderLayout.CENTER);
		applicationFrame.setVisible(true);
	}
}
