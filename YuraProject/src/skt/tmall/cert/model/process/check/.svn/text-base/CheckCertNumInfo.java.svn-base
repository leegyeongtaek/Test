package skt.tmall.cert.model.process.check;

import java.util.Map;

import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 인증번호 확인시 인증정보 checkable
 * @author leegt80
 *
 */
public class CheckCertNumInfo implements ICheckable<Map<String, Object>> {
	
	public boolean check(Map<String, Object> context) {
		
		if(ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(context.get(ICommonConstants.PROCESS_RESULT))) {
			return true;
		}
		else {
			return false;
		}
			
	}
	
}