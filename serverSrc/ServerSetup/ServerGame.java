package ServerSetup;
import ClientSetup.Tile;
import ServerCheckers.*;

/**
 * 
 *@author Alexander Pancyck
 * 
 */
public class ServerGame {
private Tile[][] tiles;
	
	//Constructor
	public ServerGame() {
		tiles=new Tile[8][8];
		initializeTiles();
		assignPlayerIDs();
	}

	public Tile getTile(int from) {
		for(Tile[] sRows:tiles){
			for(Tile t: sRows){
				if(t.getTileID()==from){
					return t;
				}
					
			}
		}		
		return null;
	}
	
	public Tile[][] getTiles(){
		return this.tiles;
	}
	
	public int getTotalTiles(){
		return tiles.length;
	}
	
	private void initializeTiles() 
		{
		boolean rowFirstOccupied;
		boolean isOccupied;
		int counter=0;
		
		//For loop to set the rows
		for(int i=0;i<Checkers.NUM_ROWS.getValue();i++)
			{
			rowFirstOccupied = (i%2 == 1) ? true : false;
				
			//For loop to set the columns
			for(int j=0;j<Checkers.NUM_COLS.getValue();j++)
				{
				isOccupied = (rowFirstOccupied && j%2 == 0) ?
						true : (!rowFirstOccupied && j%2 == 1) ? true : false;
				counter++;
					
				tiles[i][j] = new Tile(counter, i, j, isOccupied);
				}
			}		
		}
	
	private void assignPlayerIDs() 
		{
		//Rows 0-2 for player ONE
		for(int i=0;i<3;i++) {					
			//For loop for columns
			for(int j=0; j<Checkers.NUM_COLS.getValue(); j++){
				if(tiles[i][j].getIsOccupied()){
					tiles[i][j].setPlayerID(Checkers.PLAYER_ONE.getValue());
				}
			}
		}
		//Rows 5-7 for player TWO
		for(int i=5;i<8;i++){					
			//For loop for columns
			for(int j=0;j<Checkers.NUM_COLS.getValue();j++){
				if(tiles[i][j].getIsOccupied()){
					tiles[i][j].setPlayerID(Checkers.PLAYER_TWO.getValue());
				}
			}
		}
	}
	
	public void printAvailableSquareDetails(){
		
		int playerOne = 0;
		int playerTwo = 0;
		for(int i=0;i<Checkers.NUM_ROWS.getValue();i++){
			for(int j=0;j<Checkers.NUM_COLS.getValue();j++){
				if(tiles[i][j].getPlayerID()==1)
					playerOne++;
				
				if(tiles[i][j].getPlayerID()==2)
					playerTwo++;
			}
		}
		System.out.println("Player 1 has: " + playerOne);
		System.out.println("Player 2 has: " + playerTwo);
	}
	
	public boolean isOver(){
		
		int playerOne = 0;
		int playerTwo = 0;
		for(int i=0;i<Checkers.NUM_ROWS.getValue();i++){
			for(int j=0;j<Checkers.NUM_COLS.getValue();j++){
				if(tiles[i][j].getPlayerID()==1)
					playerOne++;
				
				if(tiles[i][j].getPlayerID()==2)
					playerTwo++;
			}
		}
		
		if(playerOne==0 || playerTwo==0){
			return true;
		}
		return false;
	}
}
