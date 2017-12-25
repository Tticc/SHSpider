package wenc.shspider.spider;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import javassist.bytecode.Descriptor.Iterator;
import wenc.shspider.entity.RootUrlEntity;
import wenc.shspider.entity.UrlSetEntity;
import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.util.ErrorResponseException;
import wenc.shspider.util.MyEnum;
import wenc.shspider.util.TooLargeException;

public class DataFecthThread extends Thread{
	static Logger logger = Logger.getLogger(DataFecthThread.class);
	private HashSet<String> newUrlSet = new HashSet<String>();
	private HashSet<String> rootUrlSet = new HashSet<String>();	
	private ServiceIN serviceIN = (ServiceIN)SpringContext.myGetBean("serviceIN");	
	private volatile boolean interruptMySelf = false;
	
	private Lock lock;
	private Condition cond;
	public DataFecthThread(Lock l,Condition c){
		this.lock = l;
		this.cond = c;
	}
	@Override
	public void run(){
		//while(!SpiderTools.isEmptyPersistentUrlSet()){
		while(true){
			try{
				if(interruptMySelf){
					if(lock.tryLock()){
						try{
							cond.await();
						}catch(InterruptedException ex){
							ex.printStackTrace();
						}finally{
							lock.unlock();
						}
					}
					sleep(500);
				}else{
					dataHanding(SpiderTools.getFromVisitedUrlSet());
				}
				
			}catch(Exception ex){
        		ex.printStackTrace();
        	}finally{
        		System.out.println("done! Thread name:"+super.getName());
        		System.out.println();
        	}
		}
	}
	public void interruptMe(){
		this.interruptMySelf = true;
	}
	public void runMe(){
		this.interruptMySelf = false;
	}
	private void dataHanding(String url) throws Exception{
		String contents = null;
		//url = "http://www.64365.com/fagui/";//http://www.64365.com/ask/2657012.aspx
		//why notitle:
			//http://db.auto.sohu.com//db.auto.sohu.com/jinkoubmw/1700/131983/price.html
			//why it know i'm a spider?
				//url = "http://db.auto.sohu.com/dongfengnissan/1073";
		//make it as a root url:http://db.auto.sohu.com/home/
		
		//test rooturl
		//url = "https://www.hao123.com/";
		try{
			contents = SpiderTools.getContentFromUrl(url);
		}catch(TooLargeException tle){
			tle.printStackTrace();
			contents = MyEnum.TOOLARGE.toString();
		}catch(java.net.SocketTimeoutException stex){
			stex.printStackTrace();
			contents = "<title>Error:SocketTimeoutException</title>";
		}catch(ErrorResponseException erex){
			erex.printStackTrace();
			contents = "<title>Error Code:"+erex.getResponseCode()+"</title>";
		}catch(Exception ex){
			ex.printStackTrace();
			contents = "<title>Unknow Error:"+ex.toString()+"</title>";
		}
		dealWithPage(contents,url);
	}
	private void dealWithPage(String contents, String url){
		if(contents == null){
			return;
		}
		//there are no space to save any data, yield
		//getAllLinks(contents, url);
		
		updateTags(contents, url);
	}
	private void getAllLinks(String contents, String url){
		newUrlSet.addAll(SpiderTools.getLinksFromContent(contents,url));
		rootUrlSet.addAll(SpiderTools.getRootUrl(newUrlSet));
		
		persistNewUrl();
	}
	private void persistNewUrl(){
		for(java.util.Iterator<String> it = newUrlSet.iterator();it.hasNext();){
			String url = it.next();
			addUrl(url);
			//newUrlSet.re
		}
		for(String item : rootUrlSet){
			addRootUrl(item);
		}
		rootUrlSet.removeAll(rootUrlSet);
		newUrlSet.removeAll(newUrlSet);
		//newUrlSet = new HashSet<String>();
	}
	
	
	//manipulate Database below
	private void addRootUrl(String url){
		RootUrlEntity ruEntity = new RootUrlEntity();
		ruEntity.setUrl(url);
		serviceIN.addRootUrl(ruEntity);
	}
	private void addUrl(String url){
		UrlSetEntity urlEntity = new UrlSetEntity();
		urlEntity.setUrl(url);
		//serviceIN.addUrlSet(urlEntity);
		serviceIN.addUrlSetChecked(urlEntity);
	}
	private void updateTags(String contents, String url){
		UrlSetEntity persistentUrl = serviceIN.getUrlSetEntityByUrl(url);
		persistentUrl.setModifyTime(new Date());
		persistentUrl.setPageTitle(SpiderTools.getTitle(contents));
		persistentUrl.setCharset(SpiderTools.getCharsetPattern(contents));		
		serviceIN.updateUrlSet(persistentUrl);
		logger.info("update urlset success.url:"+ url);
	}
}
