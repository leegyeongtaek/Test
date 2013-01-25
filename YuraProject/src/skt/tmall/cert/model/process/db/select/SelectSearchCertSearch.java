package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import com.skt.omp.common.util.StringUtil;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectListProcess;

/**
 * 조회인증 조회 페이지에서 이름&전화번호 뒤 4자리로 조회 or 인증번호로 조회 |  추가 : 구매자 ID, 주문번호, 기간(인증일, 주문일)
 *
 * 인증상태 (101: 미인증상태, 102:인증완료상태)
 * 주문상태 (501:배송완료, 901:구매확정)
 * searchType이 name일 경우: 이름&전화번호로 조회
 * searchType이 certNo일 경우: 인증번호로 조회
 * @author leegt80
 *
 */
public class SelectSearchCertSearch extends AbstractIbatisSelectListProcess<Map<String, Object>>{

	private final String SELECT_SEARCHCERT_SEARCH = "cert.select.searchcert.search";

	@Override
	protected String getAlias() {
		return SELECT_SEARCHCERT_SEARCH;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {

		SomCertBO certBo = new SomCertBO();

		String searchType = (String) context.get("searchType");

		certBo.setSearchType(searchType);											// 조회 조건
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 인증 처리 지점
		certBo.setStart(Long.parseLong((String)context.get("start")));				// 시작 페이지
		certBo.setEnd(Long.parseLong((String)context.get("end")));					// 종료 페이지


		certBo.setOrdType((String)context.get("vOrdType"));						// 검색 구분 값
		certBo.setOrdTypeName((String)context.get("vOrdTypeName"));				// 검색 값
		certBo.setShDateType((String)context.get("vShDateType"));				// 기간 구분 값
		certBo.setShDateFrom((String)context.get("vShDateFrom"));				// 시작 날짜
		certBo.setShDateTo((String)context.get("vShDateTo"));					// 종료 날짜
		certBo.setShopNo(Long.parseLong((String)context.get("certShopNo")));	// 상점번호

		certBo.setOrdPrdStat(StringUtil.nvl(context.get("ordPrdStat"), ""));	//주문상태


		// 이름&전화번호로 조회
		if ("name".equals(searchType)) {
			certBo.setBuyerNm((String) context.get("buyerNm"));	// 이름
			certBo.setSmsTelNo((String) context.get("telNo"));	// 전화번호
		}
		// 인증번호로 조회
		else if("certNo".equals(searchType)) {
			certBo.setCertNo((String) context.get("certNo"));	// 인증번호
		}
		return certBo;
	}
}