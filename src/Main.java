import gui.JfgpWindow;

public class Main {

	// Launches application in the default - not signed-in view.
	public static void main(String[] args) {
		
		try { new JfgpWindow(); } catch (Exception e) { e.printStackTrace(); }
	}
}
