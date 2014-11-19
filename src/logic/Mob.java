package logic;

import java.awt.Point;
import java.awt.Rectangle;

import utils.GameUtils.Direction;
import utils.GameUtils.TILE_TYPE;
import utils.TimeFrame;

/**
 * this class manages the mobs logical unit:
 * 		the mob's health
 * 		the mob's locations
 * 		the mob's routing algorithm
 * @author Tomer
 *
 */
public class Mob extends Rectangle {

	private final Point roomEntryPoint;
	private final int mobSize;
	private boolean inGame = false;
	private double health;
	private final int maxHealth;

	// movement fields 
	private Direction direction = Direction.no_direction;

	private final TimeFrame walkSpeed;

	private final TimeFrame blockCounter;

	public Mob(Point entryPoint, int maxHealth, int mobSpeed,int mobSize) {
		this.maxHealth = maxHealth;
		this.mobSize = mobSize;
		roomEntryPoint = entryPoint;
		walkSpeed = new TimeFrame(mobSpeed);
		blockCounter = new TimeFrame(mobSize - 1);
		// New mobs branch test
		
	}

	public void spawnMob() {
		health = maxHealth;
		setBounds(roomEntryPoint.x, roomEntryPoint.y, mobSize, mobSize);
		walkSpeed.reset();
		blockCounter.reset();
		direction = Direction.no_direction;
		inGame = true;
	}

	/**
	 * does the mob walking and routing
	 * 
	 * @param room
	 *            - the room in which we walk in.
	 */
	public void physics(Room room) {
		if (!walkSpeed.tick()) {
			return;
		}

		updateLocation(room);

		if (!blockCounter.tick()) {
			return;
		}

		if (!collision(room)) {
			return; // continue walking
		}

		// collide in next step ... reRout

		int xCoord = room.getXCoord(x);
		int yCoord = room.getYCoord(y);

		direction = getNewDirection(room, xCoord, yCoord);
	}

	private Direction getNewDirection(Room room, int xCoord, int yCoord) {
		Point downPoint = new Point(xCoord, yCoord + 1);
		if ( checkDirection(downPoint, Direction.downward, room) )
			return Direction.downward;
			
		Point upPoint = new Point(xCoord, yCoord - 1);
		if ( checkDirection(upPoint, Direction.upward, room) )
			return Direction.upward;

		Point rightPoint = new Point(xCoord + 1, yCoord);
		if ( checkDirection(rightPoint, Direction.right, room) )
			return Direction.right;

		Point leftPoint = new Point(xCoord - 1, yCoord);
		if ( checkDirection(leftPoint, Direction.left, room) )
			return Direction.left;


		Point thePoint = new Point(xCoord, yCoord);
		if (room.getBlockType(thePoint) != TILE_TYPE.END) {
			throw new IllegalStateException("Map unpassable");
		}
		
		// finished the map
		inGame = false;
		return direction;

	}

	private boolean checkDirection(Point p, Direction d, Room room) {
		return direction != d.opposite() && room.pointInRoom(p)
				&& room.blockWalkable(p);
	}

	private void updateLocation(Room room) {
		if (direction == Direction.no_direction)
			direction = getNewDirection(room, room.getXCoord(x),
					room.getYCoord(y));
		switch (direction) {
		case right:
			x++;
			break;
		case downward:
			y++;
			break;
		case left:
			x--;
			break;
		case upward:
			y--;
			break;
		case no_direction:
		default:
			throw new IllegalStateException("no direction");
		}
	}

	/**
	 * checks whether we are going to collide in a wall in the next step
	 * 
	 * @param room
	 *            - the room in which we walk
	 * @return true if we are going to collide , false otherwise.
	 */
	private boolean collision(Room room) {
		int nextX = room.getXCoord(x) + deltaXCoord(room);
		int nextY = room.getYCoord(y) + deltaYCoord(room);
		Point nextCoord = new Point(nextX, nextY);
		return (!room.pointInRoom(nextCoord))
				|| (room.getBlockType(nextCoord) == TILE_TYPE.GROUND);
	}

	/**
	 * hit the mob with i hit points
	 * 
	 * @param damage
	 * @return
	 */
	public void hit(double damage) {
		health -= damage;
		if (health <= 0)
			inGame = false;
	}

	public double getHealth() {
		return health;
	}

	private int deltaXCoord(Room room) {
		if (direction == Direction.right)
			return 1;
		if (direction == Direction.left)
			return -1;
		return 0;
	}

	private int deltaYCoord(Room room) {
		if (direction == Direction.downward)
			return 1;
		if (direction == Direction.upward)
			return -1;
		return 0;
	}

	public void kill() {
		inGame = false;

	}

	public boolean isInGame() {
		return inGame;
	}

}
