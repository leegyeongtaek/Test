package skt.tmall.common.aop;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StopWatch;

import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.core.IDisposable;

import skt.tmall.common.aop.DatabaseServiceAdvice;
import skt.tmall.common.db.ISelectDynamicQuery;
import skt.tmall.common.db.ISelectWrapper;
import skt.tmall.common.db.JdbcTemplateManager;
import skt.tmall.common.db.SqlProvider;
import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.db.AbstractBaseSelectProcess;
import skt.tmall.common.model.process.db.AbstractDatabaseProcess;

/**
 * TODO .. extract interface
 * 
 * 1. sql wrapping 을 한다.
 * 2. database 접근에 필요한 객체를 동적 injection 시킨다. 
 * 3. 쿼리 실행 시간을 로깅한다. 
 * 4. dynamic sql의 where절을 생성한다.
 * 
 * @author leegt80
 */
@Aspect
public class DatabaseServiceAdvice {

	private static Log log = LogFactory.getLog(DatabaseServiceAdvice.class);

	private JdbcTemplateManager jdbcTemplateManager;

	private SqlProvider sqlProvider;

	private ISelectWrapper selectWrapper;
	
	private ISelectDynamicQuery selectDynamic;

	/**
	 * 
	 * @param call
	 * @param context per thread context
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around("skt.tmall.common.aop.SystemPointcut.databaseService() and args(context)")
	public Object aroundDatabaseProcess(ProceedingJoinPoint call,
			HashMap<String, Object> context) throws Throwable {
		Object target = call.getTarget();

		if (log.isDebugEnabled()) {
			log
					.debug("@Around(\"skt.tmall.common.aop.SystemPointcut.databaseService()\") : "
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
		
		/*
		 * isDynamic injection
		 */
		checkDynamic(context, target);

		/*
		 * isWrapping injection
		 */
		checkWrapping(context, target);

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
	 * 동적 쿼리가 존재할 경우 데이터베이스 서비스 프로세스에 ISelectDynamicQuery를 injection 한다.
	 * @param context
	 * @param target
	 * @return
	 * @throws ProcessException
	 */
	@SuppressWarnings("unchecked")
	private boolean checkDynamic(HashMap<String, Object> context, Object target)
			throws ProcessException {
		if (context.get(ICommonConstants.IS_DYNAMIC) != null) {
			if (target instanceof AbstractBaseSelectProcess) {
				AbstractBaseSelectProcess process = (AbstractBaseSelectProcess) target;
				process.setSelectDynamic(selectDynamic);
				return true;
			} else {
				throw new ProcessException(
						"Dynamic service is must subtype of AbstractBaseSelectProcess");
			}
		}
		return false;
	}

	/**
	 * wrapping 되야할 경우 데이터베이스 서비스 프로세스에 ISelectWrapper injection 한다.
	 * @param context
	 * @param target
	 * @return
	 * @throws ProcessException
	 */
	@SuppressWarnings("unchecked")
	private boolean checkWrapping(HashMap<String, Object> context, Object target)
			throws ProcessException {
		if (context.get(ICommonConstants.IS_WRAPPING) != null) {
			if (target instanceof AbstractBaseSelectProcess) {
				AbstractBaseSelectProcess process = (AbstractBaseSelectProcess) target;
				process.setSelectWrapper(selectWrapper);
				return true;
			} else {
				throw new ProcessException(
						"wrapping service is must subtype of AbstractBaseSelectProcess");
			}
		}
		return false;
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
		if (target instanceof AbstractDatabaseProcess) {
			AbstractDatabaseProcess process = (AbstractDatabaseProcess) target;
			process.setJdbcTemplate(this.jdbcTemplateManager);
			process.setSqlProvider(this.sqlProvider);
		} else {
			throw new ProcessException(
					"databaseService is must subtype of AbstractDatabseProcess");
		}

	}

	/**
	 * 최초 WAS 시작때 injection 되어야 한다.
	 * @param jdbcTemplateManager
	 */
	@Required
	public void setJdbcTemplateManager(JdbcTemplateManager jdbcTemplateManager) {
		this.jdbcTemplateManager = jdbcTemplateManager;
	}

	/**
	 * 최초 WAS 시작때 injection 되어야 한다.
	 * @param selectWrapper
	 */
	@Required
	public void setSelectWrapper(ISelectWrapper selectWrapper) {
		this.selectWrapper = selectWrapper;
	}
	
	/**
	 * 최초 WAS 시작때 injection 되어야 한다.
	 * @param selectDynamic
	 */
	@Required
	public void setSelectDynamic(ISelectDynamicQuery selectDynamic) {
		this.selectDynamic = selectDynamic;
	}

	/**
	 * 최초 WAS 시작때 injection 되어야 한다.
	 * @param sqlProvider
	 */
	@Required
	public void setSqlProvider(SqlProvider sqlProvider) {
		this.sqlProvider = sqlProvider;
	}

}
