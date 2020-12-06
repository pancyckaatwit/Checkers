package Client;

import java.awt.Color;

public enum Colors {

	BLACK(Color.BLACK),
	WHITE(Color.WHITE),
	RED(Color.RED),
	ORANGE(new Color(255,144,0)),
	PURPLE(new Color(128,100,162)),
	GREEN(Color.GREEN);
	
	
	//properties
	private Color color;
	
	//Constructor
	Colors(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public static Color getMyDefaultColor(int ID){
		if(ID==1){
			return RED.getColor();
		}
		else if(ID==2){
			return ORANGE.getColor();
		}
		
		return null;
	}
	
	public static Color getFocusedColor(int ID){
		if(ID==1){
			return PURPLE.getColor();
		}
		else if(ID==2){
			return GREEN.getColor();
		}		
		return null;
	}
}
