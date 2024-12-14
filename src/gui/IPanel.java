package gui;
import javax.swing.JPanel;

/*
 * An interface that ensures that each panel uses the specified methods.
 */
public interface IPanel {
	
	public void initialise();
	
	public void addActionListeners();
	
	public void addPanelComponents(JPanel panel);
}
