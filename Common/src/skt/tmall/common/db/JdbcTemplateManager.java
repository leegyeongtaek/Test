package skt.tmall.common.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 다양한 DataSource 를 지원하기 위해 Map 에 DataSource에 맞는 
 * 여러 org.springframework.jdbc.core.JdbcTemplate를 담아 둔다.
 * 
 * 물른 스프링 기반이 아니라면 DatabaseAdvice 부터 새로 구현해야 할 것이다.
 * 
 * @author leegt80
 * 
 */
public class JdbcTemplateManager {
	
	private Map<String, JdbcTemplate> map;

	public JdbcTemplate getTemplate(String alias) {
		return map.get(alias);
	}

	@Required
	public void setJdbcTemplateMap(Map<String, JdbcTemplate> map) {
		this.map = map;
	}
}
