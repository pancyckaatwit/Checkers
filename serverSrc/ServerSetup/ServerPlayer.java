package ServerSetup;
import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Alexander Pancyck
 * 
 * ServerPlayer deals with the data that are 
 * sent and received to and from the player 
 */

public class ServerPlayer{
	private int PlayerID;
	private Socket socket;
	private DataInputStream fromPlayer;
	private DataOutputStream toPlayer;
	
	public ServerPlayer(int ID, Socket s){
		this.PlayerID = ID;
		this.socket = s;
		
		try{
			fromPlayer = new DataInputStream(socket.getInputStream());
			toPlayer = new DataOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method responsible for sending data
	public int sendData(int data){
		try {
			this.toPlayer.writeInt(data);
			return 1; 
		} catch (IOException e) {
			e.printStackTrace();
			return 99;	
		}		
	}
	
	//Method responsible for receiving data
	public int receiveData(){
		int data = 0;;
		try{
			data = this.fromPlayer.readInt();
			return data;
		}catch (IOException e) {
			e.printStackTrace();
			return 99;
		}
	}
	
	public void closeConnection(){
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isOnline(){
		return socket.isConnected();
	}
}
