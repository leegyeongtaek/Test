package skt.tmall.cert.model.web.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import skt.tmall.cert.model.bean.SomCertApiBO;
import skt.tmall.common.annotation.DelegateSupport;
import skt.tmall.common.core.ICommonConstants;
import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.util.DelegateUtils;
import skt.tmall.common.util.ISOMCommonConstants;
import skt.tmall.common.util.ObjectUtil;

public class SelectAPIDelegate {
	
	@DelegateSupport(webParameters = {"certNo"},
			processName = "cert.select.selectCertNumStat", processReferenceKey = "process")
	public void certNumStat(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> context)
	throws ProcessException, IOException {
		SomCertApiBO result = null;

		try {

			// request body XML Unmarshalling...
			BufferedReader reader = new BufferedReader(request.getReader());

			// XML to SomCertBO unmarshalling
			SomCertApiBO certApiBo = readObject(reader);
			
			context.put("certNo", certApiBo.getCertNo());
			
			DelegateUtils.defaultSelectProcess(context, "process");

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);

			e.printStackTrace(pw);

			throw new ProcessException(e);

		} finally {
			result = (SomCertApiBO) context.get(ICommonConstants.PROCESS_RESULT);
			Exception exception = (Exception) context.get("error");
			
			if(exception != null) {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);

				exception.printStackTrace(pw);

				result = new SomCertApiBO();
				result.setResultCode(ISOMCommonConstants.ERROR);
				result.setResultMessage("시스템 오류 발생.");
			}
			
			if (result != null) {
				result.setResultCode(ISOMCommonConstants.SUCCESS);
				result.setResultMessage("인증번호 상태조회 성공.");
			} else {
				result = new SomCertApiBO();
				if (context.get("certNo") == null) {
					result.setResultCode(ISOMCommonConstants.FAIL_NOTSET_CERTNO);
					result.setResultMessage("인증번호 미설정.");
				} else {
					result.setResultCode(ISOMCommonConstants.SUCCESS_NO_SEARCH_DATA);
					result.setResultMessage("인증번호 상태조회 성공(조회 결과 0건).");
				}
			}
			
			response.setHeader("Content-Type", "text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			try {
				// ClientMessage to XML marshalling
				writeXML(result, response.getOutputStream());
			} catch (JAXBException e) {
				throw new ProcessException(e);
			}

			response.flushBuffer();
		}
	}
	
	// XML to SomCertBO unmarshalling
	public static SomCertApiBO readObject(Reader reader) throws JAXBException{

		JAXBContext jaxbContext = JAXBContext.newInstance(SomCertApiBO.class);

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		SomCertApiBO certApiBo = (SomCertApiBO) unmarshaller.unmarshal(reader);

		return certApiBo;
	}

	// ClientMessage to XML marshalling
	public static void writeXML(SomCertApiBO somCertApiBO, ServletOutputStream servletOutputStream) throws JAXBException{

		JAXBContext jaxbContext = JAXBContext.newInstance(SomCertApiBO.class);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshaller.marshal(somCertApiBO, servletOutputStream);
	}
}
