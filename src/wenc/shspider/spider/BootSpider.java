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
		for (int i = 0; i < threadNum; i++) {             
			ths.add(new DataFecthThread());
            ths.get(i).start();
			Thread.sleep(4000);
        }
		
	}
	public static int getUrlNum(){
		return urlNum;
	}
}
