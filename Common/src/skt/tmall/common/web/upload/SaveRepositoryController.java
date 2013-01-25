/**
 * 
 */
package skt.tmall.common.web.upload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import skt.tmall.common.repository.IRepository;
import skt.tmall.common.repository.IToken;
import skt.tmall.common.repository.temp.TempHandle;

/**
 * 웹에서 업로드 절차는 임시 디렉토리에 파일을 저장한 후 실제 저장소로 옮기는 순서로 정했다.
 * 
 * 업로드된 파일이 반드시 시스템에 필요하지 않기 때문이다.
 * 
 * 위에서 말한 절차를 구현 하고 있다.
 * 
 * @author cshan
 * 
 */
public class SaveRepositoryController implements Controller {

	private static final String FILE_NAME = "fileName";

	private static final String TOKEN = "token";

	private IRepository repository;

	private UploadService uploadService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String token = request.getParameter(TOKEN);
		String fileName = request.getParameter(FILE_NAME);
		File tempFile = uploadService.getTempFile(token, fileName);
		TempHandle tempHandle = new TempHandle(tempFile);
		IToken checkin = repository.checkin(tempHandle, null);
		uploadService.deleteTempFile(token, fileName);
		ModelAndView mv = new ModelAndView();
		String id = checkin.getID();
		mv.addObject(TOKEN, id);
		return mv;
	}

	@Required
	public void setRepository(IRepository repository) {
		this.repository = repository;
	}

	@Required
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}
}
