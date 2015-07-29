package logic;

import static logic.Path.getNextPoint;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import logic.Path.PathBuilder;
import utils.GameUtils.Direction;
import utils.GameUtils.TILE_TYPE;
/**
 * a collection of blocks, also iterable over it's blocks (going up to down,
 * left to right)
 * 
 * @author Tomer
 * 
 */
public class Room extends Rectangle implements Iterable<Point> {

	private static final Random RANDOM = new Random(System.currentTimeMillis());
	protected final int blockSize;
	private final Point entryPoint;
	protected final Block[][] blocks;
	private final int roomWidth;
	private final int roomHeight;
	public final Collection<Path> paths;

	public Room(final int _roomWidth, final int _roomHeight, final int _blockSize,
			final Point _basePoint, final File level) {
		roomWidth = _roomWidth;
		roomHeight = _roomHeight;
		setBounds(_basePoint.x, _basePoint.y, roomWidth * _blockSize,
			roomHeight * _blockSize);

		blocks = new Block[roomHeight][roomWidth];

		blockSize = _blockSize;
		entryPoint = loadMap(level);
		paths = AnalyzePaths();
		for (Path p : paths)
			for (Point coord : p.getCoords())
				getBlock(coord).addPath(p);
	}


	private Point loadMap(final File level) {
		Scanner scanner = null;

		try {
			scanner = new Scanner(level);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		Point entryPoint = null;
		for (int row = 0; row < roomHeight; row++) {
			for (int col = 0; col < roomWidth; col++) {
				if (!scanner.hasNext())
					throw new IllegalArgumentException(
							"given text file too small");
				blocks[row][col] = new Block(x + (col * blockSize), y
					+ (row * blockSize), blockSize, blockSize,
					scanner.nextInt());
			}
			if (entryPoint == null
					&& blocks[row][0].getType() == TILE_TYPE.PATH) {
				entryPoint = new Point(blocks[row][0].x, blocks[row][0].y);
			}
		}
		if (entryPoint == null)
			throw new IllegalArgumentException(
					"Map has no entry point on first column");

		if (scanner.hasNext())
			throw new IllegalArgumentException("Map too big");

		scanner.close();
		return entryPoint;
	}

	/**
	 * @return all the paths in the map
	 */
	private Set<Path> AnalyzePaths() {
		int row = 0;
		for (; blocks[row][0].getType() != TILE_TYPE.PATH; row++)
			;
		Point startCoord = new Point(0, row);
		return AnalyzePaths_recursive(startCoord, startCoord);
	}

	private Set<Path> AnalyzePaths_recursive(final Point prevPoint, final Point currentPoint) {
		HashSet<Path> $ = new HashSet<>();
		Set<Direction> walkableDirections = getWalkableDirections(prevPoint, currentPoint);
		if (walkableDirections.isEmpty() && getBlockType(currentPoint) == TILE_TYPE.END)
			$.add(new Path(currentPoint));
		for (Direction d : walkableDirections)
			for (Path p : AnalyzePaths_recursive(currentPoint, getNextPoint(currentPoint, d)))
				$.add(new PathBuilder(currentPoint).addTurn(d).concat(p).build());
		return $;
	}

	public Set<Direction> getWalkableDirections(final Point prevPoint, final Point currentPoint) {
		Set<Direction> walkableDirections = new HashSet<>();
		Arrays.asList(Direction.values()).forEach(d -> {
			final Point nextPoint = getNextPoint(currentPoint, d);
			if (d != Direction.no_direction && blockWalkable(nextPoint) && (!nextPoint.equals(prevPoint)))
				walkableDirections.add(d);
		});
		return walkableDirections;
	}

	public TILE_TYPE getBlockType(final Point coord) {
		if (!pointInRoom(coord))
			return TILE_TYPE.EMPTY;
		return getBlock(coord).getType();
	}

	public boolean blockHasTower(final Point coord) {
		if (!pointInRoom(coord))
			return false;
		return getBlock(coord).hasTower();
	}

	public void blockSetTower(final Point coord, final Tower tower) {
		getBlock(coord).setTower(tower);
	}

	public Tower blockGetTower(final Point coord) {
		return getBlock(coord).getTower();
	}

	public Point getEntryPoint() {
		return entryPoint;
	}

	public Rectangle blockGetRect(final Point coord) {
		return getBlock(coord);
	}

	private Block getBlock(final Point coord) {
		if (!pointInRoom(coord))
			throw new IllegalArgumentException("Point not in the room");
		return blocks[coord.y][coord.x];
	}

	@Override
	public Iterator<Point> iterator() {
		return new Iterator<Point>() {
			int x = -1;
			int y = 0;

			@Override
			public boolean hasNext() {
				return !(y == blocks.length - 1 && x == blocks[0].length - 1);
			}

			@Override
			public Point next() {
				if (x != blocks[0].length - 1) {
					x++;
				} else {
					y++;
					x = 0;
				}
				return new Point(x, y);
			}

			@Override
			public void remove() {
				return;
			}

		};
	}

	public int getXCoord(final int _x) {
		return (_x - x) / blockSize;
	}

	public int getYCoord(final int _y) {
		return (_y - y) / blockSize;
	}

	public boolean blockWalkable(final Point p) {
		switch (getBlockType(p)) {
		case PATH:
		case END:
			return true;
		case EMPTY:
		case GROUND:
			return false;
		default:
			throw new IllegalStateException("unknown block type");

		}
	}

	public boolean pointInRoom(final Point p) {
		return p.x >= 0 && p.x < roomWidth && p.y >= 0 && p.y < roomHeight;
	}

	public Path getRandomPath() {
		int size = paths.size();
		return paths.toArray(new Path[size])[RANDOM.nextInt(size)];
	}

	public boolean blockOnPath(final Point coord, final Path p) {
		if (!pointInRoom(coord))
			return false;
		return getBlock(coord).isOnPath(p);
	}
}