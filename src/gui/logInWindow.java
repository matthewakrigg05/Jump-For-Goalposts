package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.Dimension;

public class logInWindow extends JFrame {
	private JTextField emailField;
	private JPasswordField passwordField;

	public logInWindow() {
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 300, 50};
		gridBagLayout.rowHeights = new int[]{0, 14, 38, 20, 40, 40, 40};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel emailLabel = new JLabel("Email Address:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.NORTH;
		gbc_emailLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 0);
		gbc_emailLabel.gridwidth = 3;
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 1;
		getContentPane().add(emailLabel, gbc_emailLabel);
		
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(6, 25));
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.anchor = GridBagConstraints.WEST;
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 2;
		getContentPane().add(emailField, gbc_emailField);
		emailField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.WEST;
		gbc_passwordLabel.fill = GridBagConstraints.VERTICAL;
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.gridx = 1;
		gbc_passwordLabel.gridy = 4;
		getContentPane().add(passwordLabel, gbc_passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(7, 25));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 5;
		getContentPane().add(passwordField, gbc_passwordField);
		
		JButton logInButton = new JButton("Log In");
		GridBagConstraints gbc_logInButton = new GridBagConstraints();
		gbc_logInButton.insets = new Insets(0, 0, 0, 5);
		gbc_logInButton.gridx = 1;
		gbc_logInButton.gridy = 6;
		getContentPane().add(logInButton, gbc_logInButton);
		
		setVisible(true);
	}

}
