package Client;
import java.io.IOException;

import javax.swing.*;


import Client.BoardPanel;

public class ClientMain {
	
	public static void main(String[] args) {
		
		ClientApplication client = new ClientApplication();
		
		client.setTitle("Checkers Client");
		client.pack();
		client.setVisible(true);
		client.setLocation(200, 100);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
