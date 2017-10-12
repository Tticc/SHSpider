package wenc.shspider.executor;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import wenc.shspider.component.*;
import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.spider.BootSpider;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.test.TestCus;
import wenc.shspider.test.TestThread;

@Component("executor")
public class Executor {
	static Logger logger = Logger.getLogger(Executor.class);
	//static ApplicationContext ctx;
	static final int numofthread = 3;
	@Autowired @Qualifier("customizatedLog4j")
	private CustomizatedLog4j customizatedLog4j;
	
	@Autowired
	private ServiceIN serviceIN;

	@Autowired
	private BootSpider bootSpider;
	
	public static void main(String[] args) throws InterruptedException{
		
		Executor exe2 = (Executor)SpringContext.myGetBean("executor");
		//exe2.addUrl();
		//boot spider with thread number
		exe2.bootWithThreadNum(numofthread);
		int hours = 0;
		while(true){
			Thread.sleep(1000*60*60);
			System.out.println("Spider has run " +(++hours)+ " hours!");
		}
		/*while(true){
			try{
				Thread.sleep(1000*60*7);
				System.out.println("start to destory context");
				SpringContext.destroyContext();
				exe2 = (Executor)SpringContext.myGetBean("executor");
				System.out.println("start to boot thread");
				exe2.bootWithThreadNum(numofthread);
				logger.info("tttttttttttt new context");
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("reopen thread!");
				logger.info("reopen thread!");
			}
		}*/
		/* test area *****************************************************************/
		//exe2.fetchFromDB();
		
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
	//for test
	private void fetchFromDB(){
		List<String> list = serviceIN.fetchPersistedUrl(10);
		System.out.println("-----------------------------------------------------------");
		for(String item : list){
			System.out.println(item);
		}
	}
	//useful
	private void addUrl(){
		serviceIN.addUrlSet();
	}
	//useful
	private void bootWithThreadNum(int threadNum){
		try {
			bootSpider.bootWithThreadNum(threadNum);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//useless
	private void addLog1(){
		CustomizatedLog4j.info(logger, "log beyond instance");
	}
	private void addLog(){
		logger.info("newer log");
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
