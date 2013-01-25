/**
 * 
 */
package skt.tmall.common.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * @author leegt80
 * 
 */
public class SqlProvider {

	private static final String TYPES = ".types";

	private MessageSourceAccessor accessor;

	/*
	 * cache role (Sql 객체를 담고 있다.)
	 */
	private Map<String, Sql> map;

	private Map<String, Integer> typesMap;

	/**
	 * 하드코딩한 이유는 이것만 해도 충분하다 더이상 추가로 지원할 이유가 없을것 같다.
	 * 
	 * 추가로 데이터 타입을 지원할 때 쯤엔 이 시스템 쓰지도 않는다...
	 * 
	 * @see java.sql.Types
	 */
	public SqlProvider() {
		this.map = new HashMap<String, Sql>();
		typesMap = new HashMap<String, Integer>();
		typesMap.put("bit", new Integer(-7));
		typesMap.put("tinyint", new Integer(-6));
		typesMap.put("smallint", new Integer(5));
		typesMap.put("integer", new Integer(4));
		typesMap.put("bigint", new Integer(-5));
		typesMap.put("float", new Integer(6));
		typesMap.put("real", new Integer(7));
		typesMap.put("double", new Integer(8));
		typesMap.put("numeric", new Integer(2));
		typesMap.put("decimal", new Integer(3));
		typesMap.put("char", new Integer(1));
		typesMap.put("varchar", new Integer(12));
		typesMap.put("longvarchar", new Integer(-1));
		typesMap.put("date", new Integer(91));
		typesMap.put("time", new Integer(92));
		typesMap.put("timestamp", new Integer(93));
		typesMap.put("binary", new Integer(-2));
		typesMap.put("varbinary", new Integer(-3));
		typesMap.put("longvarbinary", new Integer(-4));
		typesMap.put("null", new Integer(0));
		typesMap.put("other", new Integer(1111));
		typesMap.put("java_object", new Integer(2000));
		typesMap.put("distinct", new Integer(2001));
		typesMap.put("struct", new Integer(2002));
		typesMap.put("array", new Integer(2003));
		typesMap.put("blob", new Integer(2004));
		typesMap.put("clob", new Integer(2005));
		typesMap.put("ref", new Integer(2006));
		typesMap.put("datalink", new Integer(70));
		typesMap.put("boolean", new Integer(16));
		typesMap.put("rowid", new Integer(-8));
		typesMap.put("nchar", new Integer(-15));
		typesMap.put("nvarchar", new Integer(-9));
		typesMap.put("longnvarchar", new Integer(-16));
		typesMap.put("nclob", new Integer(2011));
		typesMap.put("sqlxml", new Integer(2009));
	}

	@Required
	public void setSqlSource(MessageSourceAccessor accessor) {
		this.accessor = accessor;
	}

	public Sql getSql(String alias) {
		if (map.containsKey(alias)) {
			return map.get(alias);
		} else {
			String s = accessor.getMessage(alias);
			String types = accessor.getMessage(alias + TYPES);
			if (!StringUtils.isEmpty(types)) {
				StringTokenizer st = new StringTokenizer(types);
				int countTokens = st.countTokens();
				ArrayList<Integer> _types = new ArrayList<Integer>(countTokens);
				while (st.hasMoreElements()) {
					String type = (String) st.nextElement();
					Integer _type = typesMap.get(type.trim());
					_types.add(_type);
				}
				Sql sql = new Sql(s, _types);
				map.put(alias, sql);
				return sql;
			} else {
				Sql sql = new Sql(s);
				map.put(alias, sql);
				return sql;
			}
		}
	}
}
