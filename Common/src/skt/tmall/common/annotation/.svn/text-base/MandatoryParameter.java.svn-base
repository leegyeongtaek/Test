package skt.tmall.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * XXXDelegate에서 사용되며 parameters에 나열된 파라메터들은 필수 임을 나타낸다.
 * 강제성을 가진다.
 * @see com.yuraeltec.common.aop.WebRequestExtractAdvice
 * 
 * @author leegt80
 */
public @interface MandatoryParameter {
	
	/**
	 * @return
	 */
	String [] parameters();
}
