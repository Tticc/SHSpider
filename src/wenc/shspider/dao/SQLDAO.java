package wenc.shspider.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
//need @Repository to generate the bean
@Repository
public class SQLDAO {

static Logger logger = Logger.getLogger(UrlSetDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void recreateMainTable(String mainTable,String unionString){
		String queryStr = "CREATE TABLE `"+ mainTable +"` ("+
		  "`id` int(11) NOT NULL AUTO_INCREMENT,"+
		  "`charset` varchar(255) NOT NULL,"+
		  "`createtime` datetime NOT NULL,"+
		  "`isprocess` bit(1) NOT NULL,"+
		  "`modifytime` datetime NOT NULL,"+
		  "`pagetitle` varchar(255) DEFAULT NULL,"+
		  "`tag_ids` varchar(255) DEFAULT NULL,"+
		  "`url` varchar(255) NOT NULL,"+
		  "PRIMARY KEY (`id`),"+
		  "UNIQUE KEY `url` (`url`)"+
		  ") "+
		  "ENGINE=MRG_MYISAM "+
		  "DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci "+
		  "UNION=("+ unionString +") "+
		  "INSERT_METHOD=LAST "+
		  "ROW_FORMAT=DYNAMIC";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.executeUpdate();
		
	}
	public void dropTable(String tableName){
		String queryStr = "drop table "+tableName;
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.executeUpdate();
		System.out.println();
	}
	
	public void migrateAndDeleteRecord(String tableName,String lastTable, int count){
		String queryStr = "INSERT INTO "+tableName+"(url,pagetitle,charset,isprocess,tag_ids,createtime,modifytime) SELECT url,pagetitle,charset,isprocess,tag_ids,createtime,modifytime FROM "+ lastTable +" order by "+ lastTable +".id limit 0,?";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.setInteger(0, count);
		query.executeUpdate();
		System.out.println();
		
	}
	
	public void dropRecords(String lastTable, int count){
		String queryStr = "delete from "+lastTable+" order by "+ lastTable +".id limit ?";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.setInteger(0, count);
		query.executeUpdate();
		System.out.println();
	}
	public int countLastTable(){
		String queryStr = "select count(*) from urlset_last";
		BigInteger total = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(queryStr).uniqueResult();
		
		return total.intValue();
	}
	public boolean haveTable(String tableName){
		String queryStr = "SELECT table_name FROM information_schema.TABLES WHERE table_name =?";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.setString(0, tableName);
		List li = query.list();
		if(li != null && li.size() != 0){
			return true;
		}
		return false;
	}
	
	public void createSubTable(String tableName,int startNum){
		System.out.println("tableName is:"+tableName +",startNum is:"+startNum);
		String queryStr = "CREATE TABLE `"+tableName+"` ("+
		  "`id` int(11) NOT NULL AUTO_INCREMENT,"+
		  "`charset` varchar(255) NOT NULL,"+
		  "`createtime` datetime NOT NULL,"+
		  "`isprocess` bit(1) NOT NULL,"+
		  "`modifytime` datetime NOT NULL,"+
		  "`pagetitle` varchar(255) DEFAULT NULL,"+
		  "`tag_ids` varchar(255) DEFAULT NULL,"+
		  "`url` varchar(255) NOT NULL,"+
		  "PRIMARY KEY (`id`),"+
		  "UNIQUE KEY `url` (`url`)"+
		  ") "+
		  "ENGINE=MyISAM "+
		  "DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci "+
		  "AUTO_INCREMENT=? "+
		  "CHECKSUM=0 "+
		  "ROW_FORMAT=DYNAMIC "+
		  "DELAY_KEY_WRITE=0";

		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		query.setInteger(0, startNum);
		query.executeUpdate();
				
	}
	
	/*public List<String> fetchPersistedUrl(int number) {
	String queryStr = "select url from urlset where isprocess = 0 limit 0,?";
	Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
	query.setInteger(0, number);
	
	return query.list();
	}*/
	
	
}
