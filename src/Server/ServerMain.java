package Server;

import javax.swing.JFrame;

public class ServerMain {
	public static void main(String[] args) {
		
		ServerApp server = new ServerApp();
		server.setSize(500,250);
		server.setVisible(true);
		server.setTitle("Checkers Server");
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		server.startRunning();
	}
}
