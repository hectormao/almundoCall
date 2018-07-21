package com.almundo.call.infra.ctl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.call.infra.ent.CallResponse;
import com.almundo.call.infra.ser.CallService;

/**
 * Rest Controller that exposes the call operations
 * @author hectormao
 *
 */
@RestController
@RequestMapping("/call")
public class CallController {
	
	private static final Logger logger = LoggerFactory.getLogger(CallController.class);
	
	@Autowired
	private CallService callService;
	
	/**
	 * start a new call
	 * @return
	 */
	@RequestMapping("/start")
	public CallResponse startCall() {
		logger.info("Receive a new call");
		return callService.incomingCall();
	}
	
	/**
	 * Finish an active call
	 * @param callId
	 * @return
	 */
	@RequestMapping("/finish/{callId}")
	public CallResponse finishCall(@PathVariable("callId") String callId) {
		logger.info("Hang the call: {}", callId);
		return callService.hangCall(callId);
	}

}
