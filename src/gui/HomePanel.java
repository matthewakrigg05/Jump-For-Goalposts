package gui;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import league.Team;
import leagueDB.leagueData;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	// all static - information based dashboard, no jdialogues required as there should not be 
	// any interaction with this panel. Displays league table, some top goalscorers/assists and maybe
	// some information on upcoming matches
	JfgpWindow frame;
	
	
	public HomePanel(JfgpWindow frame) {
		this.frame = frame;
		List<Team> seasonTeams = leagueData.getSeasonTeams(frame.getDbConnection(), leagueData.getCurrentSeason(frame.getDbConnection()).getId());
		System.out.println(seasonTeams);
		String[] leagueTableCols = {"Team", "GP", "W", "D", "L", "Points"};
		String[][] leagueTableData = leagueData.getLeagueTableData(frame.getDbConnection(), seasonTeams);
		
		DefaultTableModel tableModel = new DefaultTableModel(leagueTableData, leagueTableCols);
        JTable table = new JTable(tableModel);
        table.setEnabled(false);
        table.setOpaque(false);
        table.setShowVerticalLines(false);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        
        JScrollPane tablePane = new JScrollPane(table);
		add(tablePane);
	}

}
