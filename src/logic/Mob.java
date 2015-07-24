package logic;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;

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
	private Direction direction;

	private final TimeFrame walkSpeed;

	private final TimeFrame blockCounter;
	private Iterator<Direction> directions;

	public Mob(final Point entryPoint, final int maxHealth, final int mobSpeed, final int mobSize) {
		this.maxHealth = maxHealth;
		this.mobSize = mobSize;

		roomEntryPoint = entryPoint;
		walkSpeed = new TimeFrame(mobSpeed);
		blockCounter = new TimeFrame(mobSize - 1);
	}

	public void spawnMob(final Path path) {
		this.directions = path.iterator();
		health = maxHealth;
		setBounds(roomEntryPoint.x, roomEntryPoint.y, mobSize, mobSize);
		walkSpeed.reset();
		blockCounter.reset();
		direction = directions.next();
		inGame = true;
	}

	/**
	 * does the mob walking and routing
	 * 
	 * @param room
	 *            - the room in which we walk in.
	 */
	public void physics(final Room room) {
		if (!walkSpeed.tick()) {
			return;
		}

		updateLocation(room);

		if (!blockCounter.tick()) {
			return;
		}

		if (directions.hasNext()){
			direction = directions.next();
			return;
		}

		if (legalEnd(room))
			throw new IllegalStateException("Map unpassable");

		// finished the map
		inGame = false;
	}

	public boolean legalEnd(final Room room) {
		Point thePoint = new Point(room.getXCoord(x), room.getYCoord(y));
		return room.getBlockType(thePoint) != TILE_TYPE.END;

	}

	private void updateLocation(final Room room) {
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
	 * hit the mob with i hit points
	 * 
	 * @param damage
	 * @return
	 */
	public void hit(final double damage) {
		health -= damage;
		if (health <= 0)
			inGame = false;
	}

	public double getHealth() {
		return health;
	}

	public void kill() {
		inGame = false;

	}

	public boolean isInGame() {
		return inGame;
	}

}
