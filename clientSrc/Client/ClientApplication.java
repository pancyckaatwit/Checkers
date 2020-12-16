package Client;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import javax.swing.*;

import ClientCheckers.Checkers;
import ClientController.Controller;
import ClientController.MouseController;
import ClientSetup.Player;
import ClientView.BoardPanel;
import ServerSetup.ServerPlayer;

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

	private String clientAddress;
	private int clientPort;

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
				
				PropertyManager clientProperty = PropertyManager.getInstance();
				clientAddress = clientProperty.getIPAddress();
				clientPort = clientProperty.getPort();

				String username = (String) JOptionPane.showInputDialog(null, "Enter a username to Connect", "Connect to Server",
						JOptionPane.OK_CANCEL_OPTION);

				if (username != null && username.length() > 0)
					{
					
					player = new Player(username);
					connect();
					
					}//END IF
				else
					{
					
					JOptionPane.showMessageDialog(null, "Please enter valid name", "Error", JOptionPane.ERROR_MESSAGE, null);
					System.exit(0);
					
					}//END ELSE
				} //END TRY BLOCK
			catch (Exception ex)
				{
				
				JOptionPane.showMessageDialog(null, "Please enter valid IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE, null);
				System.exit(0);
				
				}//END CATCH

			}//END CLIENTAPP()
		/*
		 * Connect()
		 * 
		 * this class allows a connection between server and client
		 * this class takes in data from the server and from client in input and output streams
		 * 
		 */
		private void connect()
			{

			try
				{
				
				connection = new Socket(clientAddress, clientPort);

				fromServer = new DataInputStream(connection.getInputStream());
				
				toServer = new DataOutputStream(connection.getOutputStream());

				//allows checkers to differentiate players
				player.setPlayerID(fromServer.readInt());

				Controller task = new Controller(player, fromServer, toServer);
				
				setup(task);

				new Thread(task).start();
				
				}//END TRY 
			catch (UnknownHostException e)
				
				{
				// if the IP address is invlaid or absent, throw error
				JOptionPane.showMessageDialog(null,  "Check properties for IPv4", "Error", JOptionPane.ERROR_MESSAGE, null);
				
				System.exit(0);
				
				} //END CATCH
			catch (IOException e)
				{
				
				JOptionPane.showMessageDialog(null, "Cannot connect to Server", "Error", JOptionPane.ERROR_MESSAGE, null);
				
				System.exit(0);
				
				}//END CATCH
			}//END CONNECT()

		
		/*
		 * Setup()
		 * 
		 * this class allows the board to be connected with a mouseTracker
		 * 
		 */
		private void setup(Controller c)
			{
			
			MouseController mouseTracker = new MouseController();
			mouseTracker.setController(c);

			boardPanel = new BoardPanel(mouseTracker);
			
			c.setBoardPanel(boardPanel);
			
			add(boardPanel);
			
			}//END SETUP()
	}
