package com.almundo.call.dom.ent;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.almundo.call.dom.utl.EmployeeState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that represents a Employee data
 * @author hectormao
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "employee")
public class Employee {
	/**
	 * Mongo ID
	 */
	@Id
	private String id;
	/**
	 * Preson id
	 */
	private String identity;
	/**
	 * Person name
	 */
	private String name;
	/**
	 * Laboral rank
	 */
	@Indexed
	private Rank rank;
	/**
	 * Employee state
	 * FREE: available to attend a call
	 * BUSY: In a call
	 */
	@Indexed
	private EmployeeState state;
}
