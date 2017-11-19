package wenc.shspider.executor;


import static wenc.shspider.springcontext.SpringContext.myGetBean;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import wenc.shspider.component.*;
import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.spider.BootSpider;


@Component("executor")
public class Executor {
	static Logger logger = Logger.getLogger(Executor.class);
	//static ApplicationContext ctx;
	static final int numofthread = 20;
	public static final int numOfSubTable = 10000000;
	
	@Autowired @Qualifier("customizatedLog4j")
	private CustomizatedLog4j customizatedLog4j;
	
	@Autowired
	private ServiceIN serviceIN;

	@Autowired
	private BootSpider bootSpider;
	
	private static Lock lock = new ReentrantLock();
	private static Condition cond = lock.newCondition();
	public static void main(String[] args) throws InterruptedException{
		//start time:11-3 20:15
		Executor exe2 = (Executor)myGetBean("executor");
		//exe2.addUrl();
		
		//boot spider with thread number
		exe2.bootWithThreadNum(numofthread,lock,cond);
		
		//exe2.renewMainTable();
				
		int days = 0;
		while(true){
			Thread.sleep(1000*60*60*24*2);
			
			System.out.println("Spider has run " +(++days)*2+ " days!");
			int count = exe2.count()-1/numOfSubTable;
			if(count > 5){
				exe2.awaitAll();//wait all the threads.
				while(count > 0){
					Thread.sleep(1000*60*3);//10 minutes left to wait the threads
					System.out.println("i think all threads are awaited");
					//split table

					System.out.println("before create sub table");
					int index = exe2.createSubTable();
					System.out.println("after create sub table,index is:"+index);
					
					System.out.println("before migrateAndDeleteRecord");
					long start = System.currentTimeMillis();
					exe2.migrateAndDeleteRecord(index);
					System.out.println("after migrateAndDeleteRecord,cost time:"+(System.currentTimeMillis()-start)/1000);
					exe2.renewMainTable();
					System.out.println("after renewMainTable");
					Thread.sleep(1000*60*1);
					--count;
				}
				System.out.println("before runAll");
				exe2.runAll();//run all the threads
				System.out.println("after runAll");
			}
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
	private int count(){
		int total = serviceIN.countLastTable();
		System.out.println("total records of last:"+total);
		return total;
	}
	public int createSubTable(){
		return serviceIN.createSubTable();		
	}
	public void migrateAndDeleteRecord(int index){
		serviceIN.migrateAndDeleteRecord(index);
	}
	public void renewMainTable(){
		serviceIN.renewMainTable();
	}
	
	private void awaitAll(){
		System.out.println("await all threads. start");
		bootSpider.awaitAll();
		System.out.println("await all threads. end");		
	}
	public void runAll(){
		bootSpider.runAll(lock, cond);
	}
	
	//useful
	private void addUrl(){
		serviceIN.addUrlSet();
	}
	//useful
	private void bootWithThreadNum(int threadNum,Lock l,Condition c){
		try {
			bootSpider.bootWithThreadNum(threadNum,l,c);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void checkThreadLive(){
		bootSpider.checkThreadLive();
	}
	
	
	//for test
	private void fetchFromDB(){
		List<String> list = serviceIN.fetchPersistedUrl(10);
		System.out.println("-----------------------------------------------------------");
		for(String item : list){
			System.out.println(item);
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
