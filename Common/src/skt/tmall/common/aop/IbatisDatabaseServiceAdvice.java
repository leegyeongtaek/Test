package skt.tmall.common.aop;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.util.StopWatch;

import skt.tmall.common.core.IDisposable;

import skt.tmall.common.aop.IbatisDatabaseServiceAdvice;
import skt.tmall.common.db.SqlMapClientTemplateManager;
import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisDatabaseProcess;

/**
 * TODO .. extract interface
 * 
 * 1. database 접근에 필요한 객체를 동적 injection 시킨다. 
 * 2. 쿼리 실행 시간을 로깅한다. 
 * 
 * @author leegt80
 */
@Aspect
public class IbatisDatabaseServiceAdvice {

	private static Log log = LogFactory.getLog(IbatisDatabaseServiceAdvice.class);

	private SqlMapClientTemplateManager sqlMapClientTemplateManager;

	/**
	 * 
	 * @param call
	 * @param context per thread context
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around("skt.tmall.common.aop.SystemPointcut.ibatisDatabaseService() and args(context)")
	public Object aroundDatabaseProcess(ProceedingJoinPoint call,
			HashMap<String, Object> context) throws Throwable {
		Object target = call.getTarget();

		if (log.isDebugEnabled()) {
			log
					.debug("@Around(\"skt.tmall.common.aop.SystemPointcut.ibatisDatabaseService()\") : "
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
		
		StopWatch stopWatch = new StopWatch(target.getClass().getName() + "."
				+ call.getSignature().getName());
		stopWatch.start();
		try {
			if (log.isDebugEnabled()) {
				log.debug("BEFORE : " + context);
			}
			Object proceed = call.proceed();
			if (log.isDebugEnabled()) {
				log.debug("AFTER : " + context);
			}
			// instance 의 소멸이 필요할 경우 강제적으로 null 을 주어 가비지컬렉션이 정상적으로 동작 할 수 있게 지원한다. 
			if (target instanceof IDisposable) {
				((IDisposable) target).dispose();
			}
			return proceed;
		} finally {
			stopWatch.stop();
			if (log.isDebugEnabled()) {
				log.debug(stopWatch.shortSummary());
			}
		}

	}
	
	/**
	 * TODO remove instanceof
	 *  프로세스가 데이터 베이스 서비스의 가장 최상위 추상화 인스턴스 타입인지 확인 후 jdbcTemplate과 sqlProvider를 injection 한다.
	 * @param target
	 * @throws ProcessException
	 */
	private void inject(Object target) throws ProcessException {
		
		if (log.isDebugEnabled()) {
			log.debug("start init..");
		}
		if (target instanceof AbstractIbatisDatabaseProcess) {
			AbstractIbatisDatabaseProcess process = (AbstractIbatisDatabaseProcess) target;
			process.setSqlMapClientTemplate(this.sqlMapClientTemplateManager);
		} else {
			throw new ProcessException(
					"ibatisDatabaseService is must subtype of AbstractDatabseProcess");
		}
		
//		new SqlMapClientFactoryBean().;

	}

	/**
	 * 최초 WAS 시작때 injection 되어야 한다.
	 * @param jdbcTemplateManager
	 */
	@Required
	public void setSqlMapClientTemplateManager(SqlMapClientTemplateManager sqlMapClientTemplateManager) {
		this.sqlMapClientTemplateManager = sqlMapClientTemplateManager;
	}

}
