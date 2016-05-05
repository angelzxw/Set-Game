/*
 *  ServerPacketGenerator.java
 *  
 *  Class that creates functions to generate
 *  all different packet possibilities to be
 *  sent from server to client.
 *  
 */

package set.server;

import java.io.DataOutputStream;

import set.packet.DataWriter;

public class ServerPacketGenerator {
	
	/****************************/
	/** PacketGenerator fields **/
	/****************************/
	
	public DataOutputStream packet;
	
	/*********************************/
	/** PacketGenerator constructor **/
	/*********************************/
	
	public ServerPacketGenerator(DataOutputStream toClient) {
		packet = toClient;
	}
	
	/*****************************/
	/** PacketGenerator methods **/
	/*****************************/
	
	/*******************************************/
	/** Packets to send to individual clients **/
	/*******************************************/
	
	/*****************/
	/** Player data **/
	/*****************/
	
	// Packet sending client player updated stats
	public void SaveStats(int numWins, int numTotalGames, int highScore) {

		DataWriter dataWriter = new DataWriter();
		short header = -1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(numWins, packet);
			dataWriter.WriteInt(numTotalGames, packet);
			dataWriter.WriteInt(highScore, packet);
		}
	}

	// Packet sending client player data
	/*
	public void PlayerData(String username, int userID,
							int numWins, int numTotalGames, int highScore) {
		
		DataWriter dataWriter = new DataWriter();
		short header = -1;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteString(username, packet);
			dataWriter.WriteInt(userID, packet);
			dataWriter.WriteInt(numWins, packet);
			dataWriter.WriteInt(numTotalGames, packet);
			dataWriter.WriteInt(highScore, packet);
		}
	}
	*/
	
	/********************************/
	/** State change (out-of-game) **/
	/********************************/
	
	// Packet telling client to go to login state
	public void GoToLogin() {
		
		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 0;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}
	
	// Packet telling client to go to lobby state from login state
	public void GoToLobbyFromLogin() {
		
		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 1;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}
	
	// Packet telling client to go to lobby state from game state
	public void GoToLobbyFromGame() {
		
		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 2;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}
	
	/******************************/
	/** State change (game room) **/
	/******************************/
	
	// Packet telling client to go to solitaire room state
	public void GoToGameRoomSolo() {

		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = -2;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}
	
	// Packet telling client to go to game room state as host
	public void GoToGameRoomHost() {
		
		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 3;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}

	// Packet telling client to go to game room state as player
	public void GoToGameRoomPlayer() {

		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 4;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}
	
	/*************************/
	/** State change (game) **/
	/*************************/
	
	// Packet telling client to go to solitaire game state
	public void GoToGameSolo() {

		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = -1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}

	// Packet telling client to go to game state as host
	public void GoToGameHost() {

		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 5;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}

	// Packet telling client to go to game state as player
	public void GoToGamePlayer() {

		DataWriter dataWriter = new DataWriter();
		short header = 0;
		int gameState = 6;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameState, packet);
		}
	}
	
	/******************/
	/** Update lobby **/
	/******************/
	
	public void LobbyUpdateAddPlayer(String username) {

		DataWriter dataWriter = new DataWriter();
		short header = 1;
		int updateType = 1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(username, packet);
		}
	}
	
	public void LobbyUpdateRemovePlayer(String username) {

		DataWriter dataWriter = new DataWriter();
		short header = 1;
		int updateType = 2;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(username, packet);
		}
	}
	
	public void LobbyUpdateAddGame(String gameName, String username) {

		DataWriter dataWriter = new DataWriter();
		short header = 1;
		int updateType = 3;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(gameName, packet);
			dataWriter.WriteString(username, packet);
		}
	}
	
	public void LobbyUpdateRemoveGame(String gameName, String username) {

		DataWriter dataWriter = new DataWriter();
		short header = 1;
		int updateType = 4;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(gameName, packet);
			dataWriter.WriteString(username, packet);
		}
	}
	
	/********************************/
	/** Update player list in game **/
	/********************************/
	
	public void GameRoomUpdateAddPlayer(String username) {

		DataWriter dataWriter = new DataWriter();
		short header = 2;
		int updateType = 0;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(username, packet);
		}
	}
	
	public void GameRoomUpdateRemovePlayer(String username) {

		DataWriter dataWriter = new DataWriter();
		short header = 2;
		int updateType = 1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(username, packet);
		}
	}
	
	/******************/
	/** Update cards **/
	/******************/
	
	// Packet telling GUI to update cards after first deal
	public void CardUpdateInitialDeal(String boardString) {

		DataWriter dataWriter = new DataWriter();
		short header = 3;
		int updateType = 0;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteString(boardString, packet);
		}
	}
	
	// Packet telling GUI to update cards after cards are replaced
	public void CardUpdateReplaceCards(int card1Pos, int card2Pos, int card3Pos,
										String cardString) {

		DataWriter dataWriter = new DataWriter();
		short header = 3;
		int updateType = 1;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteInt(card1Pos, packet);
			dataWriter.WriteInt(card2Pos, packet);
			dataWriter.WriteInt(card3Pos, packet);
			dataWriter.WriteString(cardString, packet);
		}
	}
	
	// Packet telling GUI to update cards after cards are added
	public void CardUpdateAddCards(int boardSize, String cardString) {

		DataWriter dataWriter = new DataWriter();
		short header = 3;
		int updateType = 2;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteInt(boardSize, packet);
			dataWriter.WriteString(cardString, packet);
		}
	}
	
	// Packet telling GUI to update cards after cards are added
	public void CardUpdateCheat(int card1Pos, int card2Pos, int card3Pos) {

		DataWriter dataWriter = new DataWriter();
		short header = 3;
		int updateType = 3;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(updateType, packet);
			dataWriter.WriteInt(card1Pos, packet);
			dataWriter.WriteInt(card2Pos, packet);
			dataWriter.WriteInt(card3Pos, packet);
		}
	}
	
	/************************/
	/** Game room messages **/
	/************************/
	
	// Packet notifying that solitaire game is over
	public void GameRoomMessageInsufficientPlayers() {

		DataWriter dataWriter = new DataWriter();
		short header = 4;
		int gameMessage = 0;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
		}
	}
	
	/*******************/
	/** Game messages **/
	/*******************/
	
	// Packet notifying that solitaire game is over
	public void GameMessageSoloGameOver() {

		DataWriter dataWriter = new DataWriter();
		short header = 5;
		int gameMessage = -1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
		}
	}
	
	// Packet notifying that you have found set
	public void GameMessageYouFoundSet() {
		
		DataWriter dataWriter = new DataWriter();
		short header = 5;
		int gameMessage = 0;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
		}
	}
	
	// Packet notifying that another player has found set
	public void GameMessageOtherPlayerFoundSet(String username) {
		
		DataWriter dataWriter = new DataWriter();
		short header = 5;
		int gameMessage = 1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
			dataWriter.WriteString(username, packet); // user who found set
		}
	}
	
	// Packet notifying that you have submitted invalid set
	public void GameMessageInvalidSet() {

		DataWriter dataWriter = new DataWriter();
		short header = 5;
		int gameMessage = 2;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
		}
	}
	
	// Packet notifying that you have won game
	public void GameMessageYouWon() {
		
		DataWriter dataWriter = new DataWriter();
		short header = 5;
		int gameMessage = 3;
		
		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
		}
	}
	
	// Packet notifying that another player has won game
	public void GameMessageOtherPlayerWon(String username) {
		
		DataWriter dataWriter = new DataWriter();
		short header = 5;
		int gameMessage = 4;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(gameMessage, packet);
			dataWriter.WriteString(username, packet); // user who won
		}
	}
	
	/**************/
	/** Chatting **/
	/**************/
	
	// Packet sending player a new chat message in lobby
	public void ChatMessageInLobby(String message) {

		DataWriter dataWriter = new DataWriter();
		short header = 6;
		int chatType = 0;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(chatType, packet);
			dataWriter.WriteString(message, packet);
		}
	}
	
	// Packet sending player a new chat message in game
	public void ChatMessageInGame(String message) {

		DataWriter dataWriter = new DataWriter();
		short header = 6;
		int chatType = 1;

		synchronized (packet) {
			dataWriter.WriteShort(header, packet);
			dataWriter.WriteInt(chatType, packet);
			dataWriter.WriteString(message, packet);
		}
	}

}