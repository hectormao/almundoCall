package com.almundo.call.dom.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.almundo.call.dom.ent.Employee;

/**
 * Mongo repository to access to Employee document in MongoDB
 * @author hectormao
 *
 */
public interface EmployeeRepository extends MongoRepository<Employee, String>, EmployeeRepositoryCustom {
}
