package wenc.shspider.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
	
}
