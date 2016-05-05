/*
 *  GameServerThread.java
 *  
 *  Class that implements each server thread to
 *  handle an individual client in this multiuser
 *  system. Contains the client's socket, input and
 *  output streams to transmit packets to and from the
 *  server, and the player associated with this thread.
 *  Provides functionality to retrieve player data,
 *  add/remove players from game areas, send and parse packets.
 *  Contains a run function that executes when the thread
 *  starts and continuously manages packet transmissions.
 *  
 */

package set.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import set.packet.DataReader;
import set.server.game.Card;
import set.server.game.Game;
import set.server.game.GameEngine;
import set.server.game.GamePlayer;
import set.server.game.GameRoom;

// Note: This class will be referred to as the "client",
// since each server thread manages a client connection.
public class GameServerThread extends Thread {

	/*****************************/
	/** GameServerThread fields **/
	/*****************************/

	private Socket clientSocket;
	private DataInputStream fromClient;
	private DataOutputStream toClient;
	public ServerPacketGenerator sendPacket;
	private boolean ClientOn = true;
	
	private GamePlayer player;

	/**********************************/
	/** GameServerThread constructor **/
	/**********************************/

	public GameServerThread(Socket cSocket) {
		super(); // call Thread constructor
		clientSocket = cSocket;
		player = new GamePlayer(this);
	}
	
	/**************************/
	/** GameServerThread run **/
	/**************************/

	public void run() {
		
		System.out.println("GameServerThread: Client is running!");
		
		try {
			fromClient = new DataInputStream(clientSocket.getInputStream());
			toClient = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("GameServerThread: Error opening I/O stream ");
		}
		
		sendPacket = new ServerPacketGenerator(toClient);
		//TestingWithoutDatabase(this);
		
		while(ClientOn) {
			try {
				int availableBytes = fromClient.available();
				if(availableBytes > 0) {
					ParsePacket(fromClient);
				}
			} catch (IOException e) {
				System.err.println("GameServerThread: Error parsing packet.");
			}
		}
	}
	
	/******************************/
	/** GameServerThread methods **/
	/******************************/
	
	/********************/
	/** Player-related **/
	/********************/
	
	// Get client of the player
	public GameServerThread GetClient() {
		return this;
	}
	
	// Get user ID of player associated with client
	public int GetUserID() {
		return player.userID;
	}
	
	// Set user ID  of player(for debugging only, no database)
	public void SetUserID(int userID) {
		player.userID = userID;
	}
	
	// Get username of player associated with client
	public String GetUsername() {
		return player.username;
	}
	
	// Set username of player (for debugging only, no database)
	public void SetUsername(String username) {
		player.username = username;
	}

	// Set score of player
	public void SetScore(int score) {
		player.score = score;
	}
	
	// Increase score of player
	public void IncreaseScore(int incr) {
		player.score += incr;
	}
	
	// Decrease score of player
	public void DecreaseScore(int decr) {
		player.score -= decr;
	}
	
	// Get game that player is in
	public Game GetGame() {
		return player.game;
	}
	
	// Get game room that player is in
	public GameRoom GetGameRoom() {
		return player.gameRoom;
	}
	
	// Get name of game that player is in
	public String GetGameName() {
		return player.game.gameName;
	}

	// Get name of game room that player is in
	public String GetGameRoomName() {
		return player.gameRoom.gameName;
	}

	// Get host name of game room that player is in
	public String GetHostName() {
		return player.gameRoom.hostName;
	}
	
	// Get size of game room that player is in (0 when game has started)
	public int GetGameRoomSize() {
		//if(player.gameRoom == null) {
			//return 0;
		//}
		return player.gameRoom.ClientsInGameRoom.size();
	}
	
	// Get size of game that player is in (0 when game hasn't started)
	public int GetGameSize() {
		if(player.game == null) {
			return 0;
		}
		return player.game.ClientsInGame.size();
	}
	
	// Get score of player associated with client
	public int GetScore() {
		return player.score;
	}
	
	// Get highscore of player associated with client
	public int GetHighScore() {
		return player.highScore;
	}
	
	// Get wins of player associated with client
	public int GetWinCount() {
		return player.numWins;
	}
	
	// Get total play count of player associated with client
	public int GetTotalGameCount() {
		return player.numTotalGames;
	}
	
	// Increase player's number of wins
	public void IncreaseWinCount() {
		player.numWins++;
	}
	
