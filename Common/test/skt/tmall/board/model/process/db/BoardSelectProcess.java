/**
 * 
 */
package skt.tmall.board.model.process.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.db.IRowMapper;
import skt.tmall.common.model.process.db.AbstractSelectProcess;
import skt.tmall.common.util.DBUtils;

/**
 * @author cshan
 * 
 */
public class BoardSelectProcess extends
		AbstractSelectProcess<Map<String, Object>> {

	@Override
	protected String getAlias() {
		return "boardSelect";
	}

	@Override
	public Object[] getParameters(HashMap<String, Object> context) {
		return ICommonConstants.EMPTY_PARAMETER;
	}

	@Override
	protected IRowMapper<Map<String, Object>> getRowMapper() {
		return new IRowMapper<Map<String, Object>>() {

			public Map<String, Object> mapRow(ResultSet rs, int rowCount)
					throws SQLException {
				Map<String, Object> resultAsMap = DBUtils.getResultAsMap(rs);
				return resultAsMap;
			}

		};
	}
}
