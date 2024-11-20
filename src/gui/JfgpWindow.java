package gui;

import javax.swing.JFrame;

public class JfgpWindow {

	//Create the application.
	public JfgpWindow() {
		initialize();
	}

	 // Initialize the contents of the frame.	 
	private void initialize() {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
