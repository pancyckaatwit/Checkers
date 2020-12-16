package Server;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author Alexander Pancyck
 *
 */
public class ServerPropertyManager {

	private static ServerPropertyManager INSTANCE = null;
	private Properties property;
	
	private ServerPropertyManager() throws IOException{
		property = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ServerPort.properties");
		
		if(inputStream != null){
			property.load(inputStream);
		}
	}
	
	public static ServerPropertyManager getInstance() throws IOException{
		if(INSTANCE == null){
			INSTANCE = new ServerPropertyManager();
		}
		return INSTANCE;
	}
	
	public int getPort(){
		return Integer.parseInt(property.getProperty("port"));
	}
	
}
