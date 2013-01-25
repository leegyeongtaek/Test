/**
 * 
 */
package skt.tmall.common.web.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * 
 * 스프링 구현에 아~~주 종속적이다 갠적 스타일은 아니지만 .. 충분히 구현할 시간이 없었다.
 * 
 * 웹에서 파일 업로드는 multipart content type을 쓴다. 스프링에서 기본적으로 지원을 하고 있어 사용을 했고
 * uploadService에 위임하는 역활을 한다.
 * 
 * @author cshan
 * 
 */
public class UploadController extends SimpleFormController {

	private UploadService uploadService;
	private ProgressListenerContainer container;

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return "";
	}

	/**
	 * 
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mPart = (MultipartHttpServletRequest) request;
			String token = request.getParameter("token");
			uploadService.moveTemp(mPart, token);
			container.remove(token);
		}
		return new ModelAndView();
	}

	@Required
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@Required
	public void setProgressListenerContainer(ProgressListenerContainer container) {
		this.container = container;
	}
}
