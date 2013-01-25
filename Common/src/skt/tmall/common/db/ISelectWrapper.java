package skt.tmall.common.db;

/**
 * 동적으로 쿼리를 래핑하고 결과셋을 제한하는 목적으로 만들었다.
 * 
 * fetchabelTable.js가 client 구현이며
 * 
 * 서버에서는 WebRequestExtractAdvice를 통해 지원을 하고 있다.
 * 
 * @author leegt80
 * 
 */
public interface ISelectWrapper {

	public static final String ROWNUM = "ROW_NUMBER";

	/**
	 * 래핑된 sql을 리턴한다.
	 * 
	 * @param sql
	 * @return
	 */
	public String wrap(String sql);

	/**
	 * 래핑된 결과의 from ~ to 값을 리턴한다.
	 * 
	 * 오라클에선 rownum이 지원 되기 때문에 from, to가 가능하고
	 * 
	 * mysql에선 top이 지원 되기 때문에 from, to 를 리턴하지 않고 top과 갯수를 리턴해 구현 할 수도 있겠네..?
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Object[] getWrappingParams(Integer from, Integer to)
			throws IllegalArgumentException;

	public void setDefaultWrappingSize(int size);

	public int getDefaultWrappingSize();
}
