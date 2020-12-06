package Client;
import javax.swing.*;


import Client.BoardPanel;

public class ClientMain {
	
	public static void main(String[] args) {
		
		ClientApplication client = new ClientApplication();
		
		client.setTitle("Checkers");
		client.pack();
		client.setVisible(true);
		client.setLocation(250, 150);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
