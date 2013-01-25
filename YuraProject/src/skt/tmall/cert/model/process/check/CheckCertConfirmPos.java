package skt.tmall.cert.model.process.check;

import java.util.Map;

import skt.tmall.bdt.transfer.sms.SmsRequestBO;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.db.select.SelectTemplateSMSMessage;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.StrUtil;

/**
 * 인증번호 확인 가능 checkable (쿠프는 SMS 자체 발송함)
 * @author leegt80
 *
 */
public class CheckCertConfirmPos implements ICheckable<Map<String, Object>> {
	
	public boolean check(Map<String, Object> context) {
		
		// 인증 처리 가능 여부 조회 후 return 값이 'Y"일 경우 인증상태 완료 처리 process 진행
		String result = (String) context.get("certYn");
		
		if("Y".equals(result)) {
			
			long prdNo 		= (Long) context.get("prdNo");				// 상품번호
			String prdNm	= (String) context.get("prdNm");				// 상품명
			String shopNm	= (String) context.get("shopNm");				// 상점명
			String sendSMSTime = (String) context.get("sendSMSTime");// SMS 발송 시간
			String certDt		= (String) context.get("certDt");				// 인증 처리일
			if (certDt.length() > 17) {
				certDt = certDt.substring(5, 16);
			}
			
			// 상품명, 상점명이 18 byte 이상일 경우 16 byte + ..까지 표시 
			if (prdNm.getBytes().length > 18) 	prdNm = StrUtil.parseStringByBytes(prdNm, 16, "euc-kr")[0] + "..";
			if (shopNm.getBytes().length > 18) 	shopNm = StrUtil.parseStringByBytes(shopNm, 16, "euc-kr")[0] + "..";
			
			// 인증 확인 SMS 전송 데이터 설정 
            SmsRequestBO req = new SmsRequestBO();
            req.setTemplateId(ISOMCommonConstants.CERT_SMS_103_TID);						// 템플릿 아이디 지정. 일반 발송(템플릿 메세지를 사용하지 않을 경우)은 0을 설정.
            req.setType("7");                       															// SMS 구분. 7: 정보성, 8: 마케팅, 9: MO
            req.setSmsType("7");                 															// Callback 형태 :  7: 1way, 8: 2way
            req.setCampaign("인증완료");		 															// 캠페인 명
            req.setCellPhoneNum((String) context.get("smsTelNo"));									// SMS 전송 전화번호
            req.setMessage(shopNm+","+certDt+","+ISOMCommonConstants.MYPAGE_URL);	// 보낼 메세지 (상점명, 인증시간, shot URL (해당 상품의 구매확정페이지))
            req.setCallback( ISOMCommonConstants.SEND_TO_SMS_MESSAGE_PHONE);			// 보내는 사람 핸드폰 번호 							
           
            // SMS 발송 시간
            if (sendSMSTime != null && !"".equals(sendSMSTime)) {
            	req.setReserveStartTime(sendSMSTime);
            }
            
            context.put(SelectTemplateSMSMessage.SMS_REQUEST, req);
            
			return true;
		}
			
		return false;
	}
	
}
