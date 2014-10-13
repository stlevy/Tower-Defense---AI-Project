package ai.heuristics;

import java.awt.Point;
import java.util.List;

import logic.Game;
import logic.Tower;
import utils.GameUtils.TILE_TYPE;
import ai.nodes.AbstractState;
import ai.nodes.BoardState;

/**
 * calculates the heuristic value of a game node (BoardState) by summing the
 * amount of path the new towers "sees"
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
		List<Point> towerCoordinates = s.getTowerCoordinates();
		// check that the node is legal
		for (Point coord: towerCoordinates)
			if (game.hasTower(coord)
				|| game.getBlockType(coord) != TILE_TYPE.GROUND)
			return -1;
		for ( Tower t : game.getTowers())
			towerCoordinates.add(t.getMapLocation());
		
		int pathIntersecionsSum = 0;
		for (Point towerCoord : towerCoordinates) 
			pathIntersecionsSum += pathIntersections(towerCoord,game);
		
		return pathIntersecionsSum;
	}

	public int pathIntersections(Point coord, Game game) {
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
