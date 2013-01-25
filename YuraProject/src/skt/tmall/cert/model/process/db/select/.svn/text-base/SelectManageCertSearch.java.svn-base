package skt.tmall.cert.model.process.db.select;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skt.omp.common.util.StringUtil;

import skt.tmall.cert.model.bean.SomCertBO;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.process.ibatisDb.AbstractIbatisSelectListProcess;
import skt.tmall.common.util.StrUtil;

/**
 * BO 인증관리 조회 페이지에서 필수조건(인증시각/인증번호/상품번호)+부가조건에 따라 인증정보 조회
 *
 * ConditionType(검색조건)이 1: 인증시각, 2: 상품번호, 3: 인증번호
 * @author leegt80
 *
 */
public class SelectManageCertSearch extends AbstractIbatisSelectListProcess<Map<String, Object>>{

	private final String SELECT_MANAGECERT_SEARCH = "cert.select.managecert.search";

	@Override
	protected String getAlias() {
		return SELECT_MANAGECERT_SEARCH;
	}

	@Override
	public Object getParameter(HashMap<String, Object> context) {

		SomCertBO certBo = new SomCertBO();

		boolean isCertDt = false;
		String conditionType = "";

		// 검색구분이 인증시각이 있을 경우 조회쿼리 상태 타입을 3으로 설정
        String certDt			= StrUtil.nvl((String) context.get("certDt"));
        String certDtTo 		= StrUtil.nvl((String) context.get("certDtTo"));
        String prdNoBox 	= "";

        if (!"".equals(certDt) && !"".equals(certDtTo)) {
            isCertDt = true;
        }

		try {
			prdNoBox = URLDecoder.decode((String) context.get("prdNo"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 상품번호 설정
        if(prdNoBox!=null || !"".equals(prdNoBox)) {

            String chg = prdNoBox.replaceAll("\n", "!@!");
            chg = chg.replaceAll("\r", "");
            String[] prdNo = StringUtil.split(chg, "!@!");
            List<Long> prdNoList = new ArrayList<Long>();
            if(prdNo.length > 1){
            	String temp = "";
            	for(int i=0; i<prdNo.length; i++) {
                    if (prdNo[i] != null) {
                    	temp = prdNo[i].trim();
                        if (!"".equals(temp)) prdNoList.add(Long.parseLong(temp));
                    }
                }

            	certBo.setPrdNo(0);
                certBo.setPrdNoList(prdNoList);

                // 검색구분이 상품번호가 있을 경우 조회쿼리 상태타입을 1로 설정
                conditionType = "1";
            } else if (prdNo.length == 1) {
            	certBo.setPrdNo(Long.parseLong(prdNo[0]));

            	if (!"0".equals(prdNo[0])) conditionType = "1";
            }

        }

        // 검색구분이 인증번호가 있을 경우 조회쿼리 상태 타입을 2로 설정
        String certNo = StrUtil.nvl((String) context.get("certNo"));
        if ("".equals(conditionType) && !"".equals(certNo)) {
        	conditionType = "2";
        }

        // 주문번호
        String ordNo = StrUtil.nvl((String) context.get("ordNo"));

        String buyerNm = StrUtil.nvl((String) context.get("buyerNm"));
        String smsTelNo	= StrUtil.nvl((String) context.get("telNo"));

        // 인증일자 & 주문자명 & 전화번호 뒤 4자리
        if ("".equals(conditionType) && isCertDt && !"".equals(buyerNm) && !"".equals(smsTelNo)) {
        	conditionType = "3";
        }

        String shopNo 			= StrUtil.nvl((String) context.get("shopNo"), "0");			// 상점번호
        String shopBranchNo 	=  StrUtil.nvl((String) context.get("shopBranchNo"), "0");	// 지점번호

        shopNo 			= ("".equals(shopNo)) ? "0" : shopNo;
        shopBranchNo 	= ("".equals(shopBranchNo)) ? "0" : shopBranchNo;

        // 인증일자 & 상점번호
        if ("".equals(conditionType) && isCertDt && !"0".equals(shopNo)) {
        	conditionType = "4";
        }

        // 인증일자 & 지점번호
        if ("".equals(conditionType) && isCertDt && !"0".equals(shopBranchNo)) {
        	conditionType = "5";
        }

        String certStat = StrUtil.nvl((String) context.get("certStat"));

        // 인증일자 & 인증상태
        if ("".equals(conditionType) && isCertDt && !"".equals(certStat)) {
        	conditionType = "6";
        }

        certBo.setConditionType(conditionType);								// 검색조건타입
        certBo.setCertNo(certNo);											// 인증번호
        if(ordNo != null && !ordNo.equals("")){
        	certBo.setOrdNo(Long.parseLong(ordNo));							// 주문번호
        }
        certBo.setCertDtS(certDt);											// 인증시각 시작일
        certBo.setCertDtE(certDtTo);										// 인증시각 종료일
        certBo.setCertStat(certStat);										// 인증상태
		certBo.setBuyerNm(buyerNm);											// 구매자명
		certBo.setSmsTelNo(smsTelNo);										// 구매자 전화번호 뒤 4자리
		certBo.setShopNo(Long.valueOf(shopNo));								// 상점번호
		certBo.setShopBranchNo(Long.valueOf(shopBranchNo));					// 지점번호
		certBo.setStart(Long.parseLong((String)context.get("start")));		// 시작 페이지
		certBo.setEnd(Long.parseLong((String)context.get("end")));			// 종료 페이지
		certBo.setOrdPrdStat(StringUtil.nvl(context.get("ordPrdStat"), ""));// 주문상태

		return certBo;

	}

	@Override
	protected void processBefore(HashMap<String, Object> context) throws ProcessException {
		// 구매자 이름과 전화번호가 쌍으로 묶이는지 check
        String buyerNm = (String) context.get("buyerNm");
        String telNo 		= (String) context.get("telNo");
        // 인증 시각이 쌍으로 묶이는지 check
        String certDt		= (String) context.get("certDt");
        String certDtTo 	= (String) context.get("certDtTo");

        if ((!"".equals(buyerNm) && "".equals(telNo)))	throw new ProcessException("구매자 검색조건에 전화번호 뒤 4자리가 누락되었습니다.");
        else if (("".equals(buyerNm) && !"".equals(telNo)))	throw new ProcessException("구매자 검색조건에 이름이 누락되었습니다.");
        else if ((!"".equals(certDt) && "".equals(certDtTo)))	throw new ProcessException("인증시각 검색조건에 인증시각 종료일이 누락되었습니다.");
        else if (("".equals(certDt) && !"".equals(certDtTo)))	throw new ProcessException("인증시각 검색조건에 인증시각 시작일이 누락되었습니다.");
	}

}