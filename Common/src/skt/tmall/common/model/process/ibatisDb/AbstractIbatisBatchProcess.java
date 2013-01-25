/**
 * 
 */
package skt.tmall.common.model.process.ibatisDb;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;

/**
 * batch sql을 지원한다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractIbatisBatchProcess extends AbstractIbatisDatabaseProcess {

	private Log log = LogFactory.getLog(AbstractIbatisBatchProcess.class);

	protected abstract String getAlias();
	
	/**
	 * batch query 구현
	 * @param sqlMapClient
	 * @param context
	 * @return
	 */
	protected abstract int modify(SqlMapClient sqlMapClient, HashMap<String, Object> context) ;

	@Override
	protected final void hook(HashMap<String, Object> context) {
	}
	
	@Override
	public void proceed(HashMap<String, Object> context)
			throws ProcessException {
		
		if (log.isDebugEnabled()) {
			log.debug(getAlias());
		}
		
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		int result = -1;
		try {
			
			sqlMapClient.startBatch();
			
			modify(sqlMapClient, context);
			
			result = sqlMapClient.executeBatch();
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		context.put(ICommonConstants.PROCESS_RESULT, result);

	}

}