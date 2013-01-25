package skt.tmall.common.model.process.ibatisDb;

import java.sql.SQLException;
import java.util.HashMap;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author leegt80
 *
 * @param <R>
 */
public abstract class AbstractIbatisSelectListProcess<R> extends
		AbstractIbatisSelectProcess<R> {


	@Override
	protected Object query(SqlMapClient sqlMapClient, HashMap<String, Object> context) throws SQLException {
		return sqlMapClient.queryForList(getAlias(), getParameter(context));
	}

}
