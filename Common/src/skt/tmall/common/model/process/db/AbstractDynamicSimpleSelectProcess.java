package skt.tmall.common.model.process.db;

import java.util.HashMap;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.db.Sql;

/**
 * static 한 sql이 존재하란 법이 없다.
 * 
 * @author leegt80
 *
 * @param <T>
 */
public abstract class AbstractDynamicSimpleSelectProcess<T> extends
		AbstractBaseSimpleSelectProcess<T> {

	protected abstract Class<T> getRequiredType();

	protected abstract Sql getSql(HashMap<String, Object> context);

	@Override
	public void proceed(HashMap<String, Object> context) {
		Object select = select(getSql(context), getParameters(context),
				getRequiredType());

		context.put(ICommonConstants.PROCESS_RESULT, select);
	}

}
