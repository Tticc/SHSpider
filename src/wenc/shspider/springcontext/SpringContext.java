package wenc.shspider.springcontext;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringContext {

	private static ApplicationContext ctx;
	/**
	 * initialize context for the project.
	 */
	private static void initContext(){
		long startTimeStamp = System.currentTimeMillis();
		System.out.println("initializing");
		System.out.println("usr.dir: "+System.getProperty("user.dir"));
		//String path = Thread.currentThread().getContextClassLoader();
		//System.out.println("path is:"+path);
		
		//PropertyConfigurator.configure("config/log4j.properties");
		//ctx = new FileSystemXmlApplicationContext("config/context.xml","config/spring-hibernate.xml");
		PropertyConfigurator.configure("src/config/log4j.properties");
		ctx = new FileSystemXmlApplicationContext("src/config/context.xml","src/config/spring-hibernate.xml");
		
		System.out.println("initialize success");
		System.out.println((System.currentTimeMillis()-startTimeStamp)+"ms");
	}
	/**
	 * get bean method
	 * @param beanName
	 * @return
	 */
	public static Object myGetBean(String beanName){
		if(ctx == null){
			initContext();
		}
		return ctx.getBean(beanName);
	}
	public static void destroyContext(){
		ctx = null;
	}
}
