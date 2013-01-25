package skt.tmall.test.model.web.control;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;

public class MainDelegate {

	// @Delegate(webParameters = { "param1", "param2" }, processName = "",
	// processReferenceKey="")
	@SuppressWarnings("unchecked")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> params) {
		ApplicationContext ac = (ApplicationContext) params.get("context");
		IProcess p = (IProcess)ac.getBean("testProcess");
		try {
			p.process(params);
		} catch (ProcessException e) {
			e.printStackTrace();
		}
		return new ModelAndView();
	}
}
