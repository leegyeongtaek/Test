package skt.tmall.common.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import skt.tmall.common.util.JSMin;
import skt.tmall.common.util.MimeUtil;

/**
 * 자바스크립트 압축기 구현체.
 * 
 * @author $Id: JsProcessor.java,v 1.1 2007/04/06 11:02:04 dsjang Exp $: DefaultJsHandler.java,v 1.3 2007/02/24 09:57:17 dsjang Exp $
 * @see JSMin
 */
public class JsProcessor extends AbstractMultiResourceProcessor {

	private static final Log LOG = LogFactory.getLog(JsProcessor.class);

	private static final String RESOURCE_SEPARATOR = "/*========== {0} ==========*/\n";

	/**
	 * 기본으로 minimize를 하지 않는다.
	 */
	private static final boolean DEF_MINIMIZE = false;

	/**
	 * 기본으로 obfuscate를 하지 않는다.
	 */
	private static final boolean DEF_OBFUSCATE = false;

	/**
	 * minimize 여부.
	 */
	private boolean minimize = DEF_MINIMIZE;

	/**
	 * obfuscate 여부.
	 */
	private boolean obfuscate = DEF_OBFUSCATE;

	public JsProcessor() {
	}

	public JsProcessor(Resource resource) {
		setResource(resource);
	}

	//
	// @Inject
	//

	public void setMinimize(boolean minimize) {
		this.minimize = minimize;
	}

	public void setObfuscate(boolean obfuscate) {
		this.obfuscate = obfuscate;
	}

	//
	// @Implements(AbstractResourceProcessor)
	//

	@Override
	protected void processRequest(HttpServletRequest request) {
		super.processRequest(request);
		String minimizeParam = request.getParameter("minimize");
		if (minimizeParam != null) {
			setMinimize(Boolean.parseBoolean(minimizeParam));
		}
		String obfuscateParam = request.getParameter("obfuscate");
		if (obfuscateParam != null) {
			setObfuscate(Boolean.parseBoolean(obfuscateParam));
		}
	}

	@Override
	protected String getContentType() {
		return MimeUtil.JAVASCRIPT;
	}

	//
	// @Implements(AbstractMultiResourceProcessor)
	//

	@Override
	protected String getResourceHeader(Resource resource) {
		return MessageFormat.format(RESOURCE_SEPARATOR, new Object[] { resource
				.getDescription() });
	}

	@Override	//javascript를 병합시킴.
	protected boolean processMerge(InputStream is, OutputStream os)
			throws IOException, ServletException {
		if (!obfuscate && !minimize) {
			return false;
		}

		if (obfuscate) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("obfuscate...");
			}
			// TODO: obfuscate
		}

		if (minimize) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("minimize javascript...");
			}
			try {
				new JSMin(is, os).jsmin();
			} catch (Exception ex) {
				throw new ServletException("failed to minimize javascript.", ex);
			}
		}

		return true;
	}

}
