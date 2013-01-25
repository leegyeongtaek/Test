package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * 사용자 정보 조회
 * 
 * @author leegt80
 *
 */
public class SelectUserInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{

	private final String SELECT_USER_INFO = "cert.select.user.info";
	
	@Override
	protected String getAlias() {
		return SELECT_USER_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		return context.get(ICommonConstants.LOGIN_ID);		// 접속자 ID
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		long userNo = 0;
		
		if (context.get(ICommonConstants.PROCESS_RESULT) != null) {
			userNo = (Long) context.get(ICommonConstants.PROCESS_RESULT);
		}
		
		context.put("certConfirmor", userNo);	// 인증처리자
		context.put("updateNo", userNo);			// 수정자
		
	}

}