package skt.tmall.common.model.process.db;

import java.util.HashMap;

import skt.tmall.common.core.ICommonConstants;


/**
 * DML 쿼리를 지원한다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractModifyProcess extends AbstractBaseModifyProcess {

	protected abstract String getAlias();

	@Override
	public void proceed(HashMap<String, Object> context) {
		int modify = modify(getSqlProvider().getSql(getAlias()),
				getParameters(context));
		context.put(ICommonConstants.PROCESS_RESULT, modify);
	}

}
