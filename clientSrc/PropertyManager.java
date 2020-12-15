import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

	private static PropertyManager INSTANCE = null;
	private Properties prop;
	
	private PropertyManager() throws IOException
		{
		
		prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ClientPort.properties");
		
		if(inputStream != null)
			{
			
			prop.load(inputStream);
			
			}
		}
	
	public static PropertyManager getInstance() throws IOException
		{
		if(INSTANCE == null)
			{
			
			INSTANCE = new PropertyManager();
			
			}
		return INSTANCE;
		}
	
	public String getIPAddress()
		{
		
		return prop.getProperty("server");
		
		}
	
	public int getPort()
		{
		
		return Integer.parseInt(prop.getProperty("port"));
		
		}
	
	}
