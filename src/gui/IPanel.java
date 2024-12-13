package gui;
import javax.swing.JPanel;

public interface IPanel {
	
	public void initialise();
	
	public void addActionListeners();
	
	public void addPanelComponents(JPanel panel);
}
