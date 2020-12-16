package ClientSetup;
import java.util.LinkedList;

import ClientCheckers.Checkers;

/**
 * 
 * @author Jake Giguere
 * 
 */
public class Board {
	
private Tile[][] tiles;
	
	public Board(){
		tiles = new Tile[8][8];
		setTiles();
		assignPlayerIDs();
	}
	//Constructor
	public Tile[][] getTiles() {
		return this.tiles;
	}
	//Constructor
	public int getTotalTiles() {
		return tiles.length;
	}
	
	//This class sets the tiles for the board
	private void setTiles() {
		boolean rowInitialOccupied, isOccupied;
		int count = 0;
		
		//Rows
		for(int r=0;r<Checkers.NUM_ROWS.getValue();r++) {
			rowInitialOccupied = (r%2 == 1) ? true : false;
			
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				isOccupied = (rowInitialOccupied && c%2 == 0) ? true : (!rowInitialOccupied && c%2 == 1) ? true : false;
				count++;
				
				tiles[r][c] = new Tile(count, r, c, isOccupied);
			}
		}		
	}
	
	//Assigns each player to a set of rows
	private void assignPlayerIDs() {
		//Rows 0-2 for Player One
		for(int r=0;r<3;r++) {					
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				if(tiles[r][c].getIsOccupied()){
					tiles[r][c].setPlayerID(Checkers.PLAYER_ONE.getValue());
				}
			}
		}
		
		//Rows 5-7 for Player Two
		for(int r=5;r<8;r++) { 					
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				if(tiles[r][c].getIsOccupied()){
					tiles[r][c].setPlayerID(Checkers.PLAYER_TWO.getValue());
				}
			}
		}
	}
	
	public LinkedList<Tile> findPlayableTiles(Tile selectedTile) {
		
		LinkedList<Tile> playableTiles = new LinkedList<Tile>();
		
		int selectedRow = selectedTile.getTileRow();
		int selectedCol = selectedTile.getTileColumn();
		
		int movableRow = (selectedTile.getPlayerID()==1) ? selectedRow+1 : selectedRow-1;
		
		//check two front squares
		twoFrontTiles(playableTiles, movableRow, selectedCol);
		crossJumpFront(playableTiles, (selectedTile.getPlayerID()==1) ? movableRow+1 : movableRow-1, selectedCol, movableRow);
		return playableTiles;		
	}
	
	//Checks the two front tiles to check if they are a viable spot
	private void twoFrontTiles(LinkedList<Tile> pack, int movableRow, int selectedColumn) {
		if(movableRow>=0 && movableRow<8) {
			//Right tile
			if(selectedColumn>=0 && selectedColumn<7) {
				Tile rightCorner = tiles[movableRow][selectedColumn+1];
				if(rightCorner.getPlayerID()==0){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//Left Tile
			if(selectedColumn>0 && selectedColumn <=8) {
				Tile leftCorner = tiles[movableRow][selectedColumn-1];
				if(leftCorner.getPlayerID()==0){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	//cross jump - two front
	private void crossJumpFront(LinkedList<Tile> pack, int movableRow, int selectedColumn, int middleRow){
		int middleColumn;
		if(movableRow>=0 && movableRow<8) {
			//Right tile
			if(selectedColumn>=0 && selectedColumn<6){
				Tile rightCorner = tiles[movableRow][selectedColumn+2];				
				middleColumn = (selectedColumn+selectedColumn+2)/2;				
				if(rightCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleColumn)){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			//Left tile
			if(selectedColumn>1 && selectedColumn <=7) {
				Tile leftCorner = tiles[movableRow][selectedColumn-2];
				middleColumn = (selectedColumn+selectedColumn-2)/2;
				if(leftCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleColumn)){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	private boolean isOpponentInbetween(int row, int column) {
		return tiles[row][column].isOpponentTile();
	}
}
