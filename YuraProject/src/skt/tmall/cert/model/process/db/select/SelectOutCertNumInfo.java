package skt.tmall.cert.model.process.db.select;

import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * 인증번호 등록시 필요 내역 조회
 * 
 * @author leegt80
 *
 */
public class SelectOutCertNumInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{
	
	private final String SELECT_OUT_CERTNUM_INFO = "cert.select.out.certnum.info";
	
	@Override
	protected String getAlias() {
		return SELECT_OUT_CERTNUM_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		return Long.parseLong((String) context.get("prdNo"));		
	}
	
	/**
	 * 인증번호 등록시 필요 내역 조회 후 SomCertBO 에 설정
	 * @throws ProcessException 
	 */
	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {
			
		SomCertBO tempCertBo = (SomCertBO) context.get(ICommonConstants.PROCESS_RESULT);
		
		SomCertBO certBo = (SomCertBO) context.get("certBo");
		
		if (tempCertBo == null) throw new ProcessException("상품에 해당하는 인증 기본 정보가 존재하지 않습니다.");
		
		certBo.setCertType(tempCertBo.getCertType());		// 인증번호발생유형
		certBo.setShopNo(tempCertBo.getShopNo());		// 상점번호
		certBo.setCertExprDt(tempCertBo.getCertExprDt().substring(0, tempCertBo.getCertExprDt().indexOf(".")));	// 인증번호 만료 기간
		
		context.put("certBo", certBo);
		
	}

}