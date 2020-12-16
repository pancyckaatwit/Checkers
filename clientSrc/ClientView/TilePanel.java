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
 * his class serves to track what players click on the game board
 */
public class TilePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private boolean focused;
	private MouseHandler handler;
	private Tile tile;
	private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.black);
	
	public TilePanel(Tile tile)
		{
		
		this.tile = tile;
		this.focused = false;
		
		handler = new MouseHandler();
		
		setListener();		
		
		}
	
	public Tile getTile()
		{
		
		return this.tile;
		
		}
	
	
	public boolean isClicked()
		{
		
		return this.tile.isSelected();
		
		}
	
	
	public void resetClicked()
		{
		
		this.tile.setSelected(false);
		
		}
	
	
	public void setClicked()
		{
		
		this.tile.setSelected(true);
		
		}
	
	
	private void paint(Graphics2D graphics)
		{
		
		int layers= 24;
		
		graphics.fillOval(layers/2, layers/2, getWidth()-layers, getHeight()-layers);			
		
		}
		
	protected void paintComponent(Graphics g)
		{
		
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponents(graphics);
		
		//Fill black color
		graphics.setColor(Colors.BLACK.getColor());
		if(tile.getIsOccupied())
			{
			
			graphics.fillRect(0, 0, getWidth(), getHeight());
			
			}
		
		//fill piece color
		int tileplayerID = tile.getPlayerID();
		
		if(isClicked())
			{
			
			graphics.setColor(Colors.getColor(tileplayerID));
			
			paint(graphics);
			
			}
		else
			{
			
			if(tileplayerID==1 || tileplayerID == 2)
				{
				
				if(focused)
					{
					
					graphics.setColor(Colors.getColor(tileplayerID));
				
					}
								
				}
				paint(graphics);
			}
		
		
		//Hover effect for possible move
		if(tile.isPossibleToMove())
		{
			if(focused)
			{	
				
				setBorder(defaultBorder);
				
			}
			
			else
			{	
				
				setBorder(null);
				
			}
		}
		
		else
		{
			
			setBorder(null);
		
		}
	}
	
	public void setListener()
		{
		
		if(tile.getPlayerID()==SessionVariable.myID.getValue()|| tile.isPossibleToMove())
			{
			
			this.removeMouseListener(handler);
			this.addMouseListener(handler);
			
			}
		
		else
			{
			
			this.removeMouseListener(handler);
			
			}
		}
	
	public void setListener(MouseController mouseControl)
	{
		setListener();
		if( tile.getPlayerID()==SessionVariable.myID.getValue()|| tile.isPossibleToMove())
		{
			
			this.removeMouseListener(mouseControl);
			this.addMouseListener(mouseControl);
		
		}
		
		else
		{
			
			this.removeMouseListener(mouseControl);
		
		}
	}
	
	 
	class MouseHandler extends MouseAdapter 
		{
		
		public void mouseEntered(MouseEvent e)
			{
			
			super.mouseEntered(e);
			focused = true;
			repaint();
			
			}
			
		public void mouseExited(MouseEvent e)
			{
			
			super.mouseExited(e);
			focused = false;
			
			repaint();
			
			}
		
		public void mousePressed(MouseEvent e)
			{
			if(isClicked())
				{
				
				resetClicked();
				
				}
			
			else if(tile.getPlayerID()==SessionVariable.myID.getValue())
				{
				
				setClicked();	
				
				}
		}
	}
}
