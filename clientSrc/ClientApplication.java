import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import javax.swing.*;

import constant.Checkers;
import handler.Controller;
import handler.MouseController;
import model.Player;
import model.ServerPlayer;
import view.BoardPanel;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Client Application
 * 
 * Client application class serves to get
 * the address and port from the client that is trying to connect to a server
 * it will use a managing class to receive the stream of a user-input server
 * and port number
 * 
 * @author Jake Giguere
 *
 * 
 *        
 */
public class ClientApplication extends JFrame {

	private static final long serialVersionUID = 1L;

	private String address;
	private int port;

	// Model
	private Player player;

	// View
	private BoardPanel boardPanel;

	// Network properties
	private Socket connection;
	private DataInputStream fromServer;
	private DataOutputStream toServer;

	// Constructor
	public ClientApplication()
		{

		try 
			{
			
			PropertyManager pm = PropertyManager.getInstance();
			address = pm.getIPAddress();
			port = pm.getPort();

			String name = (String) JOptionPane.showInputDialog(null, "Enter a username to Connect", "Connect to Server",
					JOptionPane.OK_CANCEL_OPTION);

			if (name != null && name.length() > 0)
				{
				
				player = new Player(name);
				connect();
				
				}//END IF
			else
				{
				
				JOptionPane.showMessageDialog(null, "Please enter valid name", "Error", JOptionPane.ERROR_MESSAGE,
						null);
				System.exit(0);
				
				}//END ELSE
			} //END TRY BLOCK
		catch (Exception ex)
			{
			
			JOptionPane.showMessageDialog(null, "Please enter valid IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE,
					null);
			System.exit(0);
			
			}//END CATCH

		}//END CLIENTAPP()

	private void connect()
		{

		try
			{
			
			connection = new Socket(address, port);

			// Should error occurs, handle it in a seperate thread (under try)
			fromServer = new DataInputStream(connection.getInputStream());
			toServer = new DataOutputStream(connection.getOutputStream());

			// First player get 1 and second player get 2
			player.setPlayerID(fromServer.readInt());

			Controller task = new Controller(player, fromServer, toServer);
			setup(task);

			new Thread(task).start();
			
			}//END TRY 
		catch (UnknownHostException e)
			
			{
			
			JOptionPane.showMessageDialog(null, "Internal Server Error - Check your IPv4-Address", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
			
			} //END CATCH
		catch (IOException e)
			{
			
			JOptionPane.showMessageDialog(null, "Couldn't connect to Server. Please try again", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
			
			}//END CATCH
		}//END CONNECT()

	private void setup(Controller c)
		{
		
		MouseController listener = new MouseController();
		listener.setController(c);

		boardPanel = new BoardPanel(listener);
		c.setBoardPanel(boardPanel);
		add(boardPanel);
		
		}//END SETUP()
	}
