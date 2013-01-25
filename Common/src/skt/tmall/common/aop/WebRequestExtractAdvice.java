package skt.tmall.common.aop;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import skt.tmall.common.annotation.DelegateSupport;
import skt.tmall.common.annotation.MandatoryParameter;
import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.aop.WebRequestExtractAdvice;
import skt.tmall.common.exception.NeedParameterException;
import skt.tmall.common.model.security.ReAuthentication;

/**
 * DelegateSupport annotation 과 ManadatoryParameter annotation을 이용해 파라메터 파싱을 한다.
 * 
 * @author leegt80
 */
@Aspect
public class WebRequestExtractAdvice {

	private Log log = LogFactory.getLog(WebRequestExtractAdvice.class);
	
	private ReAuthentication reAuthentication = null;

	/**
	 * 필수 파라미터 (반드시 필요한 파라미터를 강제로 체크하고 싶을 때 사용한다.) 
	 * @param delegate
	 * @param madatory 
	 * @param request
	 * @param response
	 * @param parameters
	 * @throws Throwable
	 */
	@Before("skt.tmall.common.aop.SystemPointcut.webRequestExtract() and @annotation(delegate) and @annotation(madatory) and args(request, response, parameters)")
	public void beforeWebControl(DelegateSupport delegate,
			MandatoryParameter madatory, HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> parameters)
			throws Throwable {

		if (log.isDebugEnabled()) {
			log
					.debug("@Before(\"skt.tmall.common.aop.SystemPointcut.webRequestExtract()"
							+ " and @annotation(delegate) and @annotation(madatory) and args(request, response, parameters)\")");
		}
		
		/*
		 * set the sql Dynamic params...
		 * put dynaic params into the HashMap.
		 */
		checkDynamic(request, parameters);

		/*
		 * set the sql wrap params...
		 * put wrapping info datas into the HashMap.
		 */
		checkWrapping(request, parameters);

		if (delegate != null) {
			if (madatory != null) {
				String[] mandatoryParameters = madatory.parameters();
				for (String m : mandatoryParameters) {
					String p = request.getParameter(m);
					if (StringUtils.isEmpty(p)) {
						throw new NeedParameterException("[" + m
								+ "] is madatory parameter");
					}
				}
			}
			
			String[] ps = delegate.webParameters();
			
			for (String p : ps) {
				String[] parameterValues = request.getParameterValues(p);
				if (parameterValues != null) {
					parameters.put(p,
							parameterValues.length > 1 ? parameterValues
									: parameterValues[0]);
				}
			}

			/*
			 * process finding 
			 * put a process into the HashMap.
			 */
			findProcess(delegate, request, parameters);
			
			/*
			 * put a ReAuthentication...
			 */
			if (reAuthentication != null) {
				reAuthentication.checkReAuthenticated(request, parameters);
			}
			
			/**
			 * client information
			 */
			parameters.put(ICommonConstants.CLIENT_IP, request.getRemoteAddr());
			
			/**
			 * 임시처리임.. 나중에 로그인 관리, 권한관리, 세션관리등을 지원하면 빼고 따로 구현 해야함..
			 */
			/*HttpSession session = request.getSession(false);
			if(session != null){
				synchronized(session){
					parameters.put(ICommonConstants.LOGIN_PERSON, session.getAttribute("login_humoid"));
				}
			}*/
		}
	}

