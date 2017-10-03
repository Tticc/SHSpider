package wenc.shspider.serivces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenc.shspider.entity.UrlSetEntity;


@Service("serviceIN")
public class ServiceIN {
	
	@Autowired
	private UrlService urlService;

	@Autowired
	private TagService tagService;
	
	/*public void addTag(){
		System.out.println("start to add record");
		TagEntity te = new TagEntity();
		te.setTag("new");
		tagDAO.addTag(te);
	}
	public TagEntity getTag(){
		TagEntity te = tagDAO.getTagEntityByTag("new");
		System.out.println(te);
		System.out.println(te.getTag() + "," + te.getId() + "," + te.getCreateTime());
		return te;
	}
	public void updateTag(){
		TagEntity te = getTag();
		te.setTag("update");
		tagDAO.updateTag(te);
	}*/
	public void addUrlSet(){
		UrlSetEntity url = new UrlSetEntity();
		url.setUrl("https://www.hao123.com/");
		url.setTagIDs("root");
		urlService.addUrl(url);
		/*UrlSetEntity url1 = new UrlSetEntity();
		url1.setUrl("first url2");
		urlService.addUrl(url1);*/
	}
	public void addUrlSet(UrlSetEntity url){
		urlService.addUrl(url);
	}
	public UrlSetEntity getUrlSetEntityByUrl(String url){
		return urlService.getUrlSetEntityByUrl(url);
	}
	public void updateUrlSet(UrlSetEntity url){
		urlService.updateUrl(url);
	}
	public List<String> fetchPersistedUrl(int number) {		
		return urlService.fetchPersistedUrl(number);
	}
	
}