	// Increase player's total number of games
	public void IncreaseTotalGameCount() {
		player.numTotalGames++;
	}
	
	// Update player's high score
	public void UpdateHighScore(int newHS) {
		player.highScore = newHS;
	}
	
	// Save player's stats
	public void SaveStats() {
		player.SaveStats();
	}
	
	// Add player to lobby
	public void AddPlayerToLobby() {
		player.EnterGameLobby();
		//send packet (player has entered lobby)
	}
	
	// Remove player from game room
	public void RemovePlayerFromGameRoom() {
		player.ExitGameRoom();
		player.EnterGameLobby();
		//send packet
	}
	
	// Add client to game
	public void AddPlayerToGame() {
		player.JoinGame(player.gameRoom.hostName);
		//send packet
	}
	
	// Remove player from game
	public void RemovePlayerFromGame() {
		player.ExitGame();
		player.EnterGameLobby();
		//send packet
	}

	/********************/
	/** Client-related **/
	/********************/
	
	// Close client
	public void Close() {
		ClientOn = false;
		System.out.println("GameServerThread: Client closed.");
	}
	
	/********************/
	/** Packet-related **/
	/********************/
	
	// Parse packets sent from client to server
	public void ParsePacket(DataInputStream packet) {
		
		// Initialize variables for use
		
		int card1Pos = 0;
		int card2Pos = 0;
		int card3Pos = 0;
		int boardSize = 0;
		
		String cardString = "";
		String gameName = "";
		String hostName = "";
		
		boolean setFound = false;
		boolean noMoreSets = false;
		
		Card card1 = null;
		Card card2 = null;
		Card card3 = null;
		Card newCard1 = null;
		Card newCard2 = null;
		Card newCard3 = null;
		
		// Get username associated with this client
		String myUsername = this.GetUsername();
		
		// Read header and determine packet type
		DataReader dataReader = new DataReader();
		short header = dataReader.ReadShort(packet);
		
		switch(header) {
		
		/*** Begin communication ***/
		case 0:
			
			/*** Get player data from database ***/
			player.username = dataReader.ReadString(packet);
			player.userID = dataReader.ReadInt(packet);
			player.numWins = dataReader.ReadInt(packet);
			player.numTotalGames = dataReader.ReadInt(packet);
			player.highScore = dataReader.ReadInt(packet);
			
			/*** Load lobby for each client ***/
				
			// Load players in lobby
			for (Iterator<GameServerThread> clientItr = GameEngine.gameLobby.ClientsInLobby.listIterator(); clientItr
					.hasNext();) {
				GameServerThread clientInLobby = clientItr.next();
				sendPacket.LobbyUpdateAddPlayer(clientInLobby.GetUsername());
			}
			
			// Load games in lobby
			for (Iterator<GameRoom> gameRoomItr = GameEngine.gameLobby.GameRoomsInLobby.listIterator(); gameRoomItr
					.hasNext();) {
				GameRoom gameRoomInLobby = gameRoomItr.next();
				sendPacket.LobbyUpdateAddGame(gameRoomInLobby.gameName, gameRoomInLobby.hostName);
			}

			// Add player to lobby
			AddPlayerToLobby();
			sendPacket.GoToLobbyFromLogin();
			
			// Broadcast to all players when player enters lobby
			// after loading existing lobby data (or duplicate will happen)
			sendPacket.ChatMessageInLobby("[Lobby Message]: Welcome to SET: Online, " + player.username + ".");
			GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + player.username + " has entered the lobby.");
			GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(player.username);
			
			System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Username: " + player.username);
			System.out.println("UserID: " + player.userID);
			System.out.println("numWins: " + player.numWins);
			System.out.println("totalGames: " + player.numTotalGames);
			System.out.println("score: " + player.score);
			System.out.println("highscore: " + player.highScore);
			System.out.println("GameServer: Number of players in lobby: " + GameEngine.gameLobby.GetPlayerCount());
			System.out.println("GameServer: Number of games in lobby: " + GameEngine.gameLobby.GetGameRoomCount());
			break;

		/*** Lobby request ***/
		case 1:
			
			int lobbyRequest = dataReader.ReadInt(packet);
			
			switch (lobbyRequest) {

			/*** Create solitaire room ***/
			case -1:
				
				// Update all lobby GUIs to account for leaving player
				GameServer.gameServer.BroadcastLobbyUpdateRemovePlayer(myUsername);
				
				// Broadcast message that player has left
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername + " has left to play"
						+ " solitaire. He/she is too cool for you guys to play with.");
				
				// Remove player from lobby
				GameEngine.gameLobby.RemovePlayer(this);
				sendPacket.GoToGameRoomSolo();
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: " + myUsername + " has left lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;
				
			/*** Create game ***/
			case 0:
				// Update all lobby GUIs to account for leaving player
				GameServer.gameServer.BroadcastLobbyUpdateRemovePlayer(myUsername);
				
				// Create game room, add player to game room, remove player from lobby
				gameName = dataReader.ReadString(packet);
				player.CreateGameRoom(gameName);
				sendPacket.GoToGameRoomHost();
				
				// Broadcast message that player has created game
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has created a game called " + gameName + ".");
				
				// Update all lobby GUIs to account for new game
				GameServer.gameServer.BroadcastLobbyUpdateAddGame(gameName, myUsername);
				
				// Update host game GUI to account for its own joining
				sendPacket.GameRoomUpdateAddPlayer(myUsername);
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: " + myUsername + " has hosted a game called " + gameName);
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				System.out.println("GameServerThread: Game " + gameName + " has been added to lobby.");
				System.out.println("GameServerThread: Games in lobby: " + GameEngine.gameLobby.GameRoomsInLobby.size());
				System.out.println("GameServerThread: " + gameName + ": " + GetGameRoomSize() + " players");
				break;
				
			/*** Join game ***/
			case 1:
				
				// Update all lobby GUIs to account for leaving player
				GameServer.gameServer.BroadcastLobbyUpdateRemovePlayer(myUsername);
				
				// Join game room, add player to game room, remove player from lobby
				hostName = dataReader.ReadString(packet);
				player.JoinGameRoom(hostName);
				sendPacket.GoToGameRoomPlayer();
				
				// Broadcast message in lobby that player has left to join game
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has joined " + hostName + "'s game.");
				
				// Update joiner's game GUI to account for existing players in room (not including itself)
				for (Iterator<GameServerThread> clientItr = GetGameRoom().ClientsInGameRoom.listIterator();
						clientItr.hasNext();) {
					GameServerThread clientInGameRoom = clientItr.next();
					if(!clientInGameRoom.GetUsername().equals(myUsername)) {
						sendPacket.GameRoomUpdateAddPlayer(clientInGameRoom.GetUsername());
					}
				}
				
				// Broadcast message in game room that player has joined
				GetGameRoom().BroadcastChatInGame("[Game Message]: " + myUsername + " has joined.");
				
				// Update relevant game GUIs to account for new player
				GetGameRoom().BroadcastAddPlayer(myUsername);
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: " + myUsername + " has joined " + hostName + "'s game.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				System.out.println("GameServerThread: " + GetGameRoomName() + ": " + GetGameRoomSize() + " players");
				break;
				
			/*** Exit lobby ***/
			case 2:
				
				// Broadcast message in lobby that player has left
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has left.");
				
				// Update all lobby GUIs to account for leaving player
				GameServer.gameServer.BroadcastLobbyUpdateRemovePlayer(myUsername);
				
				// Remove player in memory
				GameEngine.gameLobby.RemovePlayer(this);
				
				sendPacket.GoToLogin();
				
				break;
			
			}
			break;
			
