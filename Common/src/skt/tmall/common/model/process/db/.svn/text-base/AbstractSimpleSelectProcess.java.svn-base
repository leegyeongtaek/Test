package skt.tmall.common.model.process.db;

import java.util.HashMap;

import skt.tmall.common.core.ICommonConstants;


/**
 * 
 * @author leegt80
 *
 * @param <T>
 */
public abstract class AbstractSimpleSelectProcess<T> extends
		AbstractBaseSimpleSelectProcess<T> {

	public abstract String getAlias();

	protected abstract Class<T> getRequiredType();

	@Override
	public void proceed(HashMap<String, Object> context) {
		Object select = select(getSqlProvider().getSql(getAlias()),
				getParameters(context), getRequiredType());
		context.put(ICommonConstants.PROCESS_RESULT, select);
	}

}
