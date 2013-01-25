package skt.tmall.cert.model.process.db.select;
import java.util.HashMap;
import java.util.Map;

import com.skt.omp.common.util.StringUtil;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectListProcess;

/**
 * 바로인증 조회 페이지에서 최근 인증처리한 5건 조회
 *
 * 해당 지점의 최근 인증완료 5건 조회
 * 인증상태 (102:인증완료상태)
 * 주문상태 (501:배송완료, 601: 클레임진행중, 901: 구매확정)
 * 인증만료일 (현재 인증 가능 여부 조회일보다 크거나 같아야 함)
 * @author leegt80
 *
 */
public class SelectNowCertSearch extends AbstractIbatisSelectListProcess<Map<String, Object>>{

	private final String SELECT_NOWCERT_SEARCH = "cert.select.nowcert.search";

	@Override
	protected String getAlias() {
		return SELECT_NOWCERT_SEARCH;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {

		// 인증 처리 지점
		SomCertBO certBo = new SomCertBO();
		certBo.setCertSpotNo(Long.parseLong((String) context.get("certSpotNo")));
		certBo.setOrdPrdStat(StringUtil.nvl(context.get("ordPrdStat"), ""));
		return certBo;

	}

}