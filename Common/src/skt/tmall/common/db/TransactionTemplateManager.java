package skt.tmall.common.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * 트렌젝션은 정책에 따라 여러가지가 될 수 있기때문에..
 * 지금음 springframework 에 종속적이며, 하나의 TransactionTemplate 만이 default key로 map에 들어있다.
 * 
 * @author leegt80
 * 
 */
public class TransactionTemplateManager {

	private Map<String, TransactionTemplate> map;

	public TransactionTemplate getTransactionTemplate(String alias) {
		return map.get(alias);
	}

	@Required
	public void setTransactionTemplateMap(Map<String, TransactionTemplate> map) {
		this.map = map;
	}
}
