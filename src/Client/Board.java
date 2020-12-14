package Client;

import java.util.LinkedList;
import Client.Tile;
/*
 * Class for the board to be played on
 */

public class Board {
	
private Tile[][] tiles;
	
	public Board(){
		tiles = new Tile[8][8];
		setSquares();
		assignPlayerIDs();
	}
	
	private void setSquares() {
		boolean rowInitialFilled, isFilled;
		int count = 0;
		
		//Rows
		for(int r=0;r<Checkers.NUM_ROWS.getValue();r++) {
			rowInitialFilled = (r%2 == 1) ? true : false;
			
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				isFilled = (rowInitialFilled && c%2 == 0) ? true : (!rowInitialFilled && c%2 == 1) ? true : false;
				count++;
				
				tiles[r][c] = new Tile(count, r, c, isFilled);
			}
		}		
	}

	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public int getTotlaSquares() {
		return tiles.length;
	}
	
	public void printSquareDetails() {
		for(int r=0;r<Checkers.NUM_ROWS.getValue();r++) {
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				System.out.println(tiles[r][c].getTileID() + " --"+ tiles[r][c].isPossibleToMove());
			}
		}
	}
	
	private void assignPlayerIDs() {
		
		//Rows 0-2 for player ONE
		for(int r=0;r<3;r++) {					
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				if(tiles[r][c].getIsOccupied()){
					tiles[r][c].setPlayerID(Checkers.PLAYER_ONE.getValue());
				}
			}
		}
		
		//Rows 5-7 for player TWO
		for(int r=5;r<8;r++) { 					
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++) {
				if(tiles[r][c].getIsOccupied()){
					tiles[r][c].setPlayerID(Checkers.PLAYER_TWO.getValue());
				}
			}
		}
	}
	
	public LinkedList<Tile> findPlayableSquares(Tile selectedTile) {
		
		LinkedList<Tile> playableSquares = new LinkedList<Tile>();
		
		int selectedRow = selectedTile.getTileRow();
		int selectedCol = selectedTile.getTileColumn();
		
		int movableRow = (selectedTile.getPlayerID()==1) ? selectedRow+1 : selectedRow-1;
		
		//check two front squares
		twoFrontSquares(playableSquares, movableRow, selectedCol);
		crossJumpFront(playableSquares, (selectedTile.getPlayerID()==1) ? movableRow+1 : movableRow-1, selectedCol, movableRow);
		return playableSquares;		
	}
	
	//check two front squares
	private void twoFrontSquares(LinkedList<Tile> pack, int movableRow, int selectedColumn) {
		
		if(movableRow>=0 && movableRow<8) {
			//right Corner
			if(selectedColumn>=0 && selectedColumn<7) {
				Tile rightCorner = tiles[movableRow][selectedColumn+1];
				if(rightCorner.getPlayerID()==0){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//left upper corner
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
	private void crossJumpFront(LinkedList<Tile> pack, int movableRow, int selectedCol, int middleRow){
		
		int middleColumn;
		
		if(movableRow>=0 && movableRow<8) {
			//right upper Corner
			if(selectedCol>=0 && selectedCol<6){
				Tile rightCorner = tiles[movableRow][selectedCol+2];				
				middleColumn = (selectedCol+selectedCol+2)/2;				
				if(rightCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleColumn)){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//left upper corner
			if(selectedCol>1 && selectedCol <=7) {
				Tile leftCorner = tiles[movableRow][selectedCol-2];
				middleColumn = (selectedCol+selectedCol-2)/2;
				if(leftCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleColumn)){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	private boolean isOpponentInbetween(int row, int col) {
		return tiles[row][col].isOpponentTile();
	}
}
