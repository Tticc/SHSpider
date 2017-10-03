package wenc.shspider.test;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.*;

public class TestSpider {

	private static HashSet<String> visitedUrlQueue = new HashSet<String>();
	
	@Test
	public void testHashSet(){
		visitedUrlQueue.add("url1");
		visitedUrlQueue.add("url2");
		visitedUrlQueue.add("url1");
		System.out.println(visitedUrlQueue.size());
		//while(visitedUrlQueue.size()!=0){
			for(Iterator it=visitedUrlQueue.iterator();it.hasNext();){
				System.out.println(it.next());
			}
		//}
	}
}
