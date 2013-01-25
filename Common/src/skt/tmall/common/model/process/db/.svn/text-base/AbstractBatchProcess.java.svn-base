package skt.tmall.common.model.process.db;

import java.util.HashMap;

import org.springframework.jdbc.object.BatchSqlUpdate;

import skt.tmall.common.db.Sql;
import skt.tmall.common.model.process.ProcessException;

/**
 * 
 * @author leegt80
 *
 */
public abstract class AbstractBatchProcess extends AbstractBaseBatchProcess {

	protected abstract String getAlias();

	@Override
	public void begin() {
		batchSqlUpdate = new BatchSqlUpdate(getJdbcTemplate().getDataSource(),
				getSql().getSql(), getSql().getTypes());
	}

	@Override
	public void proceed(HashMap<String, Object> context) throws ProcessException {
		if(!check()) {
			begin();
		}
	}

	public Sql getSql() {
		return getSqlProvider().getSql(getAlias());
	}

}
