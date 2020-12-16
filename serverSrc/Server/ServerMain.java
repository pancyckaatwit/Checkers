package Server;
import javax.swing.JFrame;

/**
 * @author Alexander Pancyck
 * 
 */
public class ServerMain {

	public static void main(String[] args) {
		
		ServerApp server = new ServerApp();
		server.setSize(350, 150);
		server.setVisible(true);
		server.setTitle("APJG Checkers Server");
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//start Connection
		server.startRunning();
	}
}
