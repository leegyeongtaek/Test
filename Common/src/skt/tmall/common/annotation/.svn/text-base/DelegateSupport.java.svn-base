package skt.tmall.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Httprequest의 파라메터 gethering과 event에 연결된 IProcess start point를 찾아주는 annotation
 * 
 * @see skt.tmall.common.aop.WebRequestExtractAdvice
 * @author leegt80
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DelegateSupport {
	/**
	 * HttpServletRequest에서 값을 뽑기 위한 파라메터 리스트
	 * 
	 * @see skt.tmall.common.aop.WebRequestExtractAdvice
	 * @return parased values
	 */
	String[] webParameters();

	/**
	 * IProcess 를 하나 찾아 준다.
	 * 
	 * 현재는 spring 기반이기 때문에 bean configuration에 있는 bean 중 매치되는 bean을 찾아 리턴한다.
	 * 
	 * @see skt.tmall.common.aop.WebRequestExtractAdvice
	 * @return
	 */
	String processName();

	/**
	 * The alias name to reference process instance.
	 * 
	 * ex) IProcess p = (IProcess)HashMap.get("alias");
	 * 
	 * @see skt.tmall.common.aop.WebRequestExtractAdvice
	 * @return
	 */
	String processReferenceKey();
}
