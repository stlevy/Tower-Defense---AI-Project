package ai.algorithms.nodes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.Game;
import ai.algorithms.heuristics.Heuristic;

public class BoardState extends AbstractState {

	private final List<Point> towerCoordinates;

	public BoardState(List<Point> points, Heuristic h,Game game) {
		super(h,game);
		towerCoordinates = new ArrayList<>(points);
	}

	private BoardState(BoardState board) {
		this(board.getTowerCoordinates(), board.h,board.game);
	}

	@Override
	public Vector<BoardState> expand() {
		Vector<BoardState> sons = new Vector<>();
		BoardState temp = new BoardState(this);
		for (int pointIdx = 0; pointIdx < towerCoordinates.size(); pointIdx++) {
			for (Point p : game){ 
				if (towerCoordinates.contains(p))
					continue; // already a tower in this location

				temp.towerCoordinates.set(pointIdx,p);
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

	public List<Point> getTowerCoordinates() {
		return new ArrayList<>(towerCoordinates);
	}

}
