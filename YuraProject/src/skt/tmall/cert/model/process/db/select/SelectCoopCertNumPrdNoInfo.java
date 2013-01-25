package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import com.skt.omp.common.util.StringUtil;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

/**
 * 해당 주문번호, 주문순번, 지점에 해당하는 상품번호 정보 조회
 *
 * @author leegt80
 *
 */
public class SelectCoopCertNumPrdNoInfo extends AbstractIbatisSelectObjectProcess<Map<String, Object>>{

	private final String SELECT_COOP_CERTNUM_PRDNO_INFO = "cert.select.coopCertnum.prdNo.info";

	@Override
	protected String getAlias() {
		return SELECT_COOP_CERTNUM_PRDNO_INFO;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		SomCertBO certBo = new SomCertBO();

		certBo.setOrdNo((Long) context.get("ordNo"));								// 주문번호
		certBo.setOrdPrdSeq((Long) context.get("ordPrdSeq"));						// 주문순번
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));	// 인증처리지점번호

		return certBo;
	}

	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {

		SomCertBO certBo = (SomCertBO) context.get(ICommonConstants.PROCESS_RESULT);

		context.put("logCode", "");							// 인증처리 시도중 발생한 로그 코드
		context.put("confirmLogMsg", "");					// 인증처리 시도중 발생한 로그 메세지

		if (certBo != null) {

			String prdNo = String.valueOf(certBo.getPrdNo());

			if (context.get("prdNo") != null && !"".equals(context.get("prdNo"))) {
				if (!((String) context.get("prdNo")).equals(prdNo)) {

					context.put("logCode", "110");							// 인증처리 시도중 발생한 로그 코드
					context.put("confirmLogMsg", "해당 지점에서 인증번호로 조회한 상품번호와 인증처리할 상품번호가 일치하지 않습니다.");		// 인증처리 시도중 발생한 로그 메세지

				}
			}

			context.put("prdNo", prdNo);						// 상품번호
			context.put("tempCertNo", certBo.getCertNo());		// (임시)내부 인증번호
			context.put("prdTypCd", certBo.getPrdTypCd());		// 상품구분코드
		}
		else {
			context.put("logCode", "110");									// 인증처리 시도중 발생한 로그 코드
			context.put("confirmLogMsg", "해당 지점에서 인증번호로 인증처리 할 상품번호가 존재하지 않습니다.");		// 인증처리 시도중 발생한 로그 메세지
		}

		String certNoChk = StringUtil.nvl(context.get("certNo"), ""); 		//업체에서 전송한 인증번호 체크
		if(certNoChk.equals("") || certNoChk.length() < 6){
			context.put("logCode", "110");									// 인증처리 시도중 발생한 로그 코드
			context.put("confirmLogMsg", "인증번호는 6자리 이상이어야 합니다.");// 인증처리 시도중 발생한 로그 메세지
		}
	}
}
