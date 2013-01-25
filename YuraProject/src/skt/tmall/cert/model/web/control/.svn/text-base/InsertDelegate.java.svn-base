package skt.tmall.cert.model.web.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;


import skt.tmall.cert.model.bean.ProductBo;
import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.annotation.DelegateSupport;
import skt.tmall.common.annotation.MandatoryParameter;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.util.DateUtil;
import skt.tmall.common.util.DelegateUtils;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * @author leegt80
 * 인증 모듈 UpdateDelegate
 */
public class InsertDelegate {
	
	/**
	 * 상품별 외부 인증번호 엑셀 등록 Control
	 * @param request
	 * @param response
	 * @param context (접속 사용자 IP, 접속 사용자 NO, 상품번호, 재고수량, 인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = { "userIp", "userNo", "prdNo", "stckQty", "certNo" }, 
			processName = "processGroup.cert.regOutCertInfo", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "userIp", "userNo", "prdNo", "stckQty", "certNo" })
	public void regOutCertInfo(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {
		
		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터
		
		try {
			
			String certNo = (String) context.get("certNo");
			String[] certNos = certNo.trim().split(",");
			
			long userNo    = Long.parseLong((String) context.get("userNo"));			// 접속 사용자 NO
			String curDate 	= DateUtil.dateTime("/", ":");									// current date	
			String requestIp	=  (String) context.get("userIp");								// request IP
			
			SomCertBO certBo = new SomCertBO();
			
			certBo.setPrdNo(Long.parseLong((String) context.get("prdNo")));		// 상품번호
			certBo.setCreateNo(userNo);													// 등록자
			certBo.setCreateDt(curDate);													// 등록일
			certBo.setCreateIp(requestIp);													// 등록자 IP
			certBo.setUpdateNo(userNo);													// 수정자
			certBo.setUpdateDt(curDate);													// 수정일
			certBo.setUpdateIp(requestIp);												// 수정자 IP
			certBo.setSmsSendType(ISOMCommonConstants.CERT_SMS_100);		// SMS 전송 타입 (미전송)
			certBo.setCertStat(ISOMCommonConstants.CERT_STATUS_100);			// 인증 상태 (주문가능)
			certBo.setHistAplDt(curDate);													// 이력 적용일
			certBo.setStatDesc("외부 인증번호 엑셀 등록");								// 상태설명
			
			context.put("certBo", certBo);		// 인증정보 BO
			context.put("certNos", certNos);	// 외부 인증번호 배열
			context.put("updateNo", userNo);// 수정자
			
			DelegateUtils.defaultSelectProcess(context, "process");
			
			Exception exception = (Exception) context.get("error");
			
			// 상품별 외부 인증번호 엑셀 등록 진행중 에러발생시
			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);
				
				exception.printStackTrace(pw);
				
				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");
				
				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = logMsg.substring(0, 199);
				resultData = "[]";
			}
			else {
				
				ProductBo productBo = (ProductBo) context.get("productBo");
				
				JSONArray jsonArray = JSONArray.fromObject(productBo);
				
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "외부 인증번호 등록이 완료되었습니다.";
				resultData = jsonArray.toString();
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
	
}
