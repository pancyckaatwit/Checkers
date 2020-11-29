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
		
	}
	
	public int receiveData(int data) {
		
	}
}
