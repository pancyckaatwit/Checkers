package Server;
import javax.swing.JFrame;

/**
 * @author Alexander Pancyck
 * 
 */
public class ServerMain {

	public static void main(String[] args) {
		ServerApp server = new ServerApp();
		server.setTitle("APJG Checkers Server");
		server.setSize(350, 150);
		server.setVisible(true);
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		server.startRunning();
	}
}
