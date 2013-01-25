package skt.tmall.cert.model.web.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.cert.model.process.db.select.SelectCertNumInfo;
import skt.tmall.common.annotation.DelegateSupport;
import skt.tmall.common.annotation.MandatoryParameter;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.util.DateUtil;
import skt.tmall.common.util.DelegateUtils;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.StrUtil;

/**
 * @author leegt80
 * 인증 모듈 UpdateDelegate
 */
public class UpdateDelegate {
	
	/**
	 * 인증번호 확인 Control
	 * @param request
	 * @param response
	 * @param context (접속 사용자 NO, 인증 처리 지점번호, 인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = { "userIp", "userNo", "certSpotNo", "certNo", "confirmType" }, 
			processName = "processGroup.cert.certNumConfirm1", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "userIp", "userNo", "certSpotNo", "certNo" })
	public void certNumConfirm(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {
		
		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터
		
		try {
			
			long userNo    = Long.parseLong((String) context.get("userNo"));			// 접속 사용자 NO
			String requestIp	=  (String) context.get("userIp");								// request IP
			String curDate 	= DateUtil.dateTime("/", ":");									// current date		
			context.put("certConfirmor", userNo);											// 인증처리자
			context.put("certConfirmIp", requestIp);											// 인증처리 IP
			context.put("certDt", curDate);														// 인증처리일
			context.put("certStat", ISOMCommonConstants.CERT_STATUS_102);			// 인증상태
			context.put("smsSendType", ISOMCommonConstants.CERT_SMS_103);		// SMS전송타입
			context.put("updateNo", userNo);													// 수정자
			context.put("updateDt", curDate);													// 수정일
			context.put("updateIp", requestIp);												// 수정자 IP
			context.put("histAplDt", curDate);													// 이력 적용일
			context.put("confirmType", StrUtil.nvl((String) context.get("confirmType"), ""));	// 반품거부 인증타입
			
			DelegateUtils.defaultSelectProcess(context, "process");
			
			Object result = context.get(ICommonConstants.PROCESS_RESULT);
			
			// 정상인증 완료 CASE
			if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
				// 인증이 완료된 인증번호의 정보 데이터 리턴
				SomCertBO certBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
				JSONObject json = JSONObject.fromObject(certBo);
				
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "인증이 완료되었습니다.";
				resultData = json.toString();
			}
			// 인증 실패 CASE
			else if (ISOMCommonConstants.PROCESS_RESULT_FAIL.equals(result)) {
				resultCode= ISOMCommonConstants.FAIL;
				resultMsg = "인증번호 확인 오류. 재시도";
				resultData = "[]";
			}
			// 시스템 오류 CASE
			else {
				Exception exception = (Exception) context.get("error");
				
				// 인증번호 확인 진행중 에러발생시 인증처리 불가능 처리에 대한 로그
				if(exception != null) {
					StringWriter sw = new StringWriter();
					PrintWriter  pw = new PrintWriter(sw);
					
					exception.printStackTrace(pw);
					
					String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
					
					resultCode= ISOMCommonConstants.ERROR;
					resultMsg = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
					resultData = "[]";
				}
				else {
					resultCode= ISOMCommonConstants.FAIL;
					resultMsg = ((result !=null) ? result.toString() : "");
					resultData = "[]";
				}
			}
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
			
			resultCode= ISOMCommonConstants.ERROR;
			resultMsg = logMsg.substring(0, 199);
			resultData = "[]";
			
			throw new ProcessException(e);
			
		} finally {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("'}");
			
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
			
		}
	}
	
	/**
	 * 인증번호 취소 Control
	 * @param request
	 * @param response
	 * @param context (접속 사용자 NO, 인증 처리 지점번호, 인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = { "userIp", "userNo", "certSpotNo", "certNo" }, 
			processName = "processGroup.cert.certNumCancel1", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "userIp", "userNo", "certSpotNo", "certNo" })
	public void certNumCancel(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {
		
		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터
		
		try {
		
			long userNo    = Long.parseLong((String) context.get("userNo"));			// 접속 사용자 NO
			String requestIp	=  (String) context.get("userIp");								// request IP
			String curDate 	= DateUtil.dateTime("/", ":");									// current date		
			context.put("certStat", ISOMCommonConstants.CERT_STATUS_101);			// 인증상태
			context.put("smsSendType", ISOMCommonConstants.CERT_SMS_105);		// SMS전송타입
			context.put("updateNo", userNo);													// 수정자 (인증취소처리자)
			context.put("updateDt", curDate);													// 수정일 (인증취소처리일)
			context.put("updateIp", requestIp);												// 수정자 IP (인증취소처리 IP)
			context.put("histAplDt", curDate);													// 이력 적용일
			context.put("certConfirmor", userNo);											// 인증취소처리 시도한 인증자
			context.put("certConfirmIp", requestIp);											// 인증취소처리 시도한 인증자 IP
			context.put("certDt", curDate);														// 인증취소처리 시도한 인증처리일
			
			DelegateUtils.defaultSelectProcess(context, "process");
			
			Object result = context.get(ICommonConstants.PROCESS_RESULT);
			
			String certNo = ((String) context.get("certNo"));
			
			if (certNo.length() == 12)	certNo = certNo.substring(0, 4) + "-" +  certNo.substring(4, 8) + "-" + certNo.substring(8, 12);
			
			// 정상인증 취소 CASE
			if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
				SomCertBO certBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
				JSONObject json = JSONObject.fromObject(certBo);
				
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "인증번호 " + certNo + " 인증이 취소되었습니다.";
				resultData = json.toString();
			}
			// 인증 실패 CASE
			else if (ISOMCommonConstants.PROCESS_RESULT_FAIL.equals(result)) {
				resultCode= ISOMCommonConstants.FAIL;
				resultMsg = "인증번호 취소 오류. 재시도";
				resultData = "";
			}
			// 시스템 오류 CASE
			else {
				Exception exception = (Exception) context.get("error");
				
				// 인증번호 확인 진행중 에러발생시 인증처리 취소 불가능 처리에 대한 로그
				if(exception != null) {
					StringWriter sw = new StringWriter();
					PrintWriter  pw = new PrintWriter(sw);
					
					exception.printStackTrace(pw);
					
					String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
					
					resultCode= ISOMCommonConstants.ERROR;
					resultMsg = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
					resultData = "";
				}
				else {
					resultCode= ISOMCommonConstants.FAIL;
					resultMsg = ((result !=null) ? result.toString() : "");
					resultData = "";
				}
				
			}
		
		} catch (Exception e) {
		
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
			
			resultCode= ISOMCommonConstants.ERROR;
			resultMsg = logMsg.substring(0, 199);
			resultData = "[]";
			
			throw new ProcessException(e);
		
		} finally {
		
			StringBuffer sb = new StringBuffer();
			
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("'}");
			
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
		
		}
		
	}
	
	/**
	 * 상품별 외부 인증번호 상태 변경 엑셀 등록 (엑셀 데이터로 인증확인 처리) Control
	 * @param request
	 * @param response
	 * @param context (접속 사용자 IP, 접속 사용자 NO, 상품번호, 인증번호, 인증상태, 인증처리 지점번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = { "userIp", "userNo", "prdNo", "certNo", "certStat", "certSpotNo" }, 
			processName = "processGroup.cert.certNumConfirm1", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "userIp", "userNo", "prdNo", "certNo", "certStat", "certSpotNo" })
	public void updOutCertStatusInfo(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {
		
		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터
		
		try {
			
			// 상품번호
			String prdNo = String.valueOf(context.get("prdNo"));
			
			// 외부 인증번호 배열
			String certNo = (String) context.get("certNo");
			String[] certNos = certNo.trim().split(",");
			
			// 외부 인증번호 상태 배열
			String certStat = (String) context.get("certStat");
			String[] certStates = certStat.trim().split(",");
			
			// 인증처리 지점번호 배열
			String certSpotNo = (String) context.get("certSpotNo");
			String[] certSpotNos = certSpotNo.trim().split(",");
			
			long userNo    = Long.parseLong((String) context.get("userNo"));			// 접속 사용자 NO
			String curDate 	= DateUtil.dateTime("/", ":");									// current date	
			String requestIp	=  (String) context.get("userIp");								// request IP

			if (certNos.length != certStates.length || certNos.length != certSpotNos.length) throw new ProcessException("인증상태 업로드 데이터 수가 일치하지 않습니다.");
			
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			
			for (int i = 0; i < certNos.length; i++) {
				
				context.put("prdNo", prdNo);														// 상품번호
				context.put("certConfirmor", userNo);											// 인증처리자
				context.put("certConfirmIp", requestIp);											// 인증처리 IP
				context.put("certDt", curDate);														// 인증처리일
				context.put("certStat", ISOMCommonConstants.CERT_STATUS_102);			// 인증상태
				context.put("smsSendType", ISOMCommonConstants.CERT_SMS_103);		// SMS전송타입
				context.put("updateNo", userNo);													// 수정자
				context.put("updateDt", curDate);													// 수정일
				context.put("updateIp", requestIp);												// 수정자 IP
				context.put("histAplDt", curDate);													// 이력 적용일
				context.put("updateNo", userNo);													// 수정자
				context.put("confirmType", StrUtil.nvl((String) context.get("confirmType"), ""));	// 반품거부 인증타입
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				
				// 인증완료 처리
				if (ISOMCommonConstants.CERT_STATUS_102.equals(certStates[i].trim())) {
				
					context.put("certNo", certNos[i].trim());				// 인증번호
					context.put("certSpotNo", certSpotNos[i].trim());	// 인증처리 지점번호
					
					DelegateUtils.defaultSelectProcess(context, "process");
					
					Object result = context.get(ICommonConstants.PROCESS_RESULT);
					
					// 정상인증 완료 CASE
					if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
						// 인증이 완료된 인증번호의 정보 데이터 리턴
						resultCode= ISOMCommonConstants.PROCESS_RESULT_SUCCESS;
					}
					// 인증 실패 CASE
					else if (ISOMCommonConstants.PROCESS_RESULT_FAIL.equals(result)) {
						resultCode= (String) context.get("confirmLogMsg");
					}
					// 시스템 오류 CASE
					else {
						Exception exception = (Exception) context.get("error");
						
						// 인증번호 확인 진행중 에러발생시 인증처리 불가능 처리에 대한 로그
						if(exception != null) {
							StringWriter sw = new StringWriter();
							PrintWriter  pw = new PrintWriter(sw);
							
							exception.printStackTrace(pw);
							
							String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
							
							resultCode= logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
						}
						else {
							resultCode=(String) context.get("confirmLogMsg");
						}
						
						context.remove("error");
					}
					
				}
				else {
					resultCode = "인증처리 불가 인증상태 데이터";
				}
				
				resultMap.put("prdNo", prdNo);						// 상품번호
				resultMap.put("certNo", certNos[i]);					// 인증번호
				resultMap.put("certStat", certStates[i]);				// 인증상태
				resultMap.put("certSpotNo", certSpotNos[i]);		// 인증처리 지점번호
				resultMap.put("resultValue", resultCode);			// 인증처리 결과값
				
				resultList.add(resultMap);
				
			}
			
			JSONArray objList = JSONArray.fromObject(resultList); 
			
			resultCode= ISOMCommonConstants.SUCCESS;
			resultMsg = "인증이 완료되었습니다.";
			resultData = objList.toString();
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
			
			resultCode= ISOMCommonConstants.ERROR;
			resultMsg = logMsg.substring(0, 199);
			resultData = "[]";
			
			throw new ProcessException(e);
			
		} finally {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("'}");
			
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
			
		}
	}
	
}
