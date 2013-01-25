package skt.tmall.common.web.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * @author lllkt
 * XML Transform process Class
 */
	
public abstract class CreateAbstractXMLDocument implements CreateXMLDocument{
	private DocumentBuilderFactory factory		  = DocumentBuilderFactory.newInstance();

	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	
	
	private DocumentBuilder builder;
	
	private Transformer transformer;
	
	private DOMSource source;
	
	//xml을 String으로 리턴할 StringWriter
	private StringWriter writer;
	
	//StreamResult 객체 생성
	protected StreamResult getStreamResult() {
		return new StreamResult(writer);
	}
	
	//xml 출력 포맷 설정
	protected abstract void setTransformProperty(Transformer transformer);
	
	//document instance 구현
	protected abstract Document getDocument(DocumentBuilder builder, HashMap<String, Object> context) throws SAXException, IOException;
	
	//xml transform process
	public String resultIntoTreeXML(HashMap<String, Object> context) {
		try {
			writer = new StringWriter();
			
			factory.setIgnoringElementContentWhitespace(true);
			
			builder     = factory.newDocumentBuilder();
			
			transformer = transformerFactory.newTransformer();
			
			setTransformProperty(transformer);
			
			source = new DOMSource(getDocument(builder, context));
			
			transformer.transform(source, getStreamResult());
			
//			transformer.transform(source, new StreamResult(writer));
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}
	
//	//return XML Document
//	public String getXml() {
//		return writer.toString();
//	}
}
