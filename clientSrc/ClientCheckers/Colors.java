package ClientCheckers;

import java.awt.Color;

/**
 *
 *
 * 
 * Colors
 */
public enum Colors {
	BLACK(Color.BLACK),
	WHITE(Color.WHITE),
	GREEN(Color.GREEN),
	BLUE(Color.BLUE),
	MAGENTA(Color.MAGENTA),
	YELLOW(Color.YELLOW);
	
	
	//properties
	private Color color;
	
	//Constructor
	Colors(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public static Color getDefaultColor(int ID){
		if(ID==1){
			return GREEN.getColor();
		}
		else if(ID==2){
			return BLUE.getColor();
		}
		
		return null;
	}
	
	public static Color getFocusedColor(int ID){
		if(ID==1){
			return MAGENTA.getColor();
		}
		else if(ID==2){
			return YELLOW.getColor();
		}		
		return null;
	}
}
