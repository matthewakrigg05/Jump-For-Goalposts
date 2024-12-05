package gui;
import javax.swing.*;
import accounts.*;
import leagueDB.leagueMemberData;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("serial")
public class logInWindow extends JFrame {
	
	private JTextField emailField;
	private JPasswordField passwordField;
	private Connection connection;

	public logInWindow(Connection connection) { 
		initialise();
		this.connection = connection; 
		}

	private void initialise() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Insets labelFieldInsets = new Insets(0, 0, 5, 5);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{50, 300, 50};
			gridBagLayout.rowHeights = new int[]{0, 14, 38, 20, 40, 40, 40};
		getContentPane().setLayout(gridBagLayout);
		
		JButton backButton = new JButton("Back");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
			gbc_backButton.insets = labelFieldInsets;
			gbc_backButton.gridx = 0;
			gbc_backButton.gridy = 0;
		getContentPane().add(backButton, gbc_backButton);
		
		JLabel emailLabel = new JLabel("Email Address:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
			gbc_emailLabel.anchor = GridBagConstraints.NORTH;
			gbc_emailLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_emailLabel.insets = labelFieldInsets;
			gbc_emailLabel.gridwidth = 3;
			gbc_emailLabel.gridx = 1;
			gbc_emailLabel.gridy = 1;
		getContentPane().add(emailLabel, gbc_emailLabel);
		
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(7, 25));
		GridBagConstraints gbc_emailField = new GridBagConstraints();
			gbc_emailField.anchor = GridBagConstraints.WEST;
			gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
			gbc_emailField.insets = labelFieldInsets;
			gbc_emailField.gridx = 1;
			gbc_emailField.gridy = 2;
		getContentPane().add(emailField, gbc_emailField);
		
		JLabel passwordLabel = new JLabel("Password:");
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
			gbc_passwordLabel.anchor = GridBagConstraints.WEST;
			gbc_passwordLabel.fill = GridBagConstraints.VERTICAL;
			gbc_passwordLabel.insets = labelFieldInsets;
			gbc_passwordLabel.gridx = 1;
			gbc_passwordLabel.gridy = 4;
		getContentPane().add(passwordLabel, gbc_passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(7, 25));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = labelFieldInsets;
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 1;
			gbc_passwordField.gridy = 5;
		getContentPane().add(passwordField, gbc_passwordField);
		
		JButton logInButton = new JButton("Log In");
		GridBagConstraints gbc_logInButton = new GridBagConstraints();
			gbc_logInButton.insets = labelFieldInsets;
			gbc_logInButton.gridx = 1;
			gbc_logInButton.gridy = 6;
		getContentPane().add(logInButton, gbc_logInButton);
		
		backButton.addActionListener(e -> {
			new JfgpWindow().setVisible(true);
			dispose();
		});
		
		logInButton.addActionListener(e -> {
			String email = emailField.getText();
			String password = String.valueOf(passwordField.getPassword());
			logIn(email, password, connection);
			dispose();
		});
		
		setVisible(true);
	}
	
	public static void logIn(String email, String password, Connection connection) {
		try {
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                    "SELECT * FROM userAccounts WHERE emailAddress = ? AND password = ?"
	            );

	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                int userId = resultSet.getInt("userId");
	                String userType = resultSet.getString("userType");
	                connection.close();
	                
	                switch (userType) {
	                	case "admin":
	                		new JfgpWindow(new AdminAccount(userId, email, password));
	                		break;
	                		
	                	case "referee":
	                		new JfgpWindow(new RefereeAccount(userId, email, password, 
	                				leagueMemberData.getRefereeFromId(connection, userId)), connection);
	                		break;
	                		
	                	case "manager":
	                		new JfgpWindow(new ManagerAccount(userId, email, password));
	                		break;
	                	
	                	default:
	                		JfgpWindow window = new JfgpWindow();
	                		JOptionPane.showMessageDialog(window, "Log In Failed");
	                }
	            }
	            else { 
		            JfgpWindow window = new JfgpWindow();
	        		JOptionPane.showMessageDialog(window, "Log In Failed");
	            }
	        } catch (SQLException e) { e.printStackTrace(); }
	}
}
