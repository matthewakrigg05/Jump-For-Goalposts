package gui;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
abstract class panel extends JPanel {
	
	private JPanel panel;
	private JfgpWindow frame;
	private Insets labelFieldInsets;
	private Font labelFont;
	private List<String> buttonNames;

	
	protected JButton[] panelButton;
	protected GridBagLayout gridBagLayout;
	
	public panel(JfgpWindow frame) { setFrame(frame); }
	
	// Three methods that all the child classes of this class should have, they are used to set up the panels
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
	
	public JfgpWindow getFrame() { return this.frame; }
	public void setFrame(JfgpWindow frame) { this.frame = frame; }
	
	public static JDialog getDispMatchDialog(JfgpWindow frame) {
		JDialog dispMatchDialog = new JDialog();
		
		return dispMatchDialog;
	}
}
