package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class panel extends JPanel {
	
	private JPanel panel;
	private Insets labelFieldInsets;
	private Font labelFont;
	private List<String> buttonNames;
	
	protected JButton[] panelButton;
	protected GridBagLayout gridBagLayout;
	
	public panel() {}
	
	protected void initialise() {}
	
	protected void addPanelComponents(JPanel panel) {}
	
	protected void addActionListeners() {}
	
	public JPanel getPanel() { return this.panel; }
	public void setPanel(JPanel panel) { this.panel = panel; }
	
	public Font getFont() { return this.labelFont;}
	public void setFont(Font font) {this.labelFont = font;}
	
	public Insets getInsets() { return this.labelFieldInsets; }
	public void setInsets(Insets insets) { this.labelFieldInsets = insets; }
	
	public List<String> getButtonNames() { return this.buttonNames; }
	public void setButtonNames(List<String> buttons) { this.buttonNames = buttons; }

}
