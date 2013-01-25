package skt.tmall.cert.model.process.db.update;

import java.util.HashMap;

import skt.tmall.common.model.process.ibatisDb.AbstractIbatisUpdateProcess;

/**
 * 외부 인증번호 인증에 따라 기존 (임시) 내부 인증번호를 실제 외부 인증번호로 수정
 * 히스토리 정보 수정임. 
 * 
 * @author leegt80
 *
 */
public class UpdateCertManageHist extends AbstractIbatisUpdateProcess {
	
	private final String UPDATE_CERT_MANAGE_HIST = "cert.update.manage.hist";

	@Override
	protected String getAlias() {
		return UPDATE_CERT_MANAGE_HIST;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		return  context;	
	}

}
