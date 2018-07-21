package com.almundo.call.dom.ent;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that represents a call data
 * @author hectormao
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "call")
public class Call {
	/**
	 * Mongo ID
	 */
	@Id
	private String id;
	/**
	 * Mongo Employee document id
	 */
	@Indexed
	private String employeeId;
	/**
	 * Start date of the call
	 */
	private Date startDate;
	/**
	 * Finish date of the call
	 */
	private Date finishDate;
	/**
	 * Employee notes about the call
	 */
	private String notes;
	
	public Call(String employeeId, Date startDate){
		this.employeeId = employeeId;
		this.startDate = startDate;
	}
}
