package skt.tmall.common.aop;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;

import skt.tmall.common.core.IDisposable;

import skt.tmall.common.aop.RepositoryServiceAdvice;
import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.repository.AbstractRepositoryProcess;
import skt.tmall.common.repository.IRepository;

/**
 * TODO .. extract interface
 * 
 * 외부 저장소 서비스를 지원한다.
 * 
 * @author leegt80
 * 
 */
@Aspect
public class RepositoryServiceAdvice {

	private static Log log = LogFactory.getLog(RepositoryServiceAdvice.class);
	private IRepository repository;

	@SuppressWarnings("unchecked")
	@Around("skt.tmall.common.aop.SystemPointcut.repositoryService() and args(context)")
	public Object aroundDatabaseProcess(ProceedingJoinPoint call,
			HashMap<String, Object> context) throws Throwable {
		Object target = call.getTarget();

		if (log.isDebugEnabled()) {
			log
					.debug("@Around(\"skt.tmall.common.aop.SystemPointcut.repositoryService()\") : "
							+ target.getClass().getName());
		}

		/*
		 * TODO
		 */
		if (((IProcess) target).isInit()) {
			// do nothing
		} else {
			// target object 의 필요 instance 를 injection 함.
			inject(target);
		}
		Object proceed = call.proceed();
		if (target instanceof IDisposable) {
			((IDisposable) target).dispose();
		}
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
		if (target instanceof AbstractRepositoryProcess) {
			AbstractRepositoryProcess process = (AbstractRepositoryProcess) target;
			process.setRepository(repository);
		} else {
			throw new ProcessException(
					"repositoryService is must subtype of AbstractRepositoryProcess");
		}

	}

	@Required
	public void setRepository(IRepository repository) {
		this.repository = repository;
	}

}
