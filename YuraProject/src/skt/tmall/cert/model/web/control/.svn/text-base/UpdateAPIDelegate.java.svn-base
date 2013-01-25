package skt.tmall.cert.model.web.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import net.sf.json.JSONObject;

import skt.tmall.cert.model.bean.ClientMessage;
import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.cert.model.process.db.select.SelectCertNumInfo;
import skt.tmall.common.annotation.DelegateSupport;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.util.DateUtil;
import skt.tmall.common.util.DelegateUtils;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.ObjectUtil;
import skt.tmall.common.util.StrUtil;

/**
 * @author leegt80
 * 인증 모듈 UpdateAPIDelegate (API)
 */
public class UpdateAPIDelegate {

	/**
	 * 인증번호  확인 Control
	 * @param request
	 * @param response
	 * @param nonMandatory (접속 사용자 NO, 인증 처리 지점번호)
	 * @param mandatory (인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~', 'RESULT_SellerPrdCd':'~~~'}
	 */
	@DelegateSupport(webParameters = {},
			processName = "processGroup.cert.certNumConfirm1", processReferenceKey = "process")
	public void certNumConfirm(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		// mandatory parameters
		String[] setters = { "certNo" };

		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터
		String resultSellerPrdCd = ""; // 인증 API 판매자 상품 코드

		try {

			// request body XML Unmarshalling...
			BufferedReader reader = new BufferedReader(request.getReader());

			// XML to SomCertBO unmarshalling
			SomCertBO certBo = readObject(reader);

			// check mandatory parameters
			if (!ObjectUtil.checkMandatorySetter(certBo, setters)) throw new ProcessException("mandatory parameters not existed!");

			String curDate 	= DateUtil.dateTime("/", ":");										// current date
			context.put("isAPI", "Y");															//  API 접근 여부
			context.put("certConfirmIp", context.get(ICommonConstants.CLIENT_IP));				// 인증처리 IP
			context.put("certDt", curDate);														// 인증처리일
			context.put("certStat", ISOMCommonConstants.CERT_STATUS_102);						// 인증상태
			context.put("smsSendType", ISOMCommonConstants.CERT_SMS_103);						// SMS전송타입
			context.put("updateDt", curDate);													// 수정일
			context.put("updateIp", context.get(ICommonConstants.CLIENT_IP));					// 수정자 IP
			context.put("histAplDt", curDate);													// 이력 적용일
			context.put("certSpotNo", String.valueOf(certBo.getCertSpotNo()));					// 인증처리 지점번호
			context.put("certNo", certBo.getCertNo());											// 인증번호
			context.put("confirmType", StrUtil.nvl((String) context.get("confirmType"), ""));	// 반품거부 인증타입

			DelegateUtils.defaultSelectProcess(context, "process");

			Object result = context.get(ICommonConstants.PROCESS_RESULT);

			// 정상인증 완료 CASE
			if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
				// 인증이 완료된 인증번호의 정보 데이터 리턴
				SomCertBO tempCertBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
				JSONObject json = JSONObject.fromObject(tempCertBo);

				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "인증이 완료되었습니다.";
				resultData = json.toString();
				resultSellerPrdCd = tempCertBo.getSellerPrdCd();//셀러상품타입코드

			}
			// 인증 실패 CASE
			else if (ISOMCommonConstants.PROCESS_RESULT_FAIL.equals(result)) {
				resultCode= ISOMCommonConstants.FAIL;
				resultMsg = "인증번호 확인 오류. 재시도";
				resultData = "[]";
				resultSellerPrdCd = "[]";
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
					resultSellerPrdCd = "[]";
				}
				else {
					resultCode= ISOMCommonConstants.FAIL;
					resultMsg =((result !=null) ? result.toString() : "");
					resultData = "[]";
					resultSellerPrdCd = "[]";
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
			resultSellerPrdCd = "[]";

			throw new ProcessException(e);

		} finally {

			ClientMessage cMsg = new ClientMessage();
			cMsg.setResultCode(resultCode);
			cMsg.setResultMsg(resultMsg);
			cMsg.setResultData(resultData);
			cMsg.setResultSellerPrdCd(resultSellerPrdCd);

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			try {
				// ClientMessage to XML marshalling
				writeXML(cMsg, response.getOutputStream());
			} catch (JAXBException e) {
				throw new ProcessException(e);
			}

			response.flushBuffer();

		}

	}

