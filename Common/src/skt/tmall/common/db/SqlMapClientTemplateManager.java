package skt.tmall.common.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * 다양한 DataSource 를 지원하기 위해 Map 에 DataSource에 맞는 
 * 여러 org.springframework.orm.ibatis.SqlMapClientTemplate를 담아 둔다.
 * 
 * 물른 스프링 아이바티스 기반이 아니라면 DatabaseAdvice 부터 새로 구현해야 할 것이다.
 * 
 * @author leegt80
 * 
 */
public class SqlMapClientTemplateManager {
	
	private Map<String, SqlMapClientTemplate> map;

	public SqlMapClientTemplate getTemplate(String alias) {
		return map.get(alias);
	}

	@Required
	public void setSqlMapClientTemplateMap(Map<String, SqlMapClientTemplate> map) {
		this.map = map;
	}
}
