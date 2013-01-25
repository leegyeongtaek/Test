package skt.tmall.common.db;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 오라클 버전의 시퀀스 지
 * 
 * @author leegt80
 */
public class OracleIncrementer extends AbstractSpringIncreamenter {
	
	/**
	 * sequence 존재 유무 체크
	 */
	public boolean checkExist(String incrementerName) {
		DataSource dataSource = super.incrementer.getDataSource();
		try {
			new JdbcTemplate(dataSource).execute("select " + incrementerName
					+ ".nextVal from dual");
			return true;
		} catch (DataAccessException dae) {
			return false;
		}
	}
	
	/*
	 * incrementerName은 sequence name
	 * cipher는 자릿수
	 * 생성 시퀀스 자릿수에 맞춰 startValue와 maxValue를 가지는 sequence 생성.
	 */
	public String createIncrementer(String incrementerName, int cipher) {
		long startValue = 1;
		long maxValue   = 1;
		
		startValue 	= (long) (startValue * Math.pow(10, cipher-1));
		maxValue	= startValue * 10 - 1;
		
		return "CREATE SEQUENCE "
				+ incrementerName
				+ " MINVALUE 1 MAXVALUE " 
				+ String.valueOf(maxValue)
				+ " START WITH "
				+ String.valueOf(startValue)
				+ " INCREMENT BY 1 CACHE 20";
	}

}
