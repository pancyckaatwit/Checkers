package constants;

/**
 * Server Application -> Enum Checkers
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
	
	
	/* Some more Constants 
	  public static int PLAYER1_WON = 1; // Indicate player 1 won
	  public static int PLAYER2_WON = 2; // Indicate player 2 won
	  public static int DRAW = 3; // Indicate a draw
	  public static int CONTINUE = 4; // Indicate to continue
	  */
}
