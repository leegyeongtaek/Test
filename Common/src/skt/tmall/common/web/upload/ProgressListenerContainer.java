package skt.tmall.common.web.upload;

import java.util.Hashtable;

/**
 * @see CustomMultipartResolver
 * @author cshan
 * 
 */
public class ProgressListenerContainer {

	private Hashtable<String, CustomProgressListener> context = new Hashtable<String, CustomProgressListener>(
			3);

	public synchronized CustomProgressListener create(String token)
			throws Exception {
		CustomProgressListener customProgressListener = new CustomProgressListener(
				token);
		if (context.containsKey(token)) {
			throw new Exception("This token[" + token + "] already is exist!");
		}
		context.put(token, customProgressListener);
		return customProgressListener;
	}

	public CustomProgressListener get(String token) {
		return context.get(token);
	}

	public void remove(String token) {
		this.context.remove(token);
	}
}
