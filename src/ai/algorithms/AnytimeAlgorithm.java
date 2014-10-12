package ai.algorithms;

import java.util.Vector;

import ai.nodes.AbstractState;
import ai.utils.StopperThread;

/**
 * AnytimeAlgorithm.java - a superclass for AI searcher classes.
 * T here is the type of the node defined in the solution.
 * @see http 
 *      ://cs.gettysburg.edu/~tneller/resources/ai-search/uninformed-java/index
 *      .html
 *      
 *       Copyright (C) 2003 Todd Neller
 */

public abstract class AnytimeAlgorithm<T extends AbstractState> {

	/**
	 * <code>expendedNodes</code> - number of nodes that have been goal-checked
	 */
	private int expendedNodes = 0;
	private boolean stopped = false;
	private StopperThread stopper;
	private long runningTime;

	public AnytimeAlgorithm(long runntingTime) {
		this.runningTime = runntingTime;
	}

	/**
	 * a wrapper function that starts the timer for the algorithm.
	 * @param root
	 *            - the root node
	 */
	public T search(T root) {
		stopper = new StopperThread(runningTime, this);
		stopper.start();
		return searchSolution(root);
	}

	/**
	 * <code>search</code> - Search for a best node starting at the given "root"
	 * AbstractState, and return the best node that was found.
	 * 
	 * @param root
	 *            a <code>AbstractState</code> value - the initial "root" search
	 *            node
	 */
	protected abstract T searchSolution(T root);

	/**
	 * <code>getNodeCount</code> - Returns the number of nodes examined
	 * (goal-checked) in the previous search.
	 * 
	 * @return an <code>int</code> value - the number of nodes checked in the
	 *         previous search. This may be considerably less than the number of
	 *         children generated.
	 */
	public int getExpendedNodes() {
		return expendedNodes;
	}

	/**
	 * gets the successors of a node
	 */
	@SuppressWarnings("unchecked")
	protected Vector<T> expend(T node) {
		expendedNodes++;
		return node.expand();
	}

	/**
	 * Stops the current search
	 */
	public void stop() {
		stopped = true;
	}

	/**
	 * resets the algorithm so you can reuse it.
	 */
	public void reset() {
		stopped = false;
		expendedNodes = 0;
	}

	/**
	 * @return returs whether the algorithm was already stopped or not 
	 */
	public boolean isStopped() {
		return stopped;
	}

}// AnytimeAlgorithm
