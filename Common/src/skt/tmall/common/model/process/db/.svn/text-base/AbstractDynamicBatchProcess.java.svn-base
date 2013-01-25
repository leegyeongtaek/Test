package skt.tmall.common.model.process.db;

import java.util.HashMap;

import org.springframework.jdbc.object.BatchSqlUpdate;

import skt.tmall.common.db.Sql;

/**
 * static 한 sql이 존재하란 법이 없다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractDynamicBatchProcess extends
		AbstractBaseBatchProcess {

	@Override
	public void begin() {
		batchSqlUpdate = new BatchSqlUpdate();
		batchSqlUpdate.setDataSource(getJdbcTemplate().getDataSource());
	}

	@Override
	public void proceed(HashMap<String, Object> context) {
		if (!check()) {
			begin();
			Sql sql = getSql(context);
			batchSqlUpdate.setSql(sql.getSql());
			batchSqlUpdate.setTypes(sql.getTypes());
		}
	}

	public abstract Sql getSql(HashMap<String, Object> context);
}
