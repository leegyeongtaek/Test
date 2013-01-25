package skt.tmall.common.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * 한 개이상의 리소스들을 병합하는 처리기.
 * 
 * {@link #setResources(List)} 또는 {@link #setResource(Resource)}을 사용하여 merge할
 * 리소스들을 지정해야한다.
 * 
 * @author $Id: AbstractMultiResourceProcessor.java,v 1.1 2007/04/06 11:02:04
 *         dsjang Exp $
 */
public abstract class AbstractMultiResourceProcessor extends
		AbstractResourceProcessor {

	private static final Log LOG = LogFactory
			.getLog(AbstractMultiResourceProcessor.class);

	/**
	 * 처리할 리소스(자바 스크립트) 목록
	 */
	private List<Resource> resources;

	/** CTOR */
	public AbstractMultiResourceProcessor() {
	}

	//
	// @Inject
	//

	/**
	 * 
	 * @param resources
	 * @see #setResource(Resource)
	 * Resource는 클래스 패스에 존재하는 자원...
	 */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * 
	 * @param resource
	 * @see #setResources(List)
	 */
	public void setResource(Resource resource) {
		this.resources = new ArrayList<Resource>(1);
		this.resources.add(resource);
	}

	//
	// @Implements(IResourceProcessor)
	//

	@Override
	protected int getContentLength() throws IOException {
		int contentLength = 0;
		for (Resource resource : resources) {
			contentLength += resource.getFile().length();
		}
		return contentLength;
	}

	@Override
	protected long getLastModified() throws IOException {
		long result = 0;
		for (Resource resource : resources) {
			long lastModified = resource.getFile().lastModified();	//.js 파일의 변경 시간 출력
			if (result < lastModified) {
				result = lastModified;
			}
		}
		return result;
	}

	@Override	//출력 스트림에 각각의 리소스 파일을 zip으로 넣어줌.
	protected void processInternal(OutputStream out) throws IOException,
			ServletException {
		// merge all resources
		for (Resource resource : resources) {
			if (!LOG.isDebugEnabled()) {
				out.write(getResourceHeader(resource).getBytes(
						getResponseEncoding()));
			}

			if (LOG.isTraceEnabled()) {
				LOG.trace("merge: resource=" + resource.getDescription());
			}

			InputStream in = null;
			try {
				in = resource.getInputStream();
				if (!processMerge(in, out)) {	//병합시에는 실행되지 않고, 그냥일때는 IOUtils.copy를 수행함.
					IOUtils.copy(in, out);
				}
				out.flush();
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
	}

	//
	//
	//

	/**
	 * 비압축/디버깅 모드에서 각 리소스의 처음에 출력할 문자열(일종의 코멘트).
	 * 
	 * @param resource
	 * @return 구분 문자열
	 */
	protected abstract String getResourceHeader(Resource resource);

	/**
	 * 처리기 유형에 따른 "병합" 작업을 수행.
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	protected abstract boolean processMerge(InputStream in, OutputStream out)
			throws IOException, ServletException;

}
