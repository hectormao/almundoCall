package com.almundo.call.infra.bld;

import com.almundo.call.dom.bld.CallBuilder;
import com.almundo.call.dom.bld.EmployeeBuilder;
import com.almundo.call.dom.ent.Call;
import com.almundo.call.dom.ent.Employee;
import com.almundo.call.infra.ent.CallResponse;

/**
 * Class to build CallResponse objects (Apply DataBuilder Pattern)
 * @author hectormao
 *
 */
public class CallResponseBuilder {
	private Employee attendant = new EmployeeBuilder().build();
	private Call call = new CallBuilder().build();
	private String message = "A Test Message";
	
	public CallResponseBuilder withAttendant(Employee attendant) {
		this.attendant = attendant;
		return this;
	}
	
	public CallResponseBuilder withCall(Call call) {
		this.call = call;
		return this;
	}
	
	public CallResponseBuilder withMessage(String message) {
		this.message = message;
		return this;
	}
	
	public CallResponse build() {
		return new CallResponse(attendant, call, message);
	}

}
