package Client;
import javax.swing.*;

import ClientView.BoardPanel;

/**
 *
 * @author Jake Giguere
 * 
 **/
public class ClientMain {
	
	public static void main(String[] args)
	{
		ClientApplication client = new ClientApplication();
		client.setTitle("Checkers Player");
		client.setLocation(200, 100);
		client.pack();
		client.setVisible(true);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

