
/*
 * Class for the board to be played on
 */

public class Board {
	
	private Tile[][] tiles;
	
	public Board() {
		tiles=new Tile[8][8];
		setTiles();
	}
	
	private void setTiles() {
		boolean rowInitialOccupied, isOccupied;
		int counter=0;
		
		//NUM_ROWS will be delcared in Checkers class not yet created
		//Calculation for for setting the rows of tiles
		for(int i=0; i<Checkers.NUM_ROWS.getValue; i++) {
			if(i%2==1) {
				rowInitialOccupied=true;
			}else {
				rowInitialOccupied=false;
			}
			//NUM_COLUMNS will be delcared in Checkers class not yet created
			//Calculation for setting the columns of tiles
			for(int j; j<Checkers.NUM_COLUMNS.getValue; j++) {
				
				counter++;
				tiles[i][j]=new Tile(counter, i, j, isOccupied);
			}
		}
	}
}
