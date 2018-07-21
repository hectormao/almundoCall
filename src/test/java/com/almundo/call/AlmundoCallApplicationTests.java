package com.almundo.call;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.almundo.call.dom.bld.EmployeeBuilder;
import com.almundo.call.dom.bld.RankBuilder;
import com.almundo.call.dom.ent.Employee;
import com.almundo.call.dom.ent.Rank;
import com.almundo.call.dom.repo.CallRepository;
import com.almundo.call.dom.repo.EmployeeRepository;
import com.almundo.call.dom.utl.EmployeeState;
import com.anarsoft.vmlens.concurrent.junit.TestUtil;

/**
 * Integration test to check the concurrency
 * @author hectormao
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = AlmundoCallApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.MOCK
		)
@DataMongoTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.almundo.call"})
@EnableWebMvc
@TestPropertySource(
		  locations = "classpath:application-IT.properties")
public class AlmundoCallApplicationTests {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CallRepository callRepository;
	
	/**
	 * Insert 10 free employees in ebedded mongo
	 */
	@Before
	public void setup() {
		Rank opeatorRank = new RankBuilder()
				.withLevel(1)
				.withName("operator")
				.build();  
		
		List<Employee> freeEmployees = IntStream
			.range(0, 10)
			.mapToObj(idx -> new EmployeeBuilder()
					.withId(null)
					.withIdentity(idx + "id")
					.withName("Free" + idx + " Employee" + idx)
					.withRank(opeatorRank)
					.withState(EmployeeState.FREE)
					.build())
			.collect(Collectors.toList());
		
		employeeRepository.saveAll(freeEmployees);
	}
	
	/**
	 * Clear the data
	 */
	@After
	public void clearData() {
		employeeRepository.deleteAll();
		callRepository.deleteAll();
	}
	
	/**
	 * Test the concurrency
	 * 1. launch 10 threads
	 * 2. consume the start call 
	 * @throws Exception
	 */
	@Test
	public void concurrencyApplicationTest() throws Exception{
		TestUtil.runMultithreaded(()->{
			try {
				mvc
				.perform(
					get("/call/start")
					.contentType(MediaType.APPLICATION_JSON)
					)
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
						);
			} catch(Exception ex) {
				fail(ex.getMessage());
			}
		}, 10);
		
		
		
	}

}