		/*** Game room request ***/
		case 2:
			
			int gameRoomRequest = dataReader.ReadInt(packet);
			
			switch(gameRoomRequest) {
			
			/*** Start solitaire ***/
			case -1:
				
				player.game = new Game();
				synchronized(player.game.Board) {
					
					// Start game and deal first 12 cards
					sendPacket.GoToGameSolo();
					sendPacket.CardUpdateInitialDeal(player.game.boardString());
					
					// Check if there are potential sets, if not add more cards until deck is empty
					noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
					while(noMoreSets) {
						boardSize = player.game.Board.size();
						player.game.Deal(3);
						cardString = player.game.boardString().substring(4*boardSize, 4*boardSize + 12) ;
						sendPacket.CardUpdateAddCards(boardSize, cardString);
						// update noMoreSets
						noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
					}
					//System.out.println("GameServerThread: " + player.game.boardString());
				}
				break;
				
			/*** Start hosted game ***/
			case 0:
				
				// Can't start game until at least 2 players
				if(player.gameRoom.ClientsInGameRoom.size() < 2) {
					sendPacket.GameRoomMessageInsufficientPlayers();
					System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("GameServerThread: Not enough players to start.");
					System.out.println("GameServerThread: " + GetGameRoomName() + ": " + GetGameRoomSize());
					break;
				}
				
				// Update all lobby GUIs to account for closed game
				GameServer.gameServer.BroadcastLobbyUpdateRemoveGame(this.GetGameRoomName(), myUsername);
				
				// Broadcast message in lobby that game has started
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						"'s game is starting, can no longer join.");
				
