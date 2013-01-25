package skt.tmall.common.db;

/**
 * 데이터베이스 sequence를 의미한다. database에서 지원하는 sequence가 없으면 메모리 기반이나 파일 기반으로 구현이
 * 가능하다.
 * 
 * @author leegt80
 * 
 */
public interface IIncrementer {

	public String getNextVal(String incrementerName, int cipher);

	/**
	 * sequence 기반이면 create sequence string을 테이블 max 기반이면 max select 를 실행할 테이블
	 * 스크립트를 만들어 주면 된다.
	 * 
	 * @param incrementerName
	 * @param cipher
	 * @return
	 */
	public String createIncrementer(String incrementerName, int cipher);

	/**
	 * createIncrementer() 실행여부를 체크
	 * 
	 * @param incrementerName
	 *            TODO
	 * 
	 * @return
	 */
	public boolean checkExist(String incrementerName);
}
