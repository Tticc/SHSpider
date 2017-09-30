package wenc.shspider.serivces;

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
	
}
