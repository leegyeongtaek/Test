/**
 * 
 */
package skt.tmall.common.model.process.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.repository.IHandle;
import skt.tmall.common.repository.IRepository;
import skt.tmall.common.repository.IToken;
import skt.tmall.common.repository.RepositoryException;

/**
 * 외부 저장소를 접근하기 위한 추상화이다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractRepositoryProcess implements
		IProcess<HashMap<String, Object>> {

	private IRepository repository;

	public boolean isInit() {
		return repository != null;
	}

	public final void process(HashMap<String, Object> context)
			throws ProcessException {
		if (getCheckable() != null && getCheckable().check(context)) {
			Object object = context.get(IRepositoryConstants.REPOSITORY_TOKEN);
			try {
				if (object instanceof String) {
					//
					// TODO
					//
					// TempToken token = new TempToken((String) object);
					IToken token = getToken(object);
					IHandle handle = repository.checkout(token, context
							.get(ICommonConstants.REPOSITROY_CONTEXT));
					context.put(IRepositoryConstants.REPOSITORY_HANDLE, handle);
				} else if (object instanceof String[]) {
					String[] tokens = (String[]) object;
					List<IHandle> list = new ArrayList<IHandle>(tokens.length);
					for (String string : tokens) {
						//
						// TODO
						//
						// TempToken token = new TempToken(string);
						IToken token = getToken(string);
						list.add(repository.checkout(token, context));
					}
					context.put(IRepositoryConstants.REPOSITORY_HANDLE, list);
				}
			} catch (RepositoryException re) {
				throw new ProcessException(re);
			}
		}
	}

	protected abstract IToken getToken(Object object);

	//
	// @dynamic inject
	//
	public void setRepository(IRepository repository) {
		this.repository = repository;
	}
}
