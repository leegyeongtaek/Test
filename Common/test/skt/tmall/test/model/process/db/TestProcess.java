package skt.tmall.test.model.process.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import skt.tmall.common.db.IRowMapper;
import skt.tmall.common.model.process.db.AbstractSelectProcess;

public class TestProcess extends AbstractSelectProcess<Object []> {

	public Object[] getParameters(HashMap<String, Object> context) {
		return new Object[] {};
	}

	public IRowMapper<Object[]> getRowMapper() {
		return new IRowMapper<Object[]>() {

			public Object[] mapRow(ResultSet rs, int rowCount)
					throws SQLException {

				return null;
			}

		};
	}

	@Override
	protected String getAlias() {
		return "";
	}

}
