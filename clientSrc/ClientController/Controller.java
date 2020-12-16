package ClientController;
import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import ClientCheckers.Checkers;
import ClientSetup.Player;
import ClientSetup.Tile;
import ClientView.BoardPanel;
import ServerSetup.ServerPlayer;

/**
 * Client Application -> Controller
 * The controller class 
 * 
 * ClientApp
 */
public class Controller implements Runnable {
	private boolean continueGame;
	private boolean responseWaiting;
	private boolean endGame;
	private LinkedList<Tile> highlightedTiles;
	private LinkedList<Tile> validTiles;

	private BoardPanel boardPanel;
	private Player player;
	
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	
	public Controller(Player player, DataInputStream input, DataOutputStream output)
		{
		this.player = player;
		this.fromServer = input;
		this.toServer= output;
		
		highlightedTiles = new LinkedList<Tile>();
		validTiles = new LinkedList<Tile>();
		}
	
	public void setBoardPanel(BoardPanel panel)
		{
		this.boardPanel = panel;
		}
	
	@Override
	public void run()
		{
		continueGame = true;
		responseWaiting = true;
		endGame=false;
		
		try 
			{
			//Player One
			if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue())
				{
				//wait for the notification to start
				fromServer.readInt();
				player.setMyTurn(true);
				
				}//END IF
					
			while(!endGame && continueGame)
				{
				
				if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue())
					{
					waitForPlayerAction();
					
					if(!endGame) 
						{
						
						receiveInfoFromServer();
						
						}
					}//END IF
				
				else if(player.getPlayerID()==Checkers.PLAYER_TWO.getValue())
					{
					receiveInfoFromServer();
					
					if(!endGame)
						{
						
						waitForPlayerAction();
						
						}
					}//END ELSE IF
				}//END WHILE
			
			if(endGame)
				{
				
				JOptionPane.showMessageDialog(null, "This session has ended", "Information", JOptionPane.INFORMATION_MESSAGE, null);
				
				System.exit(0);
				
				}//end IF
			
			}//End try 
		
		catch (IOException e)
			{
			
			JOptionPane.showMessageDialog(null, "Connection lost", "Error", JOptionPane.ERROR_MESSAGE, null);
			
			System.exit(0);
			
		} catch (InterruptedException e)
			{
			
			JOptionPane.showMessageDialog(null, "Connection has failed", "Error", JOptionPane.ERROR_MESSAGE, null);
			
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
			continueGame=false;
			
			}
		
		else
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
		
		while(responseWaiting)
			{
			
			Thread.sleep(100);
			
			}//end While
		responseWaiting = true;		
		
		}//end method
	
	public void move(Tile from, Tile to) throws IOException
		{
		
		to.setPlayerID(from.getPlayerID());
		from.setPlayerID(Checkers.EMPTY_TILE.getValue());
		checkCrossJump(from, to);
		
		tileDeselected();
		
		responseWaiting = false;
		sendMove(from, to);
		
		}
	
	//When a square is selected
	public void tileSelected(Tile t) throws IOException
		{
		
		if(highlightedTiles.isEmpty())
			{
			
			addSelectedTile(t);
			
			}	
		
		//checking for a possible move
		else if(highlightedTiles.size()>=1)
			{
			
			if(validTiles.contains(t))
				
				{
				
				move(highlightedTiles.getFirst(),t);
				
				}
			
			else
				{
				
				tileDeselected();
				
				addSelectedTile(t);
				
				}
			}
		}
	
	private void addSelectedTile(Tile t)
		{
		
		t.setSelected(true);
		
		highlightedTiles.add(t);
		
		getPlayableTiles(t);
		
		}

	public void tileDeselected()
		{
		
		for(Tile tile:highlightedTiles) 
			{
			
			tile.setSelected(false);
			
			}
		
		highlightedTiles.clear();
		
		for(Tile tile:validTiles)
			{
			
			tile.setPossibleToMove(false);
			
			}
		
		validTiles.clear();
		
		boardPanel.repaintPanels();
		
		}
	
	
	private void getPlayableTiles(Tile t)
		{
		
		validTiles.clear();
		
		validTiles = boardPanel.getPlayableTiles(t);
		
		for(Tile tile:validTiles)
			{
			
			System.out.println(tile.getTileID());
			
			}		
		
		boardPanel.repaintPanels();
		}
	
	
	
	
	public boolean isPlayersTurn()
		{
		
		return player.isMyTurn();
		
		}
	
	
	
	
	private void checkCrossJump(Tile from, Tile to)
		{		
		
		if(Math.abs(from.getTileRow()-to.getTileRow())==2)
			{		
			
			int midRow = (from.getTileRow() + to.getTileRow())/2;
			int midColumn = (from.getTileColumn() + to.getTileColumn())/2;
			
			Tile midTile = boardPanel.getTile((midRow*8)+midColumn+1);
			
			midTile.setPlayerID(Checkers.EMPTY_TILE.getValue());
			
			}
		}

	
	private void updateReceivedInfo(int from, int to)
		{
		
		Tile fromTile = boardPanel.getTile(from);
		
		Tile toTile = boardPanel.getTile(to);
		
		toTile.setPlayerID(fromTile.getPlayerID());
		fromTile.setPlayerID(Checkers.EMPTY_TILE.getValue());
		
		checkCrossJump(fromTile, toTile);
		
		boardPanel.repaintPanels();
		
		}
}
