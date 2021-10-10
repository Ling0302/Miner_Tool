package common;

import java.io.InputStream;

import org.apache.log4j.PropertyConfigurator;

public class Log4jConfig {

	public static void initLog4j() {
		String path = System.getProperty("user.dir");
		System.setProperty("jarpath", path); 
		InputStream is = Log4jConfig.class.getResourceAsStream("/common/log4j.properties");
		PropertyConfigurator.configure(is);
	}
}
