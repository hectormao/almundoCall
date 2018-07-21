package com.almundo.call.dom.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.almundo.call.dom.ent.Employee;
import com.almundo.call.dom.utl.EmployeeState;
/**
 * Repository implemenattion to select a free employee
 * @author hectormao
 *
 */
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeRepositoryCustomImpl.class); 
	
	private static final String STATE_FIELD = "state"; 
	private static final String RANK_LEVEL_FIELD = "rank.level";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Select a free employee, return it, and set the state in BUSY
	 * The selection takes the free employees sorted by rank level,
	 * Takes the lower level in free state
	 */
	@Override
	public Employee selectFreeEmployee() {
		
		logger.info("Select a free employee");
		
		Query query = new Query(
				Criteria
					.where(STATE_FIELD)
					.is(EmployeeState.FREE)
					)
				.with(new Sort(Sort.Direction.ASC, RANK_LEVEL_FIELD))
				.limit(1);
		Update update = new Update().set(STATE_FIELD, EmployeeState.BUSY);
		
		return mongoTemplate
				.findAndModify(
						query, 
						update, 
						new FindAndModifyOptions()
							.returnNew(true), 
						Employee.class
						);
		
	}

}
