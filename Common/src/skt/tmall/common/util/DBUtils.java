/**
 * 
 */
package skt.tmall.common.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.logging.Log;

/**
 * @author cshan
 * 
 */
public class DBUtils {

	/**
	 * 인수로 받은 컬럼명만 Map으로 변환 
	 * @param rs
	 * @param enums
	 * @param log
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(ResultSet rs, Enum[] enums, Log log)
			throws SQLException {
		Map<String, Object> r = new HashMap<String, Object>();
		for (Enum e : enums) {
			String name = e.name();
			r.put(name, rs.getObject(name));
		}
		if (log.isDebugEnabled()) {
			log.debug(r.toString());
		}
		return r;
	}

	/**
	 * 인수로 받은 컬럼명만 Map으로 변환 
	 * @param rs
	 * @param rs
	 * @param enums
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(ResultSet rs, Enum[] enums)
			throws SQLException {
		Map<String, Object> r = new HashMap<String, Object>();
		for (Enum e : enums) {
			String name = e.name();
			r.put(name, rs.getObject(name));
		}
		return r;
	}
	
	/**
	 * 튜플 value를 Object[]로 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Object[] getResultAsObject(ResultSet rs) throws SQLException {
		int cc = rs.getMetaData().getColumnCount();
		Object[] result = new Object[cc];
		for (int i = 0; i < cc; i++) {
			result[i] = rs.getObject(i + 1);
		}
		return result;
	}
	
	/**
	 * 튜플 전체를 컬럼 vs 컬럼값 매핑 Map으로 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, Object> getResultAsMap(ResultSet rs)
			throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int cc = metaData.getColumnCount();
		Map<String, Object> map = new HashMap<String, Object>(cc);
		for (int i = 0; i < cc; i++) {
			map.put(metaData.getColumnName(i + 1), rs.getObject(i + 1));
		}
		return map;
	}

	/**
	 * 튜플을 원하는 객체로 리턴 
	 * @param rs
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public static <T> Object getResult(ResultSet rs, Class<T> type)
			throws SQLException {
		BeanProcessor bp = new BeanProcessor();
		return bp.toBean(rs, type);
	}
}
