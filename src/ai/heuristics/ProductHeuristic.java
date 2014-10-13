package ai.heuristics;

import logic.Game;
import ai.nodes.AbstractState;

public class ProductHeuristic implements Heuristic {

	Heuristic h1;
	Heuristic h2;

	public ProductHeuristic(Game _game, Heuristic h1, Heuristic h2) {
		this.h1 = h1;
		this.h2 = h2;
	}

	@Override
	public double getHeuristicValue(AbstractState node,Game game) {
		double value1 = h1.getHeuristicValue(node,game);
		double value2 = h2.getHeuristicValue(node,game);
		return value1 * value2;
	}

}
