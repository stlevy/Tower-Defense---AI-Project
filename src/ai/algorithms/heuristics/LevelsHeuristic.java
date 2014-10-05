package ai.algorithms.heuristics;

import java.awt.Point;
import java.util.List;

import logic.Game;
import utils.GameUtils.TILE_TYPE;
import ai.algorithms.nodes.AbstractState;
import ai.algorithms.nodes.BoardState;

/**
 * This heuristic evaluates a Board state by the sum of tower levels
 * @author Tomer
 * 
 */
public class LevelsHeuristic implements Heuristic {

	@Override
	public double getHeuristicValue(AbstractState node,Game game) {
		if (node == null || !(node instanceof BoardState))
			throw new IllegalArgumentException("node is illegal");

		BoardState s = (BoardState) node;
		int towerIntersecionsSum = 0;
		List<Point> towers = s.getTowerCoordinates();
		for (Point p : game)
			if (game.hasTower(p))
				towers.add(p);
		for (Point towerCoord : towers) {
			int intersects = newTowerIntersections(towers, towerCoord,game);
			if (intersects < 0) // bad node
				return -1;
			towerIntersecionsSum = towerIntersecionsSum + intersects;
		}
		return towerIntersecionsSum;
	}

	private int newTowerIntersections(List<Point> towers, Point currTower, Game game) {
		if (game.hasTower(currTower)
				|| game.getBlockType(currTower) != TILE_TYPE.GROUND)
			return -1;

		int neighborTowers = 0;
		int selfBonus = (game.towerSeesPath(currTower)) ? 1 : 0;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				Point neighbor = new Point(currTower.x + deltaX, currTower.y + deltaY);
				if ((!neighbor.equals(currTower)) &&towers.contains(neighbor)){
					neighborTowers += selfBonus; // current tower gets a level up if it sees road
				} 
			}
		}
		return neighborTowers;
	}

}
