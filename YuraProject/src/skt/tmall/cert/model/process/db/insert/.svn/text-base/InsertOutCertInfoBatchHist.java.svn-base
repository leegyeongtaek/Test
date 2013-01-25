package skt.tmall.cert.model.process.db.insert;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisBatchProcess;
import skt.tmall.common.util.ISOMCommonConstants;

/**
 * 인증번호 등록 따른 히스토리 등록
 * @author leegt80
 *
 */
public class InsertOutCertInfoBatchHist extends AbstractIbatisBatchProcess {
	
	private Log log = LogFactory.getLog(InsertOutCertInfoBatchHist.class);
	
	private final String INSERT_CERT_MANAGE_HIST = "cert.insert.manage.hist";
	
	@Override
	protected String getAlias() {
		return INSERT_CERT_MANAGE_HIST;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = (SomCertBO) context.get("certBo");
		
		certBo.setCertNo((String) context.get("certNo"));	// 인증번호
		
		return certBo;
		
	}
	
	@Override
	protected int modify(SqlMapClient sqlMapClient,
			HashMap<String, Object> context) {
		
		if (log.isDebugEnabled()) {
			log.debug(getAlias());
		}
		
		try {
			
			// 외부 인증번호 배열
			String[] certNos = (String[]) context.get("certNos");
			
			for (String certNo : certNos) {
				context.put("certNo", certNo);
				sqlMapClient.update(getAlias(), getParameter(context));
			}
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		return 0;
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) throws ProcessException {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		if (result < 0) throw new ProcessException("해당 상품에 대한 외부 인증번호 등록에 실패 하였습니다.");
		else context.put(ICommonConstants.PROCESS_RESULT, ISOMCommonConstants.PROCESS_RESULT_SUCCESS);
	}
	
}
