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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wenc.shspider.serivces.ServiceIN;
import wenc.shspider.springcontext.SpringContext;
import wenc.shspider.util.MyEnum;
import wenc.shspider.util.TooLargeException;

public class SpiderTools {

	private static HashSet<String> persistentUrlSet = new HashSet<String>();
	public static final String CHARSET = "UTF-8";
	public static final long noLargeThen = 1024*1024*5;
	//public static final String GET_CHAR = "charset";

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
	public static String getContentFromUrl(String url) throws IOException,TooLargeException {
		StringBuilder result = new StringBuilder();
		BufferedReader in = null ;
		
		try {
			
			getSB(result, in, url, CHARSET);
			String tarCharset = getCharsetPattern(result.toString());
	        if(!tarCharset.equals(CHARSET) && !tarCharset.equals(MyEnum.DEFAULTCHARSET.toString())){
	        	result.delete(0, result.length());
	        	getSB(result, in, url, tarCharset);
	        }
	        /*String tarCharset = getCharsetPattern(result.toString());
	        if(!tarCharset.equals(CHARSET) && !tarCharset.equals(MyEnum.DEFAULTCHARSET.toString())){
	        	in = new BufferedReader(new InputStreamReader(connection.getInputStream(),tarCharset));
	        	result.delete(0, result.length());
	        	while ((line = in.readLine()) != null) {
		        	result.append(line);
		        }
	        }*/
	        /*URL realUrl = new URL(url);
	    	URLConnection connection = realUrl.openConnection();
	    	//将爬虫连接伪装成浏览器连接
	        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	        try{
	        	connection.connect();
	        }catch(java.net.ConnectException ex){
	        	System.out.println("time out url: " + url);
	        	throw ex;
	        }
	        long cll = connection.getContentLengthLong();
	        System.out.println("\n\nurl is: "+url);
	        System.out.println("connection.getContentLengthLong(): "+cll);
	        
	        if(cll > noLargeThen){
	        	System.out.println("file too large, not a html file");
	        	throw new TooLargeException();
	        }
	        InputStream urlStream = connection.getInputStream();
	        in = new BufferedReader(new InputStreamReader(urlStream,CHARSET));
	        String line = "";  
	        while ((line = in.readLine()) != null) {
	        	result.append(line);
	        }*/
	        
	        
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
	public static void getSB(StringBuilder result, BufferedReader in,String url, String Charset) throws IOException, TooLargeException{
		URL realUrl = new URL(url);
		URLConnection connection = realUrl.openConnection();
    	//将爬虫连接伪装成浏览器连接
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        try{
        	connection.connect();
        }catch(java.net.ConnectException ex){
        	System.out.println("time out url: " + url);
        	throw ex;
        }
        long cll = connection.getContentLengthLong();
        /*System.out.println("\n\nurl is: "+url);
        System.out.println("connection.getContentLengthLong(): "+cll);*/
        
        if(cll > noLargeThen){
        	System.out.println("file too large, not a html file");
        	throw new TooLargeException();
        }
        InputStream urlStream = connection.getInputStream();
        in = new BufferedReader(new InputStreamReader(urlStream,Charset));
        String line = "";  
        while ((line = in.readLine()) != null) {
        	result.append(line);
        }		
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
			if(length <= 0){
				continue;
			}
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
		if(content.equals(MyEnum.TOOLARGE.toString())){
			return content;
		}
		int start = content.indexOf("<title");
		if(start < 0){
			return MyEnum.NOTITLE.toString();
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
	public static String getCharset(String contents){
		int start = contents.indexOf("charset");
		if(start == -1){
			return MyEnum.DEFAULTCHARSET.toString();
		}
		contents = contents.substring(start,start+20);
		start = contents.indexOf("=");
		contents = contents.substring(start+1);
		int end = contents.indexOf(">");
		contents = contents.substring(0,end);
		return contents;
	}
	public static String getCharsetPattern(String contents){
		Pattern p=Pattern.compile("(charset *= *)('|\")?([\\d\\w-]*)('|\")?(.*)>");
		Matcher m=p.matcher(contents);
		if(m.find()){			
			return m.group(3).toUpperCase();
		}else{
			return MyEnum.DEFAULTCHARSET.toString();
		}		
	}
	
}
















