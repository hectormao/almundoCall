package com.almundo.call.infra.ser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.almundo.call.dom.bld.CallBuilder;
import com.almundo.call.dom.bld.EmployeeBuilder;
import com.almundo.call.dom.bld.RankBuilder;
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
 * Test to call service bean
 * @author hectormao
 *
 */
@RunWith(SpringRunner.class)
public class CallServiceTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public CallService callService() {
            return new CallServiceImpl();
        }
    }
	
	@Autowired
	private CallService callService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private CallRepository callRepository;
	
	/**
	 * Test when an incomming call was sucessfully
	 * 1. build a free employee
	 * 2. build a call
	 * 3. mock repository methods
	 * 4. call to incoming call mehtod
	 * 5. check if the call is asigned to employee
	 */
	@Test
	public void incommingCallSuccessfullyTest() {
		
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
		
		Call incomingCall = new CallBuilder()
				.withId("1234AA")
				.withEmployeeId(aFreeEmployee.getId())
				.withStartDate(new Date())
				.build();
		
		Mockito.when(employeeRepository.selectFreeEmployee())
			.thenReturn(aFreeEmployee);
		
		Mockito.when(callRepository.save(ArgumentMatchers.any()))
		.thenReturn(incomingCall);
		
		
		CallResponse response = callService.incomingCall();
		
		assertEquals(aFreeEmployee.getIdentity(), response.getAttendant().getIdentity());
		assertEquals(incomingCall.getId(), response.getCall().getId());
		
	}
	
	/**
	 * Test when no employees available
	 * 1. Movk repository method to return a null employee
	 * 2. call to incoming call method
	 * 3. check if the call launches an exception
	 */
	@Test(expected = NoEmployeesException.class)
	public void incommingCallNotEmployeesAvailableTest() {
		Mockito.when(employeeRepository.selectFreeEmployee())
			.thenReturn(null);
		callService.incomingCall();
		fail("The test must generate a NoEmployeesException");
	}
	
	/**
	 * Test a successfully hang call
	 * 1. mock the findById call method
	 * 2. mock the save call method
	 * 3. mock the findById employee method
	 * 4. call a hangCall method
	 * 5. check if the response has the mock employee id and mock call id
	 * @throws Exception
	 */
	@Test
	public void hangCallSuccessfullyTest() {
		
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
		
		Call finishedCall = new CallBuilder()
				.withId("1234AA")
				.withEmployeeId(aFreeEmployee.getId())
				.withStartDate(new Date())
				.withFinishDate(new Date())
				.build();
		
		Mockito.when(callRepository
				.findById(ArgumentMatchers.anyString()))
			.thenReturn(Optional.of(call));
		
		Mockito.when(callRepository.save(ArgumentMatchers.any()))
			.thenReturn(finishedCall);
		
		Mockito.when(employeeRepository.findById(ArgumentMatchers.anyString()))
			.thenReturn(Optional.of(aFreeEmployee));
		
		CallResponse response = callService.hangCall(call.getId());
		
		assertEquals(aFreeEmployee.getIdentity(), response.getAttendant().getIdentity());
		assertEquals(call.getId(), response.getCall().getId());
		
	}
	
	/**
	 * Test a failed hang call
	 * 1. mock the findById call method, returning a CallNotFoundException
	 * 2. check if the call to hangCall method launched a CallNotFoundException
	 * @throws Exception
	 */
	@Test(expected=CallNotFoundException.class)
	public void hangCallNotExistingCallTest() {
		Mockito.when(callRepository
				.findById(ArgumentMatchers.anyString()))
			.thenReturn(Optional.ofNullable(null));
		
		callService.hangCall("AnyId");
		fail("The test must generate a CallNotFoundException");
	}
	
	/**
	 * Test a failed hang call
	 * 1. mock the findById call method, returning a call with finishDate
	 * 2. check if the call to hangCall method launched a CallNotFoundException
	 * @throws Exception
	 */
	@Test(expected=CallNotFoundException.class)
	public void hangCallTerminatedCallTest() {
		
		Call call = new CallBuilder()
				.withId("1234AA")
				.withEmployeeId("EmployeeID")
				.withStartDate(new Date())
				.withFinishDate(new Date())
				.build();
		
		Mockito.when(callRepository
				.findById(ArgumentMatchers.anyString()))
			.thenReturn(Optional.ofNullable(call));
		
		callService.hangCall("AnyId");
		fail("The test must generate a CallNotFoundException");
	}
	

}
