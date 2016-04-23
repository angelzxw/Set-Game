import java.util.*;

public class Position {

    public int CT;       //count

	char ComputerBoard[][]= new char[10][10];

	public void ComputerBoard()
	{
		for (int r=0; r<10;r++)        
		{
			for (int c=0; c<10;c++)
			{
				ComputerBoard[r][c]='*';
			}
        }

        CT = 0;
	}

	public int Count;
	char CB[][]= new char[10][10];
    public void CB()
    {

	for (int r=0; r<10;r++)        
		{
			for (int c=0; c<10;c++)
			{
				CB[r][c]='*';
			}
        }
        Count = 0;
    }
        
    public void ComputerDisplay()
        {
        System.out.print(" ");
		System.out.print(" ");
		System.out.println("Computer Board");
		for (int r=0; r<10;r++)
		{
			for (int c=0; c<10;c++)
			{
				System.out.print(" "+ CB[r][c]);
			}
			System.out.println("");
		}
    }   

	

	public void placeship()
	{
		int r;
		int c;
            Scanner input = new Scanner (System.in);
            Random RandomB= new Random();
            int direction = RandomB.nextInt(1);
            if (direction==0)
            {   
            	 System.out.println("x:");
                 Random RandomR = new Random();
                 r=RandomR.nextInt(9);
                 System.out.println("y:");
                 Random RandomC = new Random();
                 c=RandomC.nextInt(9);
                 ComputerBoard[c][r]='S';
            }
            else if (direction==1)
            {
            	 System.out.println("x:");
                 Random RandomR = new Random();
                 r=RandomR.nextInt(9);
                 System.out.println("y:");
                 Random RandomC = new Random();
                 c=RandomC.nextInt(9);
                 ComputerBoard[c][r]='S';
            }
            	
            Random RandomE= new Random();    
            direction = RandomE.nextInt(1);
            if (direction==0)
            {
            	System.out.println("x(1-9):");
                Random RandomR = new Random();
                r=RandomR.nextInt(8);
                System.out.println("y(1-10):");
                Random RandomC = new Random();
                c=RandomC.nextInt(9);
                ComputerBoard[c][r]='S';
                ComputerBoard[c][r+1]='S';
            }
            else if (direction==1)
            {
            	System.out.println("x(1-9):");
                Random RandomR = new Random();
                r=RandomR.nextInt(9);
                System.out.println("y(1-10):");
                Random RandomC = new Random();
                c=RandomC.nextInt(8);
                ComputerBoard[c][r]='S';
                ComputerBoard[c][r+1]='S';
            }
            
            
        
            Random RandomF= new Random();    
            direction = RandomF.nextInt(1);
            if (direction==0)
            {
            	System.out.println("x(1-8):");
                Random RandomR = new Random();
                r=RandomR.nextInt(7);
                System.out.println("y(1-10):");
                Random RandomC = new Random();
                c=RandomC.nextInt(9);
                ComputerBoard[c][r]='S';
                ComputerBoard[c][r+1]='S';
                ComputerBoard[c][r+2]='S';
            }
            else if (direction==1)
            {
            	System.out.println("x(1-8):");
                Random RandomR = new Random();
                r=RandomR.nextInt(9);
                System.out.println("y(1-10):");
                Random RandomC = new Random();
                c=RandomC.nextInt(7);
                ComputerBoard[c][r]='S';
                ComputerBoard[c][r+1]='S';
                ComputerBoard[c][r+2]='S';
            }
        }
            	
               
	
	
	public void Attack()
	{
		Scanner input=new Scanner (System.in);
		System.out.println(" ");
		System.out.println("Player Attack");
		System.out.println("Please enter the x from 1 to 10:");
		int c = input.nextInt()-1;
		System.out.println("Please enter the y from 1 to 10:");
		int r = input.nextInt()-1;
		
		if (ComputerBoard[r][c]=='M' ||ComputerBoard[r][c]=='H')
		{   
			System.out.println("Already hit this place.");
			ComputerDisplay();
		}
		else if (ComputerBoard[r][c]=='S')
		{
			System.out.println("Player Hit");
			ComputerBoard[r][c]='H';
			CB[r][c]='H';
			ComputerDisplay();
		}
		else
		{
			System.out.println("Player Miss");
			ComputerBoard[r][c]='M';
			CB[r][c]='M';
			ComputerDisplay();
			
        }
		CT++;
        System.out.println("Attack Count: "+CT);    
        }    

	 
	 
	 
	 boolean IsDestoryed()
	    {
	        int nS = 0;
	        for (int r=0; r<10;r++)
			{
				for (int c=0; c<10;c++)
				{
	                if( ComputerBoard[r][c]=='S' )   
	                nS++;
				}
	        }
	        if( nS > 0 )    return false;       
	        else            return true;        
	    }
	}
      
    


