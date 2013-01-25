/**
 * 
 */
package skt.tmall.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;

/**
 * @author leegt80
 * 
 */
public class DelegateUtils {

	/**
	 * 기본 조회 프로세스 처리..
	 * 
	 * @param context
	 * @param processReference
	 * @return
	 * @throws ProcessException
	 */
	@SuppressWarnings("unchecked")
	public static ModelAndView defaultSelectProcess(
			HashMap<String, Object> context, String processReference)
			throws ProcessException {
		// null 객체 처리
		context.put(ICommonConstants.PROCESS_RESULT, new ArrayList<Map<String, Object>>());
		
		IProcess<HashMap<String, Object>> process = (IProcess<HashMap<String, Object>>) context
				.get(processReference);
		process.process(context);
		Object result = context
				.get(ICommonConstants.PROCESS_RESULT);
		ModelAndView mv = new ModelAndView();
		mv.addObject(ICommonConstants.PROCESS_RESULT, result);
		return mv;
	}
	
	/**
	 * 기본 DML 프로세스 처리..
	 * 
	 * @param context
	 * @param processReference
	 * @return
	 * @throws ProcessException
	 */
	@SuppressWarnings("unchecked")
	public static ModelAndView defaultModifyProcess(
			HashMap<String, Object> context, String processReference)
	throws ProcessException {
		// null 객체 처리
		context.put(ICommonConstants.PROCESS_RESULT, 0);
		
		IProcess<HashMap<String, Object>> process = (IProcess<HashMap<String, Object>>) context
		.get(processReference);
		process.process(context);
		Integer modify = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		String result = (modify >= 0) ? "true" : "false";
		
		if (context.get("error") != null) result = "false";
		
		ModelAndView mv = new ModelAndView();
		mv.addObject(ICommonConstants.PROCESS_RESULT, result);
		return mv;
	}
}
