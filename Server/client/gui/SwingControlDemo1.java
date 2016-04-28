package set.gui;
import set.game.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class SwingControlDemo {
    
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JPanel controlPanel2;
   protected ArrayList<Card> selectedCards;

   /*****************/
   /** Game fields **/
   /*****************/
   
   public SwingControlDemo(){
      prepareGUI();
   }

   public static void main(String[] args){
      SwingControlDemo swingControlDemo = new SwingControlDemo();      
      swingControlDemo.showButtonDemo();
      
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Java Swing");
      mainFrame.setSize(800,700);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setLayout(new GridLayout(4, 4));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    

      statusLabel.setSize(350,100);

      controlPanel = new JPanel(new GridLayout(3,3,1,1));
      controlPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
      controlPanel.setLayout(new FlowLayout());
      //controlPanel.setBackground(Color.WHITE);
      controlPanel.setSize(300,300);
      GridLayout layout = new GridLayout(3,4);
      
      controlPanel2 = new JPanel(new GridLayout(1,1,1,1));
      controlPanel2.setBorder(new EmptyBorder(1, 1, 1, 1));
      controlPanel2.setLayout(new FlowLayout());
      //controlPanel2.setBackground(Color.darkGray);
      //controlPanel2.setSize(10,10);
      GridLayout layout2 = new GridLayout(1,1);
      
      controlPanel.setLayout(layout); 
      controlPanel2.setLayout(layout2); 

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(controlPanel2);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }
    
   private static ImageIcon createImageIcon(String path, 
      String description) {
      java.net.URL imgURL = SwingControlDemo.class.getResource(path);
      if (imgURL != null) {
         return new ImageIcon(imgURL, description);
      } else {            
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }   

   private void showButtonDemo(){

	  Game NewGame = new Game();
	  String cardIconPathTest = "src/set/gui/Cards/1112.gif";
	  String cardIconPath1 = "src/set/gui/Cards/" + NewGame.Board.get(0).toString() + ".gif";
	  String cardIconPath2 = "src/set/gui/Cards/" + NewGame.Board.get(1).toString() + ".gif";
	  String cardIconPath3 = "src/set/gui/Cards/" + NewGame.Board.get(2).toString() + ".gif";
	  String cardIconPath4 = "src/set/gui/Cards/" + NewGame.Board.get(3).toString() + ".gif";
	  String cardIconPath5 = "src/set/gui/Cards/" + NewGame.Board.get(4).toString() + ".gif";
	  String cardIconPath6 = "src/set/gui/Cards/" + NewGame.Board.get(5).toString() + ".gif";
	  String cardIconPath7 = "src/set/gui/Cards/" + NewGame.Board.get(6).toString() + ".gif";
	  String cardIconPath8 = "src/set/gui/Cards/" + NewGame.Board.get(7).toString() + ".gif";
	  String cardIconPath9 = "src/set/gui/Cards/" + NewGame.Board.get(8).toString() + ".gif";
	  String cardIconPath10 = "src/set/gui/Cards/" + NewGame.Board.get(9).toString() + ".gif";
	  String cardIconPath11 = "src/set/gui/Cards/" + NewGame.Board.get(10).toString() + ".gif";
	  String cardIconPath12 = "src/set/gui/Cards/" + NewGame.Board.get(11).toString() + ".gif";
	  
	  System.out.println(cardIconPath1);
	  
	  
      headerLabel.setText("SET: The Online Version"); 
      JButton submitButton = new JButton("Submit Set");
      
      JToggleButton Card1 = new JToggleButton(new ImageIcon(cardIconPath1)); 
      Card1.setPreferredSize(new Dimension(95, 65));
      Card1.setBackground(Color.WHITE);
      
      JToggleButton Card2 = new JToggleButton(new ImageIcon(cardIconPath2));
      Card2.setPreferredSize(new Dimension(95, 65));
      Card2.setBackground(new Color(155, 155, 155));
      Card2.setBackground(Color.WHITE);
      
      JToggleButton Card3 = new JToggleButton(new ImageIcon(cardIconPath3));
      Card3.setPreferredSize(new Dimension(95, 65));
      Card3.setBackground(new Color(155, 155, 155));
      Card3.setBackground(Color.WHITE);
      
      JToggleButton Card4 = new JToggleButton(new ImageIcon(cardIconPath4)); 
      Card4.setPreferredSize(new Dimension(95, 65));
      Card4.setBackground(new Color(155, 155, 155));
      Card4.setBackground(Color.WHITE);
      
      JToggleButton Card5 = new JToggleButton(new ImageIcon(cardIconPath5));
      Card5.setPreferredSize(new Dimension(95, 65));
      Card5.setBackground(new Color(155, 155, 155));
      Card5.setBackground(Color.WHITE);
      
      JToggleButton Card6 = new JToggleButton(new ImageIcon(cardIconPath6));
      Card6.setPreferredSize(new Dimension(95, 65));
      Card6.setBackground(new Color(155, 155, 155));
      Card6.setBackground(Color.WHITE);
      
      JToggleButton Card7 = new JToggleButton(new ImageIcon(cardIconPath7)); 
      Card7.setPreferredSize(new Dimension(95, 65));
      Card7.setBackground(new Color(155, 155, 155));
      Card7.setBackground(Color.WHITE);
      
      JToggleButton Card8 = new JToggleButton(new ImageIcon(cardIconPath8));
      Card8.setPreferredSize(new Dimension(95, 65));
      Card8.setBackground(new Color(155, 155, 155));
      Card8.setBackground(Color.WHITE);
      
      JToggleButton Card9 = new JToggleButton(new ImageIcon(cardIconPath9));
      Card9.setPreferredSize(new Dimension(95, 65));
      Card9.setBackground(new Color(155, 155, 155));
      Card9.setBackground(Color.WHITE);
      
      JToggleButton Card10 = new JToggleButton(new ImageIcon(cardIconPath10)); 
      Card10.setPreferredSize(new Dimension(95, 65));
      Card10.setBackground(new Color(155, 155, 155));
      Card10.setBackground(Color.WHITE);
      
      JToggleButton Card11 = new JToggleButton(new ImageIcon(cardIconPath11));
      Card11.setPreferredSize(new Dimension(95, 65));
      Card11.setBackground(new Color(155, 155, 155));
      Card11.setBackground(Color.WHITE);
      
      JToggleButton Card12 = new JToggleButton(new ImageIcon(cardIconPath12));
      Card12.setPreferredSize(new Dimension(95, 65));
      Card12.setBackground(new Color(155, 155, 155));
      Card12.setBackground(Color.WHITE);

      Card1.setHorizontalTextPosition(SwingConstants.LEFT);   
      
      Card1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             JToggleButton Card1 = (JToggleButton)e.getSource();
             if (Card1.isSelected()) {
            	 Card1.setBorder(new LineBorder(Color.RED,5));
             } else {
            	 Card1.setBorder(null);
             }
          }
       });
      
      Card2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Button clicked.");
         }          
      });

      Card3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Button clicked.");
         }
      });
      
      Card4.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      
      Card5.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      Card6.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      Card7.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      Card8.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      Card9.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      Card10.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Button clicked.");
          }
       });
      Card11.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             JToggleButton Card11 = (JToggleButton)e.getSource();
             if (Card11.isSelected()) {
            	 statusLabel.setText("Button clicked.");
             } else {
            	 statusLabel.setText("Button unclicked.");
             }
          }
       });
      
      Card12.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             JToggleButton Card12 = (JToggleButton)e.getSource();
             if (Card12.isSelected()) {
            	 Card12.setBorder(new LineBorder(Color.RED,5));
             } else {
            	 Card12.setBorder(null);
             }
          }
       });

      
      controlPanel.add(Card1);
      controlPanel.add(Card2);
      controlPanel.add(Card3);   
      controlPanel.add(Card4);
      controlPanel.add(Card5);
      controlPanel.add(Card6);
      controlPanel.add(Card7);
      controlPanel.add(Card8);
      controlPanel.add(Card9);
      controlPanel.add(Card10);
      controlPanel.add(Card11);
      controlPanel.add(Card12);
      controlPanel2.add(submitButton);
      mainFrame.setVisible(true);  
   }
   //controlPanel2.add(submitButton);
}