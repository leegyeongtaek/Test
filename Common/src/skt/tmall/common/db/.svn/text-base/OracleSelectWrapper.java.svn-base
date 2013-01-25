package skt.tmall.common.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @see ISelectWrapper
 * @author leegt80
 * 
 */
public class OracleSelectWrapper implements ISelectWrapper {

	private static Log log = LogFactory.getLog(OracleSelectWrapper.class);

	private int defaultWrappingSize;

	public int getDefaultWrappingSize() {
		return defaultWrappingSize;
	}

	public Object[] getWrappingParams(Integer from, Integer to)
			throws IllegalArgumentException {
		if (from == null || to == null) {
			throw new IllegalArgumentException("to value is null");
		}
		return new Object[] { from, to };
	}

	public void setDefaultWrappingSize(int defaultWrappingSize) {
		this.defaultWrappingSize = defaultWrappingSize;
	}

	public String wrap(String sql) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ( ");
		sb.append("SELECT ROWNUM AS " + ROWNUM + ", wraper.* FROM ( ");
		sb.append(sql);
		sb.append(" ) wraper ");
		sb.append(") WHERE " + ROWNUM + " BETWEEN ? AND ? ");
		String toString = sb.toString();
		if (log.isDebugEnabled()) {
			log.debug(toString);
		}
		return toString;
	}

}
