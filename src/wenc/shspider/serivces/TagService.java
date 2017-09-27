package wenc.shspider.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenc.shspider.dao.TagDAO;

@Service("tagService")
public class TagService {

	@Autowired
	private TagDAO tagDAO;
	
}
