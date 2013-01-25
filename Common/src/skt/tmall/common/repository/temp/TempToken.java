package skt.tmall.common.repository.temp;

import java.util.UUID;

import skt.tmall.common.repository.IToken;

/**
 * 
 * @author cshan
 *
 */
public class TempToken implements IToken {

	private String uuid;

	public TempToken() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public TempToken(String token) {
		this.uuid = token;
	}

	public String getID() {
		return uuid;
	}

}
