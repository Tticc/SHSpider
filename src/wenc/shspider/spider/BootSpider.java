package wenc.shspider.spider;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.springframework.stereotype.Component;




@Component("bootSpider")
public class BootSpider {

	private static int urlNum = 1;
	private static final int TIMEOFURLNUM = 10;
	private static ArrayList<DataFecthThread> ths = new ArrayList<DataFecthThread>();
	//private
	/*private static HashSet<String> visitedUrlSet = new HashSet<String>();
	private static HashSet<String> newUrlSet = new HashSet<String>();
	private static HashSet<String> persistentUrlSet = new HashSet<String>();*/
	
	public void bootWithThreadNum(int threadNum,Lock l,Condition c) throws InterruptedException {
		urlNum = threadNum * TIMEOFURLNUM;
		int count = threadNum;
		for (int i = 0; i < threadNum; i++) {
			ths.add(new DataFecthThread(l,c));
            ths.get(ths.size()-1).start();
            //--count;
            System.out.println("\n\n\n\n");
            System.out.println("-------------------------------------------------------------");
            System.out.println("here is the size:" + ths.size());
            System.out.println("-------------------------------------------------------------");
            System.out.println("\n\n\n\n");
			Thread.sleep(4000);
        }
		
	}
	public static int getUrlNum(){
		return urlNum;
	}
	public void awaitAll(){
		for(int i = 0; i < ths.size(); i++){
			ths.get(i).interruptMe();
		}
	}
	public void runAll(Lock lock,Condition cond){
		for(int i = 0; i < ths.size(); i++){
			ths.get(i).runMe();
		}
		if(lock.tryLock()){
			try{
				cond.signalAll();
			}finally{
				lock.unlock();
			}
		}
	}
	
	
	
	//useless
	public void terminateAll(){
		try {
			terminateThread(ths.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//useless
	private void terminateThread(int num) throws InterruptedException{
		for(int i = 0 ; i < num ; i++){
			DataFecthThread dft = ths.get(0);
			dft.interruptMe();
			dft.join();
			ths.remove(0);
		}
		System.out.println("be interrupted. size:" + ths.size());
	}
	//useless
	public void checkThreadLive(){
		int size = ths.size();
		if(size > 5){
			size = 5;
		}
		for(int i = 0 ; i < size ; i++){
			System.out.println(ths.get(i).getName() +" isInterrupted? "+ths.get(i).isInterrupted());
			System.out.println(ths.get(i).getName() +" isAlive? "+ths.get(i).isAlive());
		}
	}
}
