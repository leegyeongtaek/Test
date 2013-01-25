package skt.tmall.common.web.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import skt.tmall.common.util.ServletUtil;


/**
 * 캐싱와 gzip압축을 지원하는 처리기 구현체.
 * 
 * 대부분의 {@link IResourceProcessor} 구현체는 이 클래스를 상속받고 필요한 추상 메소드만 구현하면 된다.
 * 
 * @author $Id: AbstractResourceProcessor.java,v 1.1 2007/04/06 11:02:05 dsjang Exp $
 * @see ResourceController
 */
public abstract class AbstractResourceProcessor implements IResourceProcessor {

	private static final Log LOG = LogFactory
			.getLog(AbstractResourceProcessor.class);

	/**
	 * 기본으로 gzip 압축을 하지 않는다.
	 */
	private static final boolean DEF_GZIP = false;

	/**
	 * 기본으로 모든 응답은 UTF-8 인코딩을 사용한다.
	 */
	private static final String DEF_RESPONSE_ENCODING = "UTF-8";

	//
	//
	//

	/**
	 * gzip 압축 여부.
	 */
	private boolean gzip = DEF_GZIP;

	/**
	 * 응답 인코딩.
	 */
	private String responseEncoding = DEF_RESPONSE_ENCODING;

	/** CTOR */
	public AbstractResourceProcessor() {
	}

	//
	// @Inject
	//

	public void setGzip(boolean gzip) {
		this.gzip = gzip;
	}

	public void setResponseEncoding(String responseEncoding) {
		this.responseEncoding = responseEncoding;
	}

	//
	// getters
	//

	protected boolean isGzip() {
		return gzip;
	}

	protected String getResponseEncoding() {
		return responseEncoding;
	}

	//
	// @Implements(IResourceProcessor)
	//

	/**
	 * 지정한 요청(request)를 참조하여 응답(response)에 minimize/gzip/merge한 자바스크립트를 출력한다.
	 * 
	 * NOTE: (개발의 편의를 위해) {@literal minimize}와 {@link gzip} 요청 파라메터를 사용해서
	 * {@link #minimize} 와 {@link #gzip} 속성을 직접 지정할 수 있다.
	 * 
	 * @param request
	 *            서블릿 응답
	 * @param response
	 *            서블릿 요청
	 * @param cacheFile
	 *            캐시 파일. {@literal null}이면 캐시 파일을 사용하지 않고, 응답에 직접 출력한다.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	public final void process(HttpServletRequest request,
			HttpServletResponse response, File cacheFile) throws IOException,
			ServletException {
		// emit the cache file if available and valid
		if (validCacheFile(cacheFile)) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("valid cache file: " + cacheFile);
			}
			emitCacheFile(cacheFile, response);
			return;
		}

		processRequest(request);

		OutputStream out = null;
		try {
			// output to cache file or servlet response
			out = (cacheFile != null) ? openOutputStream(cacheFile)
					: openOutputStream(response);

			// gzip
			if (gzip
					&& StringUtils.contains(request
							.getHeader(ServletUtil.ACCEPT_ENCODING),
							ServletUtil.GZIP)) {
				// gzip compression
				out = new GZIPOutputStream(out);
				if (LOG.isTraceEnabled()) {
					LOG.trace("gzip compression: cacheFile=" + cacheFile);
				}
			}

			// 자식 클래스 구현체는 출력이 어디로 가는지 모름
			// 1.캐시 2.서블릿응답 3.캐시+gzip 4.서블릿응답+gzip
			processInternal(out);
		} finally {
			IOUtils.closeQuietly(out);
		}

		// emit the generated cache file if available
		if (cacheFile != null) {
			emitCacheFile(cacheFile, response);
		}
	}

	//
	//
	//

	/**
	 * 캐시 파일의 유효성을 검증.
	 * 
	 * @param cacheFile
	 * @return
	 * @throws IOException
	 */
	private boolean validCacheFile(File cacheFile) throws IOException {
		if (cacheFile != null && cacheFile.exists()) {
			long lastModified = getLastModified();
			if (lastModified == 0) {
				return true; // use cache
			}
			return FileUtils.isFileNewer(cacheFile, lastModified);
		}
		// no cacheFile
		return false;
	}

	/**
	 * 캐시 파일을 지정한 서블릿 응답으로 직접 출력.
	 * 
	 * @param cacheFile
	 * @param response
	 * @throws IOException
	 */
	private synchronized void emitCacheFile(File cacheFile,
			HttpServletResponse response) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(cacheFile);
			os = response.getOutputStream();
			if (gzip) {
				response.setHeader(ServletUtil.CONTENT_ENCODING,
						ServletUtil.GZIP);
			}
			response.setContentLength((int) cacheFile.length());
			response.setCharacterEncoding(responseEncoding);
			response.setContentType(getContentType());
			response.setDateHeader(ServletUtil.LAST_MODIFIED, cacheFile
					.lastModified());
			if (LOG.isTraceEnabled()) {
				LOG.trace("emit cacheFile: " + cacheFile);
			}
			IOUtils.copy(is, os);
			os.flush();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 캐시 파일을 생성하기 위한 출력 스트림 열기.
	 * 
	 * @param cacheFile
	 * @throws IOException
	 */
	private OutputStream openOutputStream(File cacheFile) throws IOException {
		if (!cacheFile.exists()) {
			cacheFile.getParentFile().mkdirs();
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("emit to cache file: " + cacheFile);
		}
		return new FileOutputStream(cacheFile);
	}

	/**
	 * 서블릿 응답으로 직접 출력하기 위한 출력 스트림 열기.
	 * 
	 * @param response
	 * @throws IOException
	 */
	private OutputStream openOutputStream(HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding(responseEncoding);
		response.setContentType(getContentType());
		int contentLength = getContentLength();
		if (contentLength == 0) {
			response.setContentLength(contentLength);
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("emit to servlet response...");
		}
		return response.getOutputStream();
	}

	//
	//
	//

	/**
	 * 결과의 컨텐츠 타입.
	 * 
	 * @return mime type string
	 * @throws IOException
	 */
	protected abstract String getContentType() throws IOException;

	/**
	 * 결과 컨텐츠의 길이.
	 * 
	 * @return length in bytes. 길이를 알 수 없을 경우에는 0.
	 * @throws IOException
	 */
	protected abstract int getContentLength() throws IOException;

	/**
	 * 결과 컨텐츠의 마지막 수정 시각.
	 * 
	 * @return timestamp in millis. 마지막 수정 시각을 알 수 없을 경우에는 0.
	 * @throws IOException
	 */
	protected abstract long getLastModified() throws IOException;

	/**
	 * 서블릿 요청에 대한 추가 처리.
	 * 
	 * @param request
	 */
	protected void processRequest(HttpServletRequest request) {
		String gzipParam = request.getParameter("gzip");
		if (gzipParam != null) {
			setGzip(Boolean.parseBoolean(gzipParam));
		}
		String responseEncodingParam = request.getParameter("responseEncoding");
		if (responseEncodingParam != null) {
			setResponseEncoding(responseEncodingParam);
		}
	}

	/**
	 * 처리기 유형에 따른 추가 작업을 수행.
	 * 
	 * @param out
	 * @throws IOException
	 * @throws ServletException
	 */
	protected abstract void processInternal(OutputStream out)
			throws IOException, ServletException;

}
