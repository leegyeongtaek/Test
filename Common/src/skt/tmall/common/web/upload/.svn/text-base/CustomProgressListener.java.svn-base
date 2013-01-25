package skt.tmall.common.web.upload;

import org.apache.commons.fileupload.ProgressListener;

/**
 * @see CustomMultipartResolver
 * @author cshan
 * 
 */
public class CustomProgressListener implements ProgressListener {

	private String token;
	private long bytesRead;
	private long contentLength;

	public CustomProgressListener(String token) {
		this.token = token;
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public String getToken() {
		return token;
	}

	public void update(long bytesRead, long contentLength, int items) {
		this.bytesRead = bytesRead;
		this.contentLength = contentLength;
	}
}
