package skt.tmall.common.model.process.db.select;

import java.util.HashMap;
import java.util.Map;

import skt.tmall.bdt.transfer.sms.SmsRequestBO;
import skt.tmall.bdt.transfer.sms.SmsResponseBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * SMS 메세지 템플릿 검색
 * @author leegt80
 *
 */
public class SelectTemplateSMSMessage extends AbstractIbatisSelectObjectProcess<Map<String, Object>> {

	private final String SELECT_TEMPLATE_SMSMSG = "cert.select.template.smsmsg";
	
	public static final String SMS_REQUEST 	= "smsRequest";
	public static final String SMS_RESPONSE	= "smsResponse";
	
	public SelectTemplateSMSMessage() {
		
		checkable = new ICheckable<HashMap<String, Object>>() {

			public boolean check(HashMap<String, Object> context) {
				
				SmsRequestBO reqBO 		= (SmsRequestBO) context.get(SMS_REQUEST);
				SmsResponseBO resBO 	= new SmsResponseBO();
				
				resBO.setSendSuccess(false);
				resBO.setResultCode(0);
				
				if (reqBO == null) {
					reqBO = new SmsRequestBO();
					context.put(SMS_REQUEST, reqBO);
				}
				
		        if(reqBO.getTemplateId() > 0) {
		        	context.put(SMS_RESPONSE, resBO);
		        	return true;
		        }
		       else if (reqBO.getTemplateId() < 0) {
		        	resBO.setSendSuccess(false);
					resBO.setResultCode(0);
					resBO.setResultMessage("부적절한 Tid값, 0이상을 입력해야 합니다.");
					context.put(SMS_RESPONSE, resBO);
					return false;
		        }
		        else {
		        	resBO.setSendSuccess(true);
		    		resBO.setResultCode(1);
		    		resBO.setResultMessage("발송성공");
		        	context.put(SMS_RESPONSE, resBO);
		        	return false;
		        }
				
			}

		};
		
	}
	
	@Override
	protected String getAlias() {
		return SELECT_TEMPLATE_SMSMSG;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SmsRequestBO reqBO = (SmsRequestBO) context.get(SMS_REQUEST);
        
		return reqBO.getTemplateId();
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
			
		@SuppressWarnings("unchecked")
		Map<String, Object> templateMap = ((HashMap<String, Object>) context.get(ICommonConstants.PROCESS_RESULT));
		
		int cnt 			= Integer.parseInt(templateMap.get("paramcnt")+"");
    	String tname 	= (String)templateMap.get("tname");
    	
    	SmsRequestBO reqBO 		= (SmsRequestBO) context.get(SMS_REQUEST);
    	SmsResponseBO resBO 	= (SmsResponseBO) context.get(SMS_RESPONSE);
    	
    	 // 템플릿 사용여부와 템플릿 메시지 생성
		String message 		= reqBO.getMessage();
		String stMessage 	= "";

    	String[] result = message.split(",");
    	if(cnt == 0) {
            stMessage = tname;
        }else if(cnt != result.length) {
            stMessage = "-1";
        }else {
            for(int i = 0; i < result.length ; i++){
                String tmp = result[i].trim();
                tname = tname.replaceAll("#"+(i+1),tmp);
                stMessage+= tname.substring(0,tname.indexOf(tmp)+tmp.length());
                tname = tname.substring(tname.indexOf(tmp)+tmp.length(),tname.length());
            }
            stMessage += tname;
        }
    	
        if(stMessage.equals("-1")){
            resBO.setSendSuccess(false);
			resBO.setResultCode(0);
			resBO.setResultMessage("템플릿 아이디와 전달된 메시지 파라메터가 일치하지 않습니다.");
			context.put(SMS_RESPONSE, resBO);
        }
        
        reqBO.setMessage(stMessage);
        
        resBO.setSendSuccess(true);
		resBO.setResultCode(1);
		resBO.setResultMessage("발송성공");
		context.put(SMS_RESPONSE, resBO);
		context.put(SMS_REQUEST, reqBO);
	}

}
