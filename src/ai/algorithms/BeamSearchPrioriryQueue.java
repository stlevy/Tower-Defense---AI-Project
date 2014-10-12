package ai.algorithms;

import java.util.PriorityQueue;

import ai.nodes.AbstractState;

/**
 * an abstract beam search
 * 
 * @author Tomer
 * 
 * @param <T>
 *            - the type of the node we use in this search. must extend
 *            AbstractNode
 */
public class BeamSearchPrioriryQueue<T extends AbstractState> extends
		AnytimeAlgorithm<T> {

	private final int beamWidth;

	public BeamSearchPrioriryQueue(int _beamWidth, long runningTime) {
		super(runningTime);
		beamWidth = _beamWidth;
	}

	@Override
	protected T searchSolution(T root) {
		// the beam that will hold the best nodes at every stage
		PriorityQueue<T> best = new PriorityQueue<>();
		// the beam that will hold the best successors of the 'best' beam
		PriorityQueue<T> current = new PriorityQueue<T>();
		tryToAddToBeam(current, root);
		boolean done = false;
		while (!isStopped()) {
			done = true;
			for (T node : current) {
				if (tryToAddToBeam(best, node))
					done = false;
			}
			if (isStopped() || done)
				break;
			current.clear();
			for (T node : best) {
				for (T child : expend(node)) {
					tryToAddToBeam(current, child);
				}
				if (isStopped())
					break;
			}
		}
		if (best.isEmpty())
			return null;

		// sorts best in decending order (best first)
		T bestNode = best.remove();
		while (!best.isEmpty())
			bestNode = best.remove();

		return bestNode;
	}

	/**
	 * adds node to beam if: - the beam is not full - the worst item in the beam
	 * is worse than node * in this case , the worst item will be removed.
	 * 
	 * @return returns true if the node was added to the beam.
	 */
	private boolean tryToAddToBeam(PriorityQueue<T> beam, T node) {
		if (beam.size() < beamWidth) {
			beam.add(node);
			return true;
		}

		if (beam.peek().getWorth() < node.getWorth()) {
			beam.remove();
			beam.add(node);
			return true;
		}
		return false;

	}
}
