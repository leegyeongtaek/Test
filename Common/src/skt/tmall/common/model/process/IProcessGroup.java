package skt.tmall.common.model.process;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author leegt80
 * 
 */
public interface IProcessGroup extends
		IProcess<HashMap<String, Object>> {

	public void setProcessList(List<IProcess<HashMap<String,Object>>> processList);

}
