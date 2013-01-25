package skt.tmall.common.model.process.ibatisDb;

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
 */
public abstract class AbstractIbatisDeleteProcess extends
		AbstractIbatisDatabaseProcess {
	
	private Log log = LogFactory.getLog(AbstractIbatisDeleteProcess.class);
	
	protected abstract String getAlias();

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
		int result = -1;
		try {
			result = sqlMapClient.delete(getAlias(), getParameter(context));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		context.put(ICommonConstants.PROCESS_RESULT, result);

	}

}
