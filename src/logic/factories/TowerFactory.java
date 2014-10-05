package logic.factories;

import java.awt.Rectangle;

import logic.Tower;

public class TowerFactory {
	
	private int speed;
	private boolean configured;
	private double initialDamage;
	public Tower create(Rectangle rect , int range){
		if (!configured)
			throw new IllegalStateException("Tower factory is not configured");
		return new Tower(rect,range,speed,initialDamage);
	}
	
	public void configureFactory(int firing_speed, double initial_damage)	{
		configured = true;
		speed = firing_speed;
		initialDamage = initial_damage;
	}
}