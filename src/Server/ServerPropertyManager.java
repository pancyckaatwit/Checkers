package Server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerPropertyManager {
	private static ServerPropertyManager INSTANCE = null;
	private Properties property;
	
	private ServerPropertyManager() throws IOException {
		property = new Properties();
		InputStream is = getClass().getClassLoader().getResourceAsStream("Port.properties");
		
		if(is != null){
			property.load(is);
		}
	}
	
	public static ServerPropertyManager getInstance() throws IOException {
		if(INSTANCE == null){
			INSTANCE = new ServerPropertyManager();
		}
		return INSTANCE;
	}
	
	public int getPort() {
		return Integer.parseInt(property.getProperty("port"));
	}
}
