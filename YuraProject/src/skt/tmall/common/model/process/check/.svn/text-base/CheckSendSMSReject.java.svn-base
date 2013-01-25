package skt.tmall.common.model.process.check;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import skt.tmall.bdt.transfer.sms.SmsResponseBO;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.db.select.SelectTemplateSMSMessage;

/**
 * SMS 발송처리 성공 확인 checkable
 * @author leegt80
 *
 */
public class CheckSendSMSReject implements ICheckable<Map<String, Object>> {
	
	private Log log = LogFactory.getLog(CheckSendSMSReject.class);
	
	public boolean check(Map<String, Object> context) throws ProcessException {
		
		SmsResponseBO resBO = (SmsResponseBO) context.get(SelectTemplateSMSMessage.SMS_RESPONSE);

		// SMS 발송 성공
        if(resBO.isSendSuccess() && "R".equals(context.get("confirmType"))) {
        	if (log.isDebugEnabled()) {
     			log.debug("SMS 발송 성공: " + resBO.getResultMessage());
     		}
        	
        	return true;
        }
        // SMS 발송 성공이면서 반품거부가 아닐 일 경우 
        else if(resBO.isSendSuccess() && !"R".equals(context.get("confirmType"))) {
        	return false;
        }
        // SMS 발송 실패시 ProcessException throw
        else {
        	if (log.isDebugEnabled()) {
      			log.debug("SMS 발송 실패: " + resBO.getResultMessage());
      		}
        	
        	throw new ProcessException("SMS 발송 실패: " + resBO.getResultMessage()) ;
        }
		
	}
	
}