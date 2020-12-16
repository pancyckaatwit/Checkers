package Server;
import java.awt.BorderLayout;

import javax.swing.*;

import ServerCheckers.Checkers;
import ServerThread.HandleSession;

import java.io.*;
import java.net.*;

/**
 * @author Alexander Pancyck
 * 
 * This class will establish a Server socket
 * then wait for players to connect. After two players
 * are able to connect then a new thread 
 * starts for two other players 
 */
public class ServerApp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//Frame components
	private JScrollPane scroll;
	private JTextArea info;
	private JLabel title;
	
	//Network properties
	private ServerSocket serverSocket;
	int threadNumber;
	
	public ServerApp() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		title = new JLabel("APJG Checkers Server");
		info = new JTextArea();
		scroll = new JScrollPane(info);
		
		add(title,BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}	
	
	//Starts a connection with client
	public void startRunning() {
		
		try {
			
			ServerPropertyManager serverProperty = ServerPropertyManager.getInstance();
			int port = serverProperty.getPort();
			
			//Initializes a socket for the server
			serverSocket = new ServerSocket(port);
			info.append(serverSocket.getInetAddress().getHostAddress());
			threadNumber=1;			
			
			while(true){
				
				info.append( threadNumber + " has started\n");
				
				//Player 1 
				Socket player1 = serverSocket.accept();
				info.append("Player1 has joined");
				
				//Player 1 connected confirmation
				new DataOutputStream(player1.getOutputStream()).writeInt(Checkers.PLAYER_ONE.getValue());
				
				//Player 2
				Socket player2 = serverSocket.accept();
				info.append("Player2 has joined");
				
				
				//Player 2 connection confirmed
				new DataOutputStream(player2.getOutputStream()).writeInt(Checkers.PLAYER_TWO.getValue());
				
				//Increases Thread number
				threadNumber++;
				
				// Creates a new thread for this session of two players
				HandleSession task = new HandleSession(player1, player2);
				new Thread(task).start();
			}
		}catch(Exception ex) {	
			ex.printStackTrace();
			System.exit(0);
		}				
	}
}
