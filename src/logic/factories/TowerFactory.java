package logic.factories;

import java.awt.Rectangle;

import logic.Tower;

public class TowerFactory {

	private int speed;
	private boolean configured;
	private double initialDamage;
	private int range;
	private int bonus;

	public Tower create(Rectangle rect) {
		if (!configured)
			throw new IllegalStateException("Tower factory is not configured");
		return new Tower(rect, range, speed, initialDamage, bonus);
	}

	public void configureFactory(int firing_speed, double initial_damage,
			int tower_range, int levelUpBonus) {
		configured = true;
		speed = firing_speed;
		initialDamage = initial_damage;
		range = tower_range;
		bonus = levelUpBonus;
	}
}