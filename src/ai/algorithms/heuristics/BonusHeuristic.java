package ai.algorithms.heuristics;

import java.awt.Point;
import java.util.List;

import logic.Game;
import utils.GameUtils.TILE_TYPE;
import ai.algorithms.nodes.AbstractState;
import ai.algorithms.nodes.BoardState;

/**
 * This heuristic evaluates a Board state by the amount of bonuses it adds to
 * all the towers in the game (including the added towers)
 * 
 * @author Tomer
 * 
 */
public class BonusHeuristic extends Heuristic {

	public BonusHeuristic(Game _game) {
		super(_game);
	}

	@Override
	public double getHeuristicValue(AbstractState node) {
		if (node == null || !(node instanceof BoardState))
			throw new IllegalArgumentException("node is illegal");

		BoardState s = (BoardState) node;
		int towerIntersecionsSum = 0;
		List<Point> towers = s.getTowerCoordinates();
		for (Point towerCoord : towers) {
			int intersects = newTowerIntersections(towers, towerCoord);
			if (intersects < 0) // bad node
				return -1;
			towerIntersecionsSum = towerIntersecionsSum + intersects;
		}
		return towerIntersecionsSum;
	}

	private int newTowerIntersections(List<Point> towers, Point coord) {
		if (game.hasTower(coord)
				|| game.getBlockType(coord) != TILE_TYPE.GROUND)
			return -1;

		int neighborTowers = 0;
		int selfBonus = (game.towerSeesPath(coord)) ? 1 : 0;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				Point neighbor = new Point(coord.x + deltaX, coord.y + deltaY);
				if (deltaX == 0 && deltaY == 0)
					continue;
				if (towers.contains(neighbor)){
					neighborTowers += selfBonus; // current tower gets a bonus if it sees road
					continue;
				} 
				int neighborBonus = (game.towerSeesPath(neighbor)) ? 1 : 0; 
				if (game.hasTower(neighbor)) {
					neighborTowers = neighborTowers + neighborBonus + selfBonus; // current tower gets a bonus if it sees road
				}
			}
		}
		return neighborTowers;
	}

}
