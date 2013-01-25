package skt.tmall.common.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

/**
 * @see com.yuraeltec.common.db.IIncrementer
 * 
 * 기본 구현은 spring의
 * org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer를
 * 이용해 구현을 한다.
 * 
 * DB primary key 값을 생성하기 위해 쓴다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractSpringIncreamenter implements IIncrementer {

	private Map<String, Boolean> bm_incrementers = new HashMap<String, Boolean>();

	protected AbstractDataFieldMaxValueIncrementer incrementer;

	public String getNextVal(String incrementerName, int cipher) {

		synchronized (incrementer) {
			String incrementerSequenceName = getIncrementerSequenceName(incrementerName);
			if (bm_incrementers.get(incrementerSequenceName) == null) {
				boolean exist = checkExist(incrementerSequenceName);
				if (!exist) {
					DataSource ds = incrementer.getDataSource();
					new JdbcTemplate(ds)
							.execute(createIncrementer(incrementerSequenceName, cipher));
				}
				bm_incrementers.put(incrementerSequenceName, exist);
			}
			incrementer.setIncrementerName(incrementerSequenceName);
			return incrementer.nextStringValue();
		}
	}

	@Required
	public void setIncrementer(AbstractDataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	/**
	 * create sequence naming 
	 * @param incrementerName
	 * @return
	 */
	private String getIncrementerSequenceName(String incrementerName) {
		incrementerName = incrementerName.toLowerCase();
		return incrementerName + "_sequence";
	}
}
