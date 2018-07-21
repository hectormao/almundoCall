package com.almundo.call.infra.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * No active call found
 * @author hectormao
 *
 */
@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class CallNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CallNotFoundException(String callId) {
		super("The active call with id: [" + callId + "] not exists");
	}

}
