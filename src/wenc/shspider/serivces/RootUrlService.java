package wenc.shspider.serivces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenc.shspider.dao.RootUrlDAO;
import wenc.shspider.entity.RootUrlEntity;

@Service("rootUrlService")
public class RootUrlService {

	@Autowired
	private RootUrlDAO rootUrlDAO;
	
	public void addRootUrl(RootUrlEntity ruEntity){
		rootUrlDAO.addRootUrl(ruEntity);
	}
	public void updateRootUrl(RootUrlEntity ruEntity){
		rootUrlDAO.updateRootUrl(ruEntity);
	}
	public void deleteRootUrl(RootUrlEntity ruEntity){
		rootUrlDAO.deleteRootUrl(ruEntity);
	}
	public RootUrlEntity getRootUrlEntityByUrl(String url){
		return rootUrlDAO.getRootUrlEntityByUrl(url);
	}
	public List<String> fetchPersistedUrl(int number){
		return rootUrlDAO.fetchPersistedUrl(number);
	}
}
