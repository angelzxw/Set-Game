package set.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;

public class Scoreboard extends JPanel {

	public Scoreboard() {
		scoreTable = new JTable(scoreBoard, titles);
		JPanel upperRight = new JPanel(new BorderLayout());
		upperRight.setPreferredSize(new Dimension(275, 230));
		upperRight.setBackground(new Color(120, 160, 200));
	}
}
