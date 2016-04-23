package game.set;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

public class Game {

	// Game fields
	protected Stack<Card> Deck;
	protected ArrayList<Card> CardList, Board;
	
	// Game constructor
	public Game() {
		
		// Instantiate deck and playing field
		Deck = new Stack<Card>();
		Board = new ArrayList<Card>();
		
		// Fill up deck
		for(int numType = 1; numType <= 3; numType++) {
			for(int symType = 1; symType <= 3; symType++) {
				for(int shadType = 1; shadType <= 3; shadType++) {
					for(int colType = 1; colType <= 3; colType++) {
						Deck.push(new Card(numType, symType, shadType, colType));
					}
				}
			}
		}
		
		// Shuffle deck
		Collections.shuffle(Deck);
		
		// Deal 12 cards
		Deal(12);
	}
	 
	// Game methods
	public void Deal(int numCards) {
		// need to synchronize to make sure no two changes 
		// are happening to board simultaneously
		synchronized(Board) {
			// Should we have max # of cards?
			// Deal only if deck is not empty
			for(int i = 1; i <= numCards; i++) {
				if(!Deck.empty()) {
					Board.add(Deck.pop());
				}
			}
		}
	}
	
	public void Remove(Card card) {
		synchronized(Board) {
			int cardIndex = Board.indexOf(card);
			if(cardIndex != -1) {
				Board.set(Board.indexOf(card), new Card(0,0,0,0));
			}
		}
	}
	
	public void Replace(Card oldCard, Card newCard) {
		synchronized(Board) {
			int oldCardIndex = Board.indexOf(oldCard);
			if(oldCardIndex != -1) {
				Board.set(oldCardIndex, newCard);
			}
		}
	}
	
	// Game toString
	public String toString() {
		//return Deck.toString();
		return Board.toString();
	}
}