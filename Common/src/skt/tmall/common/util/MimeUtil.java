package skt.tmall.common.util;

import java.io.IOException;
import java.util.Properties;

/**
 * MIME helper methods.
 * 
 * <li>RFC-822 Standard for ARPA Internet text messages</li>
 * <li>RFC-2045 MIME Part 1: Format of Internet Message Bodies</li>
 * <li>RFC-2046 MIME Part 2: Media Types</li>
 * <li>RFC-2047 MIME Part 3: Header Extensions for Non-ASCII Text</li>
 * <li>RFC-2048 MIME Part 4: Registration Procedures</li>
 * <li>RFC-2049 MIME Part 5: Conformance Criteria and Examples</li>
 * 
 * @author iolo
 */
public class MimeUtil {

	public static final String DEF_MIME_TYPE = "application/octet-stream";

	public static final String TEXT = "text/plain";

	public static final String HTML = "text/html";

	public static final String DOC = "application/msword";

	public static final String DOCX = "application/vnd.ms-word.document.12";

	public static final String RTF = "text/rtf";

	public static final String PPT = "application/vnd.ms-powerpoint";

	public static final String PPTX = "application/vnd.ms-powerpoint.presentation.12";

	public static final String XLS = "application/vnd.ms-excel";

	public static final String XLSX = "application/vnd.ms-excel.12";

	public static final String XML = "text/xml";

	public static final String XHTML = "application/xhtml+xml";

	public static final String PNG = "image/png";

	public static final String GIF = "image/gif";

	public static final String JPG = "image/jpeg";

	public static final String CSS = "text/css";

	// application/x-javascript
	public static final String JAVASCRIPT = "text/javascript";

	// text/json(for dojo)
	// application/json(for prototype)
	public static final String JSON = "text/json";

	// for wiki
	public static final String WIKI = "application/x-docs-wiki";

	// for outliner
	public static final String OPML = "application/opml+xml";

	private static final String MIME_PROPERTIES = "MimeTypes.properties";

	private static Properties mimeTypes = null;

	public static Properties getMimeTypes() {
		if (mimeTypes == null) {
			synchronized (MimeUtil.class) {
				if (mimeTypes == null) {
					mimeTypes = new Properties();

					try {
						mimeTypes.load(MimeUtil.class
								.getResourceAsStream(MIME_PROPERTIES));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return mimeTypes;
	}

	/**
	 * Get MIME type for the given filename.
	 * 
	 * @param filename
	 *            the filename to get mime type
	 * @return a MIME type string
	 */
	public static String getMimeType(String filename) {
		if (filename == null) {
			return DEF_MIME_TYPE;
		}

		final int index = filename.lastIndexOf('.');
		final String ext;

		if (index > 0) {
			ext = filename.substring(index + 1).toLowerCase();
		} else {
			ext = filename;
		}

		return getMimeTypes().getProperty(ext, DEF_MIME_TYPE);
	}

	public static final String getMimeTypeFromExt(String ext) {
		return getMimeTypes().getProperty(ext, DEF_MIME_TYPE);
	}

}
