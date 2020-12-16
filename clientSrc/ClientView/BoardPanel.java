package ClientView;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.sound.midi.ControllerEventListener;
import javax.swing.*;

import ClientCheckers.SessionVariable;
import ClientController.*;
import ClientSetup.Board;
import ClientSetup.Tile;

/**
 * @author Jake Giguere
 * 
 * boardPanel class initializes the tile panels throughout
 * the game using the dot product algorithm creates a list of panels
 */
public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Tile[][] tiles;
	private Dimension panelSize = new Dimension(800,800);
	
	private Board boardModel;
	
	private MouseController mouseControl;
	
	private LinkedList<TilePanel> panels;
	
	private void initializeTilePanels()
		{
		for(int i=0;i<8;i++)
			{
			
			for(int j=0;j<8;j++)
				{
				
				TilePanel tilePanel = new TilePanel(tiles[i][j]);
				
				if(tilePanel.getTile().getPlayerID()==SessionVariable.myID.getValue()||
						tilePanel.getTile().isPossibleToMove())
					{
					
					tilePanel.addMouseListener(mouseControl);
					
					}
				
				panels.add(tilePanel);
				
				add(tilePanel);
			}			
		}
	}
	
	public BoardPanel(MouseController listener)
		{
		
		boardModel = new Board();
		
		tiles = boardModel.getTiles();
		
		setPreferredSize(panelSize);
		
		setLayout(new GridLayout(8,8));
		

		
		this.mouseControl = listener;		
		
		panels = new LinkedList<TilePanel>();		
		
		initializeTilePanels();
		
		System.out.println(boardModel.getTotalTiles());		
		}
	
	public void repaintPanels()
		{
		
		for(TilePanel panel : panels)
			{
			
			panel.setListener(mouseControl);
			
			}
		
		repaint();
		
	}
	
	public LinkedList<Tile> getPlayableTiles(Tile tile)
		{
		
		return boardModel.findPlayableTiles(tile);	
		
		}
	
	public Tile getTile(int ID)
		{
		
		return panels.get(ID-1).getTile();
		
		}
}
