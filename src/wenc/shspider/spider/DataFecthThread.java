package wenc.shspider.spider;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;

import javassist.bytecode.Descriptor.Iterator;
import wenc.shspider.entity.UrlSetEntity;
import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.util.MyEnum;
import wenc.shspider.util.TooLargeException;

public class DataFecthThread extends Thread{
	static Logger logger = Logger.getLogger(DataFecthThread.class);
	private HashSet<String> newUrlSet = new HashSet<String>();

	private ServiceIN serviceIN = (ServiceIN)SpringContext.myGetBean("serviceIN");
	private volatile boolean interruptMySelf = false;
	@Override
	public void run(){
		//while(!SpiderTools.isEmptyPersistentUrlSet()){
		while(!interruptMySelf){
			try{
				dataHanding(SpiderTools.getFromVisitedUrlSet());
				
				
			}catch(Exception ex){
        		ex.printStackTrace();
        	}finally{
        		System.out.println("done! Thread name: "+super.getName());
        		System.out.println();
        	}
		}
	}
	public void interruptIt(){
		this.interruptMySelf = true;
	}
	/*@Override
	public void interrupt(){
		
	}*/
	private void dataHanding(String url) throws Exception{
		String contents = null;
		//url = "http://www.64365.com/fagui/";//http://www.64365.com/ask/2657012.aspx
		//why notitle:
			//http://db.auto.sohu.com//db.auto.sohu.com/jinkoubmw/1700/131983/price.html
			//http://db.auto.sohu.com/yiqivw/1005
		//make it as a root url:http://db.auto.sohu.com/home/
		try{
			contents = SpiderTools.getContentFromUrl(url);
		}catch(TooLargeException tle){
			tle.printStackTrace();
			contents = MyEnum.TOOLARGE.toString();
		}
		dealWithPage(contents,url);
	}
	private void dealWithPage(String contents, String url){
		if(contents == null){
			return;
		}
		getAllLinks(contents, url);
		updateTags(contents, url);
	}
	private void getAllLinks(String contents, String url){
		newUrlSet.addAll(SpiderTools.getLinksFromContent(contents,url));
		
		persistNewUrl();
	}
	private void persistNewUrl(){
		for(java.util.Iterator<String> it = newUrlSet.iterator();it.hasNext();){
			String url = it.next();
			manipulateDB(url);
			//newUrlSet.re
		}
		newUrlSet.removeAll(newUrlSet);
		newUrlSet = new HashSet<String>();
	}
	private void manipulateDB(String url){
		UrlSetEntity urlEntity = new UrlSetEntity();
		urlEntity.setUrl(url);
		serviceIN.addUrlSet(urlEntity);
	}
	private void updateTags(String contents, String url){
		UrlSetEntity persistentUrl = serviceIN.getUrlSetEntityByUrl(url);
		persistentUrl.setModifyTime(new Date());
		persistentUrl.setPageTitle(SpiderTools.getTitle(contents));
		persistentUrl.setCharset(SpiderTools.getCharsetPattern(contents));		
		serviceIN.updateUrlSet(persistentUrl);
		logger.info("update urlset success.url: "+ url);
	}
}
