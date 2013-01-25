package skt.tmall.common.db;

import java.sql.Types;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SelectDynamicQueryImpl implements ISelectDynamicQuery {

	private static Log log = LogFactory.getLog(SelectDynamicQueryImpl.class);
	
	private StringBuffer dynamicSql;	
	
	private Object[] dynamicParams;
	
	private int[] dynamicTypes;
	
	public String dynamic(String sql) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(sql);
		sb.append(dynamicSql);

		String toString = sb.toString();
		if (log.isDebugEnabled()) {
			log.debug(toString);
		}
		return toString;
	}

	public Object[] getDynamicParams(String[] params)
			throws IllegalArgumentException {
		
		ArrayList<String> param = new ArrayList<String>();
		ArrayList<Integer> type = new ArrayList<Integer>();
		
		dynamicSql    = new StringBuffer();		
		
		int index1 = params.length;
		
		for(int i=0; i < index1; i++) {
			
			String[] subResult = params[i].split(":");
			
			if(subResult.length >= 2) {
				dynamicSql.append(" and " + subResult[0] + " like ? ");   //dynamic query
				param.add(subResult[1] + "%");      					  //dynamic parameter
				type.add(Types.VARCHAR);            					  //dynamic type
			}
			
		}
		
		int index2 = param.size();
		
		dynamicParams = new Object[index2];
		dynamicTypes  = new int[index2];
		
		for (int i=0; i < index2; i++) {
			dynamicParams[i] = param.get(i);
			dynamicTypes[i]  = type.get(i);
		}
		
		if (dynamicSql == null || param.size() == 0 ||type.size() == 0) {
			dynamicSql.append("");
		}
		
		return dynamicParams;
	}

	public int[] getDynamicTypes() {
		return dynamicTypes;
	}

}
