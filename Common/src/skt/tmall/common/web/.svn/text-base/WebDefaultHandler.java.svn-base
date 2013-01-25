package skt.tmall.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 * 어디다 써볼까? redirect를 지원하고 있네...
 * 
 * @author leegt80
 * 
 */
public class WebDefaultHandler extends MultiActionController {

	private static Log log = LogFactory.getLog(WebDefaultHandler.class);

	private Map<String, String> redirectUrlMap;

	private String suffix = "";

	/*
	 * methodNameResolver에 의해 다음  메소드 실행
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Multiactioncontroller를 상속받은 Delegate에 메소드를 호출
		ModelAndView mav = super.handleRequestInternal(request, response);	
		if (mav != null && mav.getViewName() != null) {
			if (log.isTraceEnabled()) {
				log.trace("##" + request.getPathInfo() + " use setView()!!");
			}
		}
		
		//redirectUrlMap에 다음과 같이 setting하면 return Url을 변경할 수 있다.
		//(key:기존 url method name, value:redirectUrl)
		String handlerMethodName = getMethodNameResolver()
				.getHandlerMethodName(request);
		if (mav != null && redirectUrlMap != null
				&& redirectUrlMap.containsKey(handlerMethodName)) {
			String mappedUrl = redirectUrlMap.get(handlerMethodName);
//			mav.setView(new RedirectView(mappedUrl + suffix));
			mav.setView(new InternalResourceView(mappedUrl + suffix));
		}else if (mav == null && redirectUrlMap != null
				&& redirectUrlMap.containsKey(handlerMethodName)) {
			String mappedUrl = redirectUrlMap.get(handlerMethodName);
			return new ModelAndView(new InternalResourceView(mappedUrl + suffix));
		}
		
		return mav;
		
	}
	
	@Required
	public void setUrlSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	/*
	 * redirectUrlMap에 해당 delegate 메소드(해당 url)호출에 따른 redirect url을 넣어 두고 나중에 
	 * 어떤 url이 호출되면 그것의 method가 key값이 되고 value값이 redirect될 url이 된다.
	 */
	public void setRedirectUrlMap(Map<String, String> redirectUrlMap) {
		this.redirectUrlMap = redirectUrlMap;
	}
}
