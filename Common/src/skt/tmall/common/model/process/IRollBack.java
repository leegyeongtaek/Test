package skt.tmall.common.model.process;


/**
 * @author leegt80
 *
 * @param <K>
 * 
 * TransactionalProcessGroup RollBack interface
 */
public interface IRollBack<K> {
	
	public void rollBack(K context);
	
}
