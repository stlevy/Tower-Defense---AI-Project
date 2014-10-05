package ai.algorithms.heuristics;

import logic.Game;
import ai.algorithms.nodes.AbstractState;

public abstract class Heuristic {
	
	protected Game game;

	public Heuristic(Game _game){
		game = _game;
	}

	/**
	 * calculates the heuristic value of a node
	 * @param node
	 * @return the heuristics if this node is legal,
	 * 		returs a negetive number if the node is illegal
	 */
	public abstract double getHeuristicValue(AbstractState node);
	
}