package skt.tmall.common.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import skt.tmall.common.util.MimeUtil;

/**
 * 단일 리소스 처리기.
 * 
 * TODO: remove spring dependency.(replace resource into XFile?)
 * 
 * @author $Id: AbstractSingleResourceProcessor.java,v 1.1 2007/04/06 11:02:05 dsjang Exp $
 */
public abstract class AbstractSingleResourceProcessor extends
		AbstractResourceProcessor {

	/** 출력할 리소스 */
	private Resource resource;

	/** CTOR. */
	public AbstractSingleResourceProcessor() {
	}

	/** CTOR. */
	public AbstractSingleResourceProcessor(Resource resource) {
		this.resource = resource;
	}

	//
	// @Inject
	//

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	//
	// getters
	//

	protected Resource getResource() {
		return resource;
	}

	//
	// @Implements(AbstractResourceProcessor)
	//

	@Override
	protected int getContentLength() throws IOException {
		return (int) resource.getFile().length();
	}

	@Override
	protected String getContentType() {
		return MimeUtil.getMimeType(resource.getFilename());
	}

	@Override
	protected long getLastModified() throws IOException {
		return resource.getFile().lastModified();
	}

	@Override
	protected void processInternal(OutputStream out) throws IOException,
			ServletException {
		InputStream in = null;
		try {
			in = resource.getInputStream();
			IOUtils.copy(in, out);
			out.flush();
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

}
