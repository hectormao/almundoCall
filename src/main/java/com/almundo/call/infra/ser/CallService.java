package com.almundo.call.infra.ser;

import com.almundo.call.infra.ent.CallResponse;
/**
 * Contract to Call Service
 * @author hectormao
 *
 */
public interface CallService {
	/**
	 * Process an incoming call
	 * @return
	 */
	public CallResponse incomingCall();
	
	/**
	 * Process a hang call
	 * @param callId
	 * @return
	 */
	public CallResponse hangCall(String callId);
	
}