				System.out.println("GameServerThread: Pre-game inspection of sizes...");
				System.out.println("GameServerThread: Clients in room: " + GetGameRoomSize());
				System.out.println("GameServerThread: Clients in game: " + GetGameSize());
				
				// Broadcast message in game room that game is starting
				GetGameRoom().BroadcastChatInGame("[Game Message]: Starting game...");
				
				// Update relevant game GUIs for deal, then start game
				player.gameRoom.BroadcastStartGame(); // checkpoint :)
				player.gameRoom.StartGame();
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: Game " + GetGameRoomName() + " has been started.");
				System.out.println("GameServerThread: Games in lobby: " + GameEngine.gameLobby.GameRoomsInLobby.size());
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: Moving clients to room to game...");
				System.out.println("GameServerThread: Clients in room: " + GetGameRoomSize());
				System.out.println("GameServerThread: Clients in game: " + GetGameSize());
				
				// Broadcast message in game regarding initial deal
				GetGame().BroadcastChatInGame("[Game Message]: Dealing 12 cards...");
				
				// Update relevant game GUIs with first deal
				player.game.BroadcastInitialDeal();  // checkpoint :)
				
				// Check if there are potential sets, if not add more cards
				noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
				while(noMoreSets) {
					
					boardSize = player.game.Board.size();
					player.game.Deal(3);
					cardString = player.game.boardString().substring(4*boardSize, 4*boardSize + 12) ;
					
					// Broadcast message in game regarding adding 3 cards
					GetGame().BroadcastChatInGame("[Game Message]: No possible Sets on field. Adding 3 cards...");
					
					// Update relevant game GUIs with new cards
					player.game.BroadcastAddCards(boardSize, cardString);
					
					// update noMoreSets
					noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
				}
				
				
				break;
				
			/*** Exit solitaire room ***/
			case 1:
				
				// Add player back to lobby
				AddPlayerToLobby();
				sendPacket.GoToLobbyFromGame();
				
				// Broadcast message in lobby regarding player's return
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has returned from a game.");
				
				// Update all lobby GUIs to account for returning player
				GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(myUsername);
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: Exited game room as solo.");
				System.out.println("GameServerThread: " + myUsername + " has returned to lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;
				
			/*** Exit room as host  ***/
			case 2:
				
				// Broadcast message in game regarding game cancellation
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						"'s game has been cancelled.");
				
				// Update all lobby GUIs to account for cancelled game room
				GameServer.gameServer.BroadcastLobbyUpdateRemoveGame(this.GetGameRoomName(), myUsername);
				
				// For each player in cancelled game room
				for(Iterator<GameServerThread> clientItr = player.gameRoom.ClientsInGameRoom.listIterator();
						clientItr.hasNext();) {
					GameServerThread clientInGameRoom = clientItr.next();
					
					// Update all lobby GUIs to account for each returning player
					GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(clientInGameRoom.GetUsername());
					
					// Tell each player to go to lobby GUI
					clientInGameRoom.sendPacket.GoToLobbyFromGame();
					
					// Broadcast message in lobby regarding player's return
					GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + clientInGameRoom.GetUsername() +
							" has returned from a game.");
					
