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
public abstract class AbstractIbatisInsertProcess extends
		AbstractIbatisDatabaseProcess {
	
	private Log log = LogFactory.getLog(AbstractIbatisInsertProcess.class);
	
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
		Object result = null;
		try {
			result = (Object) sqlMapClient.insert(getAlias(), getParameter(context));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		context.put(ICommonConstants.PROCESS_RESULT, result);

	}

}
