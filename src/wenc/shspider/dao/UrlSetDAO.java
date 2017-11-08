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
	public UrlSetEntity getUrlSetEntityByUrl(String url){
		String queryStr = "from UrlSetEntity where url =  ?";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setString(0, url);
		List<UrlSetEntity> ur = query.list();
		if(ur.size()!=0){
			return ur.get(0);
		}
		return null;
	}
	//use SQL
	/*public List<String> fetchPersistedUrl(int number) {
		String queryStr = "select url from urlset where isprocess = 0 limit 0,?";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.setInteger(0, number);
		
		return query.list();
	}*/
	//use HQL
	public List<UrlSetEntity> fetchPersistedUrl(int number) {
		String queryStr = "from UrlSetEntity where isprocess = 0";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setFirstResult(0);
		query.setMaxResults(number);
		List<UrlSetEntity> ur = query.list();
		return ur;
	}
	
	public void addUrl(UrlSetEntity url){
		try{
			sessionFactory.getCurrentSession().save(url);
			//System.out.println("save new url:"+url.getUrl());
		}catch(org.hibernate.exception.ConstraintViolationException cvex){
			sessionFactory.getCurrentSession().clear();
			logger.info("Duplicate entry at wenc.shspider.dao.UrlSetDAO.addUrl()");
			//sessionFactory.getCurrentSession().flush();
		}catch(org.hibernate.exception.DataException dex){
			sessionFactory.getCurrentSession().clear();
			System.out.println("Data truncation: Data too long for column 'url' at row 1");
			logger.info("Data truncation: Data too long for column 'url' at row 1");
		}catch(Exception ex){
			ex.printStackTrace();
			sessionFactory.getCurrentSession().clear();
			System.out.println("something wrong finally!!!!!!!!!!!!!!!");
		}finally{
			url = null;
		}
	}
	public void addUrlChecked(UrlSetEntity url){
		if(getUrlSetEntityByUrl(url.getUrl()) == null){
			addUrl(url);
		}
		url = null;
	}
	
	public void updateUrl(UrlSetEntity url){
		try{
			sessionFactory.getCurrentSession().update(url);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			url = null;
		}
	}
	
	public void deleteUrl(int urlId){
		UrlSetEntity url = getUrlSetEntityById(urlId);
		if(url != null){
			sessionFactory.getCurrentSession().delete(url);
		}
	}
}
