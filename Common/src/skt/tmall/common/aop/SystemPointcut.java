package skt.tmall.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 전체 시스템에 필요한 포인터컷 모음
 * 
 * 각 어드바이스에서 직접 포인터컷을 지정할 수 도 있지만 표현식이 변경될 소지도 있고 어디에 뭐가 있는지 알기 어렵기에 한군데서 모아 관리를
 * 해준다.
 * 
 * @author leegt80
 * 
 */
@Aspect
public class SystemPointcut {

	@Pointcut("execution(* skt.tmall.common.model.process.db.*.process(..))")
	public void databaseService() {
	};
	
	@Pointcut("execution(* skt.tmall.common.model.process.ibatisDb.*.process(..))")
	public void ibatisDatabaseService() {
	};

	@Pointcut("execution(* skt.tmall.common.model.process.AbstractProcessGroup.process(..))")
	public void groupService() {
	};

	@Pointcut("execution(* skt.tmall.common.model.process.repository.AbstractRepositoryProcess.process(..))")
	public void repositoryService() {
	};

	@Pointcut("execution(* skt.tmall.*.model.web.control.*.*(..))")
	public void webRequestExtract() {
	};
	
	@Pointcut("execution(* skt.tmall.common.model.process.db.*.process(..))")
	public void databaseException() {
	};
	
	@Pointcut("execution(* skt.tmall.common.model.process.ibatisDb.*.process(..))")
	public void ibatisDatabaseException() {
	};
	
	@Pointcut("execution(* skt.tmall.*.model.web.control.*.*(..))")
	public void webRequestExtractException() {
	};

}
