package Client;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JPanel;

import Client.MouseController;

import Client.Board;
import Client.Tile;
import Client.TilePanel;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Dimension panelSize = new Dimension(720,720);
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
					tPanel.addMouseController(listener);
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
