package ClientSetup;

import ClientCheckers.SessionVariable;

/**
 * 
 * @author  Jake Giguere
 * 
 */
public class Player {
	
	private String name;
	private int playerID;
	private boolean myTurn;
	
	
	public Player(String name){
		this.name = name;
		
		setPlayerTurn(false);
	}

	public String getName(){
		return this.name;
	}
	
	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		SessionVariable.myID.setValue(playerID);
	}


	public boolean isPlayerTurn() {
		return myTurn;
	}


	public void setPlayerTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	
}
