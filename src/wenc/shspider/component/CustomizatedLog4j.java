package wenc.shspider.component;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Component;

/**
 * used by instance beyond ApplicationContext.
 * @author wenc
 *
 */
@Component("customizatedLog4j")
public class CustomizatedLog4j {
	//use guidance - start
	//define a logger, and then call the log method
	static Logger logger = Logger.getLogger(CustomizatedLog4j.class);
	public static void main(String[] args){
		CustomizatedLog4j.info(logger, "test log");
	}
	//use guidance - end
	
	public static void info(Logger logger,String log){
		PropertyConfigurator.configure("src/config/log4j.properties");
		logger.info(log);
	}
	public static void debug(Logger logger,String log){
		PropertyConfigurator.configure("src/config/log4j.properties");
		logger.debug(log);
	}

	public static void error(Logger logger,String log){
		PropertyConfigurator.configure("src/config/log4j.properties");
		logger.error(log);
	}
	public static void fatal(Logger logger,String log){
		PropertyConfigurator.configure("src/config/log4j.properties");
		logger.fatal(log);
	}
	/**
	 * for test
	 */
	public void pp(){
		System.out.println("CustomizatedLog4j");
	}
}
