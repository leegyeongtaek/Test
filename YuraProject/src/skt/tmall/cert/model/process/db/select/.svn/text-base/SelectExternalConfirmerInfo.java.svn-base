package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * API로 외부 인증번호 확인/취소요청 지점, 사용자 정보 조회
 * 지점번호가 없으면 요청한 주문번호, 주문순번으로 해당 되는 지점 및 사용자 정보를 조회함.
 * 인증번호가 아닌 지점번호로 조회하는 이유는 외부 인증번호는 업체가 다를 경우 인증번호가
 * 중복이 될 수도 있기 때문이다.
 * 
 * @author leegt80
 *
 */
public class SelectExternalConfirmerInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{

	private final String SELECT_EXTERNAL_CONFIRMER_INFO = "cert.select.external.confirmer.info";
	
	@Override
	protected String getAlias() {
		return SELECT_EXTERNAL_CONFIRMER_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = new SomCertBO();
		
		// 요청시 지점 번호가 없을 경우
		if ("0".equals(context.get("certSpotNo")) || "".equals(context.get("certSpotNo"))) {
			certBo.setSearchType("A");
			certBo.setOrdNo((Long) context.get("ordNo"));				// 주문번호
			certBo.setOrdPrdSeq((Long) context.get("ordPrdSeq"));	// 주문순번
		}
		// 요청시 지점 번호가 있을 경우
		else {
			certBo.setSearchType("B");
			certBo.setOrdNo((Long) context.get("ordNo"));										// 주문번호
			certBo.setOrdPrdSeq((Long) context.get("ordPrdSeq"));							// 주문순번
			certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 지점번호
		}
		
		return certBo;
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		SomCertBO certBo = null;
		
		if (context.get(ICommonConstants.PROCESS_RESULT) != null) {
			certBo = (SomCertBO) context.get(ICommonConstants.PROCESS_RESULT);
			
			context.put("certConfirmor", certBo.getUserNo());							// 인증처리자
			context.put("updateNo", certBo.getUserNo());								// 수정자
			context.put("certSpotNo", String.valueOf(certBo.getCertSpotNo()));		// 인증처리 지점번호
		}
		else {
			context.put("certConfirmor", Long.parseLong("0"));	// 인증처리자
			context.put("updateNo", Long.parseLong("0"));		// 수정자
			context.put("certSpotNo", "0");							// 인증처리 지점번호
		}
		
	}

}