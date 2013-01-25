package skt.tmall.common.util;

import java.io.File;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;


/**
 * @author lllkt
 * Mail관련 공통 자원에 대한 추상화 클래스에서의 관리
 */
public abstract class AbstractMessageSender {
	
	protected String from;
	
	protected String subject;
	
	protected String content;
	
	protected Map<String, File> files = null;
	
	protected JavaMailSender sender;
	
	// From
	public void setFrom(String from){
		this.from = from;
	}
	
	// Subject
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	// Content
	public void setcontent(String content){
		this.content = content;
	}
	
	// Attachment Files
	public void setFiles(Map<String, File> files){
		this.files = files;
	}
	
	// JavaMailSender
	public void setSender(JavaMailSender sender){
		this.sender = sender;
	}
	
	
}
