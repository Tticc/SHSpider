package wenc.shspider.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import wenc.shspider.entity.UrlSetEntity;

@Transactional
//need @Repository to generate the bean
@Repository
public class UrlSetDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	//@Transactional
	public UrlSetEntity getUrlSetEntityById(int id){
		String queryStr = "from UrlSetEntity where id =  ?";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setInteger(0, id);
		List<UrlSetEntity> ur = query.list();
		if(ur.size()!=0){
			return ur.get(0);
		}
		return null;
	}
	
	public void addUrl(UrlSetEntity url){
		sessionFactory.getCurrentSession().save(url);
	}
	
	public void updateUrl(UrlSetEntity url){
		sessionFactory.getCurrentSession().update(url);
	}
	
	public void deleteUrl(int urlId){
		UrlSetEntity url = getUrlSetEntityById(urlId);
		if(url != null){
			sessionFactory.getCurrentSession().delete(url);
		}
	}
}
