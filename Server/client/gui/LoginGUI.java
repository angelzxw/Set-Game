package set.client.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI {
	
	/**************************/
	/** LoginGUI Constructor **/
	/**************************/
	
	public LoginGUI() {
		prepareGUI();
	}
	
	/**********************/
	/** LoginGUI methods **/
	/**********************/
	
	private void prepareGUI() {
		
		JFrame frame = new JFrame("SET: The Online Version");
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
	}

	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(180, 80, 80, 25);
		panel.add(registerButton);
	}
	

	/*******************/
	/** LoginGUI main **/
	/*******************/

	public static void main(String[] args) {
		LoginGUI demo = new LoginGUI();
		demo.prepareGUI();
	}
}