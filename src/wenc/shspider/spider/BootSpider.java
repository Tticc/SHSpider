package wenc.shspider.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.util.MyEnum;



@Component("bootSpider")
public class BootSpider {

	private static int urlNum = 1;
	private static ArrayList<DataFecthThread> ths = new ArrayList<DataFecthThread>();
	private static HashSet<String> visitedUrlSet = new HashSet<String>();
	private static HashSet<String> newUrlSet = new HashSet<String>();
	private static HashSet<String> persistentUrlSet = new HashSet<String>();
	
	public void bootWithThreadNum(int threadNum) throws InterruptedException {
		urlNum = threadNum*20;
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
