package skt.tmall.cert.model.process.db.insert;

import java.util.HashMap;

import skt.tmall.cert.model.bean.ProductStockBo;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 외부 인증번호 엑셀 업로드에 따른 상품 판매상태 히스토리 등록
 * @author leegt80
 *
 */
public class InsertPdPrdStatHist extends AbstractIbatisUpdateProcess {
	
	private final String INSERT_PDPRD_STAT_HIST = "cert.insert.pdPrdStat.hist";
	
	public InsertPdPrdStatHist() {
		checkable = new ICheckable<HashMap<String,Object>>() {
			
			public boolean check(HashMap<String, Object> context)
					throws ProcessException {
				
				if (ISOMCommonConstants.PROCESS_RESULT_SUCCESS.equals(context.get(ICommonConstants.PROCESS_RESULT))) {
					return true;
				}
				
				return false;
			}
		};
	}
	
	@Override
	protected String getAlias() {
		return INSERT_PDPRD_STAT_HIST;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		ProductStockBo productStockBo = (ProductStockBo) context.get("productStockBo");	// 상품정보
		
		return productStockBo;
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		context.put(ICommonConstants.PROCESS_RESULT, (result == 1) ? ISOMCommonConstants.PROCESS_RESULT_SUCCESS : ISOMCommonConstants.PROCESS_RESULT_FAIL);
	}
	
}
