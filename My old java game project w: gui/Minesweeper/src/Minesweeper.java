import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Minesweeper extends JFrame  implements ActionListener{
	
	private Container CT;
	private JButton btn;
	private JButton[] btns;
	private JLabel b1;
    private int count;
	private JLabel b3;
	private Timer timer;
	private int row=9;
	private int col=9;
	private int bon=10;
	private int[][] a;
	private int b;
	private int[] a1;
	private JPanel p,p1,p2;
	
	public Minesweeper(String title){
		super(title);
		CT=getContentPane();
		setSize(297,377);
		this.setBounds(400, 100, 400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer =new Timer(1000,(ActionListener) this);
		a = new int[row+2][col+2];
		initGUI();	
	}
	
	public void initGUI(){
		b=bon;
		b1=new JLabel(bon+"");
		a1=new int[bon];
		btn =new JButton("Start");
		btn.addActionListener(this);
		b3=new JLabel("");
		btns=new JButton[row*col];
		p=new JPanel();
		p.setLayout(new BorderLayout());
		CT.add(p);
		p1=new JPanel();
		p1.add(b1);
		p1.add(btn);
		p1.add(b3);
		p.add(p1,BorderLayout.CENTER);
		p2=new JPanel();
		p2.setLayout(new GridLayout(row,col,0,0));
		for(int i=0;i<row*col;i++){
			btns[i]=new JButton("");
			btns[i].setMargin(new Insets(0,0,0,0));
			btns[i].setFont(new Font(null,Font.BOLD,25));
			btns[i].addActionListener(this);
		
			p2.add(btns[i]);
		}
		CT.add(p,BorderLayout.NORTH);
		CT.add(p2,BorderLayout.CENTER);		
	}
	public void go(){		
		setVisible(true);
	}
	public static void main(String[] args){
		new Minesweeper("Minesweeper").go();
		System.out.println("Michael's comment: ");
		System.out.println("Aaron's comment: it's very impressive that Angle could make this amazing game.  I have found no bugs at all.  Good work");
	}
	public void out(int[][] a,JButton[] btns,ActionEvent e,int i,int x,int y){
	       int p=1;
		   if(a[x][y]==0){
			     a[x][y]=10;
	        	 btns[i].setEnabled(false);	
	        	 //33 
	        	for(int l=y-1;l<=y+1;l++){
	        		 int m=x-1-1;
					 int n=l-1;
					 p=1;
					 if(n>-1&&n<col&&m>-1&&m<row)
					 {
					      for(int q=0;q<row&&p==1;q++){//col-->row;
						       if(((n+col*q)>=(m*col))&&((n+col*q)<(m+1)*col)){
							        if(a[x-1][l]!=0&&a[x-1][l]!=10){
							             btns[n+col*q].setText(a[x-1][l]+"");
							             a[x-1][l]=10;
							             btns[n+col*q].setEnabled(false);
							             
							        }
							        else if(a[x-1][l]==0){
							        	 //a[x-1][l]=10;
										 btns[n+col*q].setEnabled(false);
										 out(a,btns,e,n+col*q,x-1,l); ////55////
									     a[x-1][l]=10;
										 btns[n+col*q].setEnabled(false);
										 
							        }
						            p=0;
								
						      }
					     }
					 }
					 p=1;
					 m=x;
					 if(n>-1&&n<col&&m>-1&&m<col)
					 {
	        		 for(int q=0;q<row&&p==1;q++){
						 if(((n+col*q)>=(m*col))&&((n+col*q)<(m+1)*col)){
							 if(a[x+1][l]!=0&&a[x+1][l]!=10){
					             btns[n+col*q].setText(a[x+1][l]+"");
					             a[x+1][l]=10;
					             btns[n+col*q].setEnabled(false);
					             
					        }
					        else if(a[x+1][l]==0){

								out(a,btns,e,n+col*q,x+1,l);///55////						
							    a[x+1][l]=10;
							    btns[n+col*q].setEnabled(false);
							    
					        }
							 p=0;
						 }
					 }
					  
				   }
	        	 }
	      	   int m=x-1;
	        	   int n=y-1-1;
	        	   p=1;
	        	if(n>-1&&n<col&&m>-1&&m<col)
				{
	        	   for(int q=0;q<row&&p==1;q++){
						 if(((n+col*q)>=(m*col))&&((n+col*q)<(m+1)*col)){
							 if(a[x][y-1]!=0&&a[x][y-1]!=10){
					             btns[n+col*q].setText(a[x][y-1]+"");
					             a[x][y-1]=10;
					             btns[n+col*q].setEnabled(false);
					             
					        }
					        else if(a[x][y-1]==0){

						
								 out(a,btns,e,n+col*q,x,y-1);
								 
							 a[x][y-1]=10;
							    btns[n+col*q].setEnabled(false);
							    
					        }
							 p=0;
						 }
				   }
				}
	        	   p=1;
	        	   m=x-1;
	        	   n=y+1-1;
	        	if(n>-1&&n<col&&m>-1&&m<col)
			   {
	        	   for(int q=0;q<row&&p==1;q++){
						 if(((n+col*q)>=(m*col))&&((n+col*q)<(m+1)*col)){
							 if(a[x][y+1]!=0&&a[x][y+1]!=10){
					             btns[n+col*q].setText(a[x][y+1]+"");
					             a[x][y+1]=10;
					             btns[n+col*q].setEnabled(false);
					             
					        }
					        else if(a[x][y+1]==0){
								 out(a,btns,e,n+col*q,x,y+1);													 
							      a[x][y+1]=10;
								 btns[n+col*q].setEnabled(false);
								 
					        }
							 p=0;
						 }
				  }
	        }
		}
	 
}
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource()==btn){
			timer.start();
			b=bon;
			b3.setText("");
			count=0;
			for(int i=0;i<row*col;i++){
				btns[i].setText("");
				btns[i].setEnabled(true);
			}
			for(int i=0;i<row+2;i++){
				for(int j=0;j<col+2;j++){
					a[i][j]=0;
				}
			}
			for(int i=0;i<bon;i++)
			{   int p=1;
				int m=(int)(Math.random()*row*col);
				while(p==1){
				    int l=1;
				    int j;
					for( j=0;j<i&&l==1;j++){
					
					     if(a1[j]==m){
					    	  m=(int)(Math.random()*row*col); 
					    	  l=0;
					      }
					}
					if(j==i){
						a1[i]=m;
						p=0;
					}
				}				
			}
			b1.setText(bon+"");	
		    for(int i=0;i<bon;i++){	
			    int x=(a1[i]/col+1);
			    int y=(a1[i]%col+1);
				a[x][y]=100;
		   }
		   for(int i=0;i<row+2;i++){
			   for(int j=0;j<col+2;j++){
				   if(i==0||j==0||i==row+1||j==col+1){
				      a[i][j]=0; 	
				   }
			   }
		   }
		   for(int i=1;i<=row;i++){
			   for(int j=1;j<=col;j++){
				   if(a[i][j]!=100){
					   for(int l=j-1;l<=j+1;l++){
							  if(a[i-1][l]==100){
								   a[i][j]++;
							    }
							  if(a[i+1][l]==100){
								   a[i][j]++;
							  }
						   }
					   if(a[i][j-1]==100){
						   a[i][j]++;
					   }
					   if(a[i][j+1]==100){
						   a[i][j]++;
					   }	   
				      } 
			      }
		      } 	   
	     }
		
		for(int i=0;i<col*row;i++){
			 if(btns[i].getText()!="*")
			 {
		       int x=i/col+1;
		       int y=i%col+1;
		       if(e.getSource()==btns[i]&&a[x][y]==100){
			      btns[i].setText("*");
			      btns[i].setEnabled(false);
			      a[x][y]=10;
			      for(int k=0;k<col*row;k++){
		    	    	 int m1=k/col+1;
					       int n1=k%col+1;
		    	    	 if(a[m1][n1]!=10&&btns[k].getText()=="*"){
		    	    		 btns[k].setText("*o*");
		    	    	 }
		    	     }
			      for(int j=0;j<col*row;j++){
				       int m=j/col+1;
				       int n=j%col+1;
			    	   if(a[m][n]==100){
			    		 btns[j].setText("*");
			    	     btns[j].setEnabled(false);			    	   
			    	     b3.setText("YOU LOSE£¡£¡");
			    	 }
			    	 btns[j].setEnabled(false);
			    	 a[m][n]=10;
			      }
			      timer.stop();			    
		      }
		   else if(e.getSource()==btns[i]){
			       if(a[x][y]==0){
			        	out(a,btns,e,i,x,y);
			        	a[x][y]=10;
			        	btns[i].setEnabled(false);
			       }
			        if(a[x][y]!=0&&a[x][y]!=10){
			             btns[i].setText(a[x][y]+"");
			             btns[i].setEnabled(false);
			             a[x][y]=10;  
			        }
		    }
	    }
			 else if(btns[i].getText()=="*"){
	    }
	  }
		for(int d=1;d<10;d++){
			for(int f=1;f<10;f++){
				if (a[d][f]==10){
					count++;
				}
			}
		}
 	  if(count==71){
 		  timer.stop();
 		  b3.setText("YOU WIN£¡");
 	  }
 	  count=0;
	}
}

