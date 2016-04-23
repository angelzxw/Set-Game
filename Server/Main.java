package game.set;

public class Main {
	
	// Main
	public static void main(String[] args) {
		
		Game NewGame = new Game();
		Logic NewLogic = new Logic();
		System.out.println(NewGame.Board.toString());
		
		// Debugging Game (card mechanics only)
		/*
		Card card1 = new Card(3,2,3,3);
		Card card2 = new Card(1,1,1,1);
		//System.out.println(card1.equals(card2));
		//System.out.println(NewGame.Board.get(0));
		//System.out.println(card1.equals(NewGame.Board.get(0)));
		//System.out.println(NewGame.Board.contains(card1));
		System.out.println(NewGame.Board.indexOf(card1));
		//NewGame.Remove(card1);
		NewGame.Replace(card1,card2);
		System.out.println(NewGame.toString());
		System.out.println(NewGame.Board.get(9));
		*/
		
		// Debugging Logic
		System.out.println("Total # of sets: " + NewLogic.CompleteSetCount);
				
		return;
	}

}
