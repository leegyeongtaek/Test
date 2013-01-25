package skt.tmall.common.model.process.db;

import java.util.HashMap;
import java.util.List;

import skt.tmall.common.core.ICommonConstants;


/**
 * 조회 쿼리를 지원한다.
 * 
 * @author leegt80
 * 
 * @param <R>
 */
public abstract class AbstractSelectProcess<R> extends
		AbstractBaseSelectProcess<R> {

	protected abstract String getAlias();

	@Override
	public void proceed(HashMap<String, Object> context) {
		List<R> select = select(getSqlProvider().getSql(getAlias()),	//쿼리문과 타입, 파라미터값을 setting.
				getParameters(context));
		context.put(ICommonConstants.PROCESS_RESULT, select);
	}

}
