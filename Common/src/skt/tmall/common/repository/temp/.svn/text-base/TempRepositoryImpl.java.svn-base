package skt.tmall.common.repository.temp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Required;

import skt.tmall.common.repository.IHandle;
import skt.tmall.common.repository.IRepository;
import skt.tmall.common.repository.IToken;
import skt.tmall.common.repository.RepositoryException;

/**
 * 임시 Repository implementation
 * 
 * @author cshan
 * 
 */
public class TempRepositoryImpl implements IRepository {

	private String stageDir;

	public IToken checkin(IHandle handle, Object param) throws RepositoryException {
		File file = handle.getFile();
		IToken token = new TempToken();
		new File(stageDir + File.separator + token.getID()).mkdir();
		try {
			FileUtils.copyFile(file, new File(stageDir + File.separator
					+ token.getID() + File.separator + file.getName()));
			return token;
		} catch (IOException e) {
			throw new RepositoryException(e);
		}
	}

	public IHandle checkout(IToken token, Object param) throws RepositoryException {
		String id = token.getID();
		File dir = new File(stageDir + File.separator + id);
		File f = null;
		try {
			f = dir.listFiles()[0];
			return new TempHandle(f);
		} catch (Exception e) {
			throw new RepositoryException(token.getID() + " is null");
		}
	}

	public void delete(IToken token, Object param) throws RepositoryException {
		String id = token.getID();
		File dir = new File(stageDir + File.separator + id);
		File f = null;
		try {
			f = dir.listFiles()[0];
			f.delete();
			dir.delete();
		} catch (Exception e) {
			throw new RepositoryException(token.getID() + " is null");
		}
	}

	public void update(IToken token, IHandle handle, Object param) throws RepositoryException {
		delete(token, null);
		checkin(handle, null);
	}

	@Required
	public void setStageDir(String stageDir) {
		this.stageDir = stageDir;
	}
}
