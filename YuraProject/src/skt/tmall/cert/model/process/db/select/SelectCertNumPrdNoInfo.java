package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 해당 인증번호, 지점에 해당하는 상품번호 정보 조회
 * 상품번호가 파라미터로 넘어와 존재하면 상품번호도 쿼리 조건에 포함 시킨다.
 *
 * @author leegt80
 *
 */
public class SelectCertNumPrdNoInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{

	private final String SELECT_CERTNUM_PRDNO_INFO = "cert.select.certnum.prdNo.info";

	@Override
	protected String getAlias() {
		return SELECT_CERTNUM_PRDNO_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		SomCertBO certBo = new SomCertBO();

		certBo.setCertNo((String) context.get("certNo"));									// 인증번호
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 인증처리지점번호

		// 상품번호가 파라미터로 넘어와 존재하면 상품번호도 쿼리 조건에 포함 시킨다.
		if (context.get("prdNo") != null && !"".equals(context.get("prdNo"))) {
			certBo.setPrdNo(Long.parseLong((String) context.get("prdNo")));
		}else {
			certBo.setPrdNo(0);
		}

		return certBo;
	}

	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {

		SomCertBO certBo = (SomCertBO) context.get(ICommonConstants.PROCESS_RESULT);

		context.put("logCode", "");							// 인증처리 시도중 발생한 로그 코드
		context.put("confirmLogMsg", "");					// 인증처리 시도중 발생한 로그 메세지

		if (certBo == null) {
			context.put("logCode", "110");																				// 인증처리 시도중 발생한 로그 코드
			context.put("confirmLogMsg", "인증번호가 일치하지 않습니다. 다시 확인해주세요.");				// 인증처리 시도중 발생한 로그 메세지
			return;
		}

		String prdNo = String.valueOf(certBo.getPrdNo());

		context.put("prdNo", prdNo);						// 상품번호
		context.put("prdTypCd", certBo.getPrdTypCd());		// 상품구분코드

		if (context.get("prdNo") != null && !"".equals(context.get("prdNo"))) {
			if (!((String) context.get("prdNo")).equals(prdNo)) {
				context.put("logCode", "110");																			// 인증처리 시도중 발생한 로그 코드
				context.put("confirmLogMsg", "인증번호가 일치하지 않습니다. 다시 확인해주세요.");			// 인증처리 시도중 발생한 로그 메세지
				return;
			}
		}

		if(ISOMCommonConstants.CERT_STATUS_100.equals(certBo.getCertStat())){
			context.put("logCode", "110");
			context.put("confirmLogMsg", "인증불가. 입력하신 인증번호의 주문건이 존재하지 않습니다. 관리자에게 문의해주세요.");
		}else if(ISOMCommonConstants.CERT_STATUS_104.equals(certBo.getCertStat()) || ISOMCommonConstants.PRD_ORD_STAT_A01.equals(certBo.getOrdPrdStat()) ){
			context.put("logCode", "110");
			context.put("confirmLogMsg", "인증불가. 환불 처리된 인증번호입니다.");
		}

	}

}
