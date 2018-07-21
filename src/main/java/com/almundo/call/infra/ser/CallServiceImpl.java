package com.almundo.call.infra.ser;


import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almundo.call.dom.ent.Call;
import com.almundo.call.dom.ent.Employee;
import com.almundo.call.dom.ent.Rank;
import com.almundo.call.dom.repo.CallRepository;
import com.almundo.call.dom.repo.EmployeeRepository;
import com.almundo.call.dom.utl.EmployeeState;
import com.almundo.call.infra.ent.CallResponse;
import com.almundo.call.infra.exc.CallNotFoundException;
import com.almundo.call.infra.exc.NoEmployeesException;

/**
 * Bean that processes the incoming and finish call
 * Assigns the free employees to call
 * Releases the employee to attend another calls
 * @author hectormao
 *
 */
@Service
public class CallServiceImpl implements CallService{
	
	private static final Logger logger = LoggerFactory.getLogger(CallServiceImpl.class);
	
	/**
	 * Unknown employee used when the real was removed
	 */
	private static final Employee UNKNOWN_EMPLOYEE = new Employee(
			"-1", 
			"-1", 
			"Unknown Employee", 
			new Rank(0, "Unknown"), 
			EmployeeState.FREE
			);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CallRepository callRepository;
	
	/**
	 * Process an incoming call
	 * Assign a free employee to call
	 * Create a new call
	 */
	public CallResponse incomingCall() {
		logger.info("Incoming call");
		Employee freeEmployee = employeeRepository.selectFreeEmployee();
		logger.info("Employee assigned {}", freeEmployee);
		return Optional
				.ofNullable(freeEmployee)				
				.map(this::createCall)
				.orElseThrow(NoEmployeesException::new);
	}
	/**
	 * Finish a call
	 * Release the employee
	 */
	public CallResponse hangCall(String callId) {
		logger.info("Hang a call: {}", callId);
		return callRepository
				.findById(callId)
				.filter(call -> Objects.isNull(call.getFinishDate()))
				.map(this::finishCall)
				.orElseThrow(() -> new CallNotFoundException(callId));
	}
	
	/**
	 * Finish a call
	 * Set the finish date to call
	 * release the employee
	 * @param call
	 * @return
	 */
	private CallResponse finishCall(Call call) {
		logger.info("finish the call");
		call.setFinishDate(new Date());
		callRepository.save(call);
		
		logger.trace("Set Finish Date to call: {}", call);
		
		Employee employee = employeeRepository
				.findById(call.getEmployeeId())
				.map(this::setFreeEmployee)
				.orElse(UNKNOWN_EMPLOYEE);
		CallResponse response = new CallResponse(employee, call, "your call was finished");
		
		logger.trace("Finish call response: {}", response);
		
		return response;
	}
	
	/**
	 * Set the Employee state to FREE
	 * @param employee
	 * @return
	 */
	private Employee setFreeEmployee(Employee employee) {
		employee.setState(EmployeeState.FREE);
		employeeRepository.save(employee);
		
		logger.trace("Set Employee state FREE: {}", employee);
		
		return employee;
	}
	/**
	 * Create a new call
	 * set Employee state to BUSY
	 * @param employee
	 * @return
	 */
	private CallResponse createCall(Employee employee) {
		
		Call call = callRepository
				.save(new Call(employee.getId(), new Date()));
		CallResponse response = new CallResponse(employee, call, "Your call is answered");
		logger.trace("New call response: {}", response);
		return response;
	}
	
}
