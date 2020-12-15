package view;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.sound.midi.ControllerEventListener;
import javax.swing.*;
import model.Board;
import model.Tile;
import constant.SessionVariable;
import handler.*;

/**
 * Client Application -> ClientApp
 * 
 * 
 * ClientApp
 */
public class BoardPanel extends JPanel {

private static final long serialVersionUID = 1L;
	
	private Dimension panelSize = new Dimension(800,800);
	private Board boardModel;
	private MouseController listener;
	private LinkedList<TilePanel> panels;
	private Tile[][] tiles;

	public BoardPanel(MouseController listener) {
		setPreferredSize(panelSize);
		setLayout(new GridLayout(8,8));
		
		boardModel = new Board();
		tiles = boardModel.getTiles();
		this.listener = listener;		
		panels=new LinkedList<TilePanel>();		
		
		initializeTilePanels();
		
		System.out.println(boardModel.getTotalTiles());		
	}
	
	private void initializeTilePanels() {
		for(int i=0;i<8;i++){
			for(int k=0;k<8;k++){
				TilePanel tPanel = new TilePanel(tiles[i][k]);
				if(tPanel.getTile().isPossibleToMove() || tPanel.getTile().getPlayerID()==SessionVariable.myID.getValue()){
					tPanel.addMouseListener(listener);
				}
				panels.add(tPanel);
				add(tPanel);
			}			
		}
	}
	
	public void repaintPanels(){
		for(TilePanel panel : panels){
			panel.setListener(listener);
		}
		repaint();
	}
	
	public LinkedList<Tile> getPlayableTiles(Tile t){
		return boardModel.findPlayableTiles(t);		
	}
	
	public Tile getTile(int ID){
		return panels.get(ID-1).getTile();
	}
}
