package wenc.shspider.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.util.MyEnum;

public class SpiderTools {

	private static HashSet<String> persistentUrlSet = new HashSet<String>();
	public static final String CHARSET = "utf-8";

	public synchronized static String getFromVisitedUrlSet(){
		if(persistentUrlSet.size() == 0){
			addManyToPersistentUrlSet(fetchPersistedUrl());
		}
		String url = MyEnum.NOTURL.toString();
		for(Iterator it = persistentUrlSet.iterator();it.hasNext();){
			url = (String) it.next();
		}
		removeFromPersistentUrlSet(url);
		return url;
		
	}

	public synchronized static boolean isEmptyPersistentUrlSet(){
		if(persistentUrlSet.isEmpty()){
			addManyToPersistentUrlSet(fetchPersistedUrl());
		}
		return persistentUrlSet.isEmpty();
	}
	public synchronized static void addToPersistentUrlSet(String url){
		persistentUrlSet.add(url);
	}
	public synchronized static void addManyToPersistentUrlSet(List<String> list){
		persistentUrlSet.addAll(list);
	}
	public synchronized static void removeFromPersistentUrlSet(String url){
		persistentUrlSet.remove(url);
	}
	public synchronized static void removeAllFromPersistentUrlSet(){
		persistentUrlSet = new HashSet<String>();
	}
	private static List<String> fetchPersistedUrl(){
		ServiceIN serviceIN = (ServiceIN)SpringContext.myGetBean("serviceIN");
		return serviceIN.fetchPersistedUrl(BootSpider.getUrlNum());
		//return null;
	}
	
	/**
	 * get page content by completed url
	 * @param url
	 * @return page content. type:String
	 * @throws IOException
	 */
	public static String getContentFromUrl(String url) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader in = null ;
		try {
	    	URL realUrl = new URL(url);
	    	URLConnection connection = realUrl.openConnection();
	    	//将爬虫连接伪装成浏览器连接
	        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	        connection.connect();
	        InputStream urlStream = connection.getInputStream();  
	        in = new BufferedReader(new InputStreamReader(urlStream,CHARSET));  
	        String line = "";  
	        while ((line = in.readLine()) != null) {
	        	result.append(line);
	        }
	 
	    } catch (MalformedURLException e) {
	    	System.out.print("发送GET请求出现异常！" + e);
	        e.printStackTrace();
	    } finally {
	    	try {
	        	if (in != null) {
	        		in.close();
	            }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }	 
	    return result.toString();
    }
	
	
	/**
	 * get completed img url list by html content.
	 * @param content
	 * @param oriUrl
	 * @return final img url list. type:LinkedList<String>
	 */
	public static LinkedList<String> getLinksFromContent(String content,String oriUrl){
		LinkedList<String> finalUrlList = new LinkedList<String>();
		//String title = getTitle(content);
		//check the img url is surrounding by """ or "'" .
		boolean isSingleQuoteMark = false;
        String[] contents = content.split("<a"); 
        for(int i = 0; i < contents.length; i++){
			int length = contents[i].indexOf(">");
			contents[i] = contents[i].substring(0,length);
		}
        for(String url : contents){
        	int start = url.indexOf("href=");
        	if(start<0){
        		start = url.indexOf("href =");
        		if(start < 0 ){
        			continue;
        		}
        	}
        	if(start<0){
        		continue;
        	}
        	url = url.substring(start, url.length());
        	int startOfDoubleQuote = url.indexOf("\"");
        	int startOfSingleQuote = url.indexOf("'");
        	if((startOfDoubleQuote==startOfSingleQuote)&&startOfSingleQuote==-1){
        		continue;
        	}
        	if(startOfDoubleQuote<0){
        		isSingleQuoteMark = true;
        		start = startOfSingleQuote;
        	}else if(startOfSingleQuote<0){
        		isSingleQuoteMark = false;
        		start = startOfDoubleQuote;
        	}else if(startOfDoubleQuote > startOfSingleQuote){
        		isSingleQuoteMark = true;
        		start = startOfSingleQuote;
        	}else{
        		isSingleQuoteMark = false;
        		start = startOfDoubleQuote;
        	}
        	url = url.substring(start+1, url.length());
        	int end = isSingleQuoteMark ? url.indexOf("'") : url.indexOf("\"");
        	String finalUrl = getFinalURL(url.substring(0,end),oriUrl);
        	
        	finalUrlList.add(finalUrl);
        }
        return finalUrlList;
	}
	/**
	 * get the title of the page according page content
	 * @param content
	 * @return page title
	 */
	public static String getTitle(String content){
		int start = content.indexOf("<title");
		if(start < 0){
			return "notitle";
		}
		content = content.substring(start+3, content.length());
		start = content.indexOf(">");
		content = content.substring(start+1, content.length());
		start = content.indexOf("<");
		content = content.substring(0,start);
		return content;
	}
	/**
	 * 
	 * get img final url. the param "href" is the href need to be modified, and the param "requestUrl" is the request url which get content.
	 * 
	 * @param href
	 * @param requestUrl
	 * @return final url. type:String
	 */
	public static String getFinalURL(String href, String requestUrl) {  
		
        /* 内外部链接最终转化为完整的链接格式 */
        String finalUrl = "";
  
        //check if outside url and match specificPath.
        if (href.startsWith("http://") || href.startsWith("https://")) {
        	finalUrl = href;
        } else {
        	//supplement url according to oriUrl
			String tempURL = "";
			String domain = "";
			int protocolLength = 0;
			if(requestUrl.startsWith("http://")){
				tempURL = requestUrl.replace("http://","");
				protocolLength = 7;
			}
			if(requestUrl.startsWith("https://")){
				tempURL = requestUrl.replace("https://","");
				protocolLength = 8;
			}
			
            if (href.startsWith("/")) {
				int splittoken = tempURL.indexOf("/");
				if(splittoken<0){
					domain = requestUrl;
				}else{
					domain = requestUrl.substring(0, splittoken + protocolLength);
				}
				finalUrl = domain + href;
            }else{
				int splittoken = tempURL.lastIndexOf("/");
				if(splittoken<0){
					domain = requestUrl;
				}else{
					domain = requestUrl.substring(0, splittoken + 1 + protocolLength);
				}
				finalUrl = domain + href;  
            }
        }
        return finalUrl;
    }
	
}
















