package skt.tmall.cert.model.process.check;

import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.cert.model.process.db.select.SelectCertNumInfo;
import skt.tmall.cert.model.process.db.update.UpdateCertStatusCancel;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 인증번호 취소 불가능 checkable
 * @author leegt80
 *
 */
public class CheckCertCancelImpos implements ICheckable<Map<String, Object>> {
	
	public boolean check(Map<String, Object> context) {
		
		String LOG_MSG = "";
		String LOG_CODE = "";
		
		// 인증 상태 미인증 처리 후 return 값이 'N"일 경우 인증번호 취소 불가 처리에 대한 로그 기록
		String result = (String) context.get(UpdateCertStatusCancel.CANCELYN);
		
		if("N".equals(result)){
			
			SomCertBO certBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
			
			if (certBo != null){
				String certStat = certBo.getCertStat();
				
				String isCertDeadLine	= certBo.getIsCertDeadLine();	// 인증기간만료 여부
				
				if ("Y".equals(isCertDeadLine)) {
					LOG_CODE = certStat;
					LOG_MSG = "인증취소불가. 사용기간이 만료된 인증 번호 입니다.";
				}
				else if (ISOMCommonConstants.CERT_STATUS_100.equals(certStat)) {
					LOG_CODE = certStat;
					LOG_MSG = "인증취소불가. 주문이 발생하지 않은 인증 번호 입니다.";
				}
				else if (ISOMCommonConstants.CERT_STATUS_101.equals(certStat)) {
					LOG_CODE = certStat;
					LOG_MSG = "인증취소불가. 미 인증 번호 입니다.";
				}
				else if (ISOMCommonConstants.CERT_STATUS_103.equals(certStat)) {
					LOG_CODE = certStat;
					LOG_MSG = "인증취소불가. 이미 인증 취소된 인증 번호 입니다.";
				}
				else if (ISOMCommonConstants.CERT_STATUS_104.equals(certStat)) {
					LOG_CODE = certStat;
					LOG_MSG = "인증취소불가. 이미 인증불가 처리된 인증 번호 입니다.";
				}
				else if (ISOMCommonConstants.CERT_STATUS_105.equals(certStat)) {
					LOG_CODE = certStat;
					LOG_MSG = "인증취소불가. 상점 폐쇄로 이미 인증중지 처리된 인증 번호 입니다.";
				}
				else {
					LOG_MSG = "인증취소불가. 인증번호가 일치하지 않습니다.";
					LOG_CODE 	= (String) (("".equals(context.get("logCode"))) ? "110" : context.get("logCode"));
				}
			}
			else{
				LOG_MSG 	= (String) (("".equals(context.get("confirmLogMsg"))) ? "인증취소불가. 이미 인증취소 처리 되었거나 인증번호가 일치하지 않습니다." : context.get("confirmLogMsg"));;
				LOG_CODE = "120";
			}
			
			context.put("logCode", LOG_CODE);				// 인증취소처리 시도중 발생한 로그 코드
			context.put("confirmLogMsg", LOG_MSG);		// 인증취소처리 시도중 발생한 로그 메세지
			return true;
		}
			
		return false;
	}
	
}
