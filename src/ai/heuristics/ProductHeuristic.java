package ai.heuristics;

import ai.nodes.AbstractState;
import logic.Game;

public class ProductHeuristic implements Heuristic {

	Heuristic h1;
	Heuristic h2;

	public ProductHeuristic(final Game _game, final Heuristic h1, final Heuristic h2) {
		this.h1 = h1;
		this.h2 = h2;
	}

	@Override
	public double getHeuristicValue(final AbstractState node,final Game game) {
		double value1 = h1.getHeuristicValue(node,game);
		double value2 = h2.getHeuristicValue(node,game);
		return Math.log(value1) + Math.log(value2);
	}

}
