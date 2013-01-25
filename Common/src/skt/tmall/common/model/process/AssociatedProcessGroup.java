package skt.tmall.common.model.process;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 서로 연관이 있는 프로세스들의 모음이므로 에러가 발생하면 진행을 하지 않는다.
 * 
 * 주의 Database의 Transaction은 보장하지 않는다.
 * 
 * @author leegt80
 * 
 */
public class AssociatedProcessGroup extends AbstractProcessGroup {

	private static Log log = LogFactory.getLog(AssociatedProcessGroup.class);

	@SuppressWarnings("unchecked")
	@Override
	public void proceed(HashMap<String, Object> context)
			throws ProcessException {
		try {
			for (IProcess process : processList) {
				process.process(context);
			}
		} catch (Exception e) {
			if (log.isFatalEnabled()) {
				log.fatal(e);
			}
		}
	}

}
