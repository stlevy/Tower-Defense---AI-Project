package ai.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ai.algorithms.nodes.AbstractState;

public class BeamSearchList<T extends AbstractState> extends AnytimeAlgorithm<T> {

	private final int beamWidth;

	public BeamSearchList(int _beamWidth,long runningTime) {
		super(runningTime);
		beamWidth = _beamWidth;
	}

	@Override
	protected T searchSolution(T root) {
		List<T> best = new ArrayList<>();
		List<T> current = new ArrayList<>();
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
		Collections.sort(best,new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				return o2.compareTo(o1);
			}
		});
		
		return best.get(0);
	}

	private boolean tryToAddToBeam(List<T> beam, T node) {
		if (beam.size() < beamWidth) {
			beam.add(node);
			return true;
		}
		Collections.sort(beam);

		if (beam.get(0).getWorth() < node.getWorth()) {
			beam.remove(0);
			beam.add(node);
			return true;
		}
		return false;

	}
}
