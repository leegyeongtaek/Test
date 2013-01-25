package skt.tmall.cert.model.process.db.update;

import java.util.HashMap;

import skt.tmall.cert.model.bean.ProductStockBo;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 외부 인증번호 엑셀 업로드에 따른 상품 판매상태 수정
 * 품절 상태인 상품에 대해서만 판매중 상태로 변경 
 * 
 * @author leegt80
 *
 */
public class UpdateDpGnrlDispSelStatCd extends AbstractIbatisUpdateProcess {
	

	private final String UPDATE_CERT_DPGNRLDISPSELSTATCD_103 = "cert.update.dpGnrlDispSelStatCd.103";

	@Override
	protected String getAlias() {
		return UPDATE_CERT_DPGNRLDISPSELSTATCD_103;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		ProductStockBo productStockBo = (ProductStockBo) context.get("productStockBo");	// 상품정보
		
		return productStockBo;
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		if (result > 0) context.put(ICommonConstants.PROCESS_RESULT, ISOMCommonConstants.PROCESS_RESULT_SUCCESS);
		
	}

}
