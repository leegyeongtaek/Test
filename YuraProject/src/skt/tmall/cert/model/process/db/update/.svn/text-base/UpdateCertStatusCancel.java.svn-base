package skt.tmall.cert.model.process.db.update;

import java.util.HashMap;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ICheckable;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 인증 상태 미인증 처리	(history 테이블(sm_cert_hist에는 인증취소(103) -> 미인증(101) 이 연속해서 들어감)
 *
 * 인증취소 인증번호와 인증을 처리한 지점번호에 해당하는 데이터에 대하여 인증상태를 미인증 처리로 돌림
 * 인증을 처리한 지점번호,
 * 인증상태(101: 미인증),
 * 등을 수정함.
 * @author leegt80
 *
 */
public class UpdateCertStatusCancel extends AbstractIbatisUpdateProcess {

	public final static String CANCELYN = "_cancelYn";

	private final String CONFIRM_FAIL 			= "인증 취소 실패";

	private final String UPDATE_CERT_STATUS_CANCEL = "cert.update.certStatus.cancel";

	public UpdateCertStatusCancel(){
		checkable = new ICheckable<HashMap<String,Object>>() {

			public boolean check(HashMap<String, Object> context)
					throws ProcessException {
				if ("110".equals(context.get("logCode"))) {

					// 인증 상태 설명
					context.put("statDesc", context.get("confirmLogMsg"));
					context.put(CANCELYN, "N");

					return false;
				}

				return true;
			}
		};
	}

	@Override
	protected String getAlias() {
		return UPDATE_CERT_STATUS_CANCEL;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {

		String sellerCertYn = "N";
		long certSpotNo 	= Long.parseLong((String) context.get("certSpotNo"));
		long updateNo		= (Long) context.get("updateNo");

		// 셀러인증처리여부 (인증취소처리지점과 인증 취소를 처리하는 인원의 정보가 동일하지 않으면 Y, 같으면 N)
		if (certSpotNo != updateNo)	sellerCertYn = "Y";

		SomCertBO certBo = new SomCertBO();

		certBo.setPrdNo(Long.parseLong((String)context.get("prdNo")));	// 상품번호
		certBo.setCertNo((String) context.get("certNo"));				// 인증번호
		certBo.setCertSpotNo(certSpotNo);								// 인증처리지점번호
		certBo.setCertStat((String) context.get("certStat"));			// 인증상태
		certBo.setSmsSendType((String) context.get("smsSendType"));		// SMS 전송타입
		certBo.setUpdateNo(updateNo);									// 수정자
		certBo.setUpdateDt((String) context.get("updateDt"));			// 수정일
		certBo.setUpdateIp((String) context.get("updateIp"));			// 수정자 IP
		certBo.setSellerCertYn(sellerCertYn);							// 셀러인증취소처리여부

		return certBo;

	}

	@Override
	protected void processAfter(HashMap<String, Object> context) {

		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);

		// 인증 상태 설명
		context.put("statDesc", (result == 1) ?  ISOMCommonConstants.CERT_STATUS_DESC2 : CONFIRM_FAIL);
		context.put(CANCELYN, (result == 1) ?  "Y" : "N");
	}

}