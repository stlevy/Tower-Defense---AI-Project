package ai.algorithms.heuristics;

import logic.Game;
import ai.algorithms.nodes.AbstractState;

/**
 * calculates a weighted heuristics, the heuristic value for a node n will be:
 * 			h1(n)*alpha + h2(n)*(1-alpha)
 * 
 * @author Tomer
 * 
 */
public class WeightedHeuristics extends Heuristic {

	Heuristic h1;
	Heuristic h2;
	double alpha;

	public WeightedHeuristics(Game _game, Heuristic h1, Heuristic h2,
			double alpha) {
		super(_game);

		if (h1.game != h2.game || h1.game != _game)
			throw new IllegalArgumentException(
					"all heuristics must work on the same game");
		if (alpha > 1 || alpha < 0)
			throw new IllegalArgumentException("Alpha must be in [0,1]");
		this.h1 = h1;
		this.h2 = h2;
		this.alpha = alpha;

	}

	@Override
	public double getHeuristicValue(AbstractState node) {
		double value1 = h1.getHeuristicValue(node);
		double value2 = h2.getHeuristicValue(node);
		return alpha * value1 + (1 - alpha) * value2;
	}

}
