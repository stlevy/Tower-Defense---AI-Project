package ai.algorithms;

import java.util.PriorityQueue;

import ai.algorithms.nodes.AbstractState;

public class BeamSearchPrioriryQueue<T extends AbstractState> extends AnytimeAlgorithm<T> {

	private final int beamWidth;
	
	public BeamSearchPrioriryQueue(int _beamWidth,long runningTime) {
		super(runningTime);
		beamWidth = _beamWidth;
	}

	@Override
	protected T searchSolution(T root) {
		PriorityQueue<T> best = new PriorityQueue<>();
		PriorityQueue<T> current = new PriorityQueue<T>();
		tryToAddToBeam(current, root);
		boolean done = false;
		while ( (!isStopped()) && (!done)) {
			done = true;
			for (T node : current) {
				 if (tryToAddToBeam(best, node) )
					 done = false;
			}
			current.clear();
			for (T node : best) {
				for (T child : expend(node)){
					tryToAddToBeam(current, child);
				}
			}
		}
		if( best.isEmpty() )
			return null;
		
		// sorts best in decending order (best first)
		T bestNode = best.remove();
		while ( !best.isEmpty())
			bestNode = best.remove();
		
		return bestNode;
	}

	private boolean tryToAddToBeam(PriorityQueue<T> current, T node) {
		if (current.size() < beamWidth) {
			current.add(node);
			return true;
		}

		if (current.peek().getWorth() < node.getWorth()) {
			current.remove();
			current.add(node);
			return true;
		}
		return false;

	}
}
