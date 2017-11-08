package wenc.shspider.spider;

import java.util.ArrayList;

import org.springframework.stereotype.Component;




@Component("bootSpider")
public class BootSpider {

	private static int urlNum = 1;
	private static final int TIMEOFURLNUM = 5;
	private static ArrayList<DataFecthThread> ths = new ArrayList<DataFecthThread>();
	/*private static HashSet<String> visitedUrlSet = new HashSet<String>();
	private static HashSet<String> newUrlSet = new HashSet<String>();
	private static HashSet<String> persistentUrlSet = new HashSet<String>();*/
	
	public void bootWithThreadNum(int threadNum) throws InterruptedException {
		urlNum = threadNum * TIMEOFURLNUM;
		int count = threadNum;
		for (int i = 0; i < threadNum; i++) {
			ths.add(new DataFecthThread());
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
	public void renewAllThread(){
		int count = ths.size();
		for(int i = 0; i < count; i++){
			try{
				//terminate one
				terminateThread(1);
				//add new one and start it
				ths.add(new DataFecthThread());				
				ths.get(ths.size() - 1).start();
				Thread.sleep(4000);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	private void terminateThread(int num) throws InterruptedException{
		for(int i = 0 ; i < num ; i++){
			DataFecthThread dft = ths.get(0);
			dft.interruptIt();			
			dft.join();
			ths.remove(0);
		}
		System.out.println("be interrupted. size:" + ths.size());
	}
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
