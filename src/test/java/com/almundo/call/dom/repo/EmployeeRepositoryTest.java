package com.almundo.call.dom.repo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.almundo.call.dom.bld.EmployeeBuilder;
import com.almundo.call.dom.bld.RankBuilder;
import com.almundo.call.dom.ent.Employee;
import com.almundo.call.dom.ent.Rank;
import com.almundo.call.dom.utl.EmployeeState;
/**
 * Test to check the select free employee operation
 * @author hectormao
 *
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@After
	public void clearData() {
		employeeRepository.deleteAll();
	}
	
	/**
	 * Test when exists one operator free
	 * 1. save a free operator in MongoDB
	 * 2. call selectFreeEmployee method
	 * 3. check if the response is the saved employee
	 */
	@Test
	public void selectFreeEmployeeWithOneOperatorFreeTest() {
		
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		Employee aFreeEmployee = new EmployeeBuilder()
				.withId("ThisIsMyId")
				.withIdentity("1234")
				.withName("Free Employee")
				.withRank(opeatorRank)
				.withState(EmployeeState.FREE)
				.build();
		
		employeeRepository.save(aFreeEmployee);
		
		Employee selectedEmployee = employeeRepository.selectFreeEmployee();
		assertNotNull(selectedEmployee);
		assertNotNull(aFreeEmployee.getIdentity(), selectedEmployee.getIdentity());
	}
	
	/**
	 * Test when exists one operator and supervisor free, return operator
	 * 1. insert an operator
	 * 2. insert a supervisor
	 * 3. call to selectFreeEmployee method
	 * 4. check if a response is an operator
	 */
	@Test
	public void selectFreeEmployeeWithTwoEmployeesFreeReturnLowLevelEmployeeTest() {
		
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		Rank supervisorRank = new RankBuilder()
				.withLevel(1)
				.withName("supervisor")
				.build();
		
		Employee supervisorEmployee = new EmployeeBuilder()
				.withIdentity("5678")
				.withName("Supervisor Employee")
				.withRank(supervisorRank)
				.withState(EmployeeState.FREE)
				.build();
		
		Employee operatorEmployee = new EmployeeBuilder()
				.withIdentity("1234")
				.withName("Free Employee")
				.withRank(opeatorRank)
				.withState(EmployeeState.FREE)
				.build();
		
		
		employeeRepository.saveAll(Arrays.asList(supervisorEmployee,operatorEmployee));
		
		Employee selectedEmployee = employeeRepository.selectFreeEmployee();
		assertNotNull(selectedEmployee);
		assertNotNull(operatorEmployee.getIdentity(), selectedEmployee.getIdentity());
	}
	
	/**
	 * Test when a supervisor is free and operator is busy
	 * 1. insert an operator busy
	 * 2. insert a supervisor free
	 * 3. call to selectFreeEmployee method
	 * 4. check if the response is a supervisor
	 */
	@Test
	public void selectFreeEmployeeWithOperatorEmployeeBusyReturnSupervisorEmployeeTest() {
		
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		Rank supervisorRank = new RankBuilder()
				.withLevel(1)
				.withName("supervisor")
				.build();
		
		Employee supervisorEmployee = new EmployeeBuilder()
				.withIdentity("5678")
				.withName("Supervisor Employee")
				.withRank(supervisorRank)
				.withState(EmployeeState.FREE)
				.build();
		
		Employee operatorEmployee = new EmployeeBuilder()
				.withIdentity("1234")
				.withName("Free Employee")
				.withRank(opeatorRank)
				.withState(EmployeeState.BUSY)
				.build();
		
		
		employeeRepository.saveAll(Arrays.asList(supervisorEmployee,operatorEmployee));
		
		Employee selectedEmployee = employeeRepository.selectFreeEmployee();
		assertNotNull(selectedEmployee);
		assertNotNull(supervisorEmployee.getIdentity(), selectedEmployee.getIdentity());
	}
	
	/**
	 * Test when all employees are BUSY, return null
	 * 1. insert a busy operator
	 * 2. insert a busy supervisor
	 * 3. call to freeEmployee Method
	 * 4. check if the response is null
	 */
	@Test
	public void selectFreeEmployeeWithAllEmployeesBusyReturnNullTest() {
		
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		Rank supervisorRank = new RankBuilder()
				.withLevel(1)
				.withName("supervisor")
				.build();
		
		Employee supervisorEmployee = new EmployeeBuilder()
				.withIdentity("5678")
				.withName("Supervisor Employee")
				.withRank(supervisorRank)
				.withState(EmployeeState.BUSY)
				.build();
		
		Employee operatorEmployee = new EmployeeBuilder()
				.withIdentity("1234")
				.withName("Free Employee")
				.withRank(opeatorRank)
				.withState(EmployeeState.BUSY)
				.build();
		
		
		employeeRepository.saveAll(Arrays.asList(supervisorEmployee,operatorEmployee));
		
		Employee selectedEmployee = employeeRepository.selectFreeEmployee();
		assertNull(selectedEmployee);		
	}
	
	

}
