import javax.swing.*;

import ClientView.BoardPanel;

/**

 * 
 * Main
 */
public class ClientMain {
	
	public static void main(String[] args)
	{
		ClientApplication client = new ClientApplication();
		
		client.setTitle("Checkers Player");
		client.pack();
		client.setVisible(true);
		client.setLocation(200, 100);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

