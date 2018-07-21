package com.almundo.call.infra.ent;

import com.almundo.call.dom.ent.Call;
import com.almundo.call.dom.ent.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Infra Entity that represents an operation result
 * @author hectormao
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CallResponse {
	/**
	 * Employee that was assigned to call
	 */
	private Employee attendant;
	/**
	 * Call data
	 */
	private Call call;
	/**
	 * Human readable operation message
	 */
	private String message;
}
