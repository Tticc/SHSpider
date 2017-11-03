package wenc.shspider.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.*;

public class TestSpider {

	private static HashSet<String> visitedUrlQueue = new HashSet<String>();
	public static void main(String[] args){
		//getRootUrl();
		
		/*LinkedList<String> finalUrl = new LinkedList<String>();
		finalUrl.add("http://im.clo.com1/file");
		finalUrl.add("https://im.clo.com2");
		finalUrl.add("https://im.clo.com3/");
		finalUrl.add("http://im.clo.com4/file/jfo.html");
		finalUrl.add("http://im.clo.com1/file");
		testAddAll(finalUrl);*/
		
		testForeach();
	}
	
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
	public static void testAddAll(LinkedList<String> finalUrl){
		HashSet<String> newUrlSet = new HashSet<String>();
		HashSet<String> rootUrlSet = new HashSet<String>();
		newUrlSet.addAll(finalUrl);
		rootUrlSet.addAll(newUrlSet);
		System.out.println(newUrlSet.size()+" and "+rootUrlSet.size());
		for(String item : newUrlSet){
			item+="hahahahah";
		}
		System.out.println("before");
		//newUrlSet.removeAll(newUrlSet);
		for(String item : newUrlSet){
			System.out.println(item.hashCode());
		}
		System.out.println("after");
		for(String item : rootUrlSet){
			System.out.println(item.hashCode());
		}
	}
	public static void testForeach(){
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		for(String item : list){
			item = item+"+new";
			System.out.println(item);
		}
		for(String item : list){
			System.out.println(item);
		}
	}

	//@Test
	public static void getRootUrl(){
		HashSet<String> finalUrl = new HashSet<String>();
		finalUrl.add("http://im.clo.com1/file");
		finalUrl.add("https://im.clo.com2");
		finalUrl.add("https://im.clo.com3/");
		finalUrl.add("http://im.clo.com4/file/jfo.html");
		finalUrl.add("http://im.clo.com5/files");
		
		LinkedList<String> ll = new LinkedList<String>();
		String tempUrl = "";
		for(String item : finalUrl){
			tempUrl = item+"/";
			Pattern p = Pattern.compile("(https://.*?|http://.*?)/.*");
			Matcher m = p.matcher(tempUrl);
			if(m.find()){
				ll.add(m.group(1));
			}
			//finalUrl.remove(item);
		}
		for(String item : ll){
			System.out.println(item);
		}
		finalUrl.removeAll(finalUrl);
		System.out.println(finalUrl.size());
		System.out.println(finalUrl == null);
		/*finalUrl.add("ont");
		finalUrl.add("jfidso");
		System.out.println(finalUrl.size());
		for(String item : finalUrl){
			System.out.println(item);
		}*/
	}
}
