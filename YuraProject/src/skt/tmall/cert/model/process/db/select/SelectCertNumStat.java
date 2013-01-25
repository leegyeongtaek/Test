package skt.tmall.cert.model.process.db.select;

import java.util.HashMap;
import java.util.Map;

import skt.tmall.cert.model.bean.SomCertApiBO;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectObjectProcess;

public class SelectCertNumStat extends AbstractIbatisSelectObjectProcess<Map<String, Object>> {

	public static final String SELECT_CERTNUM_STAT = "cert.select.certnum.stat";
	
	@Override
	protected String getAlias() {
		return SELECT_CERTNUM_STAT;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		SomCertApiBO certApiBo = new SomCertApiBO();
		certApiBo.setCertNo((String) context.get("certNo"));
		return certApiBo;
	}
}
