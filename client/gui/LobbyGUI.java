/*
 *  LobbyGUI.java
 *  
 *  GUI for lobby and its functionalities.
 *  - Create solitaire
 *  - Create game
 *  - Join game
 *  - Exit lobby
 *  - List of players
 *  - List of available game rooms
 *  - Chatting
 *  
 *  Notes:
 *  - Players can create games with alphanum + space characters only
 *  - Players will be alerted if they do not select a game before joining
 *  - Hosts can kick players by after selecting them from the list
 *  - Host will need extra confirmation to kick player
 *  - A game message will be displayed whenever players enter/leave lobby
 *  - Profanity is not filtered in chat
 *  
 */

package set.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import set.client.GameClient;

public class LobbyGUI extends JFrame {

	/*********************/
	/** LobbyGUI fields **/
	/*********************/

	// Client associated with this GUI
	private GameClient client;

	// Selected game in this GUI
	private String selectedGame;
	private String selectedGameName;
	private String selectedHostName;

	// Panels
	private JPanel mainPanel;
	private JPanel playerPanel;
	private JPanel infoPanel;
	private JPanel optionPanel;
	private JPanel chatPanel;

	// Labels
	private JLabel lobbyLabel;

	// Game/player lists
	private JScrollPane playerScrollPane;
	private JScrollPane gameScrollPane;
	private JList playerList;
	private JList gameList;
	private DefaultListModel<String> gameIDList;
	private DefaultListModel<String> usernameList;

	// Buttons
	private JButton soloButton;
	private JButton createButton;
	private JButton joinButton;
	private JButton exitButton;
	// private JButton selectButton;
	// private JButton cancelButton;

	// Chat box
	private JScrollPane chatScrollPane;
	public JTextArea chatLog;
	private JTextField messageBox;
	private JButton sendButton;

	/**************************/
	/** LobbyGUI constructor **/
	/**************************/

	public LobbyGUI(GameClient client) {
		super("SET: The Online Version");
		this.client = client;
		PrepareGUI();
		PrepareLayout();
		selectedHostName = "";
		// setVisible(true);
	}

	// Debug
	public LobbyGUI() {
		super("SET: The Online Version");
		PrepareGUI();
		PrepareLayout();
		setVisible(true);
		selectedHostName = "";
	}

	/**********************/
	/** LobbyGUI methods **/
	/**********************/

	/********************/
	/** Layout-related **/
	/********************/

