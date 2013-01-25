package skt.tmall.common.exception;

/**
 * 쓰지 못하게 했는데 꼭 불거 쓰는 사람들..deprecated 정도로는 성이 안찬다. 호출하면 그냥 예외를 던져버리자..
 * 
 * annotation을 지원할라다가..쩝..
 * 
 * @author leegt80
 * 
 */
public class NotSupportMethodException extends IllegalAccessError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSupportMethodException() {
		super();
	}

	public NotSupportMethodException(String msg) {
		super(msg);
	}
}
