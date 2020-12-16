package ServerCheckers;

/**
 * 
 * @author Alexander Pancyck
 * 
 * 
 */
public enum Checkers {	
	//Number of rows
	NUM_ROWS(8),
	//Number of columns
	NUM_COLS(8),
	//Pieces for one player
	TOTAL_PIECES(12),
	//Total number of tiles on board
	TOTAL_TILES(64),
	//Total number of usable tiles
	PLAYABLE_TILES(32),
	//Represents an empty tile
	EMPTY_TILE(0),
	//Represents player one
	PLAYER_ONE(1),
	//Represents player two
	PLAYER_TWO(2),
	//Designates Winner
	YOU_WIN(90),
	//Designates Loser
	YOU_LOST(91),
	//Represents a double jump
	DOUBLE_JUMP(92);
	
	private int value;
	
	public int getValue() {
		return this.value;
	}
	
	//Constructor
	private Checkers(int value) {
		this.value=value;
	}

}
