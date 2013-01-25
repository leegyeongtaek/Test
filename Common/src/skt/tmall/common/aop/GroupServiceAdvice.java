package skt.tmall.common.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;

import skt.tmall.common.aop.GroupServiceAdvice;
import skt.tmall.common.db.TransactionTemplateManager;
import skt.tmall.common.model.process.FileUploadTransactionalProcessGroup;
import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.TransactionalProcessGroup;

/**
 * TODO .. extract interface
 * 
 * DatabaseService 와 유사하며 DB 트랜잭션을 지원한다.
 * 
 * DatabaseService 와 유사하며 FileUpload 트랜잭션을 지원한다.
 * 
 * @see skt.tmall.common.aop.DatabaseServiceAdvice
 * 
 * @author leegt80
 */
@Aspect
public class GroupServiceAdvice {

	private static Log log = LogFactory.getLog(GroupServiceAdvice.class);

	private TransactionTemplateManager transactionTemplateManager;
	
	private String realPath;
	
	private String tempPath;

	@SuppressWarnings("unchecked")
	@Around("skt.tmall.common.aop.SystemPointcut.groupService()")
	public Object aroundTransactionProcess(ProceedingJoinPoint call)
			throws Throwable {
		Object target = call.getTarget();

		if (log.isDebugEnabled()) {
			log
					.debug("@Around(\"skt.tmall.common.aop.SystemPointcut.groupService()\") : "
							+ target.getClass().getName());
		}

		if (((IProcess) target).isInit()) {
			// do nothing
		} else {
			// target object 의 필요 instance 를 injection 함.
			inject(target);
		}

		Object proceed = call.proceed();
		return proceed;
	}

	/**
	 * TODO remove instanceof
	 * 
	 * @param target
	 * @throws ProcessException
	 */
	private void inject(Object target) throws ProcessException {
		if (log.isDebugEnabled()) {
			log.debug("start init..");
		}
		if (target instanceof TransactionalProcessGroup) {
			((TransactionalProcessGroup) target)
					.setTransactionTemplate(transactionTemplateManager);
		} else if (target instanceof FileUploadTransactionalProcessGroup) {
			((FileUploadTransactionalProcessGroup) target)
					.setTransactionTemplate(transactionTemplateManager);
			
			((FileUploadTransactionalProcessGroup) target)
					.setTempPath(tempPath);
			
			if (((FileUploadTransactionalProcessGroup) target).getRealPath() == null) {
				((FileUploadTransactionalProcessGroup) target)
						.setRealPath(realPath);
			}
		}else {
			throw new ProcessException(
					"databaseService is must subtype of AbstractProcessGroup");
		}
	}

	@Required
	public void setTransactionTemplateManager(
			TransactionTemplateManager transactionTemplateManager) {
		this.transactionTemplateManager = transactionTemplateManager;
	}
	
	@Required
	public void setRealPath(String realPath){
		this.realPath = realPath;
	}
	
	@Required
	public void setTempPath(String tempPath){
		this.tempPath = tempPath;
	}
}
