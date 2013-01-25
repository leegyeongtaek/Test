package skt.tmall.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.AuthenticationEntryPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
* Used by the <code>SecurityEnforcementFilter</code> to commence authentication via the {@link
* BasicProcessingFilter}.<P>Once a user agent is authenticated using BASIC authentication, logout requires that
* the browser be closed or an unauthorized (401) header be sent. The simplest way of achieving the latter is to call
* the {@link #commence(ServletRequest, ServletResponse, AuthenticationException)} method below. This will indicate to
* the browser its credentials are no longer authorized, causing it to prompt the user to login again.</p>
*
* @author Ben Alex
* @version $Id: BasicProcessingFilterEntryPoint.java 1948 2007-08-25 00:15:30Z benalex $
*/
public class CertProcesssingFilterEntryPoint implements AuthenticationEntryPoint, InitializingBean {
   //~ Instance fields ================================================================================================

   private String realmName;

   //~ Methods ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.hasText(realmName, "realmName must be specified");
   }

   public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException)
       throws IOException, ServletException {
       HttpServletResponse httpResponse = (HttpServletResponse) response;
       httpResponse.setCharacterEncoding("UTF-8");
       httpResponse.getWriter().print("("+authException.getMessage()+")");
       httpResponse.flushBuffer();
   }

   public String getRealmName() {
       return realmName;
   }

   public void setRealmName(String realmName) {
       this.realmName = realmName;
   }

}

