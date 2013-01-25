package skt.tmall.common.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.util.MessageMailSender;

/**
 * @author leegt80
 * System Exception Aspect Class
 * 1. 에러 발생시 관리자에게 에러 메세지 전송.
 * 2. 에러 발생시 에러 처리 view. (미적용)
 */
@Aspect
public class DatabaseExceptionAdvice{
	
	private String systemName;			
	private MessageMailSender sender;
	private ArrayList<Object> toAddress;
	
	String errorClassName;
	String errorFileName;
	String errorMethodName;
	
	int    errorLineNumber;
	
	public void setMailSender(MessageMailSender sender) {
		this.sender = sender;
	}
	
	public void setToAddress(ArrayList<Object> toAddress) {
		this.toAddress = toAddress;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	private void sendErrorMessage(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter  pw = new PrintWriter(sw);
		
		ex.printStackTrace(pw);
		
//		ex.getCause().printStackTrace(pw);
		
		if (sender != null){
			sender.setSubject("This letter is 《" + systemName + "》 System Error Message");			//Title
			sender.setcontent(sw.toString());				//Contents
			if (toAddress != null && toAddress.size() > 0)sender.sendMessage(toAddress);				//Email
		}
		
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.databaseException()", throwing="ex")
	public void afterThrowingAdvice(RuntimeException ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.databaseException()", throwing="ex")
	public void afterThrowingAdvice(NullPointerException ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.databaseException()", throwing="ex")
	public void afterThrowingAdvice(ProcessException ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.databaseException()", throwing="ex")
	public void afterThrowingAdvice(Exception ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.ibatisDatabaseException()", throwing="ex")
	public void afterIbatisThrowingAdvice(RuntimeException ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.ibatisDatabaseException()", throwing="ex")
	public void afterIbatisThrowingAdvice(NullPointerException ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.ibatisDatabaseException()", throwing="ex")
	public void afterIbatisThrowingAdvice(ProcessException ex) {
		sendErrorMessage(ex);
	}
	
	@AfterThrowing(pointcut="skt.tmall.common.aop.SystemPointcut.ibatisDatabaseException()", throwing="ex")
	public void afterIbatisThrowingAdvice(Exception ex) {
		sendErrorMessage(ex);
	}
	
}
