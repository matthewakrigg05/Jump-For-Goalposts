package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class toolBar extends JToolBar {
	
	private final String[] toolBarButtonNames = {"Home", "Teams", "Players", "Fixtures", "Results", "Role", "Log In"};
	private JButton[] toolBarButton;
	Font toolBarFont = new Font("Tahoma", Font.PLAIN, 24);
	Color fgColour = new Color(192, 192, 192);
	EmptyBorder buttonBorder = new EmptyBorder(4, 5, 0, 0);
	
	public toolBar(JfgpWindow frame) {
		
		toolBarButton = new JButton[7];
		for (int i = 0; i < 7; i++) {
			toolBarButton[i] = new JButton(toolBarButtonNames[i]);
		}
		
		setBackground(new Color(0, 128, 128));
		setFloatable(false);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new EmptyBorder(0, 0, 0, 20));
		setForeground(fgColour);
		setOrientation(SwingConstants.VERTICAL);
		setFont(toolBarFont);

		for (int i = 0; i < 7; i++) {
			toolBarButton[i].setFont(toolBarFont);
			toolBarButton[i].setForeground(fgColour);
			toolBarButton[i].setFocusPainted(false);
			toolBarButton[i].setBorderPainted(false);
			toolBarButton[i].setOpaque(false);
			toolBarButton[i].setContentAreaFilled(false);
			toolBarButton[i].setHorizontalAlignment(SwingConstants.LEFT);
			toolBarButton[i].setBorder(buttonBorder);
			toolBarButton[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		JSeparator toolBarSep = new JSeparator();
		toolBarSep.setOrientation(SwingConstants.VERTICAL);
		toolBarSep.setBounds(new Rectangle(0, 0, 0, 35));
		
		add(toolBarButton[0]);
		add(toolBarButton[1]);
		add(toolBarButton[2]);
		add(toolBarButton[3]);
		add(toolBarButton[4]);
		add(toolBarSep);
		add(toolBarButton[5]);
		add(toolBarButton[6]);
		
		toolBarButton[0].addActionListener(e -> {
			this.updateFrame(frame, new HomePanel());
		});
		
		toolBarButton[1].addActionListener(e -> {
			this.updateFrame(frame, new TeamsPanel());
		});
		
		toolBarButton[2].addActionListener(e -> {
			this.updateFrame(frame, new PlayersPanel());
		});
		
		toolBarButton[3].addActionListener(e -> {
			this.updateFrame(frame, new FixturesPanel());
		});
		
		toolBarButton[4].addActionListener(e -> {
			this.updateFrame(frame, new ResultsPanel());
		});
		
		toolBarButton[5].addActionListener(e -> {
			this.updateFrame(frame, new AdminPanel());
		});
		
		toolBarButton[6].addActionListener(e -> {
			new logInWindow().setVisible(true);
			frame.dispose();
		});
	}
	
	private void updateFrame(JfgpWindow frame, JPanel panel) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(this, BorderLayout.WEST);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
}
