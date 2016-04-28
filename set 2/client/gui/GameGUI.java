package set.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;

//import set.server.game.Card;
//import set.server.game.Game;

public class GameGUI {

	/********************/
	/** GameGUI fields **/
	/********************/

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel controlPanel2;
	
	private JButton submitButton;
	private ArrayList<JToggleButton> cardArray;
	//private ArrayList<Card> selectedCards;

	/*************************/
	/** GameGUI Constructor **/
	/*************************/

	public GameGUI() {
		prepareGUI();
		prepareLayout();
	}

	/*********************/
	/** GameGUI methods **/
	/*********************/
	
	// Load GUI
	private void prepareGUI() {
		
		mainFrame = new JFrame("Java Swing");
		mainFrame.setSize(800, 700);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new GridLayout(4, 4));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);

		statusLabel.setSize(350, 100);

		controlPanel = new JPanel(new GridLayout(3, 3, 1, 1));
		controlPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		controlPanel.setLayout(new FlowLayout());
		// controlPanel.setBackground(Color.WHITE);
		controlPanel.setSize(300, 300);
		GridLayout layout = new GridLayout(3, 4);

		controlPanel2 = new JPanel(new GridLayout(1, 1, 1, 1));
		controlPanel2.setBorder(new EmptyBorder(1, 1, 1, 1));
		controlPanel2.setLayout(new FlowLayout());
		// controlPanel2.setBackground(Color.darkGray);
		// controlPanel2.setSize(10,10);
		GridLayout layout2 = new GridLayout(1, 1);

		controlPanel.setLayout(layout);
		controlPanel2.setLayout(layout2);

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(controlPanel2);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}
	
	// Layout game structure
	private void prepareLayout() {
		
		// DELETE ME, DEBUG ONLY
		//Game NewGame = new Game();

		headerLabel.setText("SET: The Online Version");
		submitButton = new JButton("Submit Set");
		controlPanel2.add(submitButton);
		
		// Initialize card array
		cardArray = new ArrayList<JToggleButton>();
		LineBorder defaultBorder = new LineBorder(Color.BLACK, 1);
		LineBorder selectedBorder = new LineBorder(Color.ORANGE, 3);
		UIManager.put("ToggleButton.select", Color.WHITE);
		
		// Set properties for all cards
		for(int i = 0; i < 12; i++) {
			
			// DELETE ME, DEBUG ONLY
			//String cardIconPath = "src/set/client/gui/Cards/" + NewGame.Board.get(i).toString() + ".gif";
			//System.out.println(cardIconPath);
			//cardArray.add(new JToggleButton(new ImageIcon(cardIconPath)));
			
			cardArray.add(new JToggleButton());
			JToggleButton card = cardArray.get(i);
			
			card.setPreferredSize(new Dimension(95, 65));
			card.setBackground(Color.WHITE);
			
			controlPanel.add(card);
			
			card.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (card.isSelected()) {
						card.setBorder(selectedBorder);
					} else {
						card.setBorder(defaultBorder);
					}
				}
			});
		}
		
		cardArray.get(0).setHorizontalTextPosition(SwingConstants.LEFT);
		mainFrame.setVisible(true);
	}
	
	public void updateGUI(int boardSize, String cardString) {
		for(int i = 0; i < boardSize; i++) {
			JToggleButton card = cardArray.get(i);
			String cardID = cardString.substring(4*i,4*i+4);
			String cardIconPath = "src/set/client/gui/Cards/" + cardID + ".gif";
			//System.out.println("GameGUI: " + cardIconPath);
			card.setIcon(new ImageIcon(cardIconPath));
		}
	}

	/*
	private ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = GameGUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("GameGUI: Couldn't find file: " + path);
			return null;
		}
	}
	*/
	
	/******************/
	/** GameGUI main **/
	/******************/

	public static void main(String[] args) {
		GameGUI demo = new GameGUI();
		demo.updateGUI(12,"123112223312213312331333131232231133121233223121");
	}
}