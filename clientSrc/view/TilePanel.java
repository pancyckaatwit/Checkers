package view;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Tile;
import constant.*;
import handler.MouseController;

/**
 * Client Application -> SquarePanel
 * @author Jake Giguere
 * 
 * Square Panel
 */
public class TilePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static final int WHEN_FOCUSED = 0;
	
	private Tile tile;
	private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);
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
		int squareplayerID = tile.getPlayerID();
		if(isClicked()){
			g2.setColor(Colors.getFocusedColor(squareplayerID));
			paint(g2);
		}else{
			if(squareplayerID==1 || squareplayerID == 2){
				if(focused){
					g2.setColor(Colors.getFocusedColor(squareplayerID));
				}else{
					g2.setColor(Colors.getDefaultColor(squareplayerID));					
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
	 * Mouse Listener 
	 */
	class MouseHandler extends MouseAdapter {
		
		public void mouseEntered(MouseEvent e){	
			super.mouseEntered(e);
			focused = true;
		}
		
		public void mouseExited(MouseEvent e){
			super.mouseExited(e);
			focused = false;
		}
		
		public void mousePressed(MouseEvent e) {
			if(isClicked()){
				System.out.println("deselect - "+tile.getTileID());
				resetClicked();
			}
			else if(tile.getPlayerID()==SessionVariable.myID.getValue()){
				System.out.println("select - "+tile.getTileID());
				setClicked();	
			}
		}
	}
}
