package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Font;

public class AdminPanel extends JPanel {

	public AdminPanel() {
		
		Insets labelFieldInsets = new Insets(0, 0, 10, 25);
		Font labelFont = (new Font("Tahoma", Font.PLAIN, 25));
		int numOfCols = 5;
		int numOfRows = 11;
		int colWidth = (getWidth() / numOfCols);
		int rowHeight = (getHeight() / numOfRows);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {colWidth, colWidth, colWidth, colWidth, colWidth};
		gridBagLayout.rowHeights = new int[] {rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight};
		setLayout(gridBagLayout);
		
		JLabel leagueOptLabel = new JLabel("League Options:");
		leagueOptLabel.setFont(labelFont);
		GridBagConstraints gbc_leagueOptLabel = new GridBagConstraints();
		gbc_leagueOptLabel.insets = new Insets(0, 0, 5, 5);
		gbc_leagueOptLabel.gridx = 1;
		gbc_leagueOptLabel.gridy = 1;
		add(leagueOptLabel, gbc_leagueOptLabel);
		
		JLabel matchOptLabel = new JLabel("Match Options:");
		matchOptLabel.setFont(labelFont);
		GridBagConstraints gbc_matchOptLabel = new GridBagConstraints();
		gbc_matchOptLabel.insets = labelFieldInsets;
		gbc_matchOptLabel.gridx = 3;
		gbc_matchOptLabel.gridy = 1;
		add(matchOptLabel, gbc_matchOptLabel);
		
		JButton leagueButton = new JButton("League");
		leagueButton.setFont(labelFont);
		GridBagConstraints gbc_leagueButton = new GridBagConstraints();
		gbc_leagueButton.insets = labelFieldInsets;
		gbc_leagueButton.gridx = 1;
		gbc_leagueButton.gridy = 3;
		add(leagueButton, gbc_leagueButton);
		
		JButton genFixturesButton = new JButton("Generate Fixtures");
		genFixturesButton.setFont(labelFont);
		GridBagConstraints gbc_genFixturesButton = new GridBagConstraints();
		gbc_genFixturesButton.insets = labelFieldInsets;
		gbc_genFixturesButton.gridx = 3;
		gbc_genFixturesButton.gridy = 3;
		add(genFixturesButton, gbc_genFixturesButton);
		
		JButton seasonButton = new JButton("Season");
		seasonButton.setFont(labelFont);
		GridBagConstraints gbc_seasonButton = new GridBagConstraints();
		gbc_seasonButton.insets = labelFieldInsets;
		gbc_seasonButton.gridx = 1;
		gbc_seasonButton.gridy = 4;
		add(seasonButton, gbc_seasonButton);
		
		JButton matchRefsButton = new JButton("Assign Match Referees");
		matchRefsButton.setFont(labelFont);
		GridBagConstraints gbc_matchRefsButton = new GridBagConstraints();
		gbc_matchRefsButton.insets = labelFieldInsets;
		gbc_matchRefsButton.gridx = 3;
		gbc_matchRefsButton.gridy = 4;
		add(matchRefsButton, gbc_matchRefsButton);
		
		JButton teamButton = new JButton("Team");
		teamButton.setFont(labelFont);
		GridBagConstraints gbc_teamButton = new GridBagConstraints();
		gbc_teamButton.insets = labelFieldInsets;
		gbc_teamButton.gridx = 1;
		gbc_teamButton.gridy = 5;
		add(teamButton, gbc_teamButton);
		
		JButton recMatchButton = new JButton("Record Matches");
		recMatchButton.setFont(labelFont);
		GridBagConstraints gbc_recMatchButton = new GridBagConstraints();
		gbc_recMatchButton.insets = labelFieldInsets;
		gbc_recMatchButton.gridx = 3;
		gbc_recMatchButton.gridy = 5;
		add(recMatchButton, gbc_recMatchButton);
		
		JButton managersButton = new JButton("Managers");
		managersButton.setFont(labelFont);
		GridBagConstraints gbc_managersButton = new GridBagConstraints();
		gbc_managersButton.insets = labelFieldInsets;
		gbc_managersButton.gridx = 1;
		gbc_managersButton.gridy = 6;
		add(managersButton, gbc_managersButton);
		
		JButton updateLeagueDataButton = new JButton("Update League Data");
		updateLeagueDataButton.setFont(labelFont);
		GridBagConstraints gbc_updateLeagueDataButton = new GridBagConstraints();
		gbc_updateLeagueDataButton.insets = labelFieldInsets;
		gbc_updateLeagueDataButton.gridx = 3;
		gbc_updateLeagueDataButton.gridy = 6;
		add(updateLeagueDataButton, gbc_updateLeagueDataButton);
		
		JButton refereesButton = new JButton("Referees");
		refereesButton.setFont(labelFont);
		GridBagConstraints gbc_refereesButton = new GridBagConstraints();
		gbc_refereesButton.insets = labelFieldInsets;
		gbc_refereesButton.gridx = 1;
		gbc_refereesButton.gridy = 7;
		add(refereesButton, gbc_refereesButton);
		
		JButton playersButton = new JButton("Players");
		playersButton.setFont(labelFont);
		GridBagConstraints gbc_playersButton = new GridBagConstraints();
		gbc_playersButton.insets = labelFieldInsets;
		gbc_playersButton.gridx = 1;
		gbc_playersButton.gridy = 8;
		add(playersButton, gbc_playersButton);

	}

}
