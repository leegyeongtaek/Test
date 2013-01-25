package skt.tmall.common.model.process;

/**
 * @author leegt80
 *
 * @param <K>
 * 
 * 트랜젝션 프로세스 그룹에서 LOOP를 사용하기 위한 인터페이스
 * AstractProcessGroup 참조.
 */
public interface ILoop<K> {
	
	// context에서 loop count를 담기 위해 사용되는 key
	final String LOOPCOUNT = "LOOP_CURRENT_COUNT";
	
	// loop의 count 갯수를 구현한다.
	public int getLoopCount(K context);
	
}
