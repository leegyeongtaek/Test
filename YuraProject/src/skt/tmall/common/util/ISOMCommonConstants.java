/**
 *
 */
package skt.tmall.common.util;

/**
 * SOM 전체 공통으로 쓰이는 상수 모음
 *
 *  @author leegt80
 *
 */
public interface ISOMCommonConstants {

	/**
	 * 인증 SMS 발송 전화번호
	 */
	public static final String SEND_TO_SMS_MESSAGE_PHONE = "15990110";

	/**
	 * 인증 API 응답 데이터 KEY
	 */
	public static final String RESULT_CODE		= "RESULT_CODE";	// 인증 API 응답 코드
	public static final String RESULT_MSG		= "RESULT_MSG";	// 인증 API 상세 응답 메세지
	public static final String RESULT_DATA		= "RESULT_DATA";	// 인증 API 응답 데이터

	/**
	 * 인증 API 응답 코드
	 */
	public static final String SUCCESS 					= "100";	// 응답 성공 (RESULT_CODE)
	public static final String SUCCESS_NO_SEARCH_DATA 	= "101";	// 응답 성공(조회 결과 0건) (RESULT_CODE)				
	public static final String FAIL 					= "200";	// 응답 실패 (RESULT_CODE)			
	public static final String FAIL_NOTSET_CERTNO		= "201";	// 응답 실패(인증번호 미설정) (RESULT_CODE)
	public static final String ERROR					= "300";	// 시스템 오류 (RESULT_CODE)

	/**
	 * 프로세스 실행이 성공일 경우
	 *
	 * @author leegt80
	 */
	public static final String PROCESS_RESULT_SUCCESS = "SUCCESS";

	/**
	 * 프로세스 실행이 실패일 경우
	 *
	 * @author leegt80
	 */
	public static final String PROCESS_RESULT_FAIL = "FAIL";

	/**
	 * 프로세스 실행이 에러일 경우
	 *
	 * @author leegt80
	 */
	public static final String PROCESS_RESULT_ERROR = "ERROR";

	/**
	 * 인증상태
	 * @author leegt80
	 */
	public static final String CERT_STATUS_100 = "100";	// 주문가능
	public static final String CERT_STATUS_101 = "101";	// 미인증
	public static final String CERT_STATUS_102 = "102";	// 인증완료
	public static final String CERT_STATUS_103 = "103";	// 인증취소
	public static final String CERT_STATUS_104 = "104";	// 인증불가(반품완료)
	public static final String CERT_STATUS_105 = "105";	// 인증중지

	/**
	 * 인증 SMS 상태
	 * @author leegt80
	 */
	public static final String CERT_SMS_100 = "100";	// 미전송
	public static final String CERT_SMS_101 = "101";	// 인증번호전송
	public static final String CERT_SMS_102 = "102";	// 인증번호재전송
	public static final String CERT_SMS_103 = "103";	// 인증확인전송
	public static final String CERT_SMS_104 = "104";	// 인증중지전송
	public static final String CERT_SMS_105 = "105";	// 인증취소전송

	/**
	 * 주문상태
	 * @author leegt80
	 */
	public static final String PRD_ORD_STAT_501 = "501";	// 발송완료
	public static final String PRD_ORD_STAT_601 = "601";	// 클레임 진행
	public static final String PRD_ORD_STAT_901 = "901";	// 구매확정
	public static final String PRD_ORD_STAT_A01 = "A01";	// 반품 완료

	/**
	 * 인증상태 처리에 따른 Desc
	 */
	public static final String CERT_STATUS_DESC1 = "정상적으로 인증 완료되었습니다.";						// 인증 완료/반품 거부 처리 Description
	public static final String CERT_STATUS_DESC2 = "정상적으로 인증 취소되었습니다.";						// 인증 취소 처리 Description
	public static final String CERT_STATUS_DESC3 = "정상적으로 반품 완료되었습니다.";						// 인증 반품완료 처리 Description
	public static final String CERT_STATUS_DESC4 = "상점폐쇠로 인해 정상적으로 인증 중지되었습니다.";	// 인증 중지 처리 Description

	/**
	 * SMS 전송 TID (Template ID)
	 */
	public static final int CERT_SMS_103_TID = 6005;	// 인증확인 SMS TID
	public static final int CERT_SMS_105_TID = 6006;	// 인증취소 SMS TID

	/**
	 * 나의 11번가 short url
	 */
	public static final String MYPAGE_URL = "http://11st.kr/AusU23";

}