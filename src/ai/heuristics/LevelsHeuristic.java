package ai.heuristics;

import java.awt.Point;
import java.util.List;

import logic.Game;
import logic.Tower;
import utils.GameUtils.TILE_TYPE;
import ai.nodes.AbstractState;
import ai.nodes.BoardState;

/**
 * This heuristic evaluates a Board state by the sum of tower level ups
 * @author Tomer
 * 
 */
public class LevelsHeuristic implements Heuristic {

	@Override
	public double getHeuristicValue(AbstractState node,Game game) {
		if (node == null || !(node instanceof BoardState))
			throw new IllegalArgumentException("node is illegal");

		BoardState s = (BoardState) node;
		List<Point> towers = s.getTowerCoordinates();
		//check that node is legal
		for (Point coord : towers) 
			if (game.hasTower(coord)
					|| game.getBlockType(coord) != TILE_TYPE.GROUND)
				return -1;
			
		for (Tower t : game.getTowers())
			towers.add(t.getMapLocation());
		
		int towerLevelsSum = 0;
		for (Point towerCoord : towers) {
			if (!game.towerSeesPath(towerCoord))
				continue;
			towerLevelsSum += towerLevel(towerCoord,towers);
		}
		return towerLevelsSum;
	}

	private int towerLevel(Point coord, List<Point> towers) {
		int neighborTowers = 0;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				if (deltaX == 0 && deltaY == 0)
					continue;
				Point neighbor = new Point(coord.x + deltaX, coord.y + deltaY);
				if (towers.contains(neighbor) )
					neighborTowers++;
			}
		}
		return neighborTowers;
	}

}
