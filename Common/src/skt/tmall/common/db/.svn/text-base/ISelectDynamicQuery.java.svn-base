package skt.tmall.common.db;

/**
 * @author leegt80
 * query의 where절을 동적으로 생성.
 */
public interface ISelectDynamicQuery {
	/**
	 * dynamic query된 sql을 리턴한다.
	 * 
	 * @param sql
	 * @return
	 */
	public String dynamic(String sql);
	
	/**
	 * Form 결과의 textInput param 값을 리턴한다.
	 * 
	 * @param context
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Object[] getDynamicParams(String[] params)
			throws IllegalArgumentException;
	
	public int[] getDynamicTypes();
}
