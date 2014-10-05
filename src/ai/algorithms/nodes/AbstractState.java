package ai.algorithms.nodes;

import java.util.Vector;

import ai.algorithms.heuristics.Heuristic;

/**
 * AbstractState.java - a simple node for uninformed AI search (assuming cost
 * equals depth).
 * 
 * @author Todd Neller
 * @version 1.0
 * 
 * 
 *          Copyright (C) 2003 Todd Neller
 * 
 *          This program is free software; you can redistribute it and/or modify
 *          it under the terms of the GNU General Public License as published by
 *          the Free Software Foundation; either version 2 of the License, or
 *          (at your option) any later version.
 * 
 *          This program is distributed in the hope that it will be useful, but
 *          WITHOUT ANY WARRANTY; without even the implied warranty of
 *          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *          General Public License for more details.
 * 
 *          Information about the GNU General Public License is available online
 *          at: http://www.gnu.org/licenses/ To receive a copy of the GNU
 *          General Public License, write to the Free Software Foundation, Inc.,
 *          59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * @param <T>
 */

public abstract class AbstractState implements Comparable<AbstractState> {

	protected Heuristic h;
	private Double worth = null;

	/**
	 * Creates an <code>AbstractState</code> instance and sets it to an initial
	 * search state. One will generally want to override this constructor to
	 * initialize a root node for search.
	 */
	public AbstractState(Heuristic h) {
		this.h = h;
	}

	/**
	 * <code>isGoal</code> - test whether or not the current node is a goal
	 * node.
	 * 
	 * @return a <code>boolean</code> value - the value of this node by the
	 *         heuristic function h
	 */
	public final Double getWorth() {
		if (worth != null)
			return worth;
		worth = h.getHeuristicValue(this);
		return worth;
	}

	/**
	 * <code>expand</code> - return a (possibly empty) Vector of this node's
	 * children. A new child is created by calling <code>childClone</code> and
	 * appropriately modifying the state of the returned node.
	 * 
	 * @return a <code>Vector</code> of SearchNodes that are children of this
	 *         node
	 */

	@SuppressWarnings("rawtypes")
	public abstract Vector expand();

	@Override
	public abstract String toString();

	/**
	 * NOTE: It is generally beneficial to also override the default toString
	 * method to provide a text representation of the node state. You will want
	 * to override the default equals method if you're using a search algorithm
	 * with repeated-state checking.
	 */

	@Override
	public abstract boolean equals(Object other);

	public final int compareTo(AbstractState node) {
		if (node == null || !getClass().isInstance(node))
			throw new IllegalArgumentException("Invalid node, must be GameNode");
		
		if ( this.equals(node) )
			return 0;
		
		if (getWorth() - node.getWorth() < 0 )
			return -1;
		return 1;
	}

}// AbstractState

