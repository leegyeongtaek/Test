package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectListProcess;

/**
 * 인증번호 내역 히스토리 조회
 * 
 * @author leegt80
 *
 */
public class SelectCertNoHistory extends AbstractIbatisSelectListProcess<Map<String, Object>>{

	private final String SELECT_CERTNO_HISTORY = "cert.select.certno.history";
	
	@Override
	protected String getAlias() {
		return SELECT_CERTNO_HISTORY;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = new SomCertBO();
		
		certBo.setCertNo((String) context.get("certNo"));						// 인증번호
		return certBo;
		
	}

}