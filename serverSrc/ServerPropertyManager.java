import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerPropertyManager {

	private static ServerPropertyManager INSTANCE = null;
	private Properties prop;
	
	private ServerPropertyManager() throws IOException{
		prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ServerPort.properties");
		
		if(inputStream != null){
			prop.load(inputStream);
		}
	}
	
	public static ServerPropertyManager getInstance() throws IOException{
		if(INSTANCE == null){
			INSTANCE = new ServerPropertyManager();
		}
		return INSTANCE;
	}
	
	public int getPort(){
		return Integer.parseInt(prop.getProperty("port"));
	}
	
}
