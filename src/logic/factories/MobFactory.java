package logic.factories;

import java.awt.Point;

import logic.Mob;

public class MobFactory {
	boolean configured = false;
	private int speed;
	private int health;
	private int size;
	
	public Mob create(Point entryPoint){
		if (!configured)
			throw new IllegalStateException("Mob factory is not configured");
		return new Mob(entryPoint,health,speed,size);
	}
	
	public Mob[] createArray(int arraySize){
		return new Mob[arraySize];
	}
	
	public void configureFactory(int maximum_health,int walk_speed , int mob_size)	{
		health = maximum_health;
		speed = walk_speed;
		size = mob_size;
		configured = true;
	}
}
