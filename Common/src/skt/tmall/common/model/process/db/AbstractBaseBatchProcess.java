/**
 * 
 */
package skt.tmall.common.model.process.db;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.BatchSqlUpdate;

import skt.tmall.common.exception.NotSupportMethodException;

/**
 * batch sql을 지원한다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractBaseBatchProcess extends AbstractDatabaseProcess {

	private final static NotSupportMethodException E = new NotSupportMethodException(
			"BatchProcess must call super.process(HashMap context)");

	private Log log = LogFactory.getLog(AbstractBaseBatchProcess.class);

	private int batchSize = 1000;

	protected BatchSqlUpdate batchSqlUpdate;

	public abstract void begin();

	protected boolean check() {
		return batchSqlUpdate != null && batchSqlUpdate.getSql() != null;
	}

	protected int[] flush() {
		if (!check()) {
			throw E;
		}
		return batchSqlUpdate.flush();
	}

	public int getBatchSize() {
		return batchSize;
	}

	public final Object[] getParameters(HashMap<String, Object> context) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final void hook(HashMap<String, Object> context) {
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	protected int update(Object[] params) {
		if (!check()) {
			throw E;
		}
		if (log.isDebugEnabled()) {
			log.debug(batchSqlUpdate.getSql());
			for (int i = 0; i < params.length; i++) {
				log.debug("[" + i + "]'" + params[i] + "'");
			}
		}
		return batchSqlUpdate.update(params);
	}
}
