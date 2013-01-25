package skt.tmall.common.web.resource;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 리소스 처리기
 * 
 * TODO: servlet api와 독립적으로 만들 것.
 * 
 * @author $Id: IResourceProcessor.java,v 1.1 2007/04/06 11:02:05 dsjang Exp $
 */
public interface IResourceProcessor {

	/**
	 * 지정한 요청(request)를 참조하여 응답(response)에 처리된 결과를 출력한다.
	 * 
	 * @param request
	 *            서블릿 응답
	 * @param response
	 *            서블릿 요청
	 * @param cacheFile
	 *            캐시 파일. {@literal null}이면 캐시 파일을 사용하지 않고, 응답에 직접 출력한다.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	void process(HttpServletRequest request, HttpServletResponse response,
			File cacheFile) throws IOException, ServletException;

}
