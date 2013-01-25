package skt.tmall.cert.model.process.db.insert;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisBatchProcess;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.StrUtil;

/**
 * 인증번호 처리에 따른 히스토리 등록
 * @author leegt80
 *
 */
public class InsertCertManageBatchHist extends AbstractIbatisBatchProcess {
	
	private Log log = LogFactory.getLog(InsertCertManageBatchHist.class);
	
	private final String INSERT_CERT_MANAGE_HIST = "cert.insert.manage.hist";
	
	@Override
	protected String getAlias() {
		return INSERT_CERT_MANAGE_HIST;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {
		
		SomCertBO certBo = new SomCertBO();
		certBo.setCertNo((String) context.get("certNo"));						// 인증번호
		certBo.setCertStat((String) context.get("certStat"));						// 인증상태
		certBo.setHistAplDt((String) context.get("histAplDt"));					// 이력 적용일
		certBo.setStatDesc(StrUtil.nvl((String) context.get("statDesc")));		// 상태설명
		certBo.setPrdNo(Long.parseLong((String) context.get("prdNo")));	// 상품번호
		
		return certBo;
		
	}
	
	@Override
	protected int modify(SqlMapClient sqlMapClient,
			HashMap<String, Object> context) {
		
		if (log.isDebugEnabled()) {
			log.debug(getAlias());
		}
		
		try {
			
			LinkedList<String> certStat = new LinkedList<String>();
			certStat.add(ISOMCommonConstants.CERT_STATUS_103);		// 인증취소
			certStat.add(ISOMCommonConstants.CERT_STATUS_101);		// 미인증
			
			for (String string : certStat) {
				context.put("certStat", string);
				sqlMapClient.update(getAlias(), getParameter(context));
			}
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		return 0;
	}
	
	@Override
	protected void processAfter(HashMap<String, Object> context) {
		
		int result = (Integer) context.get(ICommonConstants.PROCESS_RESULT);
		
		context.put(ICommonConstants.PROCESS_RESULT, (result >= 0) ? ISOMCommonConstants.PROCESS_RESULT_SUCCESS : ISOMCommonConstants.PROCESS_RESULT_FAIL);
	}
	
}
