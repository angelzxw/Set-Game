import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class Login extends JFrame  implements ActionListener{
	
	private Container CT;
	private JButton btn, btn2;
	private JLabel uname, pwd, errmeg;
	private JTextField t1, t2;
	private JPanel p, p0, p1, p2, p3;
	
	public Login(String title){
		super(title);
		CT=getContentPane();
		//setSize(100,300);
		this.setBounds(450, 300, 400, 160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer(1000,(ActionListener) this);
		initGUI();	
	}
	
	public void initGUI(){

		t1=new JTextField(6);
		t2=new JTextField(6);
		
		btn =new JButton("Register");
		btn2 =new JButton("Login");
		btn.addActionListener(this);
		btn2.addActionListener(this);
		btn.setPreferredSize(new Dimension(100, 30));
		btn2.setPreferredSize(new Dimension(100, 30));
		
		uname= new JLabel("Username: ", JLabel.RIGHT);
	    pwd = new JLabel("Password: ", JLabel.RIGHT);
	    //errmeg = new JLabel("Incorrect Username or Password!", JLabel.CENTER);
	    errmeg = new JLabel();
	    
		p=new JPanel();
		p.setLayout(new BorderLayout());
		//CT.add(p);
				
		p0=new JPanel();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		
		p0.add(errmeg);
		//p0.setVisible(false);
		
		p1.add(uname);
		p1.add(t1);
		
		p2.add(pwd);
		p2.add(t2);

		p3.add(btn);
		p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		CT.add(p,BorderLayout.NORTH);
		CT.add(p0, BorderLayout.SOUTH);
	}
	public void go(){		
		setVisible(true);
	}
	public static void main(String[] args){
		new Login("Welcome To Set Online").go();
	}

	public void actionPerformed(ActionEvent e) {	
		if(e.getSource()==btn){
			try {
				//link to registration website
			    Desktop.getDesktop().browse(new URL("http://199.98.20.118:8080/Login1.3/index.jsp").toURI());
			} catch (Exception e1) {}
		} else if (e.getSource()==btn2){
			//implement login function here!
			String uname = t1.getText();
			String pwd = t2.getText();
			if(!uname.isEmpty() && !pwd.isEmpty()){
				//check connect/status with database
				if (true){
					errmeg.setText("Loading The Game Lobby...");
				}else{
					errmeg.setText("Incorrect Username or Password");
				}
			}else{
				//login failed already
				errmeg.setText("Please Enter Your Username and Password");
			}
			
		}
	}
}


