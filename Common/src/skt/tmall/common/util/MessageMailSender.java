package skt.tmall.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MessageMailSender extends AbstractMessageSender{
	
	protected final Log log = LogFactory.getLog(MessageMailSender.class);
	
	public boolean sendMessage(ArrayList<Object> toAddress){
		try{
			String[] to = new String[toAddress.size()];
			int i = 0;
			for (Object object : toAddress) {
				//email 주소가 없는 사용자는 일단 나에게 메일이 보내지도록 하였다. helper에서 nullpointException이 떨어지거덩...
				to[i] = ((String)object == null) ? "" : (String)object;
				i++;   
			}
			
			MimeMessage msg = sender.createMimeMessage();
			
			//Spring에서 제공하는 Message는 SmartMimeMessage.
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content);
			
			// add attachment files.
			if (files != null) {
				Iterator<String> keys = files.keySet().iterator();
				
				while (keys.hasNext()) {
					String key = keys.next();
					File data = files.get(key);
					
//					helper.addAttachment(new String(key.getBytes(),"8859_1"), data); 
				}
			}
			
			sender.send(msg);
			
			return true;
		}catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("mailError:" + e);
			}
			return false;
		}
	}
	
}
