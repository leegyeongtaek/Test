package skt.tmall.cert.model.process.db.update;

import java.util.HashMap;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 인증 상태 완료 처리
 * 
 * 인증확인 주문번호,주문순번과 상점번호에 해당하는 데이터에 대하여
 * 인증을 처리한 지점번호,
 * 인증을 실제 지점이 했는지, 셀러가 했는지,
 * 인증상태(102: 인증완료), 
 * SMS 전송타입(103: 인증확인전송)
 * 등을 수정함.
 * @author leegt80
 *
 */
public class UpdateCoopCertStatusConfirm extends AbstractIbatisUpdateProcess {
	
	private final String CONFIRM_FAIL 			= "인증 확인 실패";
	
	private final String UPDATE_COOP_CERT_STATUS_CONFIRM = "cert.update.coopCertStatus.confirm";

	@Override
	protected String getAlias() {
		return UPDATE_COOP_CERT_STATUS_CONFIRM	;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		String sellerCertYn = "N";
		long certSpotNo 	= Long.parseLong((String) context.get("certSpotNo"));
		long updateNo		= (Long) context.get("updateNo");
		
		// 셀러인증처리여부 (인증처리지점과 인증을 처리하는 인원의 정보가 동일하지 않으면 Y, 같으면 N)
		if (certSpotNo != updateNo)	sellerCertYn = "Y";
		
		SomCertBO certBo = new SomCertBO();
		
		certBo.setOrdNo((Long) context.get("ordNo"));							// 주문번호
		certBo.setOrdPrdSeq((Long) context.get("ordPrdSeq"));				// 주문순번
		certBo.setCertNo((String) context.get("certNo"));						// 인증번호
		certBo.setShopNo((Long) context.get("shopNo"));						// 상점번호
		certBo.setCertSpotNo(certSpotNo);										// 인증처리지점번호
		certBo.setCertConfirmor((Long) context.get("certConfirmor"));		// 인증처리자
		certBo.setCertConfirmIp((String) context.get("certConfirmIp"));		// 인증처리 IP
		certBo.setSellerCertYn(sellerCertYn);										// 셀러인증처리여부
		certBo.setCertDt((String) context.get("certDt"));							// 인증처리일
		certBo.setCertStat((String) context.get("certStat"));						// 인증상태
		certBo.setSmsSendType((String) context.get("smsSendType"));		// SMS 전송타입
		certBo.setUpdateNo(updateNo);											// 수정자
		certBo.setUpdateDt((String) context.get("updateDt"));					// 수정일
		certBo.setUpdateIp((String) context.get("updateIp"));					// 수정자 IP
		
		return certBo;
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		// 인증 상태 설명
		context.put("statDesc", (result == 1) ?  ISOMCommonConstants.CERT_STATUS_DESC1 : CONFIRM_FAIL);
	}

}
