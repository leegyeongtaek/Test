package skt.tmall.common.model.process;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

/**
 * 프로세스의 순차적인 묶음을 의미한다.
 * 
 * 역시 IProcess이기 때문에 checkable을 지원한다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractProcessGroup implements IProcessGroup {

	protected List<IProcess<HashMap<String, Object>>> processList;
	
	/**
	 * rollback 을 위한 인터페이스 
	 */
	protected IRollBack<HashMap<String, Object>> rollback = new IRollBack<HashMap<String,Object>>() {

		public void rollBack(HashMap<String, Object> context) {}
		
	};

	private ICheckable<HashMap<String, Object>> checkable = new ICheckable<HashMap<String, Object>>() {

		public boolean check(HashMap<String, Object> context) {
			return true;
		}

	};
	
	/**
	 * 트랜젝션 프로세스에서 반복문을 필요로 할 경우 ILoop를 구현해 준다.
	 */
	private ILoop<HashMap<String, Object>> loop = new ILoop<HashMap<String,Object>>() {

		public int getLoopCount(HashMap<String, Object> context) {
			return 1;
		}
		
	};

	public ICheckable<HashMap<String, Object>> getCheckable() {
		return checkable;
	}
	
	public ILoop<HashMap<String, Object>> getLoop() {
		return loop;
	}
	
	public IRollBack<HashMap<String, Object>> getRollback() {
		return rollback;
	}

	public boolean isInit() {
		return true;
	}

	public abstract void proceed(HashMap<String, Object> context)
			throws ProcessException;

	public void process(HashMap<String, Object> context) throws ProcessException{
		if (checkable != null && !checkable.check(context)) {
			return;
		}
		
		int count = loop.getLoopCount(context);
		
		for (int i = 0; i < count; i++) {
			try {
				context.put(ILoop.LOOPCOUNT, i);
				proceed(context);
			} catch (ProcessException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void rollback(HashMap<String, Object> context) {
		rollback.rollBack(context);
	}
	
	public void setRollback(IRollBack<HashMap<String, Object>> rollback) {
		this.rollback = rollback;
	}

	public void setCheckable(ICheckable<HashMap<String, Object>> checkable) {
		this.checkable = checkable;
	}
	
	public void setLoop(ILoop<HashMap<String, Object>> loop) {
		this.loop = loop;
	}

	//
	// injected
	//
	@Required
	public void setProcessList(
			List<IProcess<HashMap<String, Object>>> processList) {
		this.processList = processList;
	}

}
