package skt.tmall.common.model.process.db.insert;

import java.util.HashMap;

import skt.tmall.bdt.transfer.sms.SmsRequestBO;
import skt.tmall.bdt.transfer.sms.SmsResponseBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.db.select.SelectTemplateSMSMessage;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;

/**
 * SMS 발송 데이터 등록
 * @author leegt80
 *
 */
public class InsertSMSMessage extends AbstractIbatisUpdateProcess {
	
	private final String INSERT_SMS_MESSAGE = "cert.insert.sms.message";
	
	public InsertSMSMessage() {
		
		checkable = new ICheckable<HashMap<String, Object>>() {

			public boolean check(HashMap<String, Object> context) {
				
				SmsResponseBO resBO 	= (SmsResponseBO) context.get(SelectTemplateSMSMessage.SMS_RESPONSE);
				
				if (resBO.isSendSuccess()) {
					return true;
				}else {
					return false;
				}
				
			}

		};
		
	}
	
	@Override
	protected String getAlias() {
		return INSERT_SMS_MESSAGE;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		return (SmsRequestBO) context.get(SelectTemplateSMSMessage.SMS_REQUEST);
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		SmsResponseBO resBO = (SmsResponseBO) context.get(SelectTemplateSMSMessage.SMS_RESPONSE);
		
		// SMS 발송 성공
		if (result == 1) {
			resBO.setSendSuccess(true);
			resBO.setResultCode(1);
			resBO.setResultMessage("발송성공");
		}
		// SMS 발송 실패
		else {
			resBO.setSendSuccess(false);
			resBO.setResultCode(0);
			resBO.setResultMessage("발송실패");
		}
		
		context.put(SelectTemplateSMSMessage.SMS_RESPONSE, resBO);
		
	}
	
}
