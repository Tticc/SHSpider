package wenc.shspider.executor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import wenc.shspider.component.*;
import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.test.TestCus;
import wenc.shspider.test.TestThread;

@Component("executor")
public class Executor {
	static Logger logger = Logger.getLogger(Executor.class);
	//static ApplicationContext ctx;
	
	@Autowired @Qualifier("customizatedLog4j")
	private CustomizatedLog4j customizatedLog4j;
	
	@Autowired
	private ServiceIN serviceIN;
	
	@Autowired
	private TestCus tc;
	
	public static void main(String[] args) throws InterruptedException{
		
		Executor exe2 = (Executor)SpringContext.myGetBean("executor");
		exe2.addUrl();
		/*
		exe2.addLog();
		System.out.println("---------------------------------------");
		TestThread tt = new TestThread();
		tt.start();
		Thread.sleep(600);
		Executor exe1 = new Executor();
		exe1.addLog1();
		*/
	}
	private void addLog1(){
		CustomizatedLog4j.info(logger, "log beyond instance");
	}
	private void addLog(){
		logger.info("newer log");
	}
	public void addUrl(){
		serviceIN.addUrlSet();
	}
	/**
	 * initialize context for the project.
	 */
	/*private static void initContext(){
	long startTimeStamp = System.currentTimeMillis();
	System.out.println("initializing");
	PropertyConfigurator.configure("src/config/log4j.properties");
	ctx = new FileSystemXmlApplicationContext("src/config/context.xml","src/config/spring-hibernate.xml");
	System.out.println("initialize success");
	System.out.println((System.currentTimeMillis()-startTimeStamp)+"ms");
	
}*/
}
