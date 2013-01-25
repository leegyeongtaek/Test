package skt.tmall.cert.model.process.check;

import java.util.Map;

import skt.tmall.common.model.process.ICheckable;

/**
 * 사용자 정보 조회 checkable
 * @author leegt80
 *
 */
public class CheckUserInfo implements ICheckable<Map<String, Object>> {
	
	public boolean check(Map<String, Object> context) {
		
		// API로 접근시 사용자 정보 조회 PROCESS 진행 
		String result = (String) context.get("isAPI");
		
		if("Y".equals(result))
			return true;
			
		return false;
	}
	
}