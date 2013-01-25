package skt.tmall.common.model.security;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import skt.tmall.common.model.process.ProcessException;

/**
 * @author leegt80 인증 및 권한 정보를 가져오는 interface
 */
public interface ReAuthentication {

	public void checkReAuthenticated(HttpServletRequest request, HashMap<String, Object> parameters)
			throws ProcessException;

}
