package ai.nodes;

import java.util.Vector;

import logic.Game;
import ai.heuristics.Heuristic;

/**
 * AbstractState.java - a superclass for nodes.
 * 
 * @see http 
 *      ://cs.gettysburg.edu/~tneller/resources/ai-search/uninformed-java/index
 *      .html
 * 
 *      Copyright (C) 2003 Todd Neller
 */

public abstract class AbstractState implements Comparable<AbstractState> {

	protected Heuristic h;
	private Double worth = null;
	protected Game game;

	public AbstractState(Heuristic h, Game game) {
		this.h = h;
		this.game = game;
	}

	/**
	 * @return a <code>boolean</code> value - the value of this node by the
	 *         heuristic function h
	 */
	public final Double getWorth() {
		if (worth != null)
			return worth;
		worth = h.getHeuristicValue(this, game);
		return worth;
	}

	/**
	 * @return a <code>Vector</code> of SearchNodes that are children of this
	 *         node
	 */

	@SuppressWarnings("rawtypes")
	public abstract Vector expand();

	@Override
	public abstract String toString();

	@Override
	public abstract boolean equals(Object other);

	/**
	 * this function defines the natural ordering of the nodes, by the heuristic
	 * function h
	 */
	public final int compareTo(AbstractState node) {
		if (node == null || !getClass().isInstance(node))
			throw new IllegalArgumentException("Invalid node, must be GameNode");

		if (this.equals(node))
			return 0;

		if (getWorth() - node.getWorth() < 0)
			return -1;
		return 1;
	}

}// AbstractState

