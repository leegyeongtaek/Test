package skt.tmall.common.model.process;

import java.util.HashMap;

public interface IProcess<K extends HashMap<String, Object>> {

	/**
	 * process method의 진행여부 결정..
	 * 
	 * @return
	 */
	public ICheckable<K> getCheckable();

	/**
	 * 준비됬나요? -> process의 check는 아니고 초기화의 체크임!!
	 * 
	 * @return
	 */
	public boolean isInit();

	public void process(K context) throws ProcessException;

	public void setCheckable(ICheckable<K> checkable);
	
	public void rollback(K context);

}
