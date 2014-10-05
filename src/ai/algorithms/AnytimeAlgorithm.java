package ai.algorithms;

import java.util.Vector;

import ai.algorithms.nodes.AbstractState;
import ai.utils.StopperThread;

/**
 * AnytimeAlgorithm.java - a superclass for AI searcher classes.
 * 
 * @author Todd Neller
 * @version 1.0
 * @see http
 *      ://cs.gettysburg.edu/~tneller/resources/ai-search/uninformed-java/index
 *      .html
 * 
 *      Copyright (C) 2003 Todd Neller
 * 
 *      This program is free software; you can redistribute it and/or modify it
 *      under the terms of the GNU General Public License as published by the
 *      Free Software Foundation; either version 2 of the License, or (at your
 *      option) any later version.
 * 
 *      This program is distributed in the hope that it will be useful, but
 *      WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 *      Public License for more details.
 * 
 *      Information about the GNU General Public License is available online at:
 *      http://www.gnu.org/licenses/ To receive a copy of the GNU General Public
 *      License, write to the Free Software Foundation, Inc., 59 Temple Place -
 *      Suite 330, Boston, MA 02111-1307, USA.
 */

public abstract class AnytimeAlgorithm<T extends AbstractState> {

	/**
	 * <code>expendedNodes</code> - number of nodes that have been goal-checked
	 */
	private int expendedNodes = 0;
	private boolean stopped = false;
	private StopperThread stopper;
	private long runningTime;
	
	public AnytimeAlgorithm(long runntingTime){
		this.runningTime = runntingTime;
	}
	
	/**
	 * an anytime algorithm - searches for solutions for search_time milliseconds.  
	 * @param root - the root node
	 * @param search_time - the time the algorithm will run
	 */
	public T search(T root){
		stopper = new StopperThread(runningTime,this);
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

	@SuppressWarnings("unchecked")
	protected Vector<T> expend(T node){
		expendedNodes++;
		return node.expand();
	}
	/**
	 * Stops the current search
	 */
	public void stop() {
		stopped = true;
	}
	
	public void reset(){
		stopped = false;
		expendedNodes = 0;
	}
	
	public boolean isStopped(){
		return stopped;
	}
	
}// AnytimeAlgorithm
