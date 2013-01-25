/**
 * 
 */
package skt.tmall.common.model.process.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.core.IDisposable;

import skt.tmall.common.db.IRowMapper;
import skt.tmall.common.db.ISelectDynamicQuery;
import skt.tmall.common.db.ISelectWrapper;
import skt.tmall.common.db.Sql;

/**
 * select 쿼리를 지원하고 sql wrapping을 지원한다.
 * 
 * @author leegt80
 */
public abstract class AbstractBaseSelectProcess<R> extends
		AbstractDatabaseProcess implements IDisposable {

	private Log log = LogFactory.getLog(AbstractBaseSelectProcess.class);

	private int fetchSize = 10;

	private ISelectWrapper selectWrapper;
	
	private ISelectDynamicQuery selectDynamic;

	private Integer pageFrom;

	private Integer pageTo;
	
	private String[] dynamic_Params;
	
	private int tempSize;

	public void dispose() {
		selectDynamic = null;
		selectWrapper = null;
		pageFrom = null;
		pageTo = null;
	}

	protected int getFetchSize() {
		return fetchSize;
	}

	public abstract Object[] getParameters(HashMap<String, Object> context);

	protected abstract IRowMapper<R> getRowMapper();

	@Override
	protected final void hook(HashMap<String, Object> context) {
		if (context.get(ICommonConstants.IS_WRAPPING) != null) {
			pageFrom = Integer.valueOf((String) context
					.get(ICommonConstants.PAGE_FROM));
			pageTo = Integer.valueOf((String) context
					.get(ICommonConstants.PAGE_TO));
		}
		
		if (context.get(ICommonConstants.IS_DYNAMIC) != null) {
			dynamic_Params = (String[]) context.get(ICommonConstants.DYNAMIC_PARAM);
		}
		else
		{
			selectDynamic = null;
		}
	}

	private Object[] mergeParams(Object[] params) {
		Object[] wrappingPrams = selectWrapper.getWrappingParams(pageFrom,
				pageTo);
		Object[] tempParam = new Object[params.length + wrappingPrams.length];
		System.arraycopy(params, 0, tempParam, 0, params.length);
		System.arraycopy(wrappingPrams, 0, tempParam, params.length,
				wrappingPrams.length);
		params = tempParam;
		return params;
	}

	private int[] mergeTypes(int[] types) {
		int[] tempTypes = new int[types.length + 2];
		int[] wrapTypes = { Types.INTEGER, Types.INTEGER };
		System.arraycopy(types, 0, tempTypes, 0, types.length);
		System.arraycopy(wrapTypes, 0, tempTypes, types.length,
				wrapTypes.length);
		types = tempTypes;
		return types;
	}
	
	private Object[] dynamicParams(Object[] params) {
		Object[] dynamicParams = selectDynamic.getDynamicParams(dynamic_Params);
		tempSize = params.length + dynamicParams.length;
		Object[] tempParam = new Object[tempSize];
		System.arraycopy(params, 0, tempParam, 0, params.length);
		System.arraycopy(dynamicParams, 0, tempParam, params.length, dynamicParams.length);
		params = tempParam;
		return params;
	}
	
	private int[] dynamicTypes(int[] types) {
		int[] tempTypes    = new int[tempSize];
		int[] dynamicTypes = selectDynamic.getDynamicTypes();
		System.arraycopy(types, 0, tempTypes, 0, types.length);
		System.arraycopy(dynamicTypes, 0, tempTypes, types.length, dynamicTypes.length);
		types = tempTypes;
		return types;
	}
	
	protected String afterSql(String sql) {
		return sql;
	}
	
	@SuppressWarnings("unchecked")
	protected List<R> select(Sql sql, Object[] params) {
		String strSql = sql.getSql();
		int[] types = sql.getTypes();
		
		if(selectDynamic != null) {
			params = dynamicParams(params);
			types  = dynamicTypes(types);
			strSql = selectDynamic.dynamic(strSql);
		}
		
		strSql = afterSql(strSql);

		if (selectWrapper != null) {
			strSql = selectWrapper.wrap(strSql);
			params = mergeParams(params);
			types = mergeTypes(types);
		}
		
		if (log.isDebugEnabled()) {
			log.debug(strSql);
			for (int i = 0; i < params.length; i++) {
				log.debug("[" + i + "]'" + params[i] + "'");
			}
		}
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(getFetchSize());
		
		try{
			// 각 프로세스에서 inner class 로 생성한 IRowMapper 에서 resultSet 데이터를 Map 에 설정하여 return 한다. 
			List<R> query = jdbcTemplate.query(strSql, params, types,
					new RowMapper() {
						public Object mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return getRowMapper().mapRow(rs, rowNum);
						}
					});
			return query;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	//
	// @dynamic inject
	//
	public void setSelectWrapper(ISelectWrapper selectWrapper) {
		this.selectWrapper = selectWrapper;
	}
	
	// 
	// @dynamic inject
	//
	public void setSelectDynamic(ISelectDynamicQuery selectDynamic) {
		this.selectDynamic = selectDynamic;
	}

}
