package com.almundo.call.dom.bld;

import com.almundo.call.dom.ent.Rank;
/**
 * Class to build Rank objects (Apply DataBuilder Pattern)
 * @author hectormao
 *
 */
public class RankBuilder {
	private int level = 1;
	private String name = "operator";
	
	public RankBuilder withLevel(int level) {
		this.level = level;
		return this;
	}
	
	public RankBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public Rank build() {
		return new Rank(level, name);
	}

}