					System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("GameServerThread: Game name: " + GetGameRoomName());
					System.out.println("GameServerThread: Removed " + clientInGameRoom.GetUsername() +
							" from game room.");
					System.out.println("GameServerThread: Remaining in room: " + GetGameRoomSize());
				}
				
				// Remove all players from game room and add to lobby (internally)
				RemovePlayerFromGameRoom(); // do this for now, want to broadcast after
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: " + myUsername + "has disbanded the game room.");
				System.out.println("GameServerThread: Game has ended, " + myUsername + " and other players have returned to lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;

			/*** Exit room as player ***/
			case 3:
				
				GameRoom gameRoomTemp = GetGameRoom();
				
				// Broadcast message in game regarding player's departure
				GetGameRoom().BroadcastChatInGame("[Game Message]: " + myUsername +
						" has left.");
				
				// Update all relevant game GUIs to account for leaving player
				GetGameRoom().BroadcastRemovePlayer(myUsername);
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: " + myUsername + " has left " + GetHostName() + "'s game.");
				
				// Remove player from game room and add to lobby
				RemovePlayerFromGameRoom();
				sendPacket.GoToLobbyFromGame();
				
				// Broadcast message in lobby regarding player's return
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has returned from a game.");
				
				// Update all lobby GUIs to account for returning player
				GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(myUsername);
				
				System.out.println("GameServerThread: Remaining players in " +
									gameRoomTemp.gameName + ": " + (gameRoomTemp.ClientsInGameRoom.size()));
				System.out.println("GameServerThread: " + myUsername + " has returned to lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;
				
			/*** Kick player ***/
			case 4:
				
				String playerToKick = dataReader.ReadString(packet);
				
				// Broadcast message that player has been kicked
				GetGameRoom().BroadcastChatInGame("[Game Message]: " + playerToKick +
						" has been kicked from the game.");
				
				// Remove player from game list in all relevant GUIs, broadcast
				GetGameRoom().BroadcastRemovePlayer(playerToKick);
				
				// Remove player from game room and add to lobby
				GameServerThread clientToKick = GetGameRoom().GetPlayer(playerToKick);
				clientToKick.GetGameRoom().GetPlayer(playerToKick).RemovePlayerFromGameRoom();
				clientToKick.sendPacket.GoToLobbyFromGame();
				
				// Broadcast message in lobby regarding player's return
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + playerToKick +
						" has returned from a game.");
				
				// Update all lobby GUIs to account for returning player
				GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(playerToKick);
				
				break;

			}
			break;
			
		/*** Game request ***/
		case 3:
			
			int gameRequest = dataReader.ReadInt(packet);
			
			switch(gameRequest) {
			
			/*** Cheat ***/
			case -2:
				
				//System.out.println("GameServerThread: Check1");
				ArrayList<Integer> setPosList = GameEngine.gameLogic.getSetPosition(player.game.Board);
				card1Pos = setPosList.get(0).intValue();
				card2Pos = setPosList.get(1).intValue();
				card3Pos = setPosList.get(2).intValue();
				System.out.println("GameServerThread: Cheat: " + player.game.Board.get(card1Pos).toString()
									+ " " + player.game.Board.get(card2Pos).toString()
									+ " " + player.game.Board.get(card3Pos).toString());
				sendPacket.CardUpdateCheat(card1Pos, card2Pos, card3Pos);
				break;
			
			/*** Set submitted from solitaire ***/
			case -1:
				
				// Get card positions
				card1Pos = dataReader.ReadInt(packet);
				card2Pos = dataReader.ReadInt(packet);
				card3Pos = dataReader.ReadInt(packet);
				//System.out.println("GameServerThread: Card 1: " + (card1Pos + 1));
				//System.out.println("GameServerThread: Card 2: " + (card2Pos + 1));
				//System.out.println("GameServerThread: Card 3: " + (card3Pos + 1));
				
				// Check if cards form a set
				card1 = player.game.Board.get(card1Pos);
				card2 = player.game.Board.get(card2Pos);
				card3 = player.game.Board.get(card3Pos);
				setFound = GameEngine.gameLogic.isSet(card1, card2, card3);
				System.out.println("GameServerThread: Set: " + setFound);
				
				// If set found, update game board and player score
				if(setFound) {
					//System.out.println("GameServerThread: Set found");
					synchronized(player.game.Board) {
						
						// Update internal game board
						player.game.UpdateBoard(card1, card2, card3);
						newCard1 = player.game.Board.get(card1Pos);
						newCard2 = player.game.Board.get(card2Pos);
						newCard3 = player.game.Board.get(card3Pos);
						cardString = newCard1.toString() + newCard2.toString() + newCard3.toString();
						
						// Player gets 2 points for correct set
						player.score += 2; 
						
						// Send packets
						sendPacket.GameMessageYouFoundSet();
						
						// Update external game board
						sendPacket.CardUpdateReplaceCards(card1Pos, card2Pos, card3Pos, cardString);
						
						// Check if there are potential sets, if not add more cards until deck is empty
						noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
						while(noMoreSets && !player.game.Deck.isEmpty()) {
							
							boardSize = player.game.Board.size();
							player.game.Deal(3);
							cardString = player.game.boardString().substring(4*boardSize, 4*boardSize + 12) ;
							System.out.println("GameServerThread: " + player.game.boardString());
							System.out.println("GameServerThread: " + cardString);
							
							sendPacket.CardUpdateAddCards(boardSize, cardString);
							
							// update noMoreSets
							noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
							System.out.println("GameServerThread: Added cards.");
						}
						
						// End game if no more potential sets and deck is empty
						if(noMoreSets && player.game.Deck.isEmpty()) {
							sendPacket.GameMessageSoloGameOver();
						}
					}
					
					//System.out.println("GameServerThread: Card 1 replace: " + card1Pos);
					//System.out.println("GameServerThread: Card 2 replace: " + card2Pos);
					//System.out.println("GameServerThread: Card 3 replace: " + card3Pos);
					//System.out.println("GameServerThread: Card string replace: " + cardString);

				} else {
					System.out.println("GameServerThread: Invalid set");
					player.score -= 1; // player gets 1 point deduction for incorrect set
					sendPacket.GameMessageInvalidSet();
				}
				break;
				
			/*** Set submitted from multiplayer game ***/
			case 0:
				
				// Get card positions
				card1Pos = dataReader.ReadInt(packet);
				card2Pos = dataReader.ReadInt(packet);
				card3Pos = dataReader.ReadInt(packet);
				
				// Check if cards form a set
				card1 = player.game.Board.get(card1Pos);
				card2 = player.game.Board.get(card2Pos);
				card3 = player.game.Board.get(card3Pos);
				setFound = GameEngine.gameLogic.isSet(card1, card2, card3);
				System.out.println("GameServerThread: Set: " + setFound);
				
				// If set found, update game board and player score
				if(setFound) {

					synchronized(player.game.Board) {
						
						// Update internal game board
						player.game.UpdateBoard(card1, card2, card3);
						newCard1 = player.game.Board.get(card1Pos);
						newCard2 = player.game.Board.get(card2Pos);
						newCard3 = player.game.Board.get(card3Pos);
						cardString = newCard1.toString() + newCard2.toString() + newCard3.toString();
						
						// Player gets 2 points for correct set
						player.score += 2; 
						
						// Broadcast packet to notify that set has been found
						player.game.BroadcastPlayerFoundSet(myUsername);
						player.game.BroadcastChatInGame("[Game Message]: " + myUsername +
								" has found a set.");
						
						// Broadcast update of external game board
						player.game.BroadcastReplaceCards(card1Pos, card2Pos, card3Pos, cardString);
						
						// Check if there are potential sets, if not add more cards until deck is empty
						noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
						while(noMoreSets && !player.game.Deck.isEmpty()) {
							
							boardSize = player.game.Board.size();
							player.game.Deal(3);
							cardString = player.game.boardString().substring(4*boardSize, 4*boardSize + 12) ;
							System.out.println("GameServerThread: " + player.game.boardString());
							System.out.println("GameServerThread: " + cardString);
							
							// Broadcast message in game regarding adding 3 cards
							GetGame().BroadcastChatInGame("[Game Message]: No possible Sets on field. Adding 3 cards...");
							
							// Update GUIs
							player.game.BroadcastAddCards(boardSize, cardString);
							
							// update noMoreSets
							noMoreSets = GameEngine.gameLogic.noMoreSets(GameEngine.gameLogic.getSets(player.game.Board));
							System.out.println("GameServerThread: Added cards.");
						}
						
						// End game if no more potential sets and deck is empty
						if(noMoreSets && player.game.Deck.isEmpty()) {
							String winnerName = player.game.GetWinner();
							GetGame().BroadcastChatInGame("[Game Message]: " + winnerName + " has won the game.");
							player.game.BroadcastGameOver(); // checkpoint
							player.game.EndGame();
							SetScore(0);
						}
					}
				} else {
					System.out.println("GameServerThread: Invalid set");
					player.score -= 1; // player gets 1 point deduction for incorrect set
					sendPacket.GameMessageInvalidSet();
				}
				break;
				
			/*** Exit solitaire game ***/
			case 1:
				
				// Add player back to lobby
				AddPlayerToLobby();
				sendPacket.GoToLobbyFromGame();
				
				// Broadcast lobby chat
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has returned from a game.");
				
				// Update all lobby GUIs to account for returning player
				GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(myUsername);
				
				sendPacket.GoToLobbyFromGame();
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: Exited game as solo.");
				System.out.println("GameServerThread: " + myUsername + " has returned to lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;
				
			/*** Exit game as host ***/
			case 2:
				
				// For each player in cancelled game
				for (Iterator<GameServerThread> clientItr = player.game.ClientsInGame.listIterator();
						clientItr.hasNext();) {
					GameServerThread clientInGame = clientItr.next();

					// Update all lobby GUIs to account for each returning player
					GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(clientInGame.GetUsername());

					// Tell each player to go to lobby GUI
					clientInGame.sendPacket.GoToLobbyFromGame();
					
					// Broadcast lobby chat
					GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + clientInGame.GetUsername() +
							" has returned from a game.");
					
					System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("GameServerThread: Game name: " + player.game.gameName);
					System.out.println(
							"GameServerThread: Removed " + clientInGame.GetUsername() + " from game.");
					System.out.println("GameServerThread: Remaining in game: " + GetGameSize());
				}
				
				RemovePlayerFromGame();
				sendPacket.GoToLobbyFromGame();
				System.out.println("GameServerThread: Exited game as host.");
				break;

			/*** Exit game as player ***/
			case 3:
				
				Game gameTemp = GetGame();
				
				// Broadcast chat when player leaves game
				GetGame().BroadcastChatInGame("[Game Message]: " + myUsername +
						" has left.");
				
				// Update all relevant game GUIs to account for leaving player
				GetGame().BroadcastRemovePlayer(myUsername);
				
				// Remove player from game and add to lobby
				RemovePlayerFromGame();
				sendPacket.GoToLobbyFromGame();
				
				// Broadcast chat
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has returned from a game.");
				
				// Broadcast to all players that player has re-entered lobby (GUI)
				GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(myUsername);
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: Remaining players in " +
									gameTemp.gameName + ": " + (gameTemp.ClientsInGame.size()));
				System.out.println("GameServerThread: " + myUsername + " has returned to lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;
				
			/*** Exit game when game over ***/
			case 4:
				
				// Broadcast chat
				GetGame().BroadcastChatInGame("[Game Message]: " + myUsername +
						" has left.");
				
				// Update all relevant game GUIs to account for leaving player
				GetGame().BroadcastRemovePlayer(myUsername);
				
				// Remove player from game
				GetGame().ClientsInGame.remove(myUsername);
				
				// Broadcast chat
				GameServer.gameServer.BroadcastChatInLobby("[Lobby Message]: " + myUsername +
						" has returned from a game.");
				
				// Add player back to lobby
				AddPlayerToLobby();
				sendPacket.GoToLobbyFromGame();
				
				// Update all lobby GUIs to account for returning player
				GameServer.gameServer.BroadcastLobbyUpdateAddPlayer(myUsername);
				
				System.out.println("GameServerThread: ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("GameServerThread: Exiting finished game.");
				System.out.println("GameServerThread: " + myUsername + " has returned to lobby.");
				System.out.println("GameServerThread: Players in lobby: " + GameEngine.gameLobby.ClientsInLobby.size());
				break;
				
			}
			break;
			
		/*** Chat request ***/
		case 4:
			
			int chatRequest = dataReader.ReadInt(packet);
			
			switch(chatRequest) {
			
			/*** Chat in lobby ***/
			case 0:
				String lobbyMsg = dataReader.ReadString(packet);
				GameServer.gameServer.BroadcastChatInLobby(lobbyMsg);
				break;
				
			/*** Chat in game room ***/
			case 1:
				String gameRoomMsg = dataReader.ReadString(packet);
				GetGameRoom().BroadcastChatInGame(gameRoomMsg);
				break;
				
			/*** Chat in game ***/
			case 2:
				String gameMsg = dataReader.ReadString(packet);
				GetGame().BroadcastChatInGame(gameMsg);
				break;
				
			}	
			break;
			
		/*** Close thread ***/
		case 5:
			GameServer.gameServer.DeleteClient(this);;
			break;
		}
	}
	
}

