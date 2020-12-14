package Client;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Client.Checkers;
import Client.Player;
import Client.Tile;

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
		private LinkedList<Tile> selectedTiles;
		private LinkedList<Tile> validTiles;
		//private LinkedList<Square> crossableSquares;
		
		public Controller(Player player, DataInputStream input, DataOutputStream output){
			this.player = player;
			this.fromServer = input;
			this.toServer= output;
			
			selectedTiles = new LinkedList<Tile>();
			validTiles = new LinkedList<Tile>();
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

		private void sendMove(Tile from, Tile to) throws IOException
			{
			toServer.writeInt(from.getTileID());
			toServer.writeInt(to.getTileID());
			}

		private void waitForPlayerAction() throws InterruptedException
		{
			player.setMyTurn(true);
			while(waitingForAction){
				Thread.sleep(100);
			}
			waitingForAction = true;		
		}
		
		public void move(Tile from, Tile to){
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
		public void tileSelected(Tile t)
		{
			
			if(selectedTiles.isEmpty())
				{
				addSelectedTile(t);
				}		
			//if one is already selected, check if it is possible move
			else if(selectedTiles.size()>=1){
				if(validTiles.contains(t)){
					move(selectedTiles.getFirst(),t);
				}else{
					tileDeselected();
					addSelectedTile(t);
				}
			}
		}
		
		private void addSelectedTile(Tile t){
			t.setSelected(true);
			selectedTiles.add(t);
			getPlayableTiles(t);
			
			
		}

		public void tileDeselected() {
			
			for(Tile tile:selectedTiles)
				tile.setSelected(false);
			
			selectedTiles.clear();
			
			for(Tile tile:validTiles){
				tile.setPossibleToMove(false);
			}
			
			validTiles.clear();
			boardPanel.repaintPanels();
		}
		
		
		private void getPlayableTiles(Tile t)
		{
			validTiles.clear();		
			validTiles = boardPanel.getPlayableSquares(t);
			
			for(Tile tile:validTiles){
				
				System.out.println(tile.getTileID());
			}		
			
			boardPanel.repaintPanels();
		}
		
		public boolean isPlayersTurn()
			{
			
			return player.isMyTurn();
			}
		
		private void checkCrossJump(Tile from, Tile to){		
			
			if(Math.abs(from.getTileRow()-to.getTileRow())==2)
				{		
				
				int middleRow = (from.getTileRow() + to.getTileRow())/2;
				int middleColumn = (from.getTileColumn() + to.getTileColumn())/2;
				
				Tile middleTile = boardPanel.getTile((middleRow*8)+middleColumn+1);
				
				middleTile.setPlayerID(Checkers.EMPTY_TILE.getValue());
				}
		}
	
		
		private void updateReceivedInfo(int from, int to) {
			Tile fromTile = boardPanel.getTile(from);
			Tile toTile = boardPanel.getTile(to);
			
			toTile.setPlayerID(fromTile.getPlayerID());
			fromTile.setPlayerID(Checkers.EMPTY_TILE.getValue());
			
			checkCrossJump(fromTile, toTile);
			
			boardPanel.repaintPanels();
		}
}
