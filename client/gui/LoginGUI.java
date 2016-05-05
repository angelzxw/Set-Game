/*
 *  LoginGUI.java
 *  
 */

package set.client.gui;

import java.net.URL;
import java.sql.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import set.client.GameClient;

import java.awt.*;
import java.awt.event.*;
 
public class LoginGUI extends JFrame implements ActionListener{
	
	/*********************/
	/** LoginGUI fields **/
	/*********************/
	
	// Client associated with this GUI
	private GameClient client;

	// Swing components
	private Container CT;
	private JButton registerBtn, loginBtn;
	private JLabel uname, pwd, errmeg;
	private JTextField userTxt;
	private JPasswordField passwTxt;
	private JPanel p, p0, p1, p2, p3;
	
	// Data associated with this client
	public String username;
	public int userID, numWins, totalGames, score, highscore;
	boolean LoggedIn = false;
	boolean InGame = false;
	
	// Accessing the database
	Connection con;
	Statement st;
	
	/**************************/
	/** LoginGUI constructor **/
	/**************************/
	
	public LoginGUI(GameClient client) {
		
		super("Welcome to SET: The Online Version");
		this.client = client;
		CT = getContentPane();
		//setSize(100,300);
		this.setBounds(450, 300, 400, 160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer(1000,(ActionListener) this);
		InitGUI();	
	}
	
	public LoginGUI(String string) {
		
		super(string);
		CT = getContentPane();
		//setSize(100,300);
		this.setBounds(450, 300, 400, 160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer(1000,(ActionListener) this);
		InitGUI();	
	}
	
	/*******************/
	/** LoginGUI main **/
	/*******************/
	
	public static void main(String[] args){
		new LoginGUI("Welcome to SET: The Online Version").go();
	}
	
	/**********************/
	/** LoginGUI methods **/
	/**********************/
	
	public void InitGUI(){

		// Text fields
		userTxt = new JTextField(10);
		passwTxt = new JPasswordField(10);
		passwTxt.setEchoChar('*');
		
		// Buttons
		registerBtn = new JButton("Register");
		loginBtn = new JButton("Login");
		registerBtn.addActionListener(this);
		loginBtn.addActionListener(this);
		registerBtn.setPreferredSize(new Dimension(100, 30));
		loginBtn.setPreferredSize(new Dimension(100, 30));
		
		// Labels
		uname = new JLabel("Username: ", JLabel.RIGHT);
	    pwd = new JLabel("Password: ", JLabel.RIGHT);
	    //errmeg = new JLabel("Incorrect Username or Password!", JLabel.CENTER);
	    errmeg = new JLabel();
	    
		// Main panel
		p = new JPanel();
		p.setLayout(new BorderLayout());
		// CT.add(p);
				
		// Subpanels
		p0 = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		// Add components

		p0.add(errmeg);
		// p0.setVisible(false);
		
		p1.add(uname);
		p1.add(userTxt);
		
		p2.add(pwd);
		p2.add(passwTxt);

		p3.add(registerBtn);
		p3.add(loginBtn);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		CT.add(p,BorderLayout.NORTH);
		CT.add(p0, BorderLayout.SOUTH);
		
		// Logout and close client when closing the GUI
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				// Closing database connection
				//System.out.println("GameGUI: Closing database connection...");
				Logout(); // for debug, don't actually log out when you X out or you can just log out by entering name
				
				e.getWindow().dispose();

				// Closing client connection
				System.out.println("GameGUI: Closing client connection...");
				client.Close();
				//System.out.println("GameGUI: Closing client connection...");
				//System.exit(0);
			}
		});
	}
	

	// Display GUI
	public void go(){		
		setVisible(true);
		//setVisible(false);
	}
	
	// Clear fields for new login
	public void ClearTextFields(){
		userTxt.setText("");
		passwTxt.setText("");
		errmeg.setText("");
		//setVisible(true); // test
	}
	
	/*******************************/
	/** LoginGUI action listeners **/
	/*******************************/
	
	public void actionPerformed(ActionEvent e) {	
		
		if(e.getSource() == registerBtn) {
			try {
				// Open link to registration website
			    Desktop.getDesktop().browse(new URL("http://199.98.20.121:8080/Login1.3/index.jsp").toURI());
			} catch (Exception e1) {
				System.err.println("LoginGUI: Could not open registration link.");
			}
		}
		
		else if (e.getSource() == loginBtn) {
			
			//implement login function here!
			username = userTxt.getText();
			String password = passwTxt.getText();
			//System.out.println(password);
			
			if(!username.isEmpty() && !password.isEmpty()) {
				
				// Check connect/status with database
				con = null;
				
				try {
					
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					String url = "jdbc:mysql://199.98.20.121/TestDatabase";
					con = DriverManager.getConnection(url, "TDguest", "TDpass");
					st = con.createStatement();
					ResultSet rs;
					
					if (con != null) {
						System.out.println("Attempting to connect...");
					}
					
					rs = st.executeQuery("select * from members where uname='" + username + "' and pass='" + password + "'");
					if (rs.next()) {
				
						//check if logged in already
						int isLoggedIn = rs.getInt("LoggedIn");
						if (isLoggedIn == 0) {
							
							// Get player info from database
							userID = rs.getInt("id");
							numWins = rs.getInt("numWins");
							totalGames = rs.getInt("totalGames");
							score = rs.getInt("score");
							highscore = rs.getInt("highscore");
							
							System.out.println("UserID: " + userID);
							System.out.println("numWins: " + numWins);
							System.out.println("totalGames: " + totalGames);
							System.out.println("score: " + score);
							System.out.println("highscore: " + highscore);
							
							// Update login status in database
							st.executeUpdate("update members set LoggedIn='1' where uname='" + username + "'");
							st.executeUpdate("update members set InGame='0' where uname='" + username + "'");
							st.executeUpdate("update members set score='0' where uname='" + username + "'");
							
							//System.out.println(rs.getInt("LoggedIn") +" " +rs.getInt("InGame") +" "+ rs.getInt("score"));
							
							LoggedIn = true;
							InGame = false;
							
							errmeg.setText("Login successfully. Loading game lobby...");
							client.lobbyGUI.Clear();
							client.sendPacket.PlayerData(username, userID, numWins, totalGames, highscore);
							
						} else {
							errmeg.setText("This account is already logged in.");
						}
						
					} else {
						errmeg.setText("Invalid username or password.");
					}
					
				} catch (Exception e1) {
						System.err.println("LoginGUI: Exception: " + e1.getMessage());
				}
				
				
				/*
				finally {
						try {
							rs.close();
							st.close();
							if (con != null) {
								con.close();
							}
						} catch (SQLException e1) {
							System.err.println("LoginGUI: Could not close connection to database.");
						}
						
				}
				*/
			} else {
				// No inputs
				errmeg.setText("Please enter your username and password.");
			}
		}
	}
	
	/*********************/
	/** Update database **/
	/*********************/
	
	public void DatabaseResetScore() {

		try {
			ResultSet rs = st.executeQuery("select * from members where uname='" + username + "'");
			if (rs.next()) {

				// Update login status in database
				st.executeUpdate("update members set score='0' where uname='" + username + "'");
				System.out.println("LoginGUI: " + username + "'s score is reset.");
			}
			rs.close();
		} catch (Exception e1) {
			System.err.println("LoginGUI: Exception: " + e1.getMessage());
		}
	}
	
	
	public void DatabaseUpdateScore() {
		
		try {
			ResultSet rs = st.executeQuery("select * from members where uname='" + username + "'");
			if (rs.next()) {

				// Update login status in database
				st.executeUpdate("update members set score='" + score + "' where uname='" + username + "'");
				System.out.println("LoginGUI: " + username + "'s new score: " + score);
			}
			rs.close();
		} catch (Exception e1) {
			System.err.println("LoginGUI: Exception: " + e1.getMessage());
		}
	}
	
	public void DatabaseUpdateStats() {
		
		try {
			ResultSet rs = st.executeQuery("select * from members where uname='" + username + "'");
			if (rs.next()) {

				// Update login status in database
				st.executeUpdate("update members set numWins='" + numWins + "' where uname='" + username + "'");
				st.executeUpdate("update members set totalGames='" + totalGames + "' where uname='" + username + "'");
				st.executeUpdate("update members set highscore='" + highscore + "' where uname='" + username + "'");
				
				System.out.println("LoginGUI: " + username + "'s new numWins: " + numWins);
				System.out.println("LoginGUI: " + username + "'s new totalGames: " + totalGames);
				System.out.println("LoginGUI: " + username + "'s new highscore: " + highscore);
			}
			rs.close();
		} catch (Exception e1) {
			System.err.println("LoginGUI: Exception: " + e1.getMessage());
		}
	}
	
	// Logout
	public void Logout() {
		
		try {
			ResultSet rs = st.executeQuery("select * from members where uname='" + username + "'");
			if (rs.next()) {

				// Update login status in database
				st.executeUpdate("update members set LoggedIn='0' where uname='" + username + "'");
				st.executeUpdate("update members set score='0' where uname='" + username + "'");
				System.out.println(username + " logged out successfully.");

			}
			rs.close();
			st.close();
		} catch (Exception e1) {
			System.err.println("LoginGUI: Exception: " + e1.getMessage());
		} finally {
			try {
				if (client.loginGUI.con != null)
					client.loginGUI.con.close();
			} catch (SQLException e1) {
			}
		}
	}
	
}
