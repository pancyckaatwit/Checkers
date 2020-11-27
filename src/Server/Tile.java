package Server;

/*
 * Class for an individual spot on the overall board
 */

class Tile {
	
	//Variables for the tile class
	private int tileID;
	private int tileRow;
	private int tileColumn;
	private boolean isOccupied; 
	private int playerID;
	
	//Constructors for an individual tile
	public Tile(int tileID, int tileRow, int tileColumn, boolean isOccupied){
		this.tileID=tileID;
		this.tileRow=tileRow;
		this.tileColumn=tileColumn;
		this.setOccupied(isOccupied);
		
		if(this.isOccupied){
			//EMPTY_TILE will be in Checkers class not yet made
			this.playerID = Checkers.EMPTY_TILE.getValue();
		}
	}
	
	public boolean getIsOcuupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied=occupied;
	}
	
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}
	
	public int getPlayerID(int playerID) {
		return this.playerID;
	}
	
	public int getTileID() {
		return this.tileID;
	}
	
	public int getTileRow() {
		return this.tileRow;
	}
	
	public int getTileColumn() {
		return this.tileColumn;
	}
	
}
