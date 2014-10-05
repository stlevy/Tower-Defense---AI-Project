package ai.algorithms.heuristics;

import java.awt.Point;
import java.util.List;

import logic.Game;
import utils.GameUtils.TILE_TYPE;
import ai.algorithms.nodes.AbstractState;
import ai.algorithms.nodes.BoardState;

/**
 * calculates the heuristic value of a game node (BoardState) by summing the
 * amount of path the towers "sees"
 * 
 * @author Tomer
 * 
 */
public class PathHeuristic implements Heuristic {

	@Override
	public double getHeuristicValue(AbstractState node,Game game) {
		if (node == null || !(node instanceof BoardState))
			throw new IllegalArgumentException("node is illegal");

		BoardState s = (BoardState) node;
		int pathIntersecionsSum = 0;
		List<Point> towers = s.getTowerCoordinates();
		for (Point p : game)
			if (game.hasTower(p))
				towers.add(p);
		for (Point towerCoord : towers) {
			int intersects = pathIntersections(towerCoord,game);
			if (intersects < 0)
				return -1;
			pathIntersecionsSum += intersects;
		}
		return pathIntersecionsSum;
	}

	public int pathIntersections(Point coord, Game game) {
		if (game.hasTower(coord)
				|| game.getBlockType(coord) != TILE_TYPE.GROUND)
			return -1;

		int path_intersections = 0;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				if (deltaX == 0 && deltaY == 0)
					continue;
				Point neighbor = new Point(coord.x + deltaX, coord.y + deltaY);
				if (game.getBlockType(neighbor) == TILE_TYPE.PATH)
					path_intersections++;
			}
		}
		return path_intersections;
	}
}
