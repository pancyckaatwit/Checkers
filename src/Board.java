import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
	
	//Static variables for number of rows/columns and color of the board
	public static int rows=8;
	public static int columns=8;
	public static Color color1=Color.black;
	public static Color color2=Color.white;
	
	
	public static void main(String[] args) {
		//Variable for board and size of board along with title
		JFrame board=new JFrame();
		board.setSize(750, 750);
		board.setTitle("Checker Board");
		
		//Sets layout for the game board
		Container pane=board.getContentPane();
		pane.setLayout(new GridLayout(rows, columns));
		
		//Sets a temporary color to alternate and make each tile on the board
		Color current;
		
		//For loop that loops through the size of the board and sets tile colors
		for(int i=0; i<rows; i++) {
			if(i%2==0) {
				current=color1;
			}else {
				current=color2;
			}
			for(int j=0; j<columns; j++) {
				JPanel panel=new JPanel();
				panel.setBackground(current);
				if(current.equals(color1)) {
					current=color2;
				}else {
					current=color1;
				}
				pane.add(panel);
			}
		}
		//Allows the board to be able to be seen
		board.setVisible(true);
	}
}
