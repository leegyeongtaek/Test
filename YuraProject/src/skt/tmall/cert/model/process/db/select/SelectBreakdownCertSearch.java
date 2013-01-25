package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectListProcess;
import skt.tmall.common.util.StrUtil;

/**
 * 모바일 인증내역 조회 페이지에서 이름&전화번호 뒤 4자리로 조회 or 인증번호 or 인증처리기간으로 조회
 * 필수조건 (상점번호, 인증처리기간)
 * 인증상태 (102:인증완료상태)
 * 주문상태 (501:배송완료, 901:구매확정)
 * searchType이 name일 경우: 이름&전화번호로 조회
 * searchType이 certNo일 경우: 인증번호로 조회
 * searchType이 certDt일 경우: 인증처리기간으로 조회
 * @author leegt80
 *
 */
public class SelectBreakdownCertSearch extends AbstractIbatisSelectListProcess<Map<String, Object>>{

	private final String SELECT_SEARCHCERT_BREAKDOWN = "cert.select.searchcert.breakdown";
	
	@Override
	protected String getAlias() {
		return SELECT_SEARCHCERT_BREAKDOWN;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = new SomCertBO();
		
		String searchType = StrUtil.nvl((String) context.get("searchType"), "");		// 조회 조건
		String certSpotNo = StrUtil.nvl((String) context.get("certSpotNo"), "0");		// 인증 처리 지점
		
		certBo.setShopNo(Long.parseLong((String) context.get("shopNo")));			// 상점번호
		certBo.setCertDtS((String) context.get("certDtS"));									// 인증조회시작일
		certBo.setCertDtE((String) context.get("certDtE"));									// 인증조회종료일
		certBo.setStart(Long.parseLong((String)context.get("start")));						// 시작 페이지
		certBo.setEnd(Long.parseLong((String)context.get("end")));						// 종료 페이지
		certBo.setSearchType(searchType);													// 조회 조건
		certBo.setCertSpotNo(Long.parseLong(certSpotNo));								// 인증 처리 지점
		
		// 이름&전화번호로 조회
		if ("name".equals(searchType)) {
			certBo.setBuyerNm((String) context.get("buyerNm"));	// 이름
			certBo.setSmsTelNo((String) context.get("telNo"));		// 전화번호
		}
		// 인증번호로 조회
		else if("certNo".equals(searchType)) {
			certBo.setCertNo((String) context.get("certNo"));		// 인증번호
		}
		
		return certBo;
		
	}

}