/**
 * 
 */
package skt.tmall.common.model.process.db;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import skt.tmall.common.db.Sql;

/**
 * 간단하게 select count(*) from .. 같은 쿼리를 쓸 수 있게 한다. selectProcess를 쓰기엔 좀 그렇지
 * 않은가???
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractBaseSimpleSelectProcess<T> extends
		AbstractDatabaseProcess {

	private Log log = LogFactory.getLog(AbstractBaseSimpleSelectProcess.class);

	public abstract Object[] getParameters(HashMap<String, Object> context);

	@Override
	protected final void hook(HashMap<String, Object> context) {

	}

	protected Object select(Sql sql, Object[] params, Class<T> requiredType) {
		if (log.isDebugEnabled()) {
			log.debug(sql.getSql());
			for (int i = 0; i < params.length; i++) {
				log.debug("[" + i + "]'" + params[i] + "'");
			}
		}
		return getJdbcTemplate().queryForObject(sql.getSql(), params,
				sql.getTypes(), requiredType);
	}
}
