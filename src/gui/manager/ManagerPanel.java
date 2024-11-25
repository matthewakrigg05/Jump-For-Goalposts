package gui.manager;

import javax.swing.JPanel;

import gui.JfgpWindow;

public class ManagerPanel extends JfgpWindow {

	private static final long serialVersionUID = 1L;
	private JPanel ManagerPanel;

	/**
	 * Create the panel.
	 */
	public ManagerPanel() {

	}

	@Override
	protected JPanel getPanel() {
		return this.ManagerPanel;
	}
}
