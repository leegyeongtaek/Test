package skt.tmall.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * String related utilities.
 * 
 * 일반적인 Util이라 다른 사람이 맹근거 쓴다.
 * 
 * @author iolo
 */
public class StrUtil {
	/**
	 * The default system encoding is "8859_1" just a 8bit byte stream.
	 */
	public static final String DEF_SYS_ENCODING = "8859_1";

	/**
	 * The default application(user) encoding.
	 * 
	 * Use the encoding specified by "file.encoding" system property if
	 * available. otherwise use UTF-8.
	 */
	public static final String DEF_APP_ENCODING = System.getProperty(
			"file.encoding", "UTF-8");

	/**
	 * The system encoding.
	 * 
	 * By default, use "8859_1".
	 * 
	 * Set "com.iowise.commons.util.sysEncoding" system property to change
	 * this.(with -D java command line argument)
	 * 
	 * @see DEF_SYS_ENCODING
	 * @see #sys2app
	 * @see #app2sys
	 */
	public static String SYS_ENCODING = System.getProperty(
			"com.iowise.commons.util.sysEncoding", DEF_SYS_ENCODING);

	/**
	 * The application(user) encoding.
	 * 
	 * Set "com.iowise.commons.util.appEncoding" system property to change
	 * this.(with -D java command line argument)
	 * 
	 * @see #sys2app
	 * @see #app2sys
	 */
	public static String APP_ENCODING = System.getProperty(
			"com.iowise.commons.util.appEncoding", DEF_APP_ENCODING);

	public static final NumberFormat METRIC_FORMAT = new DecimalFormat("0.0");

	public static final double KILO_BASE = 1024;

	public static final double MEGA_BASE = KILO_BASE * KILO_BASE;

	public static final double GIGA_BASE = MEGA_BASE * KILO_BASE;

	public static final double TERA_BASE = GIGA_BASE * KILO_BASE;

	public static final String METRIC_UNIT = "B";

	public static final String KILO_UNIT = "K";

	public static final String MEGA_UNIT = "M";

	public static final String GIGA_UNIT = "G";

	public static final String TERA_UNIT = "T";

	public static final String HEX_DIGITS = "0123456789abcdefABCDEF";

	/** special characters should be escaped for (UNIX) shell */
	private static final String SPECIAL_SHELL_CHARS = "&;`'\"|*?~<>^()[]{}$\\\n";

	/** special characters should be escaped for DOS/Unix/Mac filesystem */
	private static final String SPECIAL_FILENAME_CHARS = "\\/:*?<>|\"&";

	/** reserved file names for win32 */
	private static final String[] WIN32_SPECIAL_FILENAMES = { "CON", "PRN",
			"AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6",
			"COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5",
			"LPT6", "LPT7", "LPT8", "LPT9", "CLOCK$" };

	private static final String WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX = "&;";

	//
	// encoding(charset) related methods
	//

