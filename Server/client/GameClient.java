package set.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import set.client.gui.*;

public class GameClient {
	
	/***********************/
	/** GameClient fields **/
	/***********************/
	
	private Socket clientSocket;
	private DataInputStream clientInData;
	private DataOutputStream clientOutData;
	
	/*********************/
	/** GameClient main **/
	/*********************/

	public static void main(String args[]) throws Exception {

		//String serverAddress = (args.length == 0) ? "localhost" : args[1];
		String serverAddress = "192.168.1.59";
		int portNumber = 8901;
		GameClient newClient = new GameClient(serverAddress, portNumber);
		
		
		/*** Testing communication with server ***/
		GameGUI newGUI = new GameGUI();
		try {
			newClient.clientOutData.writeShort(0x00);
		} catch (IOException e) {
			System.out.println("GameClient: Error sending message.");
		}
		
		while(true) {
			if(newClient.clientInData.readShort() == 0x00) {
				int boardSize = newClient.clientInData.readInt();
				String cardString = newClient.clientInData.readUTF();
				//System.out.println("GameClient: Printing message: " + boardSize + cardString);
				newGUI.updateGUI(boardSize, cardString);
			}
		}
		
		/*
		try {
			
			clientOutData.writeShort(0x00);
			clientOutData.writeUTF("Jeffrey");
			clientOutData.writeUTF("Shih");
			clientOutData.flush();
			clientOutData.writeShort(0x01);
			clientOutData.writeUTF("supahotfiya7");
			clientOutData.writeUTF("123321");
			clientOutData.writeInt(999999);
			clientOutData.writeInt(69);
			clientOutData.flush();
			
		} catch (IOException e) {
			System.out.println("Client: Error sending message.");
		}
		*/
	}
	
	/****************************/
	/** GameClient constructor **/
	/****************************/
	
	public GameClient(String serverAddress, int portNumber) {
		StartClient(serverAddress, portNumber);
		PrepareIO();
	}
	
	/************************/
	/** GameClient methods **/
	/************************/
	
	// Start the client by opening a new socket
	public void StartClient(String serverAddress, int portNumber) {
		try {
			clientSocket = new Socket(serverAddress, portNumber);
		} catch(IOException e){
			System.err.println("GameClient: Could not open client on port " + portNumber + ".");
        	System.exit(-1);
		}
	}
	
	// Retrieve socket's I/O streams
	public void PrepareIO() {
		try {
			clientInData = new DataInputStream(clientSocket.getInputStream());
			clientOutData = new DataOutputStream(clientSocket.getOutputStream());
		} catch(IOException e){
			System.err.println("GameClient: Error opening I/O streams.");
		}
	}	
}
