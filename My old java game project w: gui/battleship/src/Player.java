import java.util.*;
public class Player {
		    public int CT;       
			char PlayerBoard[][]= new char[10][11];

			public void PlayerBoard()
			{
			 for (int r=0; r<10;r++)
				{
					for (int c=0; c<10;c++)
					{
						PlayerBoard[r][c]='*';
					}
		        }

		        CT = 0;
		    }

			public void PlayerDisplay()     
			{
				System.out.println("");   
				System.out.println("Player Board");
				System.out.println(" ");
				for (int r=0; r<10;r++)
				{

					for (int c=0; c<10;c++)
					{
						System.out.print(" "+ PlayerBoard[r][c]);
					}
					System.out.println("");
				}
			}

			public void placeship()         
			{
				int r;
				int c;

		            Scanner input = new Scanner (System.in);
		    System.out.println("Please place your Submarine-1 spot");
		            System.out.println("Please enter your ship direction, Horizontal (0) or Vertical (1)");
		            int direction = input.nextInt();
		            if (direction==0)          
		            {
		                System.out.println("Please place your Submarine-1 spot");
		                System.out.println("x(1-10):");
		                r=input.nextInt()-1;
		                System.out.println("y(1-10):");
		                c=input.nextInt()-1;
		                PlayerBoard[c][r]='P';
		                PlayerDisplay();}
		            else if (direction==1)     
		            {System.out.println("x:");
                    r=input.nextInt()-1;
                    System.out.println("y:");
                    c=input.nextInt()-1;
                    PlayerBoard[c][r]='S';
                    PlayerDisplay();}
		            
		                
		    System.out.println("Please place your Destroyer-2 spots");         
            System.out.println("Please enter your ship direction, Horizontal (0) or Vertical (1)");
            direction = input.nextInt();
            if (direction==0)          
            {      	
		                    System.out.println("x(1-9):");
		                    r=input.nextInt()-1;
		                    System.out.println("y(1-10):");
		                    c=input.nextInt()-1;
		                    PlayerBoard[c][r]='P';
		                    PlayerBoard[c][r+1]='P';
		                    PlayerDisplay();}
            else if (direction==1)
            {
            	 System.out.println("x(1-10):");
                 r=input.nextInt()-1;
                 System.out.println("y(1-9):");
                 c=input.nextInt()-1;
                 PlayerBoard[c][r]='P';
                 PlayerBoard[c+1][r]='P';
                 PlayerDisplay();
            }
           
            System.out.println("Please place your Battleship-3 spots");       
            System.out.println("Please enter your ship direction, Horizontal (0) or Vertical (1)");
            direction = input.nextInt();
            if (direction==0)          
            {      	
		                    System.out.println("x(1-8):");
		                    r=input.nextInt()-1;
		                    System.out.println("y(1-10):");
		                    c=input.nextInt()-1;
		                    PlayerBoard[c][r]='P';
		                    PlayerBoard[c][r+1]='P';
		                    PlayerBoard[c][r+2]='P';
		                    PlayerDisplay();
		                }
		    else if (direction==1) 
		                {
		            	
		                        System.out.println("x(1-10):");
		                        r=input.nextInt()-1;
		                        System.out.println("y(1-8):");
		                        c=input.nextInt()-1;
		                        PlayerBoard[c][r]='P';
		                        PlayerBoard[c+1][r]='P';
		                        PlayerBoard[c+2][r]='P';
		                        PlayerDisplay();
		                }
		                }

		    
		    public void Attack()
		    {
		        Scanner input=new Scanner (System.in);
				System.out.println(" ");
		        System.out.println("Computer Attack");
		        System.out.println("Please enter the x from 1 to 10:");
		        Random RandomZ = new Random();
		        int c = RandomZ.nextInt(9);
		        System.out.println("Please enter the y from 1 to 10:");
		        Random RandomX = new Random();
		        int r = RandomX.nextInt(9);
		        if (PlayerBoard[r][c]=='M' ||PlayerBoard[r][c]=='H')
		        {
		            System.out.println("Already hit this place.");
		            PlayerDisplay();

		        }
		        else if (PlayerBoard[r][c]=='P')
		        {
		            System.out.println("Computer Hit");
		            PlayerBoard[r][c]='H';
		            PlayerDisplay();
		        }
		        else
		        {
		            System.out.println("Computer Miss");
		            PlayerBoard[r][c]='M';
		            PlayerDisplay();
		        }
		        CT++;
		        System.out.println("Attack Count: "+ CT);  
		        }
		    
		    
		    public int Count1()
			 {
				 return  CT;
			 }
		    
		    boolean IsDestoryed()
		    {
		        int nP = 0;

		        for (int r=0; r<10;r++)
				{
					for (int c=0; c<10;c++)
					{
		                if( PlayerBoard[r][c]=='P' )   nP++;
					}
		        }

		        if( nP > 0 )    return false;       
		        else            return true;        
		    }
		    
		    
		    
		    }