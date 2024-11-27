package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class panel extends JPanel {
	
	protected JPanel panel;
	protected Insets labelFieldInsets;
	protected Font labelFont;
	protected GridBagLayout gridBagLayout;
	protected List<String> buttonNames;
	protected JButton[] panelButton;
	
	public panel() {}
	
	protected void initialise() {}
	
	protected void addPanelComponents(JPanel panel) {}
	
	protected void addActionListeners() {}
	
	public JPanel getPanel() { return this.panel; }

}