	/**
	 * 인증번호 취소 Control
	 * @param request
	 * @param response
	 * @param nonMandatory (접속 사용자 NO, 인증 처리 지점번호)
	 * @param context (인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = {},
			processName = "processGroup.cert.certNumCancel1", processReferenceKey = "process")
	public void certNumCancel(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		// mandatory parameters
		String[] setters = { "certNo" };

		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터

		try {

			// request body XML Unmarshalling...
			BufferedReader reader = new BufferedReader(request.getReader());

			// XML to SomCertBO unmarshalling
			SomCertBO certBo = readObject(reader);

			// check mandatory parameters
			if (!ObjectUtil.checkMandatorySetter(certBo, setters)) throw new ProcessException("mandatory parameters not existed!");

			String curDate 	= DateUtil.dateTime("/", ":");									// current date
			context.put("isAPI", "Y");															//  API 접근 여부
			context.put("certStat", ISOMCommonConstants.CERT_STATUS_101);			// 인증상태
			context.put("smsSendType", ISOMCommonConstants.CERT_SMS_105);		// SMS전송타입
			context.put("updateDt", curDate);													// 수정일 (인증취소처리일)
			context.put("updateIp", context.get(ICommonConstants.CLIENT_IP));		// 수정자 IP (인증취소처리 IP)
			context.put("histAplDt", curDate);													// 이력 적용일
			context.put("certConfirmIp", context.get(ICommonConstants.CLIENT_IP));	// 인증취소처리 시도한 인증자 IP
			context.put("certDt", curDate);														// 인증취소처리 시도한 인증처리일
			context.put("certSpotNo", String.valueOf(certBo.getCertSpotNo()));			// 인증취소처리 지점번호
			context.put("certNo", certBo.getCertNo());										// 인증번호

			DelegateUtils.defaultSelectProcess(context, "process");

			Object result = context.get(ICommonConstants.PROCESS_RESULT);

			String certNo = ((String) context.get("certNo"));

			if (certNo.length() == 12)	certNo = certNo.substring(0, 4) + "-" +  certNo.substring(4, 8) + "-" + certNo.substring(8, 12);

			// 정상인증 취소 CASE
			if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
				SomCertBO tempCertBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
				JSONObject json = JSONObject.fromObject(tempCertBo);

				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "인증번호 " + certNo + " 인증이 취소 되었습니다.";
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

			ClientMessage cMsg = new ClientMessage();
			cMsg.setResultCode(resultCode);
			cMsg.setResultMsg(resultMsg);
			cMsg.setResultData(resultData);

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			try {
				// ClientMessage to XML marshalling
				writeXML(cMsg, response.getOutputStream());
			} catch (JAXBException e) {
				throw new ProcessException(e);
			}

			response.flushBuffer();

		}

	}

	/**
	 * 외부 인증번호 확인 Control (M12)
	 * @param request
	 * @param response
	 * @param nonMandatory (인증 처리 지점번호)
	 * @param mandatory (접속 사용자 NO, 인증번호, 주문번호, 주문순번)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = {},
			processName = "processGroup.cert.coopCertNumConfirm1", processReferenceKey = "process")
	public void externalCertNumConfirm(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		// mandatory parameters
		String[] setters = { "certNo", "ordNo", "ordPrdSeq" };

		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터

		try {

			// request body XML Unmarshalling...
			BufferedReader reader = new BufferedReader(request.getReader());

			// XML to SomCertBO unmarshalling
			SomCertBO certBo = readObject(reader);

			// check mandatory parameters
			if (!ObjectUtil.checkMandatorySetter(certBo, setters)) throw new ProcessException("mandatory parameters not existed!");

			String curDate 	= DateUtil.dateTime("/", ":");							// current date
			context.put("isAPI", "Y");												//  API 접근 여부
			context.put("certConfirmIp", context.get(ICommonConstants.CLIENT_IP));	// 인증처리 IP
			context.put("certDt", curDate);											// 인증처리일
			context.put("certStat", ISOMCommonConstants.CERT_STATUS_102);			// 인증상태
			context.put("smsSendType", ISOMCommonConstants.CERT_SMS_103);			// SMS전송타입
			context.put("updateDt", curDate);										// 수정일
			context.put("updateIp", context.get(ICommonConstants.CLIENT_IP));		// 수정자 IP
			context.put("histAplDt", curDate);										// 이력 적용일
			context.put("certSpotNo", String.valueOf(certBo.getCertSpotNo()));		// 인증처리 지점번호
			context.put("certNo", certBo.getCertNo());								// 인증번호
			context.put("ordNo", certBo.getOrdNo());								// 주문번호
			context.put("ordPrdSeq", certBo.getOrdPrdSeq());						// 주문순번

			DelegateUtils.defaultSelectProcess(context, "process");

			Object result = context.get(ICommonConstants.PROCESS_RESULT);

			// 정상인증 완료 CASE
			if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
				// 인증이 완료된 인증번호의 정보 데이터 리턴
				SomCertBO tempCertBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
				JSONObject json = JSONObject.fromObject(tempCertBo);

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
					resultMsg =((result !=null) ? result.toString() : "");
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

			ClientMessage cMsg = new ClientMessage();
			cMsg.setResultCode(resultCode);
			cMsg.setResultMsg(resultMsg);
			cMsg.setResultData(resultData);


			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			try {
				// ClientMessage to XML marshalling
				writeXML(cMsg, response.getOutputStream());
			} catch (JAXBException e) {
				throw new ProcessException(e);
			}

			response.flushBuffer();

		}

	}

	/**
	 * 외부 인증번호 취소 Control
	 * @param request
	 * @param response
	 * @param nonMandatory (접속 사용자 NO, 인증 처리 지점번호)
	 * @param context (인증번호, 주문번호, 주문순번)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = {},
			processName = "processGroup.cert.coopCertNumCancel1", processReferenceKey = "process")
	public void externalCertNumCancel(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		// mandatory parameters
		String[] setters = { "certNo", "ordNo", "ordPrdSeq" };

		String resultCode 	= "";	// 인증 API 응답 코드
		String resultMsg 	= "";	// 인증 API 상세 응답 메세지
		String resultData 	= "";	// 인증 API 응답 데이터

		try {

			// request body XML Unmarshalling...
			BufferedReader reader = new BufferedReader(request.getReader());

			// XML to SomCertBO unmarshalling
			SomCertBO certBo = readObject(reader);

			// check mandatory parameters
			if (!ObjectUtil.checkMandatorySetter(certBo, setters)) throw new ProcessException("mandatory parameters not existed!");

			String curDate 	= DateUtil.dateTime("/", ":");									// current date
			context.put("isAPI", "Y");															//  API 접근 여부
			context.put("certStat", ISOMCommonConstants.CERT_STATUS_101);			// 인증상태
			context.put("smsSendType", ISOMCommonConstants.CERT_SMS_105);		// SMS전송타입
			context.put("updateDt", curDate);													// 수정일 (인증취소처리일)
			context.put("updateIp", context.get(ICommonConstants.CLIENT_IP));		// 수정자 IP (인증취소처리 IP)
			context.put("histAplDt", curDate);													// 이력 적용일
			context.put("certConfirmIp", context.get(ICommonConstants.CLIENT_IP));	// 인증취소처리 시도한 인증자 IP
			context.put("certDt", curDate);														// 인증취소처리 시도한 인증처리일
			context.put("certSpotNo", String.valueOf(certBo.getCertSpotNo()));			// 인증취소처리 지점번호
			context.put("certNo", certBo.getCertNo());										// 인증번호
			context.put("ordNo", certBo.getOrdNo());										// 주문번호
			context.put("ordPrdSeq", certBo.getOrdPrdSeq());								// 주문순번

			DelegateUtils.defaultSelectProcess(context, "process");

			Object result = context.get(ICommonConstants.PROCESS_RESULT);

			String certNo = ((String) context.get("certNo"));

			if (certNo.length() == 12)	certNo = certNo.substring(0, 4) + "-" +  certNo.substring(4, 8) + "-" + certNo.substring(8, 12);

			// 정상인증 취소 CASE
			if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(result)) {
				SomCertBO tempCertBo = (SomCertBO) context.get(SelectCertNumInfo.CERT_NUM_INFO);
				JSONObject json = JSONObject.fromObject(tempCertBo);

				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "인증번호 " + certNo + " 인증이 취소 되었습니다.";
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

			ClientMessage cMsg = new ClientMessage();
			cMsg.setResultCode(resultCode);
			cMsg.setResultMsg(resultMsg);
			cMsg.setResultData(resultData);

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			try {
				// ClientMessage to XML marshalling
				writeXML(cMsg, response.getOutputStream());
			} catch (JAXBException e) {
				throw new ProcessException(e);
			}

			response.flushBuffer();

		}

	}

	// XML to SomCertBO unmarshalling
	public static SomCertBO readObject(Reader reader) throws JAXBException{

		JAXBContext jaxbContext = JAXBContext.newInstance(SomCertBO.class);

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		SomCertBO certBo = (SomCertBO) unmarshaller.unmarshal(reader);

		return certBo;
	}

	// ClientMessage to XML marshalling
	public static void writeXML(ClientMessage cMsg, ServletOutputStream servletOutputStream) throws JAXBException{

		JAXBContext jaxbContext = JAXBContext.newInstance(ClientMessage.class);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshaller.marshal(cMsg, servletOutputStream);

	}

}