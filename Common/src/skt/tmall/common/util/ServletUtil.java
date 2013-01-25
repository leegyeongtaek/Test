package skt.tmall.common.util;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 일반적인 Util이라 다른 사람이 맹근거 쓴다.
 * 
 * @author iolo
 */
public class ServletUtil {

	//
	// common headers
	//

	public static final String CONTENT_ENCODING = "Content-Encoding";

	public static final String ACCEPT_ENCODING = "Accept-Encoding";

	public static final String LAST_MODIFIED = "Last-Modified";

	public static final String USER_AGENT = "User-Agent";

	public static final String ACCEPT_LANGUAGE = "Accept-Language";

	public static final String GZIP = "gzip";

	//
	// platform
	//

	public static final String WINDOWS = "windows";

	public static final String LINUX = "linux";

	public static final String MAC_OS = "mac os";

	public static final String SUN_OS = "sunos";

	public static final String[][] NO_CACHE_HEADERS = {
			{ "Pragma", "no-cache" }, { "Expires", "0" },
			{ "Cache-Control", "no-store,no-cache,must-revalidate" },
			{ "Cache-Control", "post-check=0,pre-check=0" } };

	public static final String[][] P3P_HEADERS = { {
			"P3P",
			"CP=\"NOI CURa ADMa DEVa TAIa OUR DELa BUS IND PHY ONL UNI COM NAV INT DEM PRE\"" } };

	/**
	 * Get the context root URL for the specified request.
	 * 
	 * {@literal scheme://serverName:serverPort/contextPath}
	 * 
	 * @param request
	 * @return context root url
	 */
	public static final String getContextURL(final HttpServletRequest request) {
		return new StringBuffer().append(request.getScheme()).append("://")
				.append(request.getServerName()).append(":").append(
						request.getServerPort()).append(
						request.getContextPath()).toString();
	}

	/**
	 * 
	 * @param response
	 * @param headers
	 */
	public static final void addHeaders(final HttpServletResponse response,
			String[][] headers) {
		for (String[] header : headers) {
			response.addHeader(header[0], header[1]);
		}
	}

	/**
	 * @see http://en.wikipedia.org/wiki/User_agent
	 *      http://www.pgts.com.au/pgtsj/pgtsj0208i.html
	 * 
	 * @author cshan
	 * @param request
	 * @return
	 */
	public static String getPlatform(final HttpServletRequest request) {
		String userAgent = request.getHeader(USER_AGENT);
		userAgent = userAgent.toLowerCase();
		if (isMatchPlatform(userAgent, WINDOWS)) {
			return WINDOWS;
		} else if (isMatchPlatform(userAgent, LINUX)) {
			return LINUX;
		} else if (isMatchPlatform(userAgent, MAC_OS)) {
			return MAC_OS;
		} else if (isMatchPlatform(userAgent, SUN_OS)) {
			return SUN_OS;
		} else {
			return null;
		}
	}

	/**
	 * Accept-Language request-header field spec
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
	 * 
	 * @author cshan
	 * 
	 * @param request
	 * @return
	 */
	public static Locale getLocale(final HttpServletRequest request) {
		String header = request.getHeader(ACCEPT_LANGUAGE);
		return getLocale(header);
	}

	/**
	 * Accept-Language request-header field spec
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
	 * 
	 * @author cshan
	 * 
	 * @param header
	 *            HttpServletRequest.getHeader("Accept-Language");
	 * @return
	 */
	public static Locale getLocale(String header) {
		StringTokenizer st = new StringTokenizer(header, ",");
		ArrayList<String> locales = new ArrayList<String>(st.countTokens());
		ArrayList<Float> qualities = new ArrayList<Float>(st.countTokens());
		while (st.hasMoreTokens()) {
			String tokenValue = st.nextToken().trim();
			int index = tokenValue.indexOf(";");
			if (index > -1) {
				String quality = tokenValue.substring(index);
				String tmpLocale = tokenValue.substring(0, index);
				try {
					Float valueOf = Float.valueOf(quality.substring(index + 1));
					qualities.add(valueOf);
					locales.add(tmpLocale.trim());
				} catch (NumberFormatException ne) {
					qualities.add(Float.valueOf(-1f));
					locales.add(tmpLocale.trim());
				}
			} else {
				qualities.add(Float.valueOf(-1f));
				locales.add(tokenValue.trim());
			}
		}
		/*
		 * find locale having lower quality value.
		 */
		float min = Float.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < qualities.size(); i++) {
			Float float1 = qualities.get(i);
			if (float1.floatValue() < min) {
				min = float1.floatValue();
				index = i;
			}
		}
		String locale = locales.get(index);
		/*
		 * is exist language-range
		 */
		int indexOf = locale.indexOf('-');

		if (indexOf == -1) {
			return new Locale(locale);
		} else {
			return new Locale(locale.substring(0, indexOf), locale
					.substring(indexOf + 1));
		}
	}

	public static boolean isMatchPlatform(final String userAgent,
			String platform) {
		return StringUtils.contains(userAgent, platform);
	}

}
