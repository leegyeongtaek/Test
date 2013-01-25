package skt.tmall.cert.model.web.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skt.omp.common.util.StringUtil;

import net.sf.json.JSONArray;

import skt.tmall.cert.model.bean.ProductCertBo;
import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.annotation.DelegateSupport;
import skt.tmall.common.annotation.MandatoryParameter;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.util.DelegateUtils;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.StrUtil;

/**
 * @author leegt80
 * 인증 모듈 SelectDelegate
 */
public class SelectDelegate {

	/**
	 * 바로인증 조회 페이지에서 최근 인증처리한 5건 조회  Control
	 * @param request
	 * @param response
	 * @param certSpotNo (인증 처리 지점번호)
	 * @param ordPrdStat (주문상태값)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~'}
	 */
	@DelegateSupport(webParameters = { "certSpotNo", "ordPrdStat" },
			processName = "cert.select.selectNowCertSearch", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "certSpotNo" })
	public void nowCertSearch(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		Object result = null;

		try {

			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (ProcessException e) {

			e.printStackTrace();
			throw new ProcessException(e);

		} finally {

			String resultCode 	= "";
			String resultMsg		= "";
			String resultData 	= "";

			result = context.get(ICommonConstants.PROCESS_RESULT);

			Exception exception = (Exception) context.get("error");

			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");

				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = "시스템 오류 발생.";
				resultData = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
			}
			else {

				// 상품명 [로 시작할 경우 JSON 포맷으로 이후 데이터 손실 발생.
				@SuppressWarnings("unchecked")
				List<SomCertBO> list = (List<SomCertBO>) result;

	            if((list != null) && (list.size() > 0)) {

                    for (int i = 0; i < list.size(); i++) {
                    	list.get(i).setPrdNm(StringUtil.encodeHTML(StrUtil.convertJSONFormat(list.get(i).getPrdNm())));
                    	list.get(i).setShopNm(StringUtil.encodeHTML(list.get(i).getShopNm()));
                    	list.get(i).setShopBranchNm(StringUtil.encodeHTML(list.get(i).getShopBranchNm()));
                    }

	            }

				// JSON 포맷 변환
				JSONArray objList = JSONArray.fromObject(list);
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "정상";
				resultData =objList.toString();
			}

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
	 * 조회인증 조회 페이지에서 이름&전화번호 뒤 4자리로 조회 or 인증번호로 조회 Control 추가 : 구매자 ID, 주문번호, 기간(인증일, 주문일), 주문상태
	 * @param request
	 * @param response
	 * @param context (인증 처리 지점번호, 조회조건, 검색 이름, 검색 전화번호, 검색 인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~', 'TOTAL_COUNT':'10'}
	 */
	@DelegateSupport(webParameters = { "certShopNo", "certSpotNo", "searchType", "buyerNm", "telNo", "certNo", "start", "end",
			"vOrdType", "vOrdTypeName", "vShDateType", "vShDateFrom", "vShDateTo", "ordPrdStat" },
			processName = "cert.select.selectSearchCertSearch", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "certShopNo", "searchType", "start", "end" })
	public void searchCertSearch(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		Object result = null;

		try {

			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (ProcessException e) {

			e.printStackTrace();
			throw new ProcessException(e);

		} finally {

			long iTotalCount = 0;
			String resultCode 	= "";
			String resultMsg		= "";
			String resultData 	= "";

			result = context.get(ICommonConstants.PROCESS_RESULT);
			Exception exception = (Exception) context.get("error");

			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");

				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = "시스템 오류 발생.";
				resultData = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
			}
			else {

				@SuppressWarnings("unchecked")
				List<SomCertBO> list = (List<SomCertBO>) result;

	            if((list != null) && (list.size() > 0)) {

	            	SomCertBO tmpBO = (SomCertBO)list.get(0);
	            	iTotalCount = tmpBO.getTotalCount();

	            	// 상품명 [로 시작할 경우 JSON 포맷으로 이후 데이터 손실 발생.
                    for (int i = 0; i < list.size(); i++) {
                    	list.get(i).setPrdNm(StringUtil.encodeHTML(StrUtil.convertJSONFormat(list.get(i).getPrdNm())));
                    	list.get(i).setShopNm(StringUtil.encodeHTML(list.get(i).getShopNm()));
                    	list.get(i).setShopBranchNm(StringUtil.encodeHTML(list.get(i).getShopBranchNm()));
                    }

	            }

	            // JSON 포맷 변환
				JSONArray objList = JSONArray.fromObject(list);
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "정상";
				resultData =objList.toString();

			}

			StringBuffer sb = new StringBuffer();
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("', '")
			.append("TOTAL_COUNT':'").append(iTotalCount).append("'}");

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
		}

	}

	/**
	 * 인증내역 조회 페이지에서 이름&전화번호 뒤 4자리로 조회 or 인증번호 or 인증처리기간으로 조회 Control
	 * 필수조건 (상점번호, 인증처리기간)
	 * @param request
	 * @param response
	 * @param context (인증 처리 지점번호, 조회조건, 검색 이름, 검색 전화번호, 검색 인증번호) :name, certNo
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~', 'TOTAL_COUNT':'10'}
	 */
	@DelegateSupport(webParameters = { "shopNo", "certSpotNo", "searchType", "buyerNm", "telNo", "certNo", "certDtS", "certDtE", "start", "end" },
			processName = "cert.select.selectBreakdownCertSearch", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "shopNo", "certDtS", "certDtE", "start", "end" })
	public void breakdownCertSearch(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
					throws ProcessException, IOException {

		Object result = null;

		try {

			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (ProcessException e) {

			e.printStackTrace();
			throw new ProcessException(e);

		} finally {

			long iTotalCount = 0;
			String resultCode 	= "";
			String resultMsg		= "";
			String resultData 	= "";

			result = context.get(ICommonConstants.PROCESS_RESULT);
			Exception exception = (Exception) context.get("error");

			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");

				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = "시스템 오류 발생.";
				resultData = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
			}
			else {

				@SuppressWarnings("unchecked")
				List<SomCertBO> list = (List<SomCertBO>) result;

				if((list != null) && (list.size() > 0)) {

					SomCertBO tmpBO = (SomCertBO)list.get(0);
					iTotalCount = tmpBO.getTotalCount();

					// 상품명 [로 시작할 경우 JSON 포맷으로 이후 데이터 손실 발생.
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setPrdNm(StrUtil.convertJSONFormat(list.get(i).getPrdNm()));
					}

				}

				// JSON 포맷 변환
				JSONArray objList = JSONArray.fromObject(list);
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "정상";
				resultData =objList.toString();

			}

			StringBuffer sb = new StringBuffer();
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("', '")
			.append("TOTAL_COUNT':'").append(iTotalCount).append("'}");

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
		}

	}

	/**
	 * BO 인증관리 조회 Control
	 * @param request
	 * @param response
	 * @param context (인증 처리 지점번호, 검색 이름, 검색 전화번호, 검색 인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~', 'TOTAL_COUNT':'10'}
	 */
	@DelegateSupport(webParameters = { "certDt", "certDtTo", "buyerNm", "telNo", "shopNo", "shopBranchNo", "prdNo", "certNo", "ordNo", "certStat", "start", "end", "ordPrdStat" },
			processName = "cert.select.selectManageCertSearch", processReferenceKey = "process")
	public void manageCertSearch(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {

		Object result = null;

		try {

			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (ProcessException e) {

			e.printStackTrace();
			throw new ProcessException(e);

		} finally {

			long iTotalCount = 0;
			String resultCode 	= "";
			String resultMsg		= "";
			String resultData 	= "";

			result = context.get(ICommonConstants.PROCESS_RESULT);
			Exception exception = (Exception) context.get("error");

			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");

				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = "시스템 오류 발생.";
				resultData = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
			}
			else {

				@SuppressWarnings("unchecked")
				List<SomCertBO> list = (List<SomCertBO>) result;

	            if((list != null) && (list.size() > 0)) {

	            	SomCertBO tmpBO = (SomCertBO)list.get(0);
	            	iTotalCount = tmpBO.getTotalCount();

	            	// 상품명 [로 시작할 경우 JSON 포맷으로 이후 데이터 손실 발생.
                    for (int i = 0; i < list.size(); i++) {
                    	list.get(i).setPrdNm(StrUtil.convertJSONFormat(list.get(i).getPrdNm()));
                    }

	            }

				// JSON 포맷 변환
				JSONArray objList = JSONArray.fromObject(list);
				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "정상";
				resultData =objList.toString();
			}

			StringBuffer sb = new StringBuffer();
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("', '")
			.append("TOTAL_COUNT':'").append(iTotalCount).append("'}");

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
		}

	}

	/**
	 * BO 인증번호 내역 히스토리 조회 Control
	 * @param request
	 * @param response
	 * @param context (인증번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~', 'TOTAL_COUNT':'10'}
	 */
	@DelegateSupport(webParameters = { "certNo" },
			processName = "cert.select.selectCertNoHistory", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "certNo" })
	public void certStatHistoryList(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
					throws ProcessException, IOException {

		Object result = null;

		try {

			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (ProcessException e) {

			e.printStackTrace();
			throw new ProcessException(e);

		} finally {

			long iTotalCount = 0;
			String resultCode 	= "";
			String resultMsg		= "";
			String resultData 	= "";

			result = context.get(ICommonConstants.PROCESS_RESULT);
			Exception exception = (Exception) context.get("error");

			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");

				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = "시스템 오류 발생.";
				resultData = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
			}
			else {
				// JSON 포맷 변환
				JSONArray objList = JSONArray.fromObject(result);

				@SuppressWarnings("unchecked")
				List<SomCertBO> list = (List<SomCertBO>) result;

	            if((list != null) && (list.size() > 0)) {

	            	SomCertBO tmpBO = (SomCertBO)list.get(0);
	            	iTotalCount = tmpBO.getTotalCount();

	            }

				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "정상";
				resultData =objList.toString();
			}

			StringBuffer sb = new StringBuffer();
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("', '")
			.append("TOTAL_COUNT':'").append(iTotalCount).append("'}");

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
		}

	}

	/**
	 * 미인증 상품 내역 조회 Control
	 * @param request
	 * @param response
	 * @param context (지점번호)
	 * @throws ProcessException
	 * @throws IOException
	 * @return {'RESULT_CODE':'100', 'RESULT_MSG':'~~~', 'RESULT_DATA':'~~~', 'TOTAL_COUNT':'10'}
	 */
	@DelegateSupport(webParameters = { "shopNo", "start", "end" },
			processName = "cert.select.selectNonCertInfo", processReferenceKey = "process")
	@MandatoryParameter(parameters = { "shopNo", "start", "end" })
	public void nonCertInfoList(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
					throws ProcessException, IOException {

		Object result = null;

		try {

			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (ProcessException e) {

			e.printStackTrace();
			throw new ProcessException(e);

		} finally {

			long iTotalCount = 0;
			String resultCode 	= "";
			String resultMsg		= "";
			String resultData 	= "";

			result = context.get(ICommonConstants.PROCESS_RESULT);
			Exception exception = (Exception) context.get("error");

			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				String logMsg = sw.toString().trim().replaceAll(":", "").replaceAll("\n", "").replaceAll("\r\t", "");

				resultCode= ISOMCommonConstants.ERROR;
				resultMsg = "시스템 오류 발생.";
				resultData = logMsg.substring(0, 199) + ((result !=null) ? result.toString() : "");
			}
			else {
				// JSON 포맷 변환
				JSONArray objList = JSONArray.fromObject(result);

				@SuppressWarnings("unchecked")
				List<ProductCertBo> list = (List<ProductCertBo>) result;

				if((list != null) && (list.size() > 0)) {

					ProductCertBo productCertBo = (ProductCertBo)list.get(0);
					iTotalCount = (Long) productCertBo.getTotalCount();

					for (int i = 0; i < list.size(); i++) {
                    	list.get(i).setPrdNm(StrUtil.convertJSONFormat(list.get(i).getPrdNm()));
                    }

				}

				resultCode= ISOMCommonConstants.SUCCESS;
				resultMsg = "정상";
				resultData =objList.toString();
			}

			StringBuffer sb = new StringBuffer();
			sb.append("{'")
			.append(ISOMCommonConstants.RESULT_CODE).append("':'").append(resultCode).append("', '")
			.append(ISOMCommonConstants.RESULT_MSG).append("':'").append(resultMsg).append("', '")
			.append(ISOMCommonConstants.RESULT_DATA).append("':'").append(resultData).append("', '")
			.append("TOTAL_COUNT':'").append(iTotalCount).append("'}");

			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(sb.toString());
			response.flushBuffer();
		}

	}

}
