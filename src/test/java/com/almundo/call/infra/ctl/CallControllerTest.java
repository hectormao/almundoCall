package com.almundo.call.infra.ctl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.almundo.call.dom.bld.CallBuilder;
import com.almundo.call.dom.bld.EmployeeBuilder;
import com.almundo.call.dom.bld.RankBuilder;
import com.almundo.call.dom.ent.Call;
import com.almundo.call.dom.ent.Employee;
import com.almundo.call.dom.ent.Rank;
import com.almundo.call.dom.utl.EmployeeState;
import com.almundo.call.infra.bld.CallResponseBuilder;
import com.almundo.call.infra.ent.CallResponse;
import com.almundo.call.infra.exc.CallNotFoundException;
import com.almundo.call.infra.exc.NoEmployeesException;
import com.almundo.call.infra.ser.CallService;

/**
 * Test the Call Controller bean
 * @author hectormao
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CallController.class)
public class CallControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CallService callService;
	
	/**
	 * Test a successfully incoming call
	 * 1. mock the call service  incoming call method
	 * 2. make http get call to /call/start
	 * 3. check if the response has 200 status
	 * @throws Exception
	 */
	@Test
	public void callIncomingCallSuccessfullyTest() throws Exception{
		
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		Employee aFreeEmployee = new EmployeeBuilder()
				.withId("1234ID")
				.withIdentity("1234")
				.withName("Free Employee")
				.withRank(opeatorRank)
				.withState(EmployeeState.FREE)
				.build();
		
		Call call = new CallBuilder()
				.withId("1234AA")
				.withEmployeeId(aFreeEmployee.getId())
				.withStartDate(new Date())
				.withFinishDate(null)
				.build();
		
		CallResponse callResponse = new CallResponseBuilder()
				.withCall(call)
				.withAttendant(aFreeEmployee)
				.withMessage("My own message")
				.build();
		
		Mockito.when(callService.incomingCall())
			.thenReturn(callResponse);
		mvc
			.perform(
				get("/call/start")
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andExpect(content()
					.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
					);
		
	}
	
	/**
	 * Test a failed incoming call causes by No employees available
	 * 1. mock the call service incoming call method, launching NoEmployeeException
	 * 2. make http get call to /call/start
	 * 3. check if the response has 404 status
	 * @throws Exception
	 */
	@Test
	public void callIncomingCallReceiveNotEmployessExceptionFromServiceTest() throws Exception{
		
		Mockito.when(callService.incomingCall())
			.thenThrow(new NoEmployeesException());
		mvc
			.perform(
				get("/call/start")
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isNotFound());
		
	}
	
	/**
	 * Test a successfully hang call
	 * 1. mock the call service hangCall method 
	 * 3. make http get call to /call/finish
	 * 4. check if the response has 200 status
	 * @throws Exception
	 */
	@Test
	public void callHangCallSuccessfullyTest() throws Exception{
		
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		Employee aFreeEmployee = new EmployeeBuilder()
				.withId("1234ID")
				.withIdentity("1234")
				.withName("Free Employee")
				.withRank(opeatorRank)
				.withState(EmployeeState.FREE)
				.build();
		
		Call call = new CallBuilder()
				.withId("1234AA")
				.withEmployeeId(aFreeEmployee.getId())
				.withStartDate(new Date())
				.withFinishDate(new Date())
				.build();
		
		CallResponse callResponse = new CallResponseBuilder()
				.withCall(call)
				.withAttendant(aFreeEmployee)
				.withMessage("My own message")
				.build();
		
		Mockito.when(callService.hangCall(ArgumentMatchers.anyString()))
			.thenReturn(callResponse);
		mvc
			.perform(
				get("/call/finish/" + call.getId())
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andExpect(content()
					.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
					);
		
	}
	
	/**
	 * Test a failed incoming call causes by No employees available
	 * 1. mock the call service hang call method, launching CallNotFoundException
	 * 2. make http get call to /call/finish
	 * 3. check if the response has 404 status
	 * @throws Exception
	 */
	@Test
	public void callHangCallReceiveCallNotFoundExceptionFromServiceTest() throws Exception{
		
		final String callId = "AA4321";
		
		Mockito.when(callService.hangCall(ArgumentMatchers.anyString()))
			.thenThrow(new CallNotFoundException(callId));
		mvc
			.perform(
				get("/call/finish/" + callId)
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isNotFound());
		
	}
	
	
	
}
