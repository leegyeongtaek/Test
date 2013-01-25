package skt.tmall.common.model.process.db;

import java.util.HashMap;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.db.Sql;

/**
 * static 한 sql이 존재하란 법이 없다.
 * 
 * @author leegt80
 *
 */
public abstract class AbstractDynamicModifyProcess extends
		AbstractBaseModifyProcess {

	public abstract Sql getSql(HashMap<String, Object> context);

	@Override
	public void proceed(HashMap<String, Object> context) {
		int modify = modify(getSql(context), getParameters(context));
		context.put(ICommonConstants.PROCESS_RESULT, modify);
	}
}
