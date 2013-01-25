/**
 * 
 */
package skt.tmall.common.web.upload;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.repository.IRepository;
import skt.tmall.common.repository.IToken;
import skt.tmall.common.repository.temp.TempHandle;
import skt.tmall.common.web.upload.UploadService;

/**
 * 웹에서 업로드 절차는 임시 디렉토리에 파일을 저장한 후 실제 저장소로 옮기는 순서로 정했다.
 * 
 * 업로드된 파일이 반드시 시스템에 필요하지 않기 때문이다.
 * 
 * 위에서 말한 절차를 구현 하고 있다.
 * 
 * @author lllkt
 * 
 */
public class AutoCadSaveRepositoryController implements Controller {

	public static final String FILE_NAME = "fileName";

	public static final String TOKEN = "token";
	
	public static final String REGEDIT = "regedit";

	private IRepository repository;

	private UploadService uploadService;
	
	private IProcess<HashMap<String,Object>> DrawingProcessGroup;
	
	private Vector<Object> params;

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id = "";
		
		HashMap<String, Object> context = new HashMap<String, Object>();
		
		String token 	= request.getParameter(TOKEN);
		String fileName = request.getParameter(FILE_NAME);
		
		context.put(TOKEN, token);
		context.put(FILE_NAME, fileName);
		
		for (Enumeration<Object> e = params.elements(); e.hasMoreElements();){
			String paramName = (String)e.nextElement();
			String[] parameterValues = request.getParameterValues(paramName);
			if (parameterValues != null) {
				context.put(paramName, parameterValues.length > 1 ? parameterValues : parameterValues[0]);
			}
		}
		
		DrawingProcessGroup.process(context);
		
		//UploadCompareCheckable 에서 regedit이 true일때 수행
		if(((Boolean)context.get("_regedit")).booleanValue()){
			File tempFile = uploadService.getTempFile((String)context.get(TOKEN), (String)context.get(FILE_NAME));
			TempHandle tempHandle = new TempHandle(tempFile);
			IToken checkin = repository.checkin(tempHandle, context);
			uploadService.deleteTempFile((String)context.get(TOKEN), (String)context.get(FILE_NAME));
			id = checkin.getID();
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject(TOKEN, id);
		mv.addObject(FILE_NAME, context.get(FILE_NAME));
		mv.addObject(REGEDIT, context.get("_regedit"));
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
	
	public void setDrawingProcessGroup(IProcess<HashMap<String,Object>> DrawingProcessGroup){
		this.DrawingProcessGroup = DrawingProcessGroup;
	}
	
	//파라미터 injection
	public void setParameterList(Vector<Object> params){
		this.params = params;
	}
}
