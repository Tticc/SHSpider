package wenc.shspider.dao;

import java.util.List;

import org.apache.log4j.Logger;
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
	static Logger logger = Logger.getLogger(UrlSetDAO.class);
	
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
	/*
	 
	 */
	public void addUrl(UrlSetEntity url){
		try{
			sessionFactory.getCurrentSession().save(url);
		}catch(org.hibernate.exception.ConstraintViolationException ex){
			sessionFactory.getCurrentSession().clear();
			logger.info("Duplicate entry at wenc.shspider.dao.UrlSetDAO.addUrl()");
			//sessionFactory.getCurrentSession().flush();
		}
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
