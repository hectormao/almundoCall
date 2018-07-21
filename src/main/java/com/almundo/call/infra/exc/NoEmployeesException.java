package com.almundo.call.infra.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * No FREE Employees found
 * @author hectormao
 *
 */
@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class NoEmployeesException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NoEmployeesException() {
		super("No Employees available, please try again in a few minutes");
	}

}
