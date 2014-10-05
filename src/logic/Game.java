package logic;

import java.awt.Point;
import java.util.List;

import utils.GameConfiguration;
import utils.GameUtils.TILE_TYPE;

public interface Game extends Iterable<Point>{

	public abstract void initializeLevel(int level, int gameWidth,
			int gameHeight);

	public abstract void gamePhysics();

	public abstract void buyTower(Point roomCoord, int item);

	public abstract Mob[] getMobs();

	public abstract Room getRoom();

	public abstract List<Tower> getTowers();

	public abstract int getMoney();

	public abstract int getHealth();

	public abstract int availableItemsToBuy(int item);

	public abstract TILE_TYPE getBlockType(Point coord);

	public abstract boolean hasTower(Point coord);
	
	public abstract boolean towerSeesPath(Point towerCoord);
	
	public abstract boolean isOver();

	public abstract int getMobsKilled();
	
	public abstract GameConfiguration getConfiguration();
}