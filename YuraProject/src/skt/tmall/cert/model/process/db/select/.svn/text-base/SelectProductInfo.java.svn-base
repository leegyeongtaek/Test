package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.ProductBo;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * 상품 정보 조회
 * 
 * @author leegt80
 *
 */
public class SelectProductInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{

	private final String SELECT_PRODUCT_INFO = "cert.select.product.info";
	
	@Override
	protected String getAlias() {
		return SELECT_PRODUCT_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		return Long.parseLong((String) context.get("prdNo"));		// 상품번호
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		ProductBo productBo = null;
		
		if (context.get(ICommonConstants.PROCESS_RESULT) != null) {
			productBo = (ProductBo) context.get(ICommonConstants.PROCESS_RESULT);
		}else {
			productBo = new ProductBo();
		}
		
		context.put("productBo", productBo);
		
	}

}