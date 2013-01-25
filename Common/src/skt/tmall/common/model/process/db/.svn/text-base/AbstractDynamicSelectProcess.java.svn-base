package skt.tmall.common.model.process.db;

import java.util.HashMap;
import java.util.List;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.db.Sql;

/**
 * static 한 sql이 존재하란 법이 없다.
 * 
 * @author leegt80
 *
 * @param <R>
 */
public abstract class AbstractDynamicSelectProcess<R> extends
		AbstractBaseSelectProcess<R> {

	@Override
	public void proceed(HashMap<String, Object> context) {
		List<R> select = select(getSql(context), getParameters(context));
		context.put(ICommonConstants.PROCESS_RESULT, select);
	}

	public abstract Sql getSql(HashMap<String, Object> context);

}
