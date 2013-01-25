package skt.tmall.common.model.process;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 서로 연관이 없는 프로세스의 집합이기 때문에 에러가나도 다음 프로세스를 진행한다.
 * 
 * @author leegt80
 * 
 */
public class UnAssociatedProcessGroup extends AbstractProcessGroup {

	private static Log log = LogFactory.getLog(UnAssociatedProcessGroup.class);

	@SuppressWarnings("unchecked")
	public void proceed(HashMap<String, Object> context)
			throws ProcessException {

		for (IProcess process : processList) {
			try {
				process.process(context);
			} catch (Exception e) {
				if (log.isFatalEnabled()) {
					log.fatal(e);
				}
			}
		}// end for
	}
}
