package Client;

public enum Checkers {
	
	
	NUM_ROWS(8),
	NUM_COLS(8),
	TOTAL_PIECES(12),
	TOTAL_SQUARES(64),
	PLAYABLE_SQUARES(32),
	PLAYER_ONE(1),
	PLAYER_TWO(2),
	WINNER(90),
	YOU_LOST(91),
	ROYALTY_JUMP(92), 
	EMPTY_TILE(0);
	
	private int value;
	
	private Checkers(int value) {
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}	
}
