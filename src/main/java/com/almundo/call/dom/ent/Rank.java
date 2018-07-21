package com.almundo.call.dom.ent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that represents a Laboral Rank
 * @author hectormao
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rank {
	/**
	 * number that indicates the level of rank
	 * this data determines the priority of call attention
	 * lower value - select first
	 */
	private int level;
	/**
	 * Name of laboral rank operator, supervisor, director, ...
	 */
	private String name;
}
