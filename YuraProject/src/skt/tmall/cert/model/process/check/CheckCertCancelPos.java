package skt.tmall.cert.model.process.check;

import java.util.Map;

import skt.tmall.bdt.transfer.sms.SmsRequestBO;
import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.cert.model.process.db.select.SelectCertNumInfo;
import skt.tmall.cert.model.process.db.update.UpdateCertStatusCancel;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.db.select.SelectTemplateSMSMessage;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.StrUtil;

/**
 * 인증번호 취소 히스토리 가능 checkable
 * @author leegt80
 *
 */
public class CheckCertCancelPos implements ICheckable<Map<String, Object>> {
	
	public boolean check(Map<String, Object> context) throws ProcessException {
		
		// 인증 상태 미인증(취소) 처리 후 return 값이 'Y"일 경우 인증상태 취소 히스토리 처리 process 진행
		String result = (String) context.get(UpdateCertStatusCancel.CANCELYN);
		
		if("Y".equals(result)) {
			
			SomCertBO certBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
			
			if (certBo == null) throw new ProcessException("인증번호 정보가 존재하지 않습니다. 관리자에게 문의하시기 바랍니다.");
			
			long prdNo 		= certBo.getPrdNo();					// 상품번호
			String prdNm	= certBo.getPrdNm();					// 상품명
			String shopNm	= certBo.getShopNm();					// 상점명
			String sendSMSTime = certBo.getSendSMSTime();		// SMS 발송 시간
			String certDt		= (String) context.get("certDt");		// 인증 취소 처리일
			if (certDt.length() > 17) {
				certDt = certDt.substring(5, 16);
			}
			
			// 상품명, 상점명이 18 byte 이상일 경우 16 byte + ..까지 표시 
			if (prdNm.getBytes().length > 18) 	prdNm = StrUtil.parseStringByBytes(prdNm, 16, "euc-kr")[0] + "..";
			if (shopNm.getBytes().length > 18) 	shopNm = StrUtil.parseStringByBytes(shopNm, 16, "euc-kr")[0] + "..";
			
			// 인증 취소 SMS 전송 데이터 설정 
            SmsRequestBO req = new SmsRequestBO();
            req.setTemplateId(ISOMCommonConstants.CERT_SMS_105_TID);						// 템플릿 아이디 지정. 일반 발송(템플릿 메세지를 사용하지 않을 경우)은 0을 설정.
            req.setType("7");                       															// SMS 구분. 7: 정보성, 8: 마케팅, 9: MO
            req.setSmsType("7");                 															// Callback 형태 :  7: 1way, 8: 2way
            req.setCampaign("인증취소");		 															// 캠페인 명
            req.setCellPhoneNum((String) certBo.getSmsTelNo());										// SMS 전송 전화번호
            req.setMessage(shopNm+","+certDt);														// 보낼 메세지 (상점명, 인증취소시간)
            req.setCallback(ISOMCommonConstants.SEND_TO_SMS_MESSAGE_PHONE);			// 보내는 사람 핸드폰 번호 
            
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