package Client;

import java.awt.event.MouseAdapter;


import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import Client.Square;

	public class MouseController extends MouseAdapter{
		
		private SquarePanel squarePanel;
		private Controller controller;
		
		public void setController(Controller c){
			this.controller = c;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mousePressed(e);
			
			
			try{			
				if(controller.isPlayersTurn()){
					
					highlightSelectPiece(e);
				
				}else{
					
					JOptionPane.showMessageDialog(null, "Opponent must make a move",
						"Error", JOptionPane.ERROR_MESSAGE, null);
				}
			}catch(Exception ex){
				
				System.out.println("Error");
			}	
			
			
		}
		
		private void highlightSelectPiece(MouseEvent e){
			try{
				squarePanel = (SquarePanel) e.getSource();
				Square square = squarePanel.getSquare();
				
				//if square is already selected - deselect
				if(square.isSelected()){
					
					System.out.println("deselect - "+square.getSquareID());
					controller.tileDeselected();				
				}
				//else select
				else{
					
					System.out.println("select - "+square.getSquareID());
					controller.tileSelected(square);
				}
			}catch(Exception ex){
				
				System.out.println("error");
			}
		}
	}


