package wenc.shspider.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenc.shspider.dao.UrlSetDAO;

@Service("urlService")
public class UrlService {

	@Autowired
	private UrlSetDAO urlSetDAO;
	
	
}