	@Before("skt.tmall.common.aop.SystemPointcut.webRequestExtract() and @annotation(delegate) and args(request, response, parameters)")
	public void beforeWebControl(DelegateSupport delegate,
			HttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> parameters) throws Throwable {

		if (log.isDebugEnabled()) {
			log
					.debug("@Before(\"skt.tmall.common.aop.SystemPointcut.webRequestExtract()"
							+ " and @annotation(delegate) and args(request, response, parameters)\")");
		}
		
		/*
		 * set the sql Dynamic params...
		 * put dynaic params into the HashMap.
		 */
		checkDynamic(request, parameters);

		/*
		 * set the sql wrap params...
		 * put wrapping info datas into the HashMap.
		 */
		checkWrapping(request, parameters);

		if (delegate != null) {
			String[] ps = delegate.webParameters();
			
			// setting webParameters
			if (ps.length > 0) {
				for (String p : ps) {
					String[] parameterValues = request.getParameterValues(p);
					if (parameterValues != null) {
						parameters.put(p,
								parameterValues.length > 1 ? parameterValues
										: parameterValues[0]);
					}
				}
			// setting none webParameters  
			} else {
				@SuppressWarnings("unchecked")
				Enumeration<String> iter = request.getParameterNames();
				while (iter.hasMoreElements()) {
					String key = iter.nextElement();
					String[] parameterValues = request.getParameterValues(key);
					if (parameterValues != null) {
						parameters.put(key,
								parameterValues.length > 1 ? parameterValues
										: parameterValues[0]);
					}
				}
			}

			/*
			 * process finding 
			 * put a process into the HashMap.
			 */
			findProcess(delegate, request, parameters);
			
			/*
			 * put a ReAuthentication...
			 */
			if (reAuthentication != null) {
				reAuthentication.checkReAuthenticated(request, parameters);
			}
			
			/**
			 * client information
			 */
			parameters.put(ICommonConstants.CLIENT_IP, request.getRemoteAddr());

			/**
			 * 임시처리임.. 나중에 로그인 관리, 권한관리, 세션관리등을 지원하면 빼고 따로 구현 해야함..
			 */
			/*HttpSession session = request.getSession(false);
			if(session != null){
				synchronized(session){
					parameters.put(ICommonConstants.LOGIN_PERSON, session.getAttribute("login_humoid"));
				}
			}*/
		}
	}

	/**
	 * This method fetch process
	 * @param delegate
	 * @param request
	 * @param parameters
	 */
	private void findProcess(DelegateSupport delegate,
			HttpServletRequest request, HashMap<String, Object> parameters) {
		String processName = delegate.processName();
		
		if (processName != null && !processName.equals("")) {
			String processReferenceKey = delegate.processReferenceKey();
			ServletContext sc = request.getSession().getServletContext();
			ApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(sc);
			parameters.put(processReferenceKey, wac.getBean(processName));
			parameters.put(ICommonConstants.HTTP_SERVLET_REQUEST, request);
		}
	}

	/**
	 * auto paging method 
	 * @param request
	 * @param parameters
	 */
	private void checkWrapping(HttpServletRequest request,
			HashMap<String, Object> parameters) {
		String fetchSize 	= request.getParameter(ICommonConstants.FETCH_SIZE);
		String from 		= request.getParameter(ICommonConstants.PAGE_FROM);
		String to   		= request.getParameter(ICommonConstants.PAGE_TO);
		
		if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to)
				|| StringUtils.isEmpty(fetchSize)) {
			return;
		}

		parameters.put(ICommonConstants.FETCH_SIZE, fetchSize);
		parameters.put(ICommonConstants.PAGE_FROM, from);
		parameters.put(ICommonConstants.PAGE_TO, to);
		parameters.put(ICommonConstants.IS_WRAPPING, Boolean.TRUE);
	}
	
	/**
	 * setting dynamic parameter
	 * @param request
	 * @param parameters
	 */
	private void checkDynamic(HttpServletRequest request,
			HashMap<String, Object> parameters) {
		// DynamicFormItem data...
		String[] dynamicParams = request.getParameterValues(ICommonConstants.DYNAMIC_PARAM);
		
		if (dynamicParams == null) {
			return;
		}
		
		parameters.put(ICommonConstants.DYNAMIC_PARAM, dynamicParams);
		parameters.put(ICommonConstants.IS_DYNAMIC, Boolean.TRUE);
	}
	
	/**
	 * Authentication setter
	 * @param reAuthentication
	 */
	public void setReAuthentication(ReAuthentication reAuthentication) {
		this.reAuthentication = reAuthentication;
	}
}
