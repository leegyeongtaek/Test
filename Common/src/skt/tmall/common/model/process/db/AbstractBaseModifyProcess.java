/**
 * 
 */
package skt.tmall.common.model.process.db;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import skt.tmall.common.db.Sql;

/**
 * 기본 DML을 지원한다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractBaseModifyProcess extends AbstractDatabaseProcess {

	private Log log = LogFactory.getLog(AbstractModifyProcess.class);

	public abstract Object[] getParameters(HashMap<String, Object> context);

	@Override
	protected final void hook(HashMap<String, Object> context) {

	}

	protected int modify(Sql sql, Object[] params) {
		if (log.isDebugEnabled()) {
			log.debug(sql.getSql());
			for (int i = 0; i < params.length; i++) {
				log.debug("[" + i + "]'" + params[i] + "'");
			}
		}
		int modify = getJdbcTemplate().update(sql.getSql(), params,
				sql.getTypes());
		return modify;
	}

}
