package ClientController;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.border.Border;

import ClientSetup.Tile;
import ClientView.TilePanel;

/**
 * 
 * @author Jake Giguere
 *
 */
public class MouseController extends MouseAdapter{
	
	private TilePanel tilePanel;
    private Controller controller;

    public void setController(Controller c)
        {

        this.controller = c;

        }


    @Override
    public void mouseClicked(MouseEvent e)
        {

        super.mousePressed(e);

        try
            {
            if(controller.isPlayersTurn())
                {

                highlightSelectPiece(e);

                }
            else
                {

                JOptionPane.showMessageDialog(null, "Opponent must make a move", "Error", JOptionPane.ERROR_MESSAGE, null);

                }
            }
        catch(Exception ex)
            {

            System.out.println("Error");

            }


        }


    private void highlightSelectPiece(MouseEvent e) throws IOException
        {


            tilePanel = (TilePanel) e.getSource();

            Tile tile = tilePanel.getTile();

            if(tile.isSelected())
                {

                controller.tileDeselected();

                }

            else 
                {

                controller.tileSelected(tile);

                }
            }

}
