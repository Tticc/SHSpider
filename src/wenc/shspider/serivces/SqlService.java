package wenc.shspider.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenc.shspider.dao.SQLDAO;
import wenc.shspider.executor.Executor;
import wenc.shspider.util.MyEnum;

@Service("sqlService")
public class SqlService {

	@Autowired
	private SQLDAO sqlDAO;
	
	
	public int countLastTable(){
		return sqlDAO.countLastTable();
	}
	
	public void renewMainTable(){
		sqlDAO.dropTable(MyEnum.urlset_main.toString());
		StringBuilder unionString = new StringBuilder();//"`urlset_0`,`urlset_1`,`urlset_2`,`urlset_3`,`urlset_4`,`urlset_5`,`urlset_last`";
		String tableName = "urlset_0";
		int index = 0;
		while(haveTable(tableName)){
			unionString.append("`").append(tableName).append("`,");
			tableName = MyEnum.urlset_.toString()+(++index);
		}
		unionString.append("`").append(MyEnum.urlset_last.toString()).append("`");
		sqlDAO.recreateMainTable(MyEnum.urlset_main.toString(),unionString.toString());
	}
	
	/**
	 * return index of subtable
	 * @return
	 */
	public int createSubTable(){
		String tableName = "urlset_0";
		int index = 0;
		while(haveTable(tableName)){
			tableName = MyEnum.urlset_.toString()+(++index);
		}
		System.out.println("the table:"+tableName +" will be created");
		sqlDAO.createSubTable(tableName,index*Executor.numOfSubTable+1);
		return index;
	}
	
	public void migrateAndDeleteRecord(int index){
		String tableName = MyEnum.urlset_.toString()+ index;
		sqlDAO.migrateAndDeleteRecord(tableName,MyEnum.urlset_last.toString(),Executor.numOfSubTable);
		sqlDAO.dropRecords(MyEnum.urlset_last.toString(),Executor.numOfSubTable);
	}
	
	public boolean haveTable(String tableName){
		return sqlDAO.haveTable(tableName);
	}
}
