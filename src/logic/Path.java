package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import utils.GameUtils.Direction;

public class Path implements Iterable<Direction> {

	private final ArrayList<Direction> pathTurns;
	public final ArrayList<Point> pathCoords;

	public Path(final Point initial) {
		pathTurns = new ArrayList<>();
		pathCoords = new ArrayList<>();
		pathCoords.add(initial);
	}

	private void addTurn(final Direction dir){
		pathTurns.add(dir);
		Point last = pathCoords.get(pathCoords.size() - 1);
		pathCoords.add(getNextPoint(last, dir));
	}

	public Collection<Point> getCoords() {
		return pathCoords;
	}
	@Override
	public boolean equals(final Object obj) {
		if (obj == null || obj.getClass() != Path.class)
			return false;
		Path p = (Path) obj;
		if (p.pathTurns.size() != pathTurns.size())
			return false;
		Iterator<Direction> iter2 = p.pathTurns.iterator();
		for (Iterator<Direction> iter1 = pathTurns.iterator(); iter1.hasNext();)
			if (iter1.next() != iter2.next())
				return false;
		return true;
	}

	@Override
	public Iterator<Direction> iterator() {
		return new Iterator<Direction>() {
			private int index;

			{
				index = 0;
			}

			@Override
			public boolean hasNext() {
				if (index < pathTurns.size())
					return true;
				return false;

			}

			@Override
			public Direction next() {
				return pathTurns.get(index++);
			}
		};

	}

	public static class PathBuilder {
		private final Path path;

		public PathBuilder(final Point initial) {
			path = new Path(initial);
		}

		public PathBuilder dup() {
			PathBuilder pb = new PathBuilder(path.pathCoords.get(0));
			path.forEach(dir -> pb.addTurn(dir));
			return pb;
		}

		public PathBuilder concat(final Path p) {
			for(Direction dir : p)
				path.addTurn(dir);
			return this;
		}

		public PathBuilder addTurn(final Direction turn) {
			path.addTurn(turn);
			return this;
		}

		public Path build() {
			return path;
		}
	}

	public static Point getNextPoint(final Point prev, final Direction d) {
		int newX = prev.x, newY = prev.y;
		switch (d) {
		case downward:
			newY++;
			break;
		case upward:
			newY--;
			break;
		case left:
			newX--;
			break;
		case right:
			newX++;
			break;
		case no_direction:
		default:
			break;
		}
		return new Point(newX, newY);
	}
}
