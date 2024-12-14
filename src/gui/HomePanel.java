package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import league.Season;
import leagueDB.JFGPdb;
import leagueMembers.Player;

@SuppressWarnings("serial")
public class HomePanel extends JPanel implements IPanel {

	// all static - information based dashboard, no jdialogues required as there should not be 
	// any interaction with this panel. Displays league table, some top goalscorers/assists and maybe
	// some information on upcoming matches
	JfgpWindow frame;
	JFGPdb db;
	Player topScorer;
	Season currentSeason;
	
	public HomePanel(JfgpWindow frame) {
		this.frame = frame;
		this.db = frame.getDb();
		this.currentSeason = db.findCurrentSeason();
		initialise();
	}

	@Override
	public void initialise() {
		addPanelComponents(this);
		addActionListeners();
	}

	@Override
	public void addActionListeners() {}

	@Override
	public void addPanelComponents(JPanel panel) {
		String[] leagueTableCols = {"Team", "GP", "W", "D", "L", "Points"};
		String[][] leagueTableData = db.findCurrentSeason().getLeagueTableData(db.getConnection());
		topScorer = currentSeason.getTopScorer(db, db.findCurrentSeason());
		
		DefaultTableModel tableModel = new DefaultTableModel(leagueTableData, leagueTableCols);
        JTable table = new JTable(tableModel);
        
        table.setEnabled(false);
        table.setOpaque(false);
        table.setShowVerticalLines(false);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        table.setRowSorter(null);
        
        JScrollPane tablePane = new JScrollPane(table);
		panel.add(tablePane);
		
		JLabel topScorerLabel = new JLabel("Top Scorer: " + topScorer.getFullName() + " " + topScorer.getGoals(db.getConnection()));
		panel.add(topScorerLabel);
	}
}
