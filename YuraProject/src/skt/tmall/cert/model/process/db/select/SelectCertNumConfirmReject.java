package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * 반품거부건에 한해 인증 처리 가능 여부 조회
 * 
 * 인증을 처리하려는 지점이 해당 상품에 대해 해당 인증번호로 처리할 수 있는지.
 * 인증상태 (101:미인증상태)
 * 주문상태 (501:배송완료, 601: 클레임진행중, 901: 구매확정)
 * 인증가능상태 (501:배송완료, 601: 클레임진행중, 901: 구매확정)
 * @author leegt80
 *
 */
public class SelectCertNumConfirmReject extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{

	private final String SELECT_CERTNUM_CONFIRM_REJECT = "cert.select.certnum.confirmReject";
	
	public SelectCertNumConfirmReject(){
		checkable = new ICheckable<HashMap<String,Object>>() {
			
			public boolean check(HashMap<String, Object> context)
					throws ProcessException {
				
				// 인증처리 시도중 발생한 로그 코드 && NOT 반품거부 타입
				if ("110".equals(context.get("logCode"))) {

					context.put("certYn", "N");				// 인증처리 가능 여부
					context.put("prdNo", -1);				// 상품번호
					context.put("prdNm", "");				// 상품명
					context.put("shopNo", -1);				// 상점번호
					context.put("certStat", "");				// 인증상태
					context.put("ordPrdStat", "");			// 주문상태
					context.put("isCertDeadLine", "N");	// 인증기간만료 여부
					context.put("smsTelNo", "");			// SMS 전송 전화번호
					context.put("sendSMSTime", "");		// SMS 발송 시간
					context.put("shopNm", "");				// 상점명
				
					return false;
				}else if (!"R".equals(context.get("confirmType"))) {
					return false;
				}
				
				return true;
			}
		};
	}
	
	@Override
	protected String getAlias() {
		return SELECT_CERTNUM_CONFIRM_REJECT;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = new SomCertBO();
		
		certBo.setPrdNo(Long.parseLong((String)context.get("prdNo")));					// 상품번호
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 인증 처리 지점
		certBo.setCertNo((String) context.get("certNo"));									// 인증번호
		return certBo;
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		SomCertBO certBo = (SomCertBO) context.get(ICommonConstants.PROCESS_RESULT);
		
		// 인증처리 가능
		if (certBo != null && "Y".equals(certBo.getCertYn())) {
			context.put("certYn", certBo.getCertYn());							// 인증처리 가능 여부
			context.put("prdNo", certBo.getPrdNo());						// 상품번호
			context.put("prdNm", certBo.getPrdNm());						// 상품명
			context.put("shopNo", certBo.getShopNo());					// 상점번호
			context.put("ordPrdStat", certBo.getOrdPrdStat());				// 주문상태
			context.put("isCertDeadLine", certBo.getIsCertDeadLine());	// 인증기간만료 여부
			context.put("smsTelNo", certBo.getSmsTelNo());				// SMS 전송 전화번호
			context.put("sendSMSTime", certBo.getSendSMSTime());		// SMS 발송 시간
			context.put("shopNm", certBo.getShopNm());					// 상점명
		}
		// 인증번호는 일치하나 인증 처리 불가능
		else if (certBo != null && "N".equals(certBo.getCertYn())) {
			context.put("certYn", certBo.getCertYn());							// 인증처리 가능 여부
			context.put("prdNo", certBo.getPrdNo());						// 상품번호
			context.put("prdNm", certBo.getPrdNm());						// 상품명
			context.put("shopNo", certBo.getShopNo());					// 상점번호
			context.put("certStat", certBo.getCertStat());						// 인증상태
			context.put("ordPrdStat", certBo.getOrdPrdStat());				// 주문상태
			context.put("isCertDeadLine", certBo.getIsCertDeadLine());	// 인증기간만료 여부
			context.put("smsTelNo", certBo.getSmsTelNo());				// SMS 전송 전화번호
			context.put("sendSMSTime",  certBo.getSendSMSTime());		// SMS 발송 시간
			context.put("shopNm", certBo.getShopNm());					// 상점명
		}
		// 인증번호 불일치로 인증 처리 불가능
		else {
			context.put("certYn", "N");				// 인증처리 가능 여부
			context.put("prdNo", -1);				// 상품번호
			context.put("prdNm", "");				// 상품명
			context.put("shopNo", -1);				// 상점번호
			context.put("certStat", "");				// 인증상태
			context.put("ordPrdStat", "");			// 주문상태
			context.put("isCertDeadLine", "N");	// 인증기간만료 여부
			context.put("smsTelNo", "");			// SMS 전송 전화번호
			context.put("sendSMSTime", "");		// SMS 발송 시간
			context.put("shopNm", "");				// 상점명
		}
		
	}

}
