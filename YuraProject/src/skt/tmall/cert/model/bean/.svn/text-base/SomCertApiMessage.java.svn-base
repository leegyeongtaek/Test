package skt.tmall.cert.model.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SomCertApiMessage")
public class SomCertApiMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resultCode 	= "";		// 인증 API 응답 코드
	private String resultMsg 	= "";		// 인증 API 상세 응답 메세지
	private String resultData 	= "";		// 인증 API 응답 데이터

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return "ClientMessage [resultMsg=" + resultMsg + ", resultData=" + resultData + ", resultCode=" + resultCode + "]";
	}
}
