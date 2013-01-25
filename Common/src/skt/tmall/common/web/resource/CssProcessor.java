package skt.tmall.common.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import skt.tmall.common.util.MimeUtil;

/**
 * CSS 압축기 구현체.
 * 
 * @author $Id: CssProcessor.java,v 1.1 2007/04/06 11:02:04 dsjang Exp $
 * @see CSSMin
 */
public class CssProcessor extends AbstractMultiResourceProcessor {

	private static final Log LOG = LogFactory.getLog(CssProcessor.class);

	private static final Pattern COMMENTS_PATTERN = Pattern
			.compile("/\\*[^*]*\\*+([^/][^*]*\\*+)*/");

	private static final Pattern SPACES_PATTERN = Pattern
			.compile("(\r\n)|(\r)|(\n)|(\t)|(  +)");

	private static final String RESOURCE_HEADER = "/*========== {0} ==========*/\n";

	/**
	 * 기본으로 minimize를 하지 않는다.
	 */
	private static final boolean DEF_MINIMIZE = false;

	/**
	 * minimize 여부.
	 */
	private boolean minimize = DEF_MINIMIZE;

	/** CTOR. */
	public CssProcessor() {
	}

	/** CTOR. */
	public CssProcessor(Resource resource) {
		setResource(resource);
	}

	//
	// @Inject
	//

	public void setMinimize(boolean minimize) {
		this.minimize = minimize;
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
	}

	@Override
	protected String getContentType() {
		return MimeUtil.CSS;
	}

	//
	// @Implements(AbstractMultiResourceProcessor)
	//

	@Override
	protected String getResourceHeader(Resource resource) {
		return MessageFormat.format(RESOURCE_HEADER, new Object[] { resource
				.getDescription() });
	}

	@Override
	protected boolean processMerge(InputStream is, OutputStream os)
			throws IOException, ServletException {
		if (!minimize) {
			return false;
		}

		if (minimize) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("minimize css...");
			}
			String content = IOUtils.toString(is);
			content = COMMENTS_PATTERN.matcher(content).replaceAll(
					StringUtils.EMPTY);
			content = SPACES_PATTERN.matcher(content).replaceAll(
					StringUtils.EMPTY);
			IOUtils.write(content.trim(), os, getResponseEncoding());
		}

		return true;
	}

}
