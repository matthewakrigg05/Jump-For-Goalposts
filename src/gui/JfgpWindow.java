package gui;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

public class JfgpWindow extends JFrame {

	public JPanel currentPanel;
	
	//Create the application.
	public JfgpWindow() {	
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
}
