package skt.tmall.common.model.security;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.model.process.ProcessException;

/**
 * @author leegt80
 * (참조.. AbstractSecurityInterceptor- 296Line)
 * (이 메소드를 사용하기 위해서는 Cache를 사용하지 말아야 한다.)
 * Acegi 권한 체크시 권한 변경이 발생하면 다시 Authentication 객체를 가져오기 위해 isAuthenticated()가
 * false 이어야 한다. 
 * 즉, 1. 권한 변경후 이 문장 실행: SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
 *     2. 다음번 클라이언트 요청시 AbstractSecurityInterceptor Class에서 Authentication을 다시 ContextHolder에 담음.
 *     3. 그리고 checkReAuthenticated() 메소드를 호출하여 isAuthenticated가 true인 Authentication 객체를 ContextHolder에 담음.
 */
public abstract class AbstractReAuthentication implements ReAuthentication {
	
	private String rolePrefix = "ROLE_";
	
	protected HttpServletRequest request = null;
	
	public AbstractReAuthentication(){}
	
	/*
	 * 사용자 ID
	 */
	public abstract String getAuthentication() throws ProcessException;
	
	/*
	 * 사용자 권한
	 */
	public abstract String[] getAuthorities() throws ProcessException;

	public void checkReAuthenticated(HttpServletRequest request, HashMap<String, Object> parameters)
			throws ProcessException {
		this.request = request;
		
		String authentication 	= getAuthentication();
		String[]authorities		= getAuthorities();
		
		parameters.put(ICommonConstants.LOGIN_ID, authentication);
		
		parameters.put(ICommonConstants.LOGIN_AUTHORITY, authorities);
		
	}
	
	public String getRolePrefix() {
		return rolePrefix;
	}
	
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}
}
