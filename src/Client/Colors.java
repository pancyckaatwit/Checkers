package Client;

import java.awt.Color;

public enum Colors {

	BLACK(Color.BLACK),
	WHITE(Color.WHITE),
	RED(Color.RED);
	
	private Color color;
	
	//Constructor
	Colors(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public static Color getMyColor(int colorID){
		if(colorID==1){
			return RED.getColor();
		}
		else if(colorID==2){
			return WHITE.getColor();
		}
		
		return null;
	}
}
