package skt.tmall.common.db;

import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

import skt.tmall.common.db.OracleIncrementer;

public class IncrementerTest {

	public void incrementerTest() throws Exception {
		OracleIncrementer oracleIncrementer = new OracleIncrementer();
		OracleSequenceMaxValueIncrementer ic = new OracleSequenceMaxValueIncrementer();
		org.apache.commons.dbcp.BasicDataSource dataSource = new org.apache.commons.dbcp.BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@61.33.22.181:1521:TESTPDM");
		dataSource.setUsername("sewon");
		dataSource.setPassword("sewon");
		ic.setDataSource(dataSource);
		oracleIncrementer.setIncrementer(ic);
		System.out.println(oracleIncrementer.getNextVal("WDP", 6));
		System.out.println(oracleIncrementer.getNextVal("wdp", 6));
		System.out.println(oracleIncrementer.getNextVal("wdp", 6));
		System.out.println(oracleIncrementer.getNextVal("wdp", 6));
		System.out.println(oracleIncrementer.getNextVal("wdp", 6));
	}
}
