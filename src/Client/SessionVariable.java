package Client;

import Client.MouseController;

public enum SessionVariable {
	
	myID(3);
	public static MouseController mouseListener = new MouseController();
	
	private int value;
	
	SessionVariable(int value){
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
