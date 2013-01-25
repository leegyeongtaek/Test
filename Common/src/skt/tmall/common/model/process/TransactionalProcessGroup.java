package skt.tmall.common.model.process;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import skt.tmall.common.db.TransactionTemplateManager;

/**
 * 서로 연관이 있는 프로세스들의 모음이므로 에러가 발생하면 진행을 하지 않는다. 그리고 database transaction도 보장한다.
 * 프로그래밍적 트랜잭션.
 * 트랜젝션의 범위에 대한 정확한 제어는 이뤄지지만, 스프링에 특정 클래스가 개입되어서
 * @author leegt80
 * 
 */
public class TransactionalProcessGroup extends AssociatedProcessGroup {

	class Transaction extends TransactionCallbackWithoutResult {

		private HashMap<String, Object> context;

		public Transaction(HashMap<String, Object> context) {
			this.context = context;
		}

		/* (non-Javadoc)
		 * @see org.springframework.transaction.support.TransactionCallbackWithoutResult#doInTransactionWithoutResult(org.springframework.transaction.TransactionStatus)
		 * return값이 존재하지 않는 트랜잭션이므로 doInTransaction을 쓰지 않고 doInTransactionWithoutResult를 사용함.
		 */
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
			try {
				for (IProcess<HashMap<String,Object>> process : processList) {
					process.process(context);
				}
			} catch (Exception e) {
				if (log.isFatalEnabled()) {
					log.fatal(e);
				}
				status.setRollbackOnly();
				
				// RollBack 프로세스 처리
				for (IProcess<HashMap<String,Object>> process : processList) {
					process.rollback(context);
				}
				
				context.put(ERROR, e);
			}
		}
	}

	private static final String ERROR = "error";

	private static Log log = LogFactory.getLog(TransactionalProcessGroup.class);

	private TransactionTemplate transactionTemplate;

	private String transactionAlias = "default";

	public String getTransactionAlias() {
		return transactionAlias;
	}

	@Override
	public void proceed(HashMap<String, Object> context) throws ProcessException {
		transactionTemplate.execute(new Transaction(context));
		Exception error = (Exception) context.get(ERROR);
		if (error != null) {
//			context.remove(ERROR);
			throw new ProcessException(error);
		}
	}
	
	/*@Override
	public void proceed(HashMap<String, Object> context)
			throws ProcessException {
		try {
			for (IProcess<HashMap<String,Object>> process : processList) {
				process.process(context);
			}
		} catch (Exception e) {
			if (log.isFatalEnabled()) {
				log.fatal(e);
			}
			context.put(ERROR, e);
		} finally{
			Exception error = (Exception) context.get(ERROR);
			if (error != null) {
				context.remove(ERROR);
				throw new ProcessException(error);
			}
		}
	}*/

	public void setTransactionAlias(String transactionAlias) {
		this.transactionAlias = transactionAlias;
	}

	//
	// @daynamic inject
	//
	public void setTransactionTemplate(
			TransactionTemplateManager transactionTemplateManager) {
		this.transactionTemplate = transactionTemplateManager
				.getTransactionTemplate(transactionAlias);
	}

	@Override
	public boolean isInit() {
		return super.isInit() && transactionTemplate != null;
	}

}
