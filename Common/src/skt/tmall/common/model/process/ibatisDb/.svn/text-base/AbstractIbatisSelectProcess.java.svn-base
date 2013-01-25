package skt.tmall.common.model.process.ibatisDb;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.ibatis.sqlmap.client.SqlMapClient;

import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;

/**
 * 
 * @author leegt80
 *
 * @param <R>
 */
public abstract class AbstractIbatisSelectProcess<R> extends AbstractIbatisDatabaseProcess {
	
	private Log log = LogFactory.getLog(AbstractIbatisSelectProcess.class);
	
	protected abstract String getAlias();
	
	protected abstract Object query(SqlMapClient sqlMapClient, HashMap<String, Object> context) throws SQLException;

	@Override
	protected final void hook(HashMap<String, Object> context) {
		// TODO
	}

	@Override
	public void proceed(HashMap<String, Object> context)
			throws ProcessException {
		
		if (log.isDebugEnabled()) {
			log.debug(getAlias());
		}
		
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		Object result;
		try {
			result = query(sqlMapClient, context);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		context.put(ICommonConstants.PROCESS_RESULT, result);
	}

}
