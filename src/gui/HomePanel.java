package gui;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import league.Season;
import league.Team;
import leagueDB.RetrieveGeneralStatistics;
import leagueDB.JFGPdb;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	// all static - information based dashboard, no jdialogues required as there should not be 
	// any interaction with this panel. Displays league table, some top goalscorers/assists and maybe
	// some information on upcoming matches
	JfgpWindow frame;
	JFGPdb db;
	Player topScorer;
	
	
	public HomePanel(JfgpWindow frame) {
		this.frame = frame;
		this.db = frame.getDb();
		Season currentSeason = db.findCurrentSeason();
		
		List<Team> seasonTeams = db.findCurrentSeason().getSeasonTeams(db.getConnection());
		String[] leagueTableCols = {"Team", "GP", "W", "D", "L", "Points"};
		String[][] leagueTableData = db.findCurrentSeason().getLeagueTableData(db.getConnection());
		topScorer = currentSeason.getTopScorer(db, db.findCurrentSeason());
		
		DefaultTableModel tableModel = new DefaultTableModel(leagueTableData, leagueTableCols);
        JTable table = new JTable(tableModel);
        table.setEnabled(false);
        table.setOpaque(false);
        table.setShowVerticalLines(false);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        
        JScrollPane tablePane = new JScrollPane(table);
		add(tablePane);
		
		JLabel topScorerLabel = new JLabel("Top Scorer: " + topScorer.getFullName() + " " + topScorer.getGoals(db.getConnection()));
		add(topScorerLabel);
		
	}

}
