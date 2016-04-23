
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

				Player A = new Player();// TODO Auto-generated method stub
				Position Computer = new Position();
		        
				System.out.println("Welcome to Battleship!");
				System.out.println("Please place your three ships.");
				
				A.PlayerBoard();
				A.PlayerDisplay();
				Computer.CB();
				Computer.ComputerDisplay();
				
				A.placeship();
				A.PlayerDisplay();
				Computer.placeship();
				Computer.ComputerDisplay();
				
				for (int i=0;i<=9999;i++)
				{
					A.Attack();
				    Computer.Attack();
		            if( A.IsDestoryed()==true )
		            {
		                
		                System.out.println("Computer Win!");

		                break;
		            }

					if( Computer.IsDestoryed()==true )
		            {
		                
		                System.out.println("Player Win!");

		                break;
		            }
				}
		    }

		
		
	}
