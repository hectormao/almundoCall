package com.almundo.call.dom.repo;

import com.almundo.call.dom.ent.Employee;

/**
 * Repository with a custom method to select a free employee
 * @author hectormao
 *
 */
public interface EmployeeRepositoryCustom {
	public Employee selectFreeEmployee();
}
