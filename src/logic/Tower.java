package logic;

import java.awt.Point;
import java.awt.Rectangle;

import utils.TimeFrame;

/**
 * manages the tower logical unit: the tower's level the tower's damage
 * calculation the tower's aiming and firing
 * 
 * @author Tomer
 * 
 */
public class Tower extends Rectangle {
	private final TimeFrame firingFrame;

	protected final Rectangle towerRange;
	protected Mob aimedMob = null;

	private double initialDamage;

	private int level;
	private final int bonusPrecentage;

	private final Point mapLocation;

	public Tower(Rectangle containing, Point location, int range,
			int firing_speed, double initial_damage, int bonus) {
		setBounds(containing.x, containing.y, containing.width,
				containing.height);
		mapLocation = location;
		towerRange = new Rectangle(x - range / 2, y - range / 2,
				containing.width + range, containing.height + range);
		firingFrame = new TimeFrame(firing_speed);
		initialDamage = initial_damage;
		bonusPrecentage = bonus;
		level = 0;
	}

	/**
	 * aims and shoots for a mob in range.
	 * 
	 * @param mobs
	 *            array of mobs in game
	 * @return true if it killed a mob in this step. false otherwise
	 */
	public boolean physics(Mob[] mobs) {
		if (!firingFrame.tick())
			return false;

		if (!isAiming()) {
			aimedMob = getMobInRange(mobs);
			if (aimedMob == null)
				return false;
		}

		return hitAimedMob();
	}

	/**
	 * hits the mob aimed by the tower
	 * 
	 * @return returns true if the mob was killed
	 */
	protected boolean hitAimedMob() {
		aimedMob.hit(calculateDamage());
		if (!aimedMob.isInGame()) {
			aimedMob.kill();
			aimedMob = null;
			return true;
		}
		return false;
	}

	private Mob getMobInRange(Mob[] mobs) {
		Mob mostWounded = null;
		for (Mob mob : mobs) {
			if (!mob.isInGame() || (!towerRange.intersects(mob)))
				continue;

			if (mostWounded == null
					|| (mob.getHealth() <= mostWounded.getHealth())) {
				mostWounded = mob;
			}

		}
		return mostWounded;
	}

	/**
	 * 
	 * @return returns true only if the mob is alive , the rectangle is aiming
	 *         at it, and the mob is in the rectangle's range
	 */
	public boolean isAiming() {
		return aimedMob != null && aimedMob.isInGame()
				&& towerRange.intersects(aimedMob);
	}

	public Rectangle getRange() {
		if (towerRange == null)
			throw new IllegalStateException("Tower has no range rect!");
		return (Rectangle) towerRange.clone();
	}

	public Rectangle getAimedMob() {
		if (aimedMob == null)
			return null;
		return (Rectangle) aimedMob.clone();
	}

	public void levelUp() {
		level++;
	}

	public int getLevel() {
		return level;
	}

	public Point getMapLocation() {
		return mapLocation;
	}

	private double calculateDamage() {
		return initialDamage + initialDamage
				* (bonusPrecentage * level / 100.0);
	}
}
