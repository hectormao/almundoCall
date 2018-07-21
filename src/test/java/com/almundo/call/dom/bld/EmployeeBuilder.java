package com.almundo.call.dom.bld;

import com.almundo.call.dom.ent.Employee;
import com.almundo.call.dom.ent.Rank;
import com.almundo.call.dom.utl.EmployeeState;
/**
 * Class to build Employee objects (Apply DataBuilder Pattern)
 * @author hectormao
 *
 */
public class EmployeeBuilder {
	private String id = null;
	private String identity = "-1";
	private String name = "Unknown Name";
	private Rank rank = new RankBuilder().build();
	private EmployeeState state = EmployeeState.FREE;
	
	
	public EmployeeBuilder withId(String id) {
		this.id = id;
		return this;
	}
	
	public EmployeeBuilder withIdentity(String identity) {
		this.identity = identity;
		return this;
	}
	
	public EmployeeBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public EmployeeBuilder withRank(Rank rank) {
		this.rank = rank;
		return this;
	}
	
	public EmployeeBuilder withState(EmployeeState state) {
		this.state = state;
		return this;
	}
	
	public Employee build() {
		return new Employee(id, identity, name, rank, state);
	}

}
