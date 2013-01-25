package skt.tmall.common.web.resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 웹 루트 밖에 있는 리소스를 추가적인 처리 후 출력하기 위한 컨트롤러.
 * 
 * 매핑된 처리기를 통해 변형된 결과를 출력한다.
 * 
 * 매핑된 처리기가 없지만, 기본 리소스 디렉토리에 해당 리소스가 있을 경우 기본 처리기를 만든다.
 * 
 * @see IResourceProcessor
 * @author $Id: ResourceController.java,v 1.2 2007/04/07 06:46:20 dsjang Exp $
 */
public class ResourceController extends AbstractController {

	private static final Log LOG = LogFactory.getLog(ResourceController.class);

	/**
	 * {@literal java.io.tmpdir} 시스템 프로퍼티에 지정된 시스템 임시 디렉토리.
	 */
	private static final File DEF_CACHE_DIR = new File(System
			.getProperty("java.io.tmpdir"));

	/**
	 * 기본으로 자동 처리기 매핑은 사용하지 않는다.
	 */
	private static final File DEF_BASE_DIR = null;

	/**
	 * 처리기 이름과 처리기 객체 간의 맵핑.
	 * 
	 * 처리기 이름은 요청의 {@link HttpServletRequest#getPathInfo()} 값이다.
	 * 
	 * ex) {@literal /res/*} 이 이 컨트롤러로 매핑되어 있고,
	 * {@literal http://.../res/test.js} 요청이 들어오면, {@literal /test.js}가 처리기
	 * 이름이다.
	 */
	private Map<String, IResourceProcessor> mappings;

	/**
	 * 자동 처리기 매핑에 사용할 기본 리소스 디렉토리.
	 */
	private File baseDir = DEF_BASE_DIR;

	/**
	 * 캐시 파일들을 저장할 임시 디렉토리.
	 */
	private File cacheDir = DEF_CACHE_DIR;

	/**
	 * 캐쉬 자동 삭제
	 */
	private boolean refresh = false;

	//
	// @Inject
	//

	/**
	 * 처리기 이름과 처리기 객체 간의 맵핑을 지정한다.
	 * 
	 * @param mappings
	 *            처리기 이름(path info)와 처리기 객체 간의 매핑
	 * @see HttpServletRequest#getPathInfo()
	 */
	@Required
	public void setMappings(Map<String, IResourceProcessor> mappings) {
		this.mappings = mappings;
	}

	/**
	 * 매핑이 안된 처리기의 리소스(자바스크립트)를 찾을 디렉토리를 지정한다.
	 * 
	 * @param baseDir
	 *            디렉토리
	 */
	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	//
	// @inject
	//
	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	/**
	 * 캐시 파일들을 저장할 임시 디렉토리를 지정한다.
	 * 
	 * @param cacheDir
	 *            캐시 파일들을 저장할 임시 디렉토리. {@literal null}이면 캐시 파일을 보관하지 않는다.
	 */
	public void setCacheDir(File cacheDir) {
		if (cacheDir != null && !cacheDir.exists()) {
			try {
				FileUtils.forceMkdir(cacheDir);
			} catch (IOException ioex) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("failed to create cacheDir: " + cacheDir);
				}
				cacheDir = null;
			}
		}
		this.cacheDir = cacheDir;
	}

	//
	//
	//

	/**
	 * NOTE: (개발의 편의를 위해) {@literal refresh} 요청 파라메터를 지정하면 캐시 파일을 갱신할 수 있다.
	 * 
	 * @param request
	 *            서블릿 응답
	 * @param response
	 *            서블릿 요청
	 * @return 항상 {@literal null}
	 * @throws IOException
	 * @throws ServletException
	 * @see IResourceProcessor#process(HttpServletRequest, HttpServletResponse,
	 *      File)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// extract processor name from request
		// ex) http://.../write/res/main.js -> /main.js
		String name = request.getPathInfo();

		// lookup processor
		IResourceProcessor processor = getProcessor(name);
		if (processor == null) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("processor not found: name=" + name);
			}
			response.sendError(HttpServletResponse.SC_NOT_FOUND, name);
			return null;
		}

		File cacheFile = getCacheFile(name);

		// force to refresh the cacheFile (강제로 캐쉬파일 refresh)
		if ((cacheFile != null && cacheFile.exists())		//캐쉬 파일이 존재 하고 
				&& (ServletRequestUtils.getBooleanParameter(request, "refresh",	//파라미터에 ?refresh=true이고 default는 false
						false) || this.refresh)) {					//setter의 boolean값이 true이면 강제 refresh
			if (LOG.isTraceEnabled()) {
				LOG.trace("force to refresh the cacheFile: " + cacheFile);
			}
			cacheFile.delete();
		}
		processor.process(request, response, cacheFile);

		return null;
	}

	//
	//
	//

	/**
	 * 
	 * @param name
	 * @return
	 */
	private IResourceProcessor getProcessor(String name) {
		IResourceProcessor processor = (mappings != null) ? mappings.get(name)
				: null;
		if (processor != null) {
			return processor;
		}
		if (baseDir != null && baseDir.exists()) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("create default processor: name=" + name);
			}
			File resourceFile = new File(baseDir, name);
			if (resourceFile.exists() && resourceFile.canRead()) {
				Resource resource = new FileSystemResource(resourceFile);
				// TODO: make this to dynamic
				if (name.endsWith(".js")) {
					processor = new JsProcessor(resource);
				} else if (name.endsWith(".css")) {
					processor = new CssProcessor(resource);
				} else {
					processor = new DefaultProcessor(resource);
				}
				// add mapping for later use
				if (mappings == null) {
					mappings = new HashMap<String, IResourceProcessor>();
				}
				mappings.put(name, processor);
			}
		}
		return processor;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private File getCacheFile(String name) {
		return (cacheDir != null) ? new File(cacheDir, name) : null;
	}

}
