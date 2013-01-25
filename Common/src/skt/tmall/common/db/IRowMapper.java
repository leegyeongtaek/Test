package skt.tmall.common.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * java.sql.ResultSet을 노출시키지 않기 위해 또하나의 래퍼를 사용한다.
 * ResultSet을 client 에게 노출하지 않고 IRowMapper를 구현한 클래스에서 
 * ResultSet 결과 데이터를 Object 형식으로 지원하도록 함.
 * 여기서는 Map 을 return 할 것임. 
 * 
 * java.sql.ResultSet을 client 에서 사용못하게 함으로써 jdbc 가 아니더라고 지원이 가능하다 ..물른 이론상..ㅋㅋ
 * 
 * 
 * @author leegt80
 * 
 */
public interface IRowMapper<T> {

	public T mapRow(ResultSet rs, int rowCount) throws SQLException;
}
