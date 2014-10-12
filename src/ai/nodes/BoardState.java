package ai.nodes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.Game;
import utils.GameUtils.TILE_TYPE;
import ai.heuristics.Heuristic;

public class BoardState extends AbstractState {

	private final List<Point> towerCoordinates;

	/**
	 * this constructor is only used once, to create the root node.
	 */
	public BoardState(int availableTowers, Heuristic h, Game game) {
		super(h, game);
		towerCoordinates = new ArrayList<>();
		for (Point p : game) {
			if (game.towerSeesPath(p)
					&& game.getBlockType(p) == TILE_TYPE.GROUND
					&& !game.hasTower(p))
				towerCoordinates.add(p);
			if (towerCoordinates.size() == availableTowers)
				break;
		}
	}
	
	private BoardState(BoardState board) {
		super(board.h, board.game);
		towerCoordinates = new ArrayList<>(board.getTowerCoordinates());
	}

	@Override
	public Vector<BoardState> expand() {
		Vector<BoardState> sons = new Vector<>();
		BoardState temp = new BoardState(this);
		for (int pointIdx = 0; pointIdx < towerCoordinates.size(); pointIdx++) {
			for (Point p : game) {
				if (towerCoordinates.contains(p)
						|| game.getBlockType(p) != TILE_TYPE.GROUND
						|| game.hasTower(p))
					continue; // remove illegal states

				temp.towerCoordinates.set(pointIdx, p);
				sons.add(new BoardState(temp));
			}
			// return the tower to it's original place
			temp.towerCoordinates.set(pointIdx, towerCoordinates.get(pointIdx));
		}
		return sons;
	}

	@Override
	public String toString() {
		String res = "[";
		for (Point p : towerCoordinates) {
			res += pointToString(p);
		}
		res.substring(0, res.length() - 1);
		return res + "]";
	}

	private String pointToString(Point p) {
		return "(" + p.x + "," + p.y + ")";
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (this == other)
			return true;
		if (!(other instanceof BoardState))
			return false;
		BoardState node = (BoardState) other;
		return towerCoordinates.containsAll(node.towerCoordinates)
				&& node.towerCoordinates.containsAll(towerCoordinates);

	}

	/**
	 * @returns a COPY of the original list
	 */
	public List<Point> getTowerCoordinates() {
		return new ArrayList<>(towerCoordinates);
	}

}
