package ClientView;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ClientCheckers.*;
import ClientController.MouseController;
import ClientSetup.Tile;

/**
 *
 * @author Jake Giguere
 * 
 * Tile Panel on Board UI
 */
public class TilePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Tile tile;
	private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.black);
	private Border thickBorder = BorderFactory.createLineBorder(Colors.WHITE.getColor(),5);
	private boolean focused;
	private MouseHandler handler;
	
	//Constructor
	public TilePanel(Tile t){
		this.tile = t;
		this.focused = false;
		handler = new MouseHandler();
		setListener();		
	}
	
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponents(g2);
		
		//Fill black color
		g2.setColor(Colors.BLACK.getColor());
		if(tile.getIsOccupied()){
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
		
		//fill piece color
		int tileplayerID = tile.getPlayerID();
		if(isClicked()){
			g2.setColor(Colors.getColor(tileplayerID));
			paint(g2);
		}else{
			if(tileplayerID==1 || tileplayerID == 2){
				if(focused){
					g2.setColor(Colors.getColor(tileplayerID));
				}else{
					g2.setColor(Colors.getColor(tileplayerID));					
				}
				paint(g2);
			}
		}
		
		//Hover effect for possible move
		if(tile.isPossibleToMove()){
			if(focused){				
				setBorder(defaultBorder);
			}else{				
				setBorder(null);
			}
		}else{
			setBorder(null);
		}
	}
	
	public void setListener() {
		if(tile.isPossibleToMove() || tile.getPlayerID()==SessionVariable.myID.getValue()){
			this.removeMouseListener(handler);
			this.addMouseListener(handler);
		}else{
			this.removeMouseListener(handler);
		}
	}
	
	public void setListener(MouseController MyListner){
		setListener();
		if(tile.isPossibleToMove() || tile.getPlayerID()==SessionVariable.myID.getValue()){
			this.removeMouseListener(MyListner);
			this.addMouseListener(MyListner);
		}else{
			this.removeMouseListener(MyListner);
		}
	}
	
	//return Tile
	public Tile getTile(){
		return this.tile;
	}
	
	//return clicked
	public boolean isClicked(){
		return this.tile.isSelected();
	}
	
	//reset clicked to false
	public void resetClicked(){
		this.tile.setSelected(false);
	}
	
	//reset clicked to true
	public void setClicked(){
		this.tile.setSelected(true);
	}
	
	private void paint(Graphics2D g2){
		int padding= 24;
		g2.fillOval(padding/2, padding/2, getWidth()-padding, getHeight()-padding);			
	}
	
	/**
	 * Mouse Controller 
	 */
	class MouseHandler extends MouseAdapter {
		
		public void mouseEntered(MouseEvent e){	
			super.mouseEntered(e);
			focused = true;
			repaint();
		}
		
		public void mouseExited(MouseEvent e){
			super.mouseExited(e);
			focused = false;
			repaint();
		}
		
		public void mousePressed(MouseEvent e) {
			if(isClicked()){
				
				resetClicked();
			}
			else if(tile.getPlayerID()==SessionVariable.myID.getValue()){
				setClicked();	
			}
		}
	}
}
