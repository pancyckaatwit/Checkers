package Client;


import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
import javax.swing.*;

import Client.Controller;
import Client.Player;
import Client.BoardPanel;

import java.io.*;
import java.net.*;
import java.util.Random;

import Client.MouseController;

import Client.BoardPanel;

import java.io.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApplication extends JFrame {
	
	//this is a default serialVersionUID
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
	public ClientApplication() {

		try {
			PropertyManager pm = PropertyManager.getInstance();
			
			address = pm.getAddress();
			port = pm.getPort();

			String name = (String) JOptionPane.showInputDialog(null, "Enter your name to Connect", "Connect to Server", JOptionPane.OK_CANCEL_OPTION);

			if (name != null && name.length() > 0) {
				player = new Player(name);
				connect();
			} else {
				JOptionPane.showMessageDialog(null, "Please enter valid name", "Error", JOptionPane.ERROR_MESSAGE, null);
				System.exit(0);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Please enter valid IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}

	}

	private void connect() {

		try {
			connection = new Socket(address, port);

			// Should error occurs, handle it in a separate thread
			fromServer = new DataInputStream(connection.getInputStream());
			toServer = new DataOutputStream(connection.getOutputStream());

			// First player get 1 and second player get 2
			player.setPlayerID(fromServer.readInt());

			Controller task = new Controller(player, fromServer, toServer);
			setup(task);

			new Thread(task).start();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Internal Server Error - Check your IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't connect to Server. Please try again", "Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
	}

	private void setup(Controller c) {
		MouseController listener = new MouseController();
		listener.setController(c);

		boardPanel = new BoardPanel(listener);
		c.setBoardPanel(boardPanel);
		add(boardPanel);
		
	}

	private void add(BoardPanel boardPanel) {
		this.boardPanel.add(boardPanel);
	}
}
