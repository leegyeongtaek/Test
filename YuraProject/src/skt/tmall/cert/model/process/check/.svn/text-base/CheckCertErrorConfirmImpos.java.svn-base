package skt.tmall.cert.model.process.check;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import skt.tmall.common.model.process.ICheckable;

/**
 * 인증번호 확인 진행중 에러발생에 따라 인증처리 불가능 checkable
 * @author leegt80
 *
 */
public class CheckCertErrorConfirmImpos implements ICheckable<Map<String, Object>> {
	
	public boolean check(Map<String, Object> context) {
		
		Exception exception = (Exception) context.get("error");
		
		// 인증번호 확인 진행중 에러발생시 인증처리 불가능 처리에 대한 로그 기록
		if(exception != null){
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			
			exception.printStackTrace(pw);
			
			String logMsg = sw.toString().trim();
			
			logMsg = (logMsg.length() > 200) ? logMsg.substring(0, 199) : logMsg;
			context.put("logCode", "900");					// 인증처리 시도중 발생한 로그 코드
			context.put("confirmLogMsg", logMsg);		// 인증처리 시도중 발생한 로그 메세지
			return true;
		}
			
		return false;
	}
	
}