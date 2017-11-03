package wenc.shspider.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wenc.shspider.entity.RootUrlEntity;
import wenc.shspider.entity.UrlSetEntity;


@Transactional
//need @Repository to generate the bean
@Repository
public class RootUrlDAO {
	static Logger logger = Logger.getLogger(RootUrlDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	//fetchPersistedUrl
	public RootUrlEntity getRootUrlEntityByUrl(String url){
		String queryStr = "from RootUrlEntity where url =  ?";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setString(0, url);
		List<RootUrlEntity> ur = query.list();
		if(ur.size()!=0){
			return ur.get(0);
		}
		return null;
	}
	public List<String> fetchPersistedUrl(int number){
		String queryStr = "select url from rooturl where isprocess = 0 limit 0,?";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.setInteger(0, number);		
		return query.list();
	}
	
	public void addRootUrl(RootUrlEntity ruEntity){
		if(ruEntity == null){
			return;
		}
		try{
			sessionFactory.getCurrentSession().save(ruEntity);
		}catch(org.hibernate.exception.ConstraintViolationException cvex){
			sessionFactory.getCurrentSession().clear();
			logger.info("Duplicate entry at wenc.shspider.dao.RootUrlDAO.addRootUrl()");
			//sessionFactory.getCurrentSession().flush();
		}catch(org.hibernate.exception.DataException dex){
			sessionFactory.getCurrentSession().clear();
			System.out.println("Data truncation: Data too long for column 'ruEntity' at row 1");
			logger.info("Data truncation: Data too long for column 'ruEntity' at row 1");
		}catch(Exception ex){
			ex.printStackTrace();
			sessionFactory.getCurrentSession().clear();
			System.out.println("something wrong finally!!!!!!!!!!!!!!!");
		}finally{
			ruEntity = null;
		}
	}
	public void updateRootUrl(RootUrlEntity ruEntity){
		if(ruEntity == null){
			return;
		}
		try{
			sessionFactory.getCurrentSession().update(ruEntity);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			ruEntity = null;
		}
		
	}
	public void deleteRootUrl(RootUrlEntity ruEntity){
		if(ruEntity == null){
			return;
		}
		sessionFactory.getCurrentSession().delete(ruEntity);
	}
}
