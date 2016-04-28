/*
 *  Game.java
 *  
 *  Class that manages data associated with a game. 
 *  Contains the game room's host, host user ID, game name,
 *  deck, and board. Provides functionality to add/remove
 *  clients in a game, end a game, and perform gameplay functions, 
 *  such as dealing, replacing, and removing cards.
 *  
 */


package set.server.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import set.server.GameServerThread;

public class Game {

	/*****************/
	/** Game fields **/
	/*****************/
	
	public Stack<Card> Deck;
	public ArrayList<Card> CardList, Board;
	
	public GameServerThread host;
	public int hostUID;
	public String gameName;
	
	public List<GameServerThread> ClientsInGame = Collections.synchronizedList(new LinkedList<GameServerThread>());
	public GameServerThread winner;
	
	/**********************/
	/** Game constructor **/
	/**********************/
	
	public Game(GameServerThread host, String gameName) {
		
		// Get host user ID and name of game
		this.host = host;
		hostUID = host.GetUserID();
		this.gameName = gameName;
		
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
		//Deal(12);
	}
	
	// Constructor without arguments, for debugging
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
		//Deal(12);
	}
	
	/******************/
	/** Game methods **/
	/******************/
	
	/**********************/
	/** Gameplay-related **/
	/**********************/
	
	// Deal specified number of cards
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
	
	// Replace a card on the field with one from the deck
	public void ReplaceCard(Card card) {
		synchronized(Board) {
			if(!Deck.empty()) {
				Board.set(Board.indexOf(card), Deck.pop());
			}
			else {
				RemoveCard(card);
			}
		}
	}
	
	// Remove a card from the field
	public void RemoveCard(Card card) {
		synchronized(Board) {
			//Board.set(Board.indexOf(card), new Card(0,0,0,0));
			Board.remove(Board.indexOf(card));
		}
	}
	
	// Return number of cards left in deck
	public int CardsInDeck() {
		return Deck.size();
	}
	
	// Return number of cards on field
	public int CardsOnField() {
		return Board.size();
		}
	
	// Return string representing board's cards
	public String boardString() {
		String str = "";
		for(int i = 0; i < CardsOnField(); i++) {
			str += Board.get(i).toString();
		}
		return str;
	}
	
	/************************/
	/** Management-related **/
	/************************/
	
	// Get player in game from user ID
	public GameServerThread GetPlayer(int userID) {
		synchronized(ClientsInGame) {
			for(Iterator<GameServerThread> clientItr = ClientsInGame.listIterator(); clientItr.hasNext();) {
				GameServerThread client = clientItr.next();
				if(client.GetUserID() == userID) {
					return client;
				}
			}
		}
		return null;
	}
	
	// Add client to game
	public void AddPlayer(GameServerThread client) {
		synchronized(ClientsInGame) {
			// check if player is already in lobby 
			if(GetPlayer(client.GetUserID()) == null) {
				ClientsInGame.add(client);
			}
		}
	}
	
	// Remove client from game
	public void RemovePlayer(GameServerThread targetClient) {
		synchronized(ClientsInGame) {
			for(Iterator<GameServerThread> clientItr = ClientsInGame.listIterator(); clientItr.hasNext();) {
				GameServerThread client = clientItr.next();
				if(client == targetClient) {
					//ClientsInLobby.remove(client);
					clientItr.remove();
				}
			}
		}
	}
	
	// End game and remove all clients in the game
	public void EndGame() {
		synchronized(ClientsInGame) {
			
			// get the winner from the game
			int highestScore = 0;
			for(Iterator<GameServerThread> clientItr = ClientsInGame.listIterator(); clientItr.hasNext();) {
				GameServerThread client = clientItr.next();
				int clientScore = client.GetScore();
				if(clientScore > highestScore) {
					winner = client;
				}
			}
			
			// update stats for all players
			for(Iterator<GameServerThread> clientItr = ClientsInGame.listIterator(); clientItr.hasNext();) {
				GameServerThread client = clientItr.next();
				
				client.IncreaseTotalGameCount();
				
				if(client == winner) {
					client.IncreaseWinCount();
				}
				
				// update highscore if score is greater than current highscore
				int clientScore = client.GetScore();
				if(clientScore > client.GetHighScore()) {
					client.UpdateHighScore(clientScore);
				}
					
				client.SaveStats(); // save statistics associated with client
				clientItr.remove(); // remove all clients from game
				client.AddPlayerToLobby(); // add clients back to lobby
				
			}
		}
		
	}
	
}