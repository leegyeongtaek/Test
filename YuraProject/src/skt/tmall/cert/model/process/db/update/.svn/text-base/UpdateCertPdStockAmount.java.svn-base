package skt.tmall.cert.model.process.db.update;

import java.util.HashMap;

import skt.tmall.cert.model.bean.ProductStockBo;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;

/**
 * 외부 인증번호 엑셀 업로드에 따른 상품 재고 수량 수정
 * 
 * 인증번호 관리 테이블에 해당 상품에 관련된 인증번호가 존재하지 않고
 * 해당 셀러 인증유형 타입이 '101'(외부인증(엑셀업로드)) 이면서
 * 재고 수량이 0일 경우 해당 상품의 재고수량을 엑셀 업로드 데이터 수량 만큼 수정.
 * 외부 인증번호 엑셀 업로드에 따른 상품 재고 수량 수정
 * update 실패 시 Exception 처리.
 * 
 * @author leegt80
 *
 */
public class UpdateCertPdStockAmount extends AbstractIbatisUpdateProcess {
	
	private final String UPDATE_CERT_PDSTOCK_AMOUNT = "cert.update.certPdStock.amount";

	@Override
	protected String getAlias() {
		return UPDATE_CERT_PDSTOCK_AMOUNT;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		ProductStockBo productStockBo = new ProductStockBo();
		productStockBo.setPrdNo(Long.parseLong((String) context.get("prdNo")));		// 상품번호
		productStockBo.setStckQty(Long.parseLong((String) context.get("stckQty")));	// 외부 인증번호 수량 (재고수량)
		productStockBo.setUpdateNo((Long) context.get("updateNo"));					// 수정자
		
		context.put("productStockBo", productStockBo);	// 상품정보
		
		return productStockBo;
		
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		if (result <= 0) throw new ProcessException("해당 상품에 대한 상품 재고 수량 수정을 할 수 없습니다.");
		
	}

}