	// Load GUI, prepare panels
	public void PrepareGUI() {

		// Frame
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Main panel, contains game listing
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		mainPanel.setLayout(new GridLayout(2, 1));
		// mainPanel.setSize(200, 300);

		// Player panel, contains player listing
		playerPanel = new JPanel();
		playerPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		playerPanel.setLayout(new GridLayout(1, 1));
		// playerPanel.setSize(100, 200);

		// Information panel
		infoPanel = new JPanel();
		infoPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		infoPanel.setLayout(new GridLayout(1, 1));

		// Option panel, contains all player options
		optionPanel = new JPanel();
		optionPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		optionPanel.setLayout(new GridLayout(4, 1));

		// Chat panel
		chatPanel = new JPanel();
		chatPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		chatPanel.setLayout(new BorderLayout());

		// Add panels to main frame
		add(mainPanel, BorderLayout.CENTER);
		add(playerPanel, BorderLayout.WEST);
		add(optionPanel, BorderLayout.EAST);
		add(infoPanel, BorderLayout.NORTH);
		add(chatPanel, BorderLayout.SOUTH);
		
		
		// Logout and close client when closing the GUI
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				// Send packet request to leave lobby
				client.sendPacket.LobbyRequestExit();
				
				// Closing database connection
				System.out.println("LobbyGUI: Closing database connection...");
				client.loginGUI.Logout();
				
				e.getWindow().dispose();
				
				// Closing client connection
				System.out.println("LobbyGUI: Closing client connection...");
				client.Close();
				System.exit(0);
			}
		});
	}

	// Load content
	public void PrepareLayout() {
		PrepareLabels();
		PrepareGameList();
		PreparePlayerList();
		PrepareOptions();
		PrepareChat();
	}

	/*********************/
	/** Content-related **/
	/*********************/

	// Add scroll pane for game listings
	public void PrepareGameList() {

		// Set up game list for scroll pane
		// String gameIDList[] = { "Game 1", "Game 2", "Game 3" };
		gameIDList = new DefaultListModel<String>();
		gameList = new JList<String>(gameIDList);

		// Set up scroll pane and add to main panel
		gameScrollPane = new JScrollPane(gameList);
		// scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		gameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollPane.setBounds(50, 30, 300, 50);
		mainPanel.add(gameScrollPane);

		// gameIDList.addElement("Game X"); // testing what happens if you add
		// after

		// Add mouse listener for when game is clicked
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {

					// highlight game in yellow
					System.out.println("LobbyGUI: ~~~~~~~~~~~~~~~~~~~~~~~~");

					selectedGame = (String) gameList.getSelectedValue();
					System.out.println("LobbyGUI: Game selected: " + selectedGame);

					if (selectedGame != null) {

						// Extract game name (can remove)
						selectedGameName = selectedGame.substring(selectedGame.indexOf("Room: ") + ("Room: ").length(),
								selectedGame.indexOf(", Host: "));
						System.out.println("LobbyGUI: Game name: " + selectedGameName);

						// Extract host name (only need this)
						selectedHostName = selectedGame
								.substring(selectedGame.indexOf(", Host: ") + (", Host: ").length());
						System.out.println("LobbyGUI: Host name: " + selectedHostName);
					}
				}
			}
		};
		gameList.addMouseListener(mouseListener);
	}

	// Add scroll pane for player listings
	public void PreparePlayerList() {

		usernameList = new DefaultListModel<String>();
		playerList = new JList<String>(usernameList);

		// Set up scroll pane and add to main panel
		playerScrollPane = new JScrollPane(playerList);
		playerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		playerPanel.add(playerScrollPane);
	}

	// Add labels
	public void PrepareLabels() {

		lobbyLabel = new JLabel("", JLabel.CENTER);
		infoPanel.add(lobbyLabel);

		lobbyLabel.setText("Welcome to the lobby.");
	}

	// Add options
	public void PrepareOptions() {

		soloButton = new JButton("Create Solitaire");
		createButton = new JButton("Create Game");
		joinButton = new JButton("Join Game");
		exitButton = new JButton("Exit Lobby");

		soloButton.setEnabled(true);
		createButton.setEnabled(true);
		joinButton.setEnabled(true);
		exitButton.setEnabled(true);

		optionPanel.add(soloButton);
		optionPanel.add(createButton);
		optionPanel.add(joinButton);
		optionPanel.add(exitButton);

		soloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendPacket.LobbyRequestCreateSolo();
			}
		});

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGamePopUp();
			}
		});

		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!selectedHostName.equals("")) {
					client.sendPacket.LobbyRequestJoinGame(selectedHostName);
					selectedHostName = "";
				} else {
					JOptionPane.showMessageDialog(null, "Please select a game.", "Error", JOptionPane.WARNING_MESSAGE);
					selectedHostName = "";
				}
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExitLobbyPopUp();
			}
		});
	}

	// Add chat box
	public void PrepareChat() {

		// DefaultListModel<String> messages = new DefaultListModel<String>();
		// JList chatLog = new JList<String>(messages);

		// Chat log
		chatLog = new JTextArea();
		chatLog.setEditable(false);
		chatLog.setMargin(new Insets(7, 7, 7, 7));

		// Add chat area to scroll pane
		chatScrollPane = new JScrollPane(chatLog);
		chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Message box and send button
		messageBox = new JTextField();
		JButton sendButton = new JButton("Send");

		// Add components to frame
		mainPanel.add(chatScrollPane);
		chatPanel.add(messageBox, BorderLayout.CENTER);
		chatPanel.add(sendButton, BorderLayout.EAST);

		// process TextField after user hits Enter
		messageBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// System.out.println("[" + "USERNAME" + "]:\t" +
				// messageBox.getText());

				// Build message
				String message = "[" + client.loginGUI.username + "]: " + messageBox.getText();
				System.out.println(message);

				// Send packet containing message
				// chatLog.append(message + "\n");
				client.sendPacket.ChatMessageInLobby(message);

				// Reset message box
				messageBox.setText("");
				messageBox.requestFocusInWindow(); // ??
			}
		});

		// process TextField after user hits send
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// System.out.println("[" + "USERNAME" + "]:\t" +
				// messageBox.getText());

				// Build message
				String message = "[" + client.loginGUI.username + "]: " + messageBox.getText();
				System.out.println(message);

				// Send packet containing message
				// chatLog.append(message + "\n");
				client.sendPacket.ChatMessageInLobby(message);

				// Reset message box
				messageBox.setText("");
				messageBox.requestFocusInWindow();
			}
		});
	}

	/*************/
	/** Pop-ups **/
	/*************/
	
	public void CreateGamePopUp() {
		
		String message = "Choose your game name.";
		String title = "Create Game";
		int messageType = JOptionPane.PLAIN_MESSAGE;
		String gameName = JOptionPane.showInputDialog(null, message, title, messageType);

		if (gameName != null) {
			boolean isAlphaNumSpace = gameName.matches("[a-zA-Z0-9 ]+");
			if (isAlphaNumSpace) {
				client.gameName = gameName;
				client.sendPacket.LobbyRequestCreateGame(gameName);
			} else if (!isAlphaNumSpace) {
				JOptionPane.showMessageDialog(null, "Invalid game name. Must be alphanumeric (including space).",
						"Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void ExitLobbyPopUp() {

		String message = "Are you sure you want to exit?";
		String title = "Exit Lobby";
		int optionType = JOptionPane.YES_NO_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;
		int selection = JOptionPane.showConfirmDialog(null, message, title, optionType, messageType);

		if (selection == JOptionPane.YES_OPTION) {
			client.sendPacket.LobbyRequestExit();
			client.loginGUI.Logout();
			//System.out.println("LobbyGUI: Exit YES");
		}
	}

	/**************************/
	/** Managing lobby lists **/
	/**************************/

	public void AddPlayer(String username) {
		usernameList.addElement(username);
	}

	public void RemovePlayer(String username) {
		usernameList.removeElement(username); // look into remove later
	}

	public void AddGame(String gameName, String username) {
		String gameID = "Room: " + gameName + ", Host: " + username;
		gameIDList.addElement(gameID);
	}

	public void RemoveGame(String gameName, String username) {
		String gameID = "Room: " + gameName + ", Host: " + username;
		gameIDList.removeElement(gameID);
	}
	
	// Clear fields for new login
	public void Clear() {
		usernameList.removeAllElements(); // remove all players in game list, avoid dups
		gameIDList.removeAllElements();
		chatLog.setText(""); // clear chat
		messageBox.setText("");
	}

	/*******************/
	/** LobbyGUI main **/
	/*******************/

	public static void main(String[] args) {

		LobbyGUI demo = new LobbyGUI();

		// demo.ExitLobbyPopUp();
		// demo.CreateGamePopUp();

		demo.usernameList.addElement("Player 1");
		demo.usernameList.addElement("Player 2");
		demo.usernameList.addElement("Player 3");
		demo.usernameList.removeElement("Player 1");

		// demo.gameIDList.addElement("Room 1");
		// demo.gameIDList.addElement("Room 2");
		// demo.gameIDList.addElement("Room 3");
		// demo.gameIDList.removeElement("Room 2");

		for (int i = 0; i < 5; i++) {
			String gameID = "Room: Game " + i + ", Host: User " + i;
			demo.gameIDList.addElement(gameID);
		}

		// System.out.println("I love you poopypie. I love you too <3 <3 <3");
	}

}
