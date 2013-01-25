package skt.tmall.cert.model.process.db.insert;

import java.util.HashMap;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;

/**
 * 인증번호 확인 처리시 에러 및 인증불가에 대한 로그 기록
 * @author leegt80
 *
 */
public class InsertCertConfirmLog extends AbstractIbatisUpdateProcess {
	
	private final String INSERT_CERT_CONFIRM_LOG = "cert.insert.confirm.log";
	
	@Override
	protected String getAlias() {
		return INSERT_CERT_CONFIRM_LOG;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		String logMsg = (String) context.get("confirmLogMsg");
		
		logMsg = (logMsg.length() >= 100) ? logMsg.substring(0, 100) : logMsg;
		
		SomCertBO certBo = new SomCertBO();
		certBo.setCertNo((String) context.get("certNo"));									// 인증처리 시도한 인증번호
		certBo.setCertStat((String) context.get("certStat"));									// 인증처리 시도한 인증번호 상태
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 인증처리 시도한 인증처리지점번호
		certBo.setCertConfirmor((Long) context.get("certConfirmor"));					// 인증처리 시도한 인증자
		certBo.setCertConfirmIp((String) context.get("certConfirmIp"));					// 인증처리 시도한 인증자 IP
		certBo.setCertDt((String) context.get("certDt"));										// 인증처리 시도한 인증처리일
		certBo.setLogCode((String) context.get("logCode"));								// 인증처리 시도중 발생한 로그 코드
		certBo.setConfirmLogMsg(logMsg);													// 인증처리 시도중 발생한 로그 메세지
		
		return certBo;
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		String resultMsg = "인증처리불가. 인증번호 처리중 에러가 발생하였습니다. 재시도 바랍니다.";
		
		if (((String)context.get("logCode")).startsWith("1") && result == 1)	resultMsg = (String) context.get("confirmLogMsg");
			
		context.put(ICommonConstants.PROCESS_RESULT, resultMsg);
	}
	
}
