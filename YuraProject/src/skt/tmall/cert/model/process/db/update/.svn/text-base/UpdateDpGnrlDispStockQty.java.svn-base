package skt.tmall.cert.model.process.db.update;

import java.util.HashMap;

import skt.tmall.cert.model.bean.ProductStockBo;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;

/**
 * 외부 인증번호 엑셀 업로드에 따른 상품 총재고수량 수정
 * 
 * @author leegt80
 *
 */
public class UpdateDpGnrlDispStockQty extends AbstractIbatisUpdateProcess {
	

	private final String UPDATE_CERT_DPGNRLDISP_STOCKQTY = "cert.update.dpGnrlDisp.stockQty";

	@Override
	protected String getAlias() {
		return UPDATE_CERT_DPGNRLDISP_STOCKQTY;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		ProductStockBo productStockBo = (ProductStockBo) context.get("productStockBo");	// 상품정보
		
		return productStockBo;
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		if (result <= 0) throw new ProcessException("해당 상품에 대한 상품 재고 수량 수정을 할 수 없습니다.");
		
	}

}
