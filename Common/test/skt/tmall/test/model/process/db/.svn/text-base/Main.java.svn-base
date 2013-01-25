package skt.tmall.test.model.process.db;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import skt.tmall.common.model.process.IProcess;

public class Main {

	@SuppressWarnings("unchecked")
	public void aopExecuteDynamicProcess() throws Exception {
		ApplicationContext ct = new ClassPathXmlApplicationContext("beans.xml");
		IProcess<HashMap<String, Object>> p = (IProcess<HashMap<String, Object>>) ct
				.getBean("testDynamicProcess");
		p.process(new HashMap<String, Object>());
	}
}
