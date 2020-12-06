package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerPlayer {

	private int playerID;
	private DataInputStream inFromPlayer;
	private DataOutputStream outFromPlayer;
	private Socket socket;
	
	public ServerPlayer(int playerID, Socket socket) {
		this.playerID=playerID;
		this.socket=socket;
		
		try {
			inFromPlayer = new DataInputStream(socket.getInputStream());
			outFromPlayer = new DataOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			
		}
	}
	
	public int sendData(int data) {
		try {
			this.outFromPlayer.writeInt(data);
			return 1;
		}catch(IOException e) {
			System.out.println("Player not found.");
			return 99;
		}
	}
	
	public int receiveData() {
		int data=0;
		try {
			data=this.inFromPlayer.readInt();
			return data;
		}catch(IOException e) {
			System.out.println("Couldn't receive data from player.");
			return 99;
		}
	}
	
	public void closeConnection() {
		try {
			this.socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWorking() {
		return socket.isConnected();
	}
}
