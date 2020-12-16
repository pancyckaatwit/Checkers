package ClientSetup;

import ClientCheckers.Checkers;
import ClientCheckers.SessionVariable;

/**
 * 
 * @author Jake Giguere
 * 
 * ClientApplication
 */
public class Tile {
	
	private int TileID;
	private int TileRow;
	private int TileColumn;	
	private boolean isOccupied;
	private boolean selected;
	private boolean isPossibleToMove;
	private int playerID;	
	
	//Constructor
	public Tile(int TileID, int TileRow, int TileColumn, boolean isOccupied) {
		this.TileID=TileID;
		this.TileRow=TileRow;
		this.TileColumn=TileColumn;
		this.setIsOccupied(isOccupied);
		
		if(this.isOccupied){
			this.playerID = Checkers.EMPTY_TILE.getValue();
		}
		
		this.selected = false;
		this.isPossibleToMove = false;
	}

	public int getTileRow(){
		return this.TileRow;
	}
	
	public int getTileColumn(){
		return this.TileColumn;
	}
	
	public boolean getIsOccupied() {
		return isOccupied;
	}

	private void setIsOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	public void setPlayerID(int ID){
		this.playerID=ID;
	}
	
	public int getPlayerID(){
		return this.playerID;
	}
	
	public int getTileID(){
		return this.TileID;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isPossibleToMove() {
		return isPossibleToMove;
	}

	public void setPossibleToMove(boolean isPossibleToMove) {
		this.isPossibleToMove = isPossibleToMove;
	}
	
	public boolean isOpponentTile(){
		if(playerID!=SessionVariable.myID.getValue() && playerID!=Checkers.EMPTY_TILE.getValue())
			return true;
		else
			return false;
	}
}
