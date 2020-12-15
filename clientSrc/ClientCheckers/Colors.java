package ClientCheckers;

import java.awt.Color;

/**
 * Colors
 */
public enum Colors {
	BLACK(Color.BLACK),
	WHITE(Color.WHITE),
	RED(Color.RED);
	
	//properties
	private Color color;
	
	//Constructor
	Colors(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public static Color getColor(int ID){
		if(ID==1){
			return RED.getColor();
		}
		else if(ID==2){
			return WHITE.getColor();
		}
		
		return null;
	}
	
	public static Color getFocusedColor(int ID){
		if(ID==1){
			return RED.getColor();
		}
		else if(ID==2){
			return WHITE.getColor();
		}		
		return null;
	}
}
