package ai.heuristics;

import java.awt.Point;
import java.util.List;

import ai.nodes.AbstractState;
import ai.nodes.BoardState;
import logic.Game;
import logic.Path;
import logic.Tower;
import utils.GameUtils.TILE_TYPE;

public class NewProductHeuristics implements Heuristic {

	@Override
	public double getHeuristicValue(final AbstractState node, final Game game) {
		if (node == null || !(node instanceof BoardState))
			throw new IllegalArgumentException("node is illegal");
		BoardState s = (BoardState) node;
		List<Point> towerCoordinates = s.getTowerCoordinates();
		// check that the node is legal
		for (Point coord : towerCoordinates)
			if (game.hasTower(coord) || game.getBlockType(coord) != TILE_TYPE.GROUND)
				return -1;
		for (Tower t : game.getTowers())
			towerCoordinates.add(t.getMapLocation());

		int product = 1;
		for (Path p : game.getRoom().paths) {
			int intersectionsWithPath = 0;
			for (Point towerCoord : towerCoordinates)
				intersectionsWithPath += pathIntersections(towerCoord, game, p);
			product *= intersectionsWithPath;
		}
		return product;
	}

	public int pathIntersections(final Point coord, final Game game, final Path p) {
		int path_intersections = 0;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				if (deltaX == 0 && deltaY == 0)
					continue;
				Point neighbor = new Point(coord.x + deltaX, coord.y + deltaY);
				if (game.getRoom().blockOnPath(neighbor, p))
					path_intersections++;
			}
		}
		return path_intersections;
	}

}
