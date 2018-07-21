package com.almundo.call.dom.bld;

import java.util.Date;

import com.almundo.call.dom.ent.Call;
/**
 * Class to build Employee objects (Apply DataBuilder Pattern)
 * @author hectormao
 *
 */
public class CallBuilder {
	
	private String id = null;
	private String employeeId = null;
	private Date startDate = new Date();
	private Date finishDate = null;
	private String notes = null;
	
	public CallBuilder withId(String id) {
		this.id = id;
		return this;
	}
	
	public CallBuilder withEmployeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}
	
	public CallBuilder withStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}
	
	public CallBuilder withFinishDate(Date finishDate) {
		this.finishDate = finishDate;
		return this;
	}
	
	public CallBuilder withNotes(String notes) {
		this.notes = notes;
		return this;
	}
	
	public Call build() {
		return new Call(id,employeeId,startDate,finishDate,notes);
	}

}
