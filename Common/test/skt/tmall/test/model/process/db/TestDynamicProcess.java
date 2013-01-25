package skt.tmall.test.model.process.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skt.tmall.common.db.IRowMapper;
import skt.tmall.common.db.Sql;
import skt.tmall.common.model.process.db.AbstractDynamicSelectProcess;

public class TestDynamicProcess extends AbstractDynamicSelectProcess<Object[]> {

	private StringBuffer sb;

	private List<Integer> types;

	@Override
	public Sql getSql(HashMap<String, Object> context) {
		sb = new StringBuffer();
		types = new ArrayList<Integer>(3);
		sb.append("select * from prj ");
		sb.append("where staoid in (? , ?)");
		types.add(Integer.valueOf(Types.VARCHAR));
		types.add(Integer.valueOf(Types.VARCHAR));
		return new Sql(sb.toString(), types);
	}

	public Object[] getParameters(HashMap<String, Object> context) {
		return new Object[] { "STA130", "STA110" };
	}

	@Override
	protected IRowMapper<Object[]> getRowMapper() {
		return new IRowMapper<Object[]>() {

			public Object[] mapRow(ResultSet rs, int rowCount)
					throws SQLException {
				return null;
			}

		};
	}

}
