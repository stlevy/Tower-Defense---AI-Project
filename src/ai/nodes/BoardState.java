package ai.nodes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import ai.heuristics.Heuristic;
import logic.Game;
import utils.GameUtils.TILE_TYPE;

public class BoardState extends AbstractState {

	private final List<Point> towerCoordinates;

	/**
	 * this constructor is only used once, to create the root node.
	 */
	public BoardState(final int availableTowers, final Heuristic h, final Game game) {
		super(h, game);
		towerCoordinates = new ArrayList<>();
		ArrayList<Point> allCoordinates = new ArrayList<>();
		for (Point p : game)
			if (game.towerSeesPath(p)
					&& game.getBlockType(p) == TILE_TYPE.GROUND
					&& !game.hasTower(p))
				allCoordinates.add(p);
		Random rnd = new Random(System.currentTimeMillis());
		for (int i = 0; i < availableTowers; i++)
			towerCoordinates.add(allCoordinates.remove(rnd.nextInt(allCoordinates.size())));

	}

	public static BoardState randomRestart(final int availableTowers, final Heuristic h, final Game game) {
		return new BoardState(availableTowers, h, game);
	}

	private BoardState(final BoardState board) {
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

	private String pointToString(final Point p) {
		return "(" + p.x + "," + p.y + ")";
	}

	@Override
	public boolean equals(final Object other) {
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
