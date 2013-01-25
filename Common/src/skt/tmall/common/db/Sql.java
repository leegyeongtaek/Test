package skt.tmall.common.db;

import java.util.List;

/**
 * sql 쿼리와 바인딩 변수의 타입을 가진다. 즉 db 최소 실행단위의 추상화라 할 수 있다.
 * 
 * @author leegt80
 * 
 */
public class Sql {

	private String sql;

	private int[] types;

	public Sql() {
	}

	public Sql(String sql) {
		this.sql = sql;
		this.types = types == null ? new int[0] : types;
	}

	public Sql(String sql, int[] types) {
		this.sql = sql;
		this.types = types == null ? new int[0] : types;
	}

	public Sql(String sql, List<Integer> types) {
		this.sql = sql;
		if (types == null) {
			this.types = new int[0];
		}
		else{
			int[] _types = new int[types.size()];
			int i = 0;
			for (Integer type : types) {
				_types[i++] = type.intValue();
			}
			this.types = _types;
		}
	}

	public String getSql() {
		return sql;
	}

	public int[] getTypes() {
		return types;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTypes(int[] types) {
		this.types = types;
	}

	public String toString() {
		return getSql();
	}

}
