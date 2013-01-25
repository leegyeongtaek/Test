/**
 * 
 */
package skt.tmall.board.model.process.db;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import skt.tmall.common.model.process.IProcess;

// import com.yuraeltec.common.model.process.db.ISelectProcess;

/**
 * @author cshan
 * 
 */
public class Main {

	@SuppressWarnings("unchecked")
	public void aopExecuteDynamicProcess() throws Exception {
		ApplicationContext ct = new ClassPathXmlApplicationContext("beans.xml");
		IProcess<HashMap<String, Object>> dp = (IProcess<HashMap<String, Object>>) ct
				.getBean("boardSelect");
		dp.process(new HashMap<String, Object>());

	}
}
