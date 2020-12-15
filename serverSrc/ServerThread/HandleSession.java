package ServerThread;
import javax.swing.*;

import ClientSetup.Tile;
import ServerCheckers.Checkers;
import ServerSetup.ServerGame;
import ServerSetup.ServerPlayer;

import java.io.*;
import java.net.*;
import java.awt.*;

/**
 * Server Application --> Handle Session
 * 
 * Handle Game Logic and Player requests
 */
public class HandleSession implements Runnable {
	
	private ServerGame checkers;
	private ServerPlayer player1;
	private ServerPlayer player2;
	
	private boolean continueToPlay=true;
	
	//Construction thread
	public HandleSession(Socket p1, Socket p2){
		player1 = new ServerPlayer(Checkers.PLAYER_ONE.getValue(), p1);
		player2 = new ServerPlayer(Checkers.PLAYER_TWO.getValue(), p2);
		
		checkers = new ServerGame();
	}
	
	public void run() {		
		
		//Sends Data back and forth		
		try{
				//notifies Player 1 to start
				player1.sendData(1);				
				
				while(continueToPlay){
					//wait for player 1's Action
					int from = player1.receiveData();
					int to = player1.receiveData();
					checkStatus(from, to);
					updateGameModel(from, to);
							
					//Send the Data back to 2nd Player
					if(checkers.isOver()) {
						//Game Over notification
						player2.sendData(Checkers.YOU_LOST.getValue());
					}
					int fromStatus = player2.sendData(from);
					int toStatus = player2.sendData(to);
					checkStatus(fromStatus,toStatus);
					
					//If the game is over, break
					if(checkers.isOver()){
						player1.sendData(Checkers.WINNER.getValue());
						continueToPlay=false;
						break;
					}
					
					System.out.println("after break");
					
					//Now waits for the 2nd players action
					from = player2.receiveData();
					to = player2.receiveData();
					checkStatus(from, to);
					updateGameModel(from, to);					
					
					//Send Data back to 1st Player
					if(checkers.isOver()) {
						//Game Over notification
						player1.sendData(Checkers.YOU_LOST.getValue());
					}					
					fromStatus = player1.sendData(from);
					toStatus = player1.sendData(to);
					checkStatus(fromStatus,toStatus);
					
					//If the game is over, it breaks
					if(checkers.isOver()){
						player2.sendData(Checkers.WINNER.getValue());
						continueToPlay=false;
						break;
					}
					
					System.out.println("second break");
				}
				
				
				
		}catch(Exception ex){
			System.out.println("Connection is being closed");
			
			if(player1.isOnline()) {
				player1.closeConnection();
			}
			
			if(player2.isOnline()) {
				player2.closeConnection();
			}
			return;
		}
	}
	
	private void checkStatus(int status, int status2) throws Exception{
		if(status==99 || status2==99){
			throw new Exception("Connection is lost");
		}
	}
	
	private void updateGameModel(int from, int to){
		Tile fromTile = checkers.getTile(from);
		Tile toTile = checkers.getTile(to);
		toTile.setPlayerID(fromTile.getPlayerID());
		fromTile.setPlayerID(Checkers.EMPTY_TILE.getValue());
		
		checkCrossJump(fromTile, toTile);
	}
	
	private void checkCrossJump(Tile from, Tile to){		
		if(Math.abs(from.getTileRow()-to.getTileRow())==2){		
			int middleRow = (from.getTileRow() + to.getTileRow())/2;
			int middleCol = (from.getTileColumn() + to.getTileColumn())/2;
			
			Tile middleSquare = checkers.getTile((middleRow*8)+middleCol+1);
			middleSquare.setPlayerID(Checkers.EMPTY_TILE.getValue());
		}
	}
}