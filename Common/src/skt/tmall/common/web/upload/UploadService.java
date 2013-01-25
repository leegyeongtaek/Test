/**
 * 
 */
package skt.tmall.common.web.upload;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * IRepository로 옮겨가는 중간 단계
 * 
 * @author cshan
 * 
 */
public class UploadService {

	private String tempPath;
	private static final String FILE = "Filedata";

	/**
	 * 
	 * @param mPartRequest
	 * @param token
	 * @throws IOException
	 * @throws Exception
	 */
	protected void moveTemp(MultipartHttpServletRequest mPartRequest,
			String token) throws IOException, Exception {
		MultipartFile f = mPartRequest.getFile(FILE);
		File file = new File(tempPath + File.separator + token);
		if (!file.exists()) {
			file.mkdir();
		}
		f.transferTo(new File(tempPath + File.separator + token, f
				.getOriginalFilename()));
	}

	protected File getTempFile(String token, String fileName) {
		return new File(tempPath + File.separator + token + File.separator
				+ fileName);
	}

	protected void deleteTempFile(String token, String file) {
		File f = new File(tempPath + File.separator + token, file);
		if (f.exists()) {
			f.delete();
		}
		File dir = new File(tempPath + File.separator + token);
		if (dir.exists()) {
			File[] listFiles = dir.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				dir.delete();
			}
		}
	}

	@Required
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
}
