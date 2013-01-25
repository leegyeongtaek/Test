package skt.tmall.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

/**
 * @author leegt80
 * 사용자 인증과 권한 RETRIEVE
 * 인증 DAO(JDBC DAO)
 */
public class JdbcDaoOverImpl extends JdbcDaoImpl {

	private GrantedAuthorityImpl authority = new GrantedAuthorityImpl("ROLE_GUEST");
	
	public JdbcDaoOverImpl() {
		super();
	}

	 /**
     * Extension point to allow other MappingSqlQuery objects to be substituted in a subclass
     */
	protected void initMappingSqlQueries() {
		this.usersByUsernameMapping = new UsersByUsernameMapping(
				getDataSource());
		this.authoritiesByUsernameMapping = new AuthoritiesByUsernameMapping(
				getDataSource());
	}

	/**
	 * Query object to look up a user. inner class
	 * (사용자 인증 정보)
	 */
	@SuppressWarnings("rawtypes")
	protected class UsersByUsernameMapping extends MappingSqlQuery {
		protected UsersByUsernameMapping(DataSource ds) {
			super(ds, getUsersByUsernameQuery());
			declareParameter(new SqlParameter(Types.VARCHAR));	// user id
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			String userId 	 = rs.getString(1);
			String password = rs.getString(2);

			UserDetails user =new User(userId, password, true, true, true, true, new GrantedAuthority[] { new GrantedAuthorityImpl("HOLDER") });

			return user;
		}
	}

	/**
	 * Query object to look up a user's authorities.
	 * (사용자 권한 정보)
	 */
	@SuppressWarnings("rawtypes")
	protected class AuthoritiesByUsernameMapping extends MappingSqlQuery {
		protected AuthoritiesByUsernameMapping(DataSource ds) {
			super(ds, getAuthoritiesByUsernameQuery());
			declareParameter(new SqlParameter(Types.VARCHAR));	// user id
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			String roleName = getRolePrefix() + rs.getString(2);
			GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);

			return authority;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.acegisecurity.userdetails.jdbc.JdbcDaoImpl#addCustomAuthorities(java.lang.String, java.util.List)
	 * 사용자가 권한 정보가 없을 경우 UsernameNotFoundException이 발생 하므로 임시 Guest 권한을 부여한다. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void addCustomAuthorities(String username, List authorities) {
		if (authorities.size() < 1) {
			authorities.add(authority);
		}
	}
}
