package skt.tmall.common.util;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author lllkt
 * 접속자 관리 클래스
 * HttpSessionBindingListener 는 웹에서 동시 사용자의 수 또는 하나의 아이디로 동시접속을 제한 할때 유용한 인터페이스 이다.  HttpSessionBindingListener 는 두개의 메소드를 지니는데 valueBound() 와 valueUnbound() 메소드 이다. 
 
	valueBound() 는 HttpSessionBindingListener 클래스의 인스턴스가 세션에 attribute로
	등록될떄 호출된다  session.setAttribute(플래그, 값)
	valueUnbound()는 session.removeAttribute(플래그); 사용시 
	또는 세션종료시  session.invalidate()호출된다.
 */
public class LoginManager implements HttpSessionBindingListener{
	
	public static final String USER_IP = "userIP";

	private Log log = LogFactory.getLog(LoginManager.class);
	
	//로그인 접속자를 담기 위한 해시 테이블
	@SuppressWarnings("unused")
	private static Hashtable<HttpSession, String> loginUsers = new Hashtable<HttpSession, String>();
	
	//로그인 사용자 세션을 담기 위한 해시 테이블
	private static Hashtable<String, HttpSession> loginSessions = new Hashtable<String, HttpSession>();
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet.http.HttpSessionBindingEvent)
	 * 세션이 연결 되었을 때 호출(session.setAttribute("loginUserID", this))
	 * Hashtable에 세션과 접속자 아이디를 저장
	 */
	public void valueBound(HttpSessionBindingEvent event) {
		loginUsers.put(event.getSession(), event.getName());
		loginSessions.put(event.getName(), event.getSession());
		
		if (log.isDebugEnabled()) {
			log.debug(event.getName()+"님이 로그인 하셨습니다. 현재 접속자 수: " + getUserCount());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)
	 * 세션이 끊겼을 때 호출(invalidate)
	 * Hashtable에 저장되어 있는 로그인 정보를 제거
	 */
	public void valueUnbound(HttpSessionBindingEvent event) {
		loginUsers.remove(event.getSession());
		loginSessions.remove(event.getName());
		
		if (log.isDebugEnabled()) {
			log.debug(event.getName()+"님이 로그아웃 하셨습니다. 현재 접속자 수: " + getUserCount());
		}
	}
	
	/*
	 * 입력받은 아이디를 해시 테이블에서 찾아 이를 삭제.
	 * @param userID 사용자 아이디
	 * @return void
	 */
	public void removeSession(String userId) {
		Enumeration<HttpSession> e = loginUsers.keys();
		
		@SuppressWarnings("unused")
		HttpSession session = null;
		
		while (e.hasMoreElements()) {
			session = (HttpSession) e.nextElement();
			
			//Hashtable에서 해당 세션 키에 해당하는 userID가 존재 하면 세션을 끊어 valueUnbound 메소드를 호출 한다.
			if (loginUsers.get(session).equals(userId)) {
				session.invalidate();
			}
		}
	}
	
	/*
	 * 해당 아이디의 동시 사용을 막기 위해서 이미 사용중인 아이디인지 확인
	 * @param userId 사용자 아이디
	 * @return boolean 이미 사용중인 경우 true, 사용중이 아니면 false
	 */
	public boolean isUsing(String userId) {
		return loginUsers.containsValue(userId);
	}
	
	/*
	 * 로그인 인증을 완료한 사용자의 아이디를 해시 테이블에 저장하는 메소드
	 * @param session 세션 객체
	 * @param userId 사용자 아이디
	 */
	public void setSession(HttpSession session, String userId) {
		//name값으로 userId, value값으로 HttpSessionBindingListener를 구현하는 Object인 자기 자신을 넣는다.
		session.setAttribute(userId, this);
	}
	
	/*
	 * 입력받은 세션 Object로 아이디를 리턴
	 * @param session 접속한 사용자의 session Object
	 * @return String 사용자 아이디
	 */
	public String getUserID(HttpSession session) {
		return (String) loginUsers.get(session);
	}
	
	/*
	 * 사용자 접속 아이디로 사용자 IP 주소를 리턴
	 */
	public String getUserIP(Object userID) {
		HttpSession s = (HttpSession) loginSessions.get(userID);
		
		return (String) s.getAttribute(USER_IP);
	}
	
	/*
	 * 현재 접속한 총 사용자 수
	 * @return int 현재 접속자 수
	 */
	public int getUserCount() {
		return loginUsers.size();
	}
	
}
