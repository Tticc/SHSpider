package wenc.shspider.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import wenc.shspider.entity.TagEntity;

@Transactional
//need @Repository to generate the bean
@Repository
public class TagDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public TagEntity getTagEntityById(int id){
		String queryStr = "from TagEntity where id =  ?";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setInteger(0, id);
		List<TagEntity> ur = query.list();
		if(ur.size()!=0){
			return ur.get(0);
		}
		return null;
	}
	
	public TagEntity getTagEntityByTag(String tagName){
		String queryStr = "from TagEntity where tag =  ?";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setString(0, tagName);
		List<TagEntity> ur = query.list();
		if(ur.size()!=0){
			return ur.get(0);
		}
		return null;
	}
	
	public void addTag(TagEntity tag){
		System.out.println("before save tag");
		sessionFactory.getCurrentSession().save(tag);
		System.out.println("after save tag");
	}
	
	public void updateTag(TagEntity tag){
		sessionFactory.getCurrentSession().update(tag);
	}
	
	public void deleteTag(int tagId){
		TagEntity tag = getTagEntityById(tagId);
		if(tag != null){
			sessionFactory.getCurrentSession().delete(tag);
		}
	}
}
