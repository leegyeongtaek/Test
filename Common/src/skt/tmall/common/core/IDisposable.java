package skt.tmall.common.core;

/**
 * 객체 소멸시 강제적으로 null을 주어 가비지컬렉션이 정상적으로 동작 할 수 있게 지원한다.
 * 
 * @author leegt80
 * 
 */
public interface IDisposable {

	public void dispose();

}
