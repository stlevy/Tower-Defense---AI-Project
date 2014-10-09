package ai.heuristics;

import logic.Game;
import ai.nodes.AbstractState;

public interface Heuristic {
	
	/**
	 * calculates the heuristic value of a node
	 * @param node
	 * @return the heuristics if this node is legal,
	 * 		returs a negetive number if the node is illegal
	 */
	public abstract double getHeuristicValue(AbstractState node, Game game);
	
}