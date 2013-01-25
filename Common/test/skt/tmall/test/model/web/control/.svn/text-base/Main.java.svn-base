package skt.tmall.test.model.web.control;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ct = new ClassPathXmlApplicationContext("beans.xml");
		MainDelegate md = (MainDelegate) ct.getBean("testDelegate");
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("context", ct);
		md.main(null, null, hashMap);
	}

}
