package wenc.shspider.spider;

import java.io.IOException;
import java.util.HashSet;

import javassist.bytecode.Descriptor.Iterator;
import wenc.shspider.entity.UrlSetEntity;
import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;

public class DataFecthThread extends Thread{

	private HashSet<String> newUrlSet = new HashSet<String>();

	private ServiceIN serviceIN = (ServiceIN)SpringContext.myGetBean("serviceIN");
	
	@Override
	public void start(){
		while(!SpiderTools.isEmptyPersistentUrlSet()){
			try{
				dataHanding(SpiderTools.getFromVisitedUrlSet());
				
			}catch(Exception ex){
        		ex.printStackTrace();
        	}
		}
	}
	private void dataHanding(String url) throws IOException{
		String contents = SpiderTools.getContentFromUrl(url);
		dealWithPage(contents,url);
	}
	private void dealWithPage(String contents, String url){
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
		}
		newUrlSet = new HashSet<String>();
	}
	private void manipulateDB(String url){
		UrlSetEntity urlEntity = new UrlSetEntity();
		urlEntity.setUrl(url);
		serviceIN.addUrlSet(urlEntity);
	}
	private void updateTags(String contents, String url){
		UrlSetEntity persistentUrl = serviceIN.getUrlSetEntityByUrl(url);
		persistentUrl.setPageTitle(SpiderTools.getTitle(contents));
		serviceIN.updateUrlSet(persistentUrl);
	}
}
