package skt.tmall.common.web.resource;

import org.springframework.core.io.Resource;

/**
 * 단일 리소스를 그대로 출력하는(bypass) 처리기.
 * 
 * @author $Id: DefaultProcessor.java,v 1.1 2007/04/06 11:02:04 dsjang Exp $
 */
public class DefaultProcessor extends AbstractSingleResourceProcessor {

	/** CTOR. */
	public DefaultProcessor() {
	}

	/** CTOR. */
	public DefaultProcessor(Resource resource) {
		super(resource);
	}

}
