package skt.tmall.common.exception;

/**
 * 의도되지 않는 상황의 접근일때를 가리고 싶었다. 사실..머.. 그다지..별...
 * 
 * @author leegt80
 * 
 */
public class NeedParameterException extends IllegalAccessError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeedParameterException() {
		super();
	}

	public NeedParameterException(String msg) {
		super(msg);
	}
}
