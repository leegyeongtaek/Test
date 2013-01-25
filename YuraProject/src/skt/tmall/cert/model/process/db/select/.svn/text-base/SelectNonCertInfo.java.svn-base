package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectListProcess;

/**
 * 미인증 상품 내역 조회
 * 
 * @author leegt80
 *
 */
public class SelectNonCertInfo extends AbstractIbatisSelectListProcess<Map<String, Object>>{

	private final String SELECT_NONCERT_INFO = "cert.select.nonCert.info";
	
	@Override
	protected String getAlias() {
		return SELECT_NONCERT_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = new SomCertBO();
		
		certBo.setShopNo(Long.parseLong((String) context.get("shopNo")));	// 상점번호
		certBo.setStart(Long.parseLong((String)context.get("start")));				// 시작 페이지
		certBo.setEnd(Long.parseLong((String)context.get("end")));				// 종료 페이지
		
		return certBo;		
	}

}