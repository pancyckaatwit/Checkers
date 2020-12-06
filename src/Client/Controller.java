package Client;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Client.Checkers;
import Client.Player;
import Client.Square;

	public class Controller implements Runnable {
		private boolean continueToPlay;
		private boolean waitingForAction;
		private boolean endGame;
		
		//Network
		private DataInputStream fromServer;
		private DataOutputStream toServer;
		
		private BoardPanel boardPanel;
		private Player player;
		
		//Data
		private LinkedList<Square> selectedSquares;
		private LinkedList<Square> validTiles;
		//private LinkedList<Square> crossableSquares;
		
		public Controller(Player player, DataInputStream input, DataOutputStream output){
			this.player = player;
			this.fromServer = input;
			this.toServer= output;
			
			selectedSquares = new LinkedList<Square>();
			validTiles = new LinkedList<Square>();
		}
		
		public void setBoardPanel(BoardPanel panel)
		{
			this.boardPanel = panel;
		}
		
		@Override
		public void run()
		{
			continueToPlay = true;
			waitingForAction = true;
			endGame=false;
			
			try 
				{
				//Player One
				if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue())
				{
					//wait for the notification to start
					fromServer.readInt();
					player.setMyTurn(true);
				}
						
				while(continueToPlay && !endGame)
				{
					if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue())
					{
						waitForPlayerAction();
						if(!endGame)
							receiveInfoFromServer();
					}else if(player.getPlayerID()==Checkers.PLAYER_TWO.getValue())
					{
						receiveInfoFromServer();
						if(!endGame)
							waitForPlayerAction();
					}
				}
				
				if(endGame)
				{
					JOptionPane.showMessageDialog(null, "Game is over",
							"Information", JOptionPane.INFORMATION_MESSAGE, null);
					System.exit(0);
				}
				
			} catch (IOException e)
				{
				
				JOptionPane.showMessageDialog(null, "Connection lost",
						"Error", JOptionPane.ERROR_MESSAGE, null);
				System.exit(0);
				
			} catch (InterruptedException e)
				{
				JOptionPane.showMessageDialog(null, "Connection has failed",
						"Error", JOptionPane.ERROR_MESSAGE, null);
				
				}			
		}
		
		private void receiveInfoFromServer() throws IOException
		{
			player.setMyTurn(false);
			int from = fromServer.readInt();
			
			if(from==Checkers.YOU_LOST.getValue())
				{
				from = fromServer.readInt();
				int to = fromServer.readInt();
				updateReceivedInfo(from, to);
				endGame=true;
				}
			else if(from==Checkers.WINNER.getValue())
				{
				endGame=true;
				continueToPlay=false;
			}else
				{
				int to = fromServer.readInt();
				updateReceivedInfo(from, to);
				}
		}	

		private void sendMove(Square from, Square to) throws IOException
			{
			toServer.writeInt(from.getSquareID());
			toServer.writeInt(to.getSquareID());
			}

		private void waitForPlayerAction() throws InterruptedException
		{
			player.setMyTurn(true);
			while(waitingForAction){
				Thread.sleep(100);
			}
			waitingForAction = true;		
		}
		
		public void move(Square from, Square to){
			to.setPlayerID(from.getPlayerID());
			from.setPlayerID(Checkers.EMPTY_TILE.getValue());
			checkCrossJump(from, to);
			
			tileDeselected();
			
			waitingForAction = false;
			try
			{
				sendMove(from, to);
			} 
			catch (IOException e)
			{
				System.out.println("Sending failed");
			}		
		}
		
		//When a square is selected
		public void tileSelected(Square s)
		{
			
			if(selectedSquares.isEmpty())
				{
				addSelectedTile(s);
				}		
			//if one is already selected, check if it is possible move
			else if(selectedSquares.size()>=1){
				if(validTiles.contains(s)){
					move(selectedSquares.getFirst(),s);
				}else{
					tileDeselected();
					addSelectedTile(s);
				}
			}
		}
		
		private void addSelectedTile(Square s){
			s.setSelected(true);
			selectedSquares.add(s);
			getPlayableSquares(s);
			
			
		}

		public void tileDeselected() {
			
			for(Square square:selectedSquares)
				square.setSelected(false);
			
			selectedSquares.clear();
			
			for(Square square:validTiles){
				square.setPossibleToMove(false);
			}
			
			validTiles.clear();
			boardPanel.repaintPanels();
		}
		
		
		private void getPlayableSquares(Square s)
		{
			validTiles.clear();		
			validTiles = boardPanel.getPlayableSquares(s);
			
			for(Square square:validTiles){
				
				System.out.println(square.getSquareID());
			}		
			
			boardPanel.repaintPanels();
		}
		
		public boolean isPlayersTurn()
			{
			
			return player.isMyTurn();
			}
		
		private void checkCrossJump(Square from, Square to){		
			
			if(Math.abs(from.getSquareRow()-to.getSquareRow())==2)
				{		
				
				int middleRow = (from.getSquareRow() + to.getSquareRow())/2;
				int middleCol = (from.getSquareCol() + to.getSquareCol())/2;
				
				Square middleSquare = boardPanel.getSquare((middleRow*8)+middleCol+1);
				
				middleSquare.setPlayerID(Checkers.EMPTY_TILE.getValue());
				middleSquare.removeKing();
				}
		}
	
		
		private void updateReceivedInfo(int from, int to)
			{
			Square fromSquare = boardPanel.getSquare(from);
			Square toSquare = boardPanel.getSquare(to);
			
			toSquare.setPlayerID(fromSquare.getPlayerID());
			fromSquare.setPlayerID(Checkers.EMPTY_TILE.getValue());
			
			checkCrossJump(fromSquare, toSquare);
			
			boardPanel.repaintPanels();
		}
}
