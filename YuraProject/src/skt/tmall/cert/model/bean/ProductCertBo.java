package skt.tmall.cert.model.bean;

public class ProductCertBo {
	
	private long num;				// 조회번호
	private long shopNo;			// 상점번호
	private long ordNo;			// 주문번호
	private long prdNo;			// 상품번호
	private String prdNm;			// 상품명
	private long selPrc;				// 상품가격
	private long ordPrdWonStl;	// 주문가격
	private String ordDt;			// 주문일
	private String stlNm;			// 결제수단
	private String buyerNm;		// 구매자
	private String smsTelNo;		// 전화번호
	private String certStat;			// 인증상태
	private String certNo;			// 인증번호
	private long totalCount;		// 전체 조회수
	private long start;				// 시작 페이지
	private long end;				// 종료 페이지
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public long getShopNo() {
		return shopNo;
	}
	public void setShopNo(long shopNo) {
		this.shopNo = shopNo;
	}
	public long getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(long ordNo) {
		this.ordNo = ordNo;
	}
	public long getPrdNo() {
		return prdNo;
	}
	public void setPrdNo(long prdNo) {
		this.prdNo = prdNo;
	}
	public String getPrdNm() {
		return prdNm;
	}
	public void setPrdNm(String prdNm) {
		this.prdNm = prdNm;
	}
	public long getSelPrc() {
		return selPrc;
	}
	public void setSelPrc(long selPrc) {
		this.selPrc = selPrc;
	}
	public long getOrdPrdWonStl() {
		return ordPrdWonStl;
	}
	public void setOrdPrdWonStl(long ordPrdWonStl) {
		this.ordPrdWonStl = ordPrdWonStl;
	}
	public String getOrdDt() {
		return ordDt;
	}
	public void setOrdDt(String ordDt) {
		this.ordDt = ordDt;
	}
	public String getStlNm() {
		return stlNm;
	}
	public void setStlNm(String stlNm) {
		this.stlNm = stlNm;
	}
	public String getBuyerNm() {
		return buyerNm;
	}
	public void setBuyerNm(String buyerNm) {
		this.buyerNm = buyerNm;
	}
	public String getSmsTelNo() {
		return smsTelNo;
	}
	public void setSmsTelNo(String smsTelNo) {
		this.smsTelNo = smsTelNo;
	}
	public String getCertStat() {
		return certStat;
	}
	public void setCertStat(String certStat) {
		this.certStat = certStat;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}

}
