package wenc.shspider.serivces;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenc.shspider.dao.UrlSetDAO;
import wenc.shspider.entity.UrlSetEntity;

@Service("urlService")
public class UrlService {

	@Autowired
	private UrlSetDAO urlSetDAO;
	
	public void addUrl(UrlSetEntity url){		
			urlSetDAO.addUrl(url);		
	}
	public void addUrlChecked(UrlSetEntity url){
		urlSetDAO.addUrlChecked(url);
	}
	public int getFirstUnprocessTable(){
		return urlSetDAO.getFirstUnprocessTable();
	}
	public List<String> fetchPersistedUrl(int number) {
		List<UrlSetEntity> listEntity = urlSetDAO.fetchPersistedUrl(number);
		List<String> urlList = new ArrayList<String>();
		if(listEntity != null){
			for(UrlSetEntity item : listEntity){
				item.setIsprocess(true);
				urlList.add(item.getUrl());
				updateUrl(item);
			}
		}
		return urlList;
	}
	public void updateUrl(UrlSetEntity url){
		urlSetDAO.updateUrl(url);
	}
	public UrlSetEntity getUrlSetEntityByUrl(String url){
		return urlSetDAO.getUrlSetEntityByUrl(url);
	}
}
