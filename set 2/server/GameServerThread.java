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

import set.server.game.Game;
import set.server.game.GamePlayer;

// Note: This class will be referred to as the "client",
// since each server thread manages a client connection.
public class GameServerThread extends Thread {

	/*****************************/
	/** GameServerThread fields **/
	/*****************************/

	private Socket clientSocket;
	private DataInputStream clientInData;
	private DataOutputStream clientOutData;
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
	
	// Get username of player associated with client
	public String GetUserName() {
		return player.username;
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
	public int GetWins() {
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
		//send packet
	}
	
	// Remove player from game room
	public void RemovePlayerFromGameRoom() {
		player.ExitGameRoom();
		player.EnterGameLobby();
		//send packet
	}
	
	// Add client to game
	public void AddPlayerToGame() {
		player.JoinGame(player.gameRoom.hostUID);
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
	}
	
	/********************/
	/** Packet-related **/
	/********************/
	
	// Send packets to client
	// Synchronize method so packets are sent in order
	public synchronized void SendPacket() {
		
	}
	
	// Parse packets sent to server
	public void ParsePacket() throws IOException {
		
		short header = clientInData.readShort();
		switch(header) {
		
		case 0x00:
			System.out.println("Starting game...");
			Game NewGame = new Game();
			clientOutData.writeShort(0x00);
			clientOutData.writeInt(NewGame.CardsOnField());
			clientOutData.writeUTF(NewGame.boardString());
			clientOutData.flush();
			break;
		
		/*
		case 0x00:
			System.out.println("First name: " + clientInData.readUTF());
			System.out.println("Last name: " + clientInData.readUTF());
			break;
			
		case 0x01:
			System.out.println("User: " + clientInData.readUTF());
			System.out.println("Pass: " + clientInData.readUTF());
			System.out.println("Highscore: " + clientInData.readInt());
			System.out.println("Win count: " + clientInData.readInt());
			break;
		*/
		}
	}

	/**************************/
	/** GameServerThread run **/
	/**************************/

	public void run() {
		
		System.out.println("GameServerThread: Client is running!");
		
		try {
			clientInData = new DataInputStream(clientSocket.getInputStream());
			clientOutData = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("GameServerThread: Error opening IO stream ");
		}
		
		while(ClientOn) {
			try {
				int availableBytes = clientInData.available();
				if(availableBytes > 0) {
					ParsePacket();
				}
			} catch (IOException e) {
				System.err.println("GameServerThread: Error parsing packet.");
			}
		}
	}
	
}

