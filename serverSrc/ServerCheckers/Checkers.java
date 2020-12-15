package ServerCheckers;

/**
 * Server Application 
 * 
 * 
 * Checker Constants
 */
public enum Checkers {	
	
	NUM_ROWS(8),
	NUM_COLS(8),
	TOTAL_PIECES(12),
	TOTAL_TILES(64),
	PLAYABLE_TILES(32),
	EMPTY_TILE(0),
	PLAYER_ONE(1),
	PLAYER_TWO(2),
	WINNER(90),
	YOU_LOST(91),
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