	/**
	 * Convert string with application encoding to system encoding.
	 * 
	 * Returns <code>null</code> if inputs null. Returns the given string
	 * without convert if failed to convert.
	 * 
	 * @param str
	 *            the string to convert
	 * @return converted string
	 * @see SYS_ENCODING
	 * @see APP_ENCODING
	 */
	public static String app2sys(String str) {
		if (str == null) {
			return null;
		}

		try {
			return new String(str.getBytes(APP_ENCODING), SYS_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * Convert string with system encoding to application encoding.
	 * 
	 * Returns <code>null</code> if inputs null. Returns the given string
	 * without convert if failed to convert.
	 * 
	 * @see SYS_ENCODING
	 * @see APP_ENCODING
	 */
	public static String sys2app(String str) {
		if (str == null) {
			return null;
		}

		try {
			return new String(str.getBytes(SYS_ENCODING), APP_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	//
	// primitive type conversion methods
	//

	/**
	 * Converts string to boolean.
	 * 
	 * This function recognize several notation for boolean: true/false, yes/no,
	 * on/off, 1/0, etc... null or emptry string is false. all but false is
	 * true.
	 * 
	 * @param s
	 *            string
	 * @return boolean
	 */
	public static boolean atob(String s) {
		if ((s == null) || "".equals(s) || "0".equals(s)) {
			return false;
		}

		s = s.toLowerCase();

		return !("false".equals(s) || "no".equals(s) || "off".equals(s));
	}

	/**
	 * Converts string to double.
	 * 
	 * @param s
	 *            string
	 * @return double or 0 when error occurred
	 */
	public static double atod(String s) {
		return atod(s, 0);
	}

	/**
	 * Converts string to double with default.
	 * 
	 * @param s
	 *            string
	 * @param defaultValue
	 *            default value
	 * @return double or default value when error occurred
	 */
	public static double atod(String s, double defaultValue) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * Converts string to float.
	 * 
	 * @param s
	 *            string
	 * @return float or 0 when error occurred
	 */
	public static float atof(String s) {
		return atof(s, 0);
	}

	/**
	 * Converts string to float with default.
	 * 
	 * @param s
	 *            string
	 * @param defaultValue
	 *            default value
	 * @return float or default value when error occurred
	 */
	public static float atof(String s, float defaultValue) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * Converts string to integer.
	 * 
	 * @param s
	 *            string
	 * @return integer or 0 when error occurred
	 */
	public static int atoi(String s) {
		return atoi(s, 0);
	}

	/**
	 * Converts string to integer with default.
	 * 
	 * @param s
	 *            string
	 * @param defaultValue
	 *            default value
	 * @return integer or default value when error occurred
	 */
	public static int atoi(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * Convert string to integer with radix.
	 * 
	 * @param s
	 *            string
	 * @return integer or 0 when error occurred
	 */
	public static int atoiRadix(String s, int radix) {
		try {
			return Integer.parseInt(s, radix);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/**
	 * Converts string to long integer.
	 * 
	 * @param s
	 *            string
	 * @return long integer or 0 when error occurred
	 */
	public static long atol(String s) {
		return atol(s, 0);
	}

	/**
	 * Converts string to long integer with default.
	 * 
	 * @param s
	 *            string
	 * @param defaultValue
	 * @return long integer or default value when error occurred
	 */
	public static long atol(String s, long defaultValue) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * Convert string to long integer with radix
	 * 
	 * @param s
	 *            string
	 * @return long integer or 0 when error occurred
	 */
	public static long atolRadix(String s, int radix) {
		try {
			return Long.parseLong(s, radix);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/**
	 * Converts boolean to string.
	 * 
	 * @param b
	 *            boolean
	 * @return string
	 */
	public static String btoa(boolean b) {
		return Boolean.toString(b);
	}

	/**
	 * Converts double to string.
	 * 
	 * @param n
	 *            double
	 * @return string
	 */
	public static String dtoa(double n) {
		return Double.toString(n);
	}

	/**
	 * Converts float to string.
	 * 
	 * @param n
	 *            integer
	 * @return string
	 */
	public static String ftoa(float n) {
		return Float.toString(n);
	}

	/**
	 * Converts integer to string
	 * 
	 * @param n
	 *            integer
	 * @return string
	 */
	public static String itoa(int n) {
		return Integer.toString(n);
	}

	/**
	 * Converts long integer to string
	 * 
	 * @param n
	 *            long integer
	 * @return string
	 */
	public static String ltoa(long n) {
		return Long.toString(n);
	}

	public static final String toHex(byte n) {
		return StrUtil.lpad(Integer.toHexString(n), '0', 2);
	}

	public static final String toHex(char n) {
		return StrUtil.lpad(Integer.toHexString(n), '0', 4);
	}

	public static final String toHex(short n) {
		return StrUtil.lpad(Integer.toHexString(n), '0', 4);
	}

	public static final String toHex(int n) {
		return StrUtil.lpad(Integer.toHexString(n), '0', 8);
	}

	public static final String toHex(long n) {
		return StrUtil.lpad(Long.toHexString(n), '0', 16);
	}

	/**
	 * Splits delimitered string into a string array
	 * 
	 * @param str
	 *            delimitered string
	 * @param delim
	 *            delimiter
	 * @return string array
	 */
	public static String[] toArray(String str, String delim) {
		final StringTokenizer st = new StringTokenizer(str, delim);
		final String[] result = new String[st.countTokens()];
		int i = 0;

		while (st.hasMoreTokens()) {
			result[i++] = st.nextToken();
		}

		return result;
	}

	/**
	 * Parse properties from the string.
	 * 
	 * Format of the string must be [propertyName=property;]*
	 * 
	 * @param str
	 *            properties formatted as string
	 * @return a Properties object
	 */
	public static Properties toProperties(String str) {
		final Properties result = new Properties();

		if (str != null) {
			try {
				result.load(new ByteArrayInputStream(str.replace(';', '\n')
						.getBytes()));
			} catch (IOException ioe) {
			}
		}

		return result;
	}

	/**
	 * Convert properties to the single line of string.
	 * 
	 * @param properties
	 *            the properties to convert
	 * @return a single line string
	 */
	@SuppressWarnings("unchecked")
	public static String toString(Properties properties) {
		StringBuffer result = new StringBuffer();
		for (Iterator iter = properties.keySet().iterator(); iter.hasNext();) {
			String key = iter.next().toString();
			String value = properties.getProperty(key);
			result.append(key).append('=').append(value).append(';');
		}
		return result.toString();
	}

	//
	// string escape methods(port of php functions)
	//

	/**
	 * Converts special characters in html into html entity string.
	 * 
	 * See htmlSpecialchars() function in PHP. current implementation supports:
	 * 
	 * <ul>
	 * <li>'&amp;'(ampersand) into '&amp;amp;'</li>
	 * <li>'&quot;'(double quote) into '&amp;quot;'</li>
	 * <li>'&lt;'(less than) into '&amp;lt;'</li>
	 * <li>'&gt;'(greater than) into '&amp;gt;'</li>
	 * <li>'&amp;apos'(apostrophe) into '&amp;apos;'</li>
	 * </ul>
	 * 
	 * @see #stripTags
	 * @see <a href="http://www.php.net">PHP </a>
	 */
	public static String htmlSpecialChars(String s) {
		final StringBuffer result = new StringBuffer();
		final StringTokenizer st = new StringTokenizer(s, "&\"<>\'", true);

		while (st.hasMoreTokens()) {
			final String token = st.nextToken();

			switch (token.charAt(0)) {
			case '&':
				result.append("&amp;");

				break;

			case '\"':
				result.append("&quot;");

				break;

			case '<':
				result.append("&lt;");

				break;

			case '>':
				result.append("&gt;");

				break;

			case '\'':
				result.append("&apos;");

			default:
				result.append(token);
			}
		}

		return result.toString();
	}

	/**
	 * Escapes some special characters for sql query and so on. in current
	 * implementation single/double quotation, backslash will be escaped.
	 * 
	 * See AddSlashes() function in PHP.
	 * 
	 * @see #stripSlashes
	 * @see <a href="http://www.php.net">PHP </a>
	 */
	public static String addSlashes(String s) {
		final StringBuffer result = new StringBuffer();
		final StringTokenizer st = new StringTokenizer(s, "\'\"\\", true);

		while (st.hasMoreTokens()) {
			final String token = st.nextToken();

			switch (token.charAt(0)) {
			case '\'':
				result.append("\\\'");

				break;

			case '\"':
				result.append("\\\"");

				break;

			case '\\':
				result.append("\\\\");

				break;

			default:
				result.append(token);
			}
		}

		return result.toString();
	}

	/**
	 * Counter-part of #addSlashes. unescape backslash sequence.
	 * 
	 * See StripSlashes() function in PHP.
	 * 
	 * @see #addSlashes
	 * @see <a href="http://www.php.net">PHP </a>
	 */
	public static String stripSlashes(String s) {
		final StringBuffer result = new StringBuffer();
		final StringTokenizer st = new StringTokenizer(s, "\'\"\\", true);
		boolean escapeFlag = false;

		while (st.hasMoreTokens()) {
			final String token = st.nextToken();

			switch (token.charAt(0)) {
			case '\'':
				result.append("\'");
				escapeFlag = false;

				break;

			case '\"':
				result.append("\"");
				escapeFlag = false;

				break;

			case '\\':

				if (escapeFlag) {
					result.append("\\");
					escapeFlag = false;
				} else {
					escapeFlag = true;
				}

				break;

			default:
				result.append(token);
			}
		}

		return result.toString();
	}

	/**
	 * Remove all html tags from a string.
	 * 
	 * See stripTags() function in PHP.
	 * 
	 * @see #htmlSpecialChars
	 * @see <a href="http://www.php.net">PHP </a>
	 */
	public static String stripTags(String s) {
		final StringBuffer result = new StringBuffer();
		final StringTokenizer st = new StringTokenizer(s, "<>", true);
		boolean inTag = false;

		while (st.hasMoreTokens()) {
			final String token = st.nextToken();

			switch (token.charAt(0)) {
			case '<':
				inTag = true;

				break;

			case '>':
				inTag = false;

				break;

			default:

				if (!inTag) {
					result.append(token);
				} else {
					//
				}
			}
		}

		return result.toString();
	}

	/**
	 * Converts "\n"s into &lt;br /&gt; tags for html.
	 * 
	 * See nl2br() function in PHP.
	 * 
	 * @see #htmlSpecialChars
	 * @see <a href="http://www.php.net">PHP </a>
	 */
	public static String nl2br(String s) {
		return replace(s, "\n", "<br />");

		// v1(for JDK1.1):
		// final StringBuffer result = new StringBuffer();
		// final StringTokenizer st = new StringTokenizer(s, "\n");
		// if (st.hasMoreTokens()) {
		// do {
		// result.append(st.nextToken()).append("<br />");
		// } while (st.hasMoreTokens());
		// }
		// return result.toString();
	}

	/**
	 * Escape all characters in {@link #SPECIAL_SHELL_CHARS} with prepended
	 * "\\"(backslash).
	 * 
	 * @param s
	 *            The string to escape
	 * @return The escaped shell string.
	 * @see #unescapeShellString(String)
	 * @see #SPECIAL_SHELL_CHARS
	 */
	public static String escapeShellString(String s) {
		if (s == null) {
			return null;
		}

		final StringBuffer result = new StringBuffer();

		for (int index = 0; index < s.length(); index++) {
			final char nextChar = s.charAt(index);

			if (SPECIAL_SHELL_CHARS.indexOf(nextChar) != -1) {
				result.append('\\');
			}

			result.append(nextChar);
		}

		return result.toString();
	}

	/**
	 * Unscape all characters were escaped with
	 * {@link #escapeShellString(String)}
	 * 
	 * @param s
	 *            The string to unescape
	 * @return The unescaped shell string
	 * @see #escapeShellString(String)
	 * @see #SPECIAL_SHELL_CHARS
	 */
	public static String unescapeShellString(String s) {
		if (s == null) {
			return null;
		}

		StringBuffer result = new StringBuffer();

		boolean inBackslash = false;
		for (int index = 0; index < s.length(); index++) {
			char c = s.charAt(index);
			if (inBackslash) {
				if (SPECIAL_SHELL_CHARS.indexOf(c) < 0) {
					result.append('\\');
				}
				result.append(c);
				inBackslash = false;
			} else {
				if (c == '\\') {
					inBackslash = true;
				} else {
					result.append(c);
				}
			}
		}

		return result.toString();
	}

	/**
	 * Escape all characters in {@link #SPECIAL_FILENAME_CHARS} with &####;
	 * string(#### for character code).
	 * 
	 * @param s
	 *            the string to escape
	 * @return the escaped string
	 * @see #unescapeFilenameString(String)
	 * @see #SPECIAL_FILENAME_CHARS
	 */
	public static String escapeFilenameString(String s) {
		if (s == null) {
			return null;
		}

		StringBuffer result = new StringBuffer(s.length() * 2);

		for (int index = 0; index < s.length(); index++) {
			char c = s.charAt(index);
			if (SPECIAL_FILENAME_CHARS.indexOf(c) != -1) {
				result.append('&').append((int) c).append(';');
			} else {
				result.append(c);
			}
		}

		return result.toString();
	}

	/**
	 * Unescape all characters were escaped with
	 * {@link #escapeFilenameString(String)}.
	 * 
	 * @param s
	 * @return the unescaped string
	 * @see #escapeFilenameString(String)
	 * @see #SPECIAL_FILENAME_CHARS
	 */
	public static String unescapeFilenameString(String s) {
		if (s == null) {
			return null;
		}

		StringBuffer result = new StringBuffer(s.length() * 2);

		for (int index = 0; index < s.length(); index++) {
			char c = s.charAt(index);
			if (c == '&') {
				int endIndex = s.indexOf(';', index + 1);
				if (endIndex > 0) {
					try {
						int value = Integer.parseInt(s.substring(index + 1,
								endIndex));
						result.append((char) value);
						index = endIndex;
					} catch (NumberFormatException nfe) {
						// if (DebugUtil.DEBUG) {
						// DebugUtil.debug(
						// "invalid character sequence to unescape: "
						// + s, nfe);
						// }
						result.append(s.substring(index, endIndex + 1));
					}
				}
			} else {
				result.append(c);
			}
		}

		return result.toString();
	}

	/**
	 * Escape for win32 reserved filenames({@link #WIN32_SPECIAL_FILENAMES})
	 * with prepended {@link #WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX}.
	 * 
	 * @param s
	 *            the string to escape
	 * @return the escaped string
	 * @see #unescapeWin32FilenameString(String)
	 * @see #WIN32_SPECIAL_FILENAMES
	 * @see #WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX
	 */
	public static String escapeWin32FilenameString(String s) {
		String uc = s.toUpperCase();
		for (int i = 0; i < WIN32_SPECIAL_FILENAMES.length; i++) {
			if (uc.equals(WIN32_SPECIAL_FILENAMES[i])) {
				return WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX + s;
			}
		}
		return s;
	}

	/**
	 * Unescape for win32 reservered filenames were escaped with
	 * {@link #escapeWin32FilenameString(String).
	 * 
	 * @param s
	 *            the string to unescape
	 * @return the unescaped string
	 * @see #escapeWin32FilenameString(String)
	 * @see #WIN32_SPECIAL_FILENAMES
	 * @see #WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX
	 */
	public static String unescapeWin32FilenameString(String s) {
		if (s.startsWith(WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX)) {
			String unescaped = s.substring(WIN32_SPECIAL_FILENAME_ESCAPE_PREFIX
					.length());
			String uc = unescaped.toUpperCase();
			for (int i = 0; i < WIN32_SPECIAL_FILENAMES.length; i++) {
				if (uc.equals(WIN32_SPECIAL_FILENAMES[i])) {
					return unescaped;
				}
			}
		}
		return s;
	}

	//
	// string formatting methods for variable arguments
	//

	/**
	 * Format parameterized string
	 * 
	 * @param fmt
	 *            format string
	 * @param arg0
	 *            a paramter to replace {0}
	 * @return formatted string
	 * @see java.text.MessageFormat#format
	 */
	public static String format(String fmt, Object arg0) {
		return MessageFormat.format(fmt, new Object[] { arg0 });
	}

	/**
	 * Format parameterized string
	 * 
	 * @param fmt
	 *            format string
	 * @param arg0
	 *            a paramter to replace {0}
	 * @param arg1
	 *            a paramter to replace {1}
	 * @return formatted string
	 * @see java.text.MessageFormat#format
	 */
	public static String format(String fmt, Object arg0, Object arg1) {
		return MessageFormat.format(fmt, new Object[] { arg0, arg1 });
	}

	/**
	 * Format parameterized string
	 * 
	 * @param fmt
	 *            format string
	 * @param arg0
	 *            a paramter to replace {0}
	 * @param arg1
	 *            a paramter to replace {1}
	 * @param arg2
	 *            a paramter to replace {2}
	 * @return formatted string
	 * @see java.text.MessageFormat#format
	 */
	public static String format(String fmt, Object arg0, Object arg1,
			Object arg2) {
		return MessageFormat.format(fmt, new Object[] { arg0, arg1, arg2 });
	}

	/**
	 * Format parameterized string
	 * 
	 * @param fmt
	 *            format string
	 * @param arg0
	 *            a paramter to replace {0}
	 * @param arg1
	 *            a paramter to replace {1}
	 * @param arg2
	 *            a paramter to replace {2}
	 * @param arg3
	 *            a paramter to replace {3}
	 * @return formatted string
	 * @see java.text.MessageFormat#format
	 */
	public static String format(String fmt, Object arg0, Object arg1,
			Object arg2, Object arg3) {
		return MessageFormat.format(fmt,
				new Object[] { arg0, arg1, arg2, arg3 });
	}

	/**
	 * Format parameterized string
	 * 
	 * @param fmt
	 *            format string
	 * @param arg0
	 *            a paramter to replace {0}
	 * @param arg1
	 *            a paramter to replace {1}
	 * @param arg2
	 *            a paramter to replace {2}
	 * @param arg3
	 *            a paramter to replace {3}
	 * @param arg4
	 *            a paramter to replace {4}
	 * @return formatted string
	 * @see java.text.MessageFormat#format
	 */
	public static String format(String fmt, Object arg0, Object arg1,
			Object arg2, Object arg3, Object arg4) {
		return MessageFormat.format(fmt, new Object[] { arg0, arg1, arg2, arg3,
				arg4 });
	}

	/**
	 * Format parameterized string
	 * 
	 * @param fmt
	 *            format string
	 * @param arg
	 *            paramters
	 * @return formatted string
	 * @see java.text.MessageFormat#format
	 */
	public static String format(String fmt, Object[] arg) {
		return MessageFormat.format(fmt, arg);
	}

	//
	// misc string manipulation methods
	//

	/**
	 * Quotes a string with single quotation mark.
	 * 
	 * Single quotation marks in the string will be escaped.
	 * 
	 * @param s
	 *            unquoted string
	 * @return quoted string
	 */
	public static String quote(String s) {
		return new StringBuffer().append('\'').append(addSlashes(s)).append(
				'\'').toString();
	}

	/**
	 * Simply quotes a string with single quotation mark, but no escape
	 * processing.
	 * 
	 * @param s
	 *            unquoted string
	 * @return quoted string
	 */
	public static String squote(String s) {
		return new StringBuffer().append('\'').append(s).append('\'')
				.toString();
	}

	/**
	 * Simply quotes a string with double quotation mark, but no escape
	 * processing.
	 * 
	 * @param s
	 *            unquoted string
	 * @return quoted string
	 */
	public static String dquote(String s) {
		return new StringBuffer().append('\"').append(s).append('\"')
				.toString();
	}

	/**
	 * Get a not-null and trimmed string.
	 * 
	 * See NVL() in Oracle PL/SQL.
	 * 
	 * @param s
	 *            valid string or null
	 * @return the string itself, or empty string if the string is null
	 */
	public static String nvl(String s) {
		if (s == null) {
			return "";
		}

		return s.trim();
	}

	/**
	 * Get a not-null and trimmed string with default.
	 * 
	 * @param s
	 *            valid string or null
	 * @param def
	 *            default, will be returned instead of null
	 * @return the string itself, or default string if the string is null
	 */
	public static String nvl(String s, String def) {
		if (s == null) {
			return def;
		}

		return s.trim();
	}

	/**
	 * Pads the specified character to left(begin) of string.
	 * 
	 * Returns null, if inputs null. Returns the given string, if the string is
	 * longer than the result length.
	 * 
	 * @param s
	 *            the string to pad
	 * @param c
	 *            the padding character
	 * @param length
	 *            the length of result string
	 * @return string
	 */
	public static String lpad(String s, char c, int length) {
		if (s == null) {
			return null;
		}

		int count = length - s.length();

		if (count <= 0) {
			return s;
		}

		final StringBuffer result = new StringBuffer(length);

		while (--count >= 0) {
			result.append(c);
		}

		result.append(s);

		return result.toString();
	}

	/**
	 * Pads the specified character to right(end) of string.
	 * 
	 * Returns null, if inputs null. Returns the given string, if the string is
	 * longer than the result length.
	 * 
	 * @param s
	 *            the string to pad
	 * @param c
	 *            the padding character
	 * @param length
	 *            the length of result string
	 * @return string
	 */
	public static String rpad(String s, char c, int length) {
		if (s == null) {
			return null;
		}

		int count = length - s.length();

		if (count <= 0) {
			return s;
		}

		final StringBuffer result = new StringBuffer(length);
		result.append(s);

		while (--count >= 0) {
			result.append(c);
		}

		return result.toString();
	}

	/**
	 * Make a string filled with the specified character.
	 * 
	 * @param c
	 *            the character to fill
	 * @param length
	 *            the length of result string
	 * @return string
	 */
	public static String stringOf(char c, int length) {
		final StringBuffer result = new StringBuffer(length);

		while (--length >= 0)
			result.append(c);

		return result.toString();
	}

	/**
	 * Find occurrences of a string and replace it into another string.
	 * 
	 * Similar to java.lang.String#replace, but this method replaces string not
	 * char.
	 * 
	 * See str_replace() in PHP.
	 * 
	 * @param str
	 *            source string
	 * @param s1
	 *            string to find
	 * @param s2
	 *            string to replace
	 * @return replaced string
	 * @see java.lang.String#replace
	 * @see <a href="http://www.php.net">PHP </a>
	 */
	public static String replace(String s, String s1, String s2) {
		final StringBuffer result = new StringBuffer();
		int index1 = 0;
		int index2 = s.indexOf(s1);

		while (index2 >= 0) {
			result.append(s.substring(index1, index2));
			result.append(s2);
			index1 = index2 + s1.length();
			index2 = s.indexOf(s1, index1);
		}

		result.append(s.substring(index1));

		return result.toString();
	}

	/**
	 * cut a string within given length and appends ellipse mark.
	 * 
	 * @param val
	 *            an original string
	 * @param len
	 *            length to cut
	 * @param mark
	 *            an ellipse mark string, use "...' if null
	 * @return string
	 */
	public static String ellipsed(String val, int len, String mark) {
		if ((val == null) || val.equals("")) {
			return "";
		}

		for (int i = 0; i < val.length(); i++) {
			if (val.charAt(i) < 0) {
				len--; // sing-wide character(SBCS in win32?)
			} else {
				len -= 2; // double-wide character(DBCS in win32?)
			}

			if (len <= 0) {
				if (mark == null) {
					mark = "...";
				}

				return val + mark;
			}
		}

		return val;

		// v1(simplest implementation):
		// if (val.length() <= len) return val;
		// if(mark == null) mark = "..."; return val.substring(0, len) + mark;;
		// v2(most complex implementation):
		// byte[] temp = val.getBytes();
		// if(temp.length <= len) return val; byte[] ret = new byte[len+1];
		// for(int i = 0; i < len; i++) { if(temp[i] < 0) { ret[i] = temp[i];
		// i++; } ret[i] = temp[i]; } if(mark == null) mark = "..."; return new
		// String(ret) + mark;
	}

	/**
	 * Get a string representation of the metric value.
	 * 
	 * @param value
	 *            the metric number
	 * @return string representation
	 * @see #METRIC_FORMAT
	 */
	public static String toMetric(double value) {
		final double biased;
		final String unit;

		if (value < (KILO_BASE * 0.8)) {
			biased = value;
			unit = METRIC_UNIT;
		} else if (value < (MEGA_BASE * 0.8)) {
			biased = value / KILO_BASE;
			unit = KILO_UNIT;
		} else if (value < (GIGA_BASE * 0.8)) {
			biased = value / MEGA_BASE;
			unit = MEGA_UNIT;
		} else if (value < (TERA_BASE * 0.8)) {
			biased = value / GIGA_BASE;
			unit = GIGA_UNIT;
		} else {
			biased = value / TERA_BASE;
			unit = TERA_UNIT;
		}

		return METRIC_FORMAT.format(biased) + unit;
	}

	/**
	 * parse K, M, G, T metric string and extract the number as byte unit.
	 * 
	 * @param s
	 *            metric string
	 * @return number as byte unit
	 */
	public static double parseMetric(String s) {
		final double base;

		if (s.endsWith(KILO_UNIT)) {
			base = KILO_BASE;
		} else if (s.endsWith(MEGA_UNIT)) {
			base = MEGA_BASE;
		} else if (s.endsWith(GIGA_UNIT)) {
			base = GIGA_BASE;
		} else if (s.endsWith(TERA_UNIT)) {
			base = TERA_BASE;
		} else {
			return atod(s);
		}

		return atod(s.substring(0, s.length() - 1)) * base;
	}

	/**
	 * Convert hex digit character to decimal integer.
	 */
	public static int hexDigitToDecimal(char c) {
		if (Character.isDigit(c)) {
			return c - '0';
		} else if (Character.isLowerCase(c)) {
			return c - 'a' + 10;
		} else if (Character.isUpperCase(c)) {
			return c - 'A' + 10;
		} else {
			return 0; // shouldn't happen, we're guarded by isHexDigit()
		}
	}

	/**
	 * See if the character is hex digit or not.
	 */
	public static boolean isHexDigit(char c) {
		return (HEX_DIGITS.indexOf(c) >= 0);
	}

	/**
	 * Converts string to the specified klass of object.
	 * 
	 * This is borrowed(stealed?) from jakarta-tomcat project.
	 * 
	 * @param s
	 *            convertee source string
	 * @param t
	 *            target class to convert
	 * @return an instance of the class or null
	 * @see <a href="http://jakarta.apache.org/tomcat/">jakarta-tomcat project
	 *      </a>
	 */
	@SuppressWarnings("unchecked")
	public static Object convert(String s, Class t) {
		if (s == null) {
			if (t.equals(Boolean.class) || t.equals(Boolean.TYPE)) {
				s = "false";
			} else {
				return null;
			}
		}

		if (t.equals(Boolean.class) || t.equals(Boolean.TYPE)) {
			if (s.equalsIgnoreCase("on") || s.equalsIgnoreCase("true")) {
				s = "true";
			} else {
				s = "false";
			}

			return new Boolean(s);
		} else if (t.equals(Byte.class) || t.equals(Byte.TYPE)) {
			return new Byte(s);
		} else if (t.equals(Character.class) || t.equals(Character.TYPE)) {
			return (s.length() > 0) ? new Character(s.charAt(0)) : null;
		} else if (t.equals(Short.class) || t.equals(Short.TYPE)) {
			return new Short(s);
		} else if (t.equals(Integer.class) || t.equals(Integer.TYPE)) {
			return new Integer(s);
		} else if (t.equals(Float.class) || t.equals(Float.TYPE)) {
			return new Float(s);
		} else if (t.equals(Long.class) || t.equals(Long.TYPE)) {
			return new Long(s);
		} else if (t.equals(Double.class) || t.equals(Double.TYPE)) {
			return new Double(s);
		} else if (t.equals(String.class)) {
			return s;
		} else if (t.equals(java.io.File.class)) {
			return new java.io.File(s);
		}

		return s;
	}
	
	/**
	 * Converts string to the specified klass of object.
	 * 
	 * @param s convertee source string
	 *            
	 * @return convert String
	 * 
	 * @author lllkt
	 */
	public static String replaceNewline(String s){
		if(s.indexOf("\r") != -1) s = s.replaceAll("\r", "\\\\r");
		if(s.indexOf("\n") != -1) s = s.replaceAll("\n", "\\\\n");
		if(s.indexOf("\t") != -1) s = s.replaceAll("\t", "\\\\t");
		if(s.indexOf("\'") != -1) s = s.replaceAll("\'", "\\\\'");
		return s;
	}
	
	/**
	 * Converts string to JSON type of object
	 * 
	 * @param s convertee source string
	 * @return convert String
	 * 
	 * @author lllkt
	 */
	public static String jsonSpecialChars(String s) {
		final StringBuffer result = new StringBuffer();
		final StringTokenizer st = new StringTokenizer(s, "&<>\"\\", true);

		while (st.hasMoreTokens()) {
			final String token = st.nextToken();

			switch (token.charAt(0)) {
			case '&':
				result.append("&amp;");

				break;

			case '\"':
				result.append("\\\"");

				break;

			case '\\':
				result.append("\\\\");
				
				break;

			case '<':
				result.append("&lt;");

				break;

			case '>':
				result.append("&gt;");

				break;

			default:
				result.append(token);
			}
		}

		return result.toString();
	}
	
	/**
	 * parameter phone number return spilit type
	 * @param hpNO
	 * @return
	 */
	public String checkTypeHP(String hpNO, String spilit){
		String hp = "";
        if(StringUtils.isNotBlank(hpNO)) {
    		if(hpNO.indexOf(spilit)>-1){
    			hp=hpNO;
    		}else{
    			if(hpNO.length()==10){
    				hp = hpNO.substring(0,3);
    				hp += spilit+hpNO.substring(3, 6);
    				hp += spilit+hpNO.substring(6);
    			}else if(hpNO.length()==11){
    				hp = hpNO.substring(0,3);
    				hp += spilit+hpNO.substring(3, 7);
    				hp += spilit+hpNO.substring(7);
    			}else{
    				hp = hpNO;
    			}
    		}
        }
		return hp;
	}
	
	 /**
    *
    * Json 포맷으로 전달해야 할 문자열 변환
    * <P/>
    * JSON 포맷으로 전달해야 할 문자열이 [ 로 시작할 경우 변화 오류를 방지하기 위한 해당 문자열 @|#[ 로 대체.
    * 받는 쪽에서 다시 재 변환할 것
    *
    * @param src
    * @return
    */
   public static String convertJSONFormat(String src) {
      if (src == null || "src".equals(src)) return src;

      Pattern JSONFormat = Pattern.compile("^\\[");
      String result = null;
      Matcher m;

      m = JSONFormat.matcher(src);
      result = m.replaceAll("@#@[");

      return result;
   }
   
   public static String[] parseStringByBytes(String raw, int len, String encoding) {  

	   if (raw == null) return null;

	   String[] ary = null;

	   try {
		   // raw 의 byte
		   byte[] rawBytes = raw.getBytes(encoding);
		   int rawLength = rawBytes.length;
		   int index = 0;
		   int minus_byte_num = 0;
		   int offset = 0;
		   int hangul_byte_num = encoding.equals("UTF-8") ? 3 : 2;

		   if(rawLength > len){

			   int aryLength = (rawLength / len) + (rawLength % len != 0 ? 1 : 0);
			   ary = new String[aryLength];
	  
			   for(int i=0; i<aryLength; i++){

				   minus_byte_num = 0;
				   offset = len;

				   if(index + offset > rawBytes.length){
					   offset = rawBytes.length - index;
				   }

				   for(int j=0; j<offset; j++){      

					   if(((int)rawBytes[index + j] & 0x80) != 0){
						   minus_byte_num ++;
					   }

				   }     

				   if(minus_byte_num % hangul_byte_num != 0){
					   offset -= minus_byte_num % hangul_byte_num;
				   }     

				   ary[i] = new String(rawBytes, index, offset, encoding);     

				   index += offset ;

			   }    

		   	} else {

			   ary = new String[]{raw};

	   		}    

	   	} catch(Exception e) {

	   	}     

	   	return ary;
   } 

}
