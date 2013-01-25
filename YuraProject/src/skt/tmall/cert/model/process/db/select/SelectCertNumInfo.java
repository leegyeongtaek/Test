package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.cert.model.process.db.update.UpdateCertStatusCancel;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 해당 인증번호 정보 조회
 * 
 * @author leegt80
 *
 */
public class SelectCertNumInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{
	
	private final String SELECT_CERTNUM_INFO = "cert.select.certnum.info";
	public static final String CERT_NUM_INFO = "_cert_num_info";
	
	@Override
	protected String getAlias() {
		return SELECT_CERTNUM_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		SomCertBO certBo = new SomCertBO();
		
		// 인증 상태 미인증 처리 후 return 값이 'N"일 경우 왜 실패했는지 해당 지점번호를 넣어 조회
		String result = (String) context.get(UpdateCertStatusCancel.CANCELYN);
		if ("Y".equals(result))	certBo.setSearchType("A");
		else certBo.setSearchType("B");
		
		if (context.get("prdNo") instanceof String) {
			certBo.setPrdNo(Long.parseLong((String) context.get("prdNo")));			// 상품번호
		}else if (context.get("prdNo") instanceof Long) {
			certBo.setPrdNo((Long) context.get("prdNo"));									// 상품번호
		}
		
		certBo.setCertNo((String) context.get("certNo"));									// 인증번호
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 인증처리지점번호
		
		return certBo;		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
			
		SomCertBO certBo = (SomCertBO) context.get(ICommonConstants.PROCESS_RESULT);
		
		if (certBo != null) {
			context.put(ICommonConstants.PROCESS_RESULT, ISOMCommonConstants.PROCESS_RESULT_SUCCESS);
			context.put(CERT_NUM_INFO, certBo);
		}
		else {
			context.put(ICommonConstants.PROCESS_RESULT, ISOMCommonConstants.PROCESS_RESULT_FAIL);
			context.put(CERT_NUM_INFO, null);
		}
	}

}