/**
 * 
 */
package skt.tmall.common.model.process.db;

import java.util.HashMap;

import org.springframework.jdbc.core.JdbcTemplate;

import skt.tmall.common.db.JdbcTemplateManager;
import skt.tmall.common.db.SqlProvider;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.IProcess;
import skt.tmall.common.model.process.IRollBack;
import skt.tmall.common.model.process.ProcessException;

/**
 * 데이터 베이스 서비스의 가장 최상위 추상화이다. alias로 sql을 분배하고 프로세스의 전후로 context를 mapping 할 수 있게
 * 지원한다.
 * 
 * @author leegt80
 * 
 */
public abstract class AbstractDatabaseProcess implements
		IProcess<HashMap<String, Object>> {

	/**
	 * 여러 datasource를 지원하지만 매번 datasource를 기술하는 불편함을 덜고자 했다.
	 */
	private String databaseAlias = "default";

	/**
	 * 
	 */
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	private SqlProvider sqlProvider;
	
	/**
	 * rollback 을 위한 인터페이스 
	 */
	protected IRollBack<HashMap<String, Object>> rollback = new IRollBack<HashMap<String,Object>>() {

		public void rollBack(HashMap<String, Object> context) {}
		
	};

	/**
	 * 프로세스의 실행 여부를 결정한다. 물른 실행됨을 기본 조건으로 한다.
	 * 
	 * projected로 접근자를 바꿨다. 최초 ICheckable을 구현한 클래스를 configuration에서 injection 시켜
	 * 쓰게 하기 위함 이었는데..
	 * 
	 * 불편하다고 해서..그냥 풀었다. 덕분에 코딩으로 ICheckable을 클래스 내부에서 박아 쓸 수 있게 되었고 .. bean 설정
	 * 파일에서 프로세스의 흐름을 보겠다는 의도는 물거품이 되었다...
	 * 
	 */
	protected ICheckable<HashMap<String, Object>> checkable = new ICheckable<HashMap<String, Object>>() {

		public boolean check(HashMap<String, Object> context) {
			return true;
		}

	};
	
	public IRollBack<HashMap<String, Object>> getRollback() {
		return rollback;
	}

	public ICheckable<HashMap<String, Object>> getCheckable() {
		return checkable;
	}

	public String getDatabaseAlias() {
		return databaseAlias;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * SQL에 바인딩 값 배열을 리턴한다.
	 * 
	 * @param context
	 * @return
	 */
	public abstract Object getParameters(HashMap<String, Object> context);

	protected SqlProvider getSqlProvider() {
		return sqlProvider;
	}

	/**
	 * proceed 실행전 값을 가로채기 위해..
	 * 
	 * sql wrapping이나 기타 자동화를 지원 할 수 있다.
	 * (hook Method)
	 * @param context
	 */
	protected abstract void hook(HashMap<String, Object> context);

	/**
	 * 그다지 필요 없지만 개인적 취향이다..
	 */
	public boolean isInit() {
		return jdbcTemplate != null && sqlProvider != null;
	}

	/**
	 * main 메소드라고 할 수 있겠네...
	 * 
	 * @param context
	 * @throws ProcessException 
	 */
	public abstract void proceed(HashMap<String, Object> context) throws ProcessException;

	/**
	 * checkable이 false이더라고 logging을 하지 않는다. 왜?? ICheckable에서 알아서 로깅 하라는 뜻이다..
	 * 
	 * (Template Method)
	 */
	public final void process(HashMap<String, Object> context)
			throws ProcessException {
		hook(context);
		if (checkable != null && checkable.check(context)) {
			processBefore(context);
			proceed(context);
			processAfter(context);
		}
	}

	/**
	 * 이거이거 위험하다.. 오사용의 소지가 다분하다..
	 * 
	 * 의도는 proceed되기전 context의 매핑에 사용한다.
	 * 
	 * 즉 Process를 쓰기위해선 context 키 값이 고정 되어 있어야 하는데 프로세스의 나열 순서에 따라 선행된 context에서
	 * 값의 키 값들이 다를 수도 있을 것이다.
	 * 
	 * 그러면 여기에서 각 선행된 process 타입따라 context 키값을 매핑 시킬 수 도 있을 것이다. 하지만 그러면 특정
	 * 프로세스에 종속된 경우가 되기 때문에 오버라이딩 해서 processAfter를 바꿔 주는게 맞다고 본다.
	 * 
	 * @see skt.tmall.project.model.process.db.template.CreateWorkflowCategoryActivityDependency
	 *      skt.tmall.project.model.process.db.template.CreateWorkflowActivityDependency
	 *      skt.tmall.project.model.process.db.insert.template.ProjectTemplateInsertTest
	 * @param context
	 */
	protected void processAfter(HashMap<String, Object> context) {
	}

	/**
	 * @see processAfter
	 * @param context
	 */
	protected void processBefore(HashMap<String, Object> context) {
	}
	
	public final void rollback(HashMap<String, Object> context) {
		rollback.rollBack(context);
	}
	
	public void setRollback(IRollBack<HashMap<String, Object>> rollback) {
		this.rollback = rollback;
	}

	public void setCheckable(ICheckable<HashMap<String, Object>> checkable) {
		this.checkable = checkable;
	}

	//
	// @injected
	//
	public void setDatabaseAlias(String databaseAlias) {
		this.databaseAlias = databaseAlias;
	}

	//
	// @dynamic inject
	//
	public void setJdbcTemplate(JdbcTemplateManager jdbcTemplateManager) {
		this.jdbcTemplate = jdbcTemplateManager.getTemplate(getDatabaseAlias());
	}

	//
	// @dynamic inject
	//
	public void setSqlProvider(SqlProvider sqlProvider) {
		this.sqlProvider = sqlProvider;
	}

}
