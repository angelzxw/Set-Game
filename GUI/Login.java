import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Login extends JFrame  implements ActionListener{
	
	private Container CT;
	private JButton btn, btn2;
	private JLabel uname, pwd;
	private JTextField t1, t2;

	private JPanel p,p1,p2,p3;
	
	public Login(String title){
		super(title);
		CT=getContentPane();
		//setSize(100,300);
		this.setBounds(450, 300, 400, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer(1000,(ActionListener) this);
		initGUI();	
	}
	
	public void initGUI(){

		t1=new JTextField(6);
		t2=new JTextField(6);
		
		btn =new JButton("Register");
		btn2 =new JButton("Login");
		
		uname= new JLabel("Username: ", JLabel.RIGHT);
	    pwd = new JLabel("Password: ", JLabel.RIGHT);
		
	    btn.addActionListener(this);
		btn2.addActionListener(this);
		
		p=new JPanel();
		p.setLayout(new BorderLayout());
		CT.add(p);
		
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		
		p1.add(uname);
		p1.add(t1);
		
		p2.add(pwd);
		p2.add(t2);

		p3.add(btn);
		p3.add(btn2);

		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3,BorderLayout.SOUTH);
		
		CT.add(p,BorderLayout.NORTH);		
	}
	public void go(){		
		setVisible(true);
	}
	public static void main(String[] args){
		new Login("Welcome To Set Online").go();
	}

	public void actionPerformed(ActionEvent e) {	
		if(e.getSource()==btn){
		//link to registration website
			try {
			    Desktop.getDesktop().browse(new URL("http://199.98.20.118:8080/Login1.3/index.jsp").toURI());
			} catch (Exception e1) {}
		} else if (e.getSource()==btn){
			//implement login function here!
		}
	}
}


