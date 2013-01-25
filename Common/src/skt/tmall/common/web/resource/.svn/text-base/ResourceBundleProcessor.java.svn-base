package skt.tmall.common.web.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import skt.tmall.common.util.MimeUtil;
import skt.tmall.common.util.StrUtil;

/**
 * {@link ResourceBundle}을 JSON(또는 자바스크립트) 형식으로 출력하기 위한 프로세서.
 * 
 * TODO: utilize cache!
 * 
 * @author $Id: ResourceBundleProcessor.java,v 1.1 2007/04/06 11:02:05 dsjang Exp $
 * @see ResourceBundle
 */
public class ResourceBundleProcessor extends AbstractResourceProcessor {

	private String baseName;

	private String var;

	private Locale locale;

	/** CTOR. */
	public ResourceBundleProcessor() {
	}

	//
	// @Inject
	//

	@Required
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setLocale(String locale) {
		this.locale = new Locale(locale);
	}

	//
	// @Implements(AbstractResourceProcessor)
	//

	/**
	 * overrides handler settings
	 */
	protected void processRequest(HttpServletRequest request) {
		super.processRequest(request);
		if (locale == null) {
			locale = request.getLocale();
		}
		String varParam = request.getParameter("var");
		if (varParam != null) {
			setVar(varParam);
		}
		String localeParam = request.getParameter("locale");
		if (localeParam != null) {
			setLocale(varParam);
		}
	}

	@Override
	protected int getContentLength() throws IOException {
		return 0; // unknown
	}

	@Override
	protected String getContentType() throws IOException {
		return StringUtils.isEmpty(var) ? MimeUtil.JSON : MimeUtil.JAVASCRIPT;
	}

	@Override
	protected long getLastModified() throws IOException {
		return 0; // unknown
	}

	@Override
	protected void processInternal(OutputStream out) throws IOException,
			ServletException {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

		// javascript assignment statement?
		if (StringUtils.isNotEmpty(var)) {
			out.write(var.getBytes(getResponseEncoding()));
			out.write('=');
		}

		emitResourceBundle(out, bundle);

		if (StringUtils.isNotEmpty(var)) {
			out.write(';');
		}
		out.write('\n');

		out.flush();
	}

	//
	//
	//

	@SuppressWarnings("unchecked")
	private void emitResourceBundle(OutputStream out, ResourceBundle bundle)
			throws IOException {
		out.write('{');
		boolean isFirst = true;
		for (Enumeration e = bundle.getKeys(); e.hasMoreElements();) {
			String key = escapeJavaScript((String) e.nextElement());
			String value = escapeJavaScript(bundle.getString(key));
			if (isFirst) {
				isFirst = false;
			} else {
				out.write(',');
			}
			out.write('\"');
			out.write(key.getBytes(getResponseEncoding()));
			out.write('\"');
			out.write(':');
			out.write('\"');
			out.write(value.getBytes(getResponseEncoding()));
			out.write('\"');
		}
		out.write('}');
	}

	private String escapeJavaScript(String str) {
		// return str;
		// return StringEscapeUtils.escapeJavaScript(str);
		return StrUtil.addSlashes(str);
	}

}
