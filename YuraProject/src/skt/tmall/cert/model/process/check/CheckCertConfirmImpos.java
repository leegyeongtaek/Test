package skt.tmall.cert.model.process.check;

import java.util.Map;

import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.StrUtil;

/**
 * 인증번호 확인 불가능 checkable
 * @author leegt80
 *
 */
public class CheckCertConfirmImpos implements ICheckable<Map<String, Object>> {

	public boolean check(Map<String, Object> context) {

		String LOG_MSG = "";
		String LOG_CODE = "";

		String result = (String) context.get("certYn");

		// 인증 처리 가능 여부 조회 후 return 값이 'N"일 경우 인증번호 확인 불가 처리에 대한 로그 기록
		if("N".equals(result)){

			String certStat 			= StrUtil.nvl((String) context.get("certStat"));				// 인증상태
			String ordPrdStat		= StrUtil.nvl((String) context.get("ordPrdStat"));			// 주문상태
			String isCertDeadLine	= StrUtil.nvl((String) context.get("isCertDeadLine"));		// 인증기간만료 여부

			if ("Y".equals(isCertDeadLine)) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 사용기간이 만료된 인증번호 입니다.";
			}
			else if (ISOMCommonConstants.CERT_STATUS_100.equals(certStat)) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 입력하신 인증번호의 주문건이 존재하지 않습니다. 관리자에게 문의해주세요.";
			}
			else if (ISOMCommonConstants.CERT_STATUS_101.equals(certStat) && ISOMCommonConstants.PRD_ORD_STAT_601.equals(ordPrdStat)) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 반품 처리중인 인증번호 입니다.";
			}
			else if (ISOMCommonConstants.CERT_STATUS_102.equals(certStat)) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 이미 사용된 인증번호 입니다.";
			}
			else if (ISOMCommonConstants.CERT_STATUS_103.equals(certStat)) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 이미 인증 취소된 인증번호 입니다.";
			}
			else if (ISOMCommonConstants.CERT_STATUS_104.equals(certStat) || ISOMCommonConstants.PRD_ORD_STAT_A01.equals(ordPrdStat) ) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 환불 처리된 인증번호입니다.";
			}
			else if (ISOMCommonConstants.CERT_STATUS_105.equals(certStat)) {
				LOG_CODE = certStat;
				LOG_MSG = "인증불가. 상점폐쇄로 사용이 중지된 인증번호 입니다.";
			}
			else {
				LOG_MSG 	= (String) (("".equals(context.get("confirmLogMsg"))) ? "인증불가. 인증번호가 일치하지 않습니다." : context.get("confirmLogMsg"));;
				LOG_CODE 	= (String) (("".equals(context.get("logCode"))) ? "110" : context.get("logCode"));
			}

			context.put("logCode", LOG_CODE);				// 인증처리 시도중 발생한 로그 코드
			context.put("confirmLogMsg", LOG_MSG);		// 인증처리 시도중 발생한 로그 메세지
			return true;
		}

		return false;
	}

}
