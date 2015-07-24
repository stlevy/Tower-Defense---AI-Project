package logic;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import logic.factories.MobFactory;
import logic.factories.TowerFactory;
import utils.GameConfiguration;
import utils.GameUtils.TILE_TYPE;

/**
 * the game model, completely decoupled from the AI and GUI parts.
 * simply controlls the game objects (towers,room,monsters)
 * @author Tomer
 *
 */
public class GameModel implements Game {

	private Point initialRoomPoint;
	private Point roomEntryPoint;

	private Room room;
	private List<Tower> towers;
	private Mob[] mobs;
	private MobSpawner mobSpawner;

	private final MobFactory mobFactory;
	private final TowerFactory towerFactory;

	private int money;
	private int health;
	private int killedMobs;
	private int passedMobs;

	private final GameConfiguration conf;

	public GameModel(final MobFactory mFact, final TowerFactory tFact,
			final GameConfiguration conf) throws IOException {
		mobFactory = mFact;
		towerFactory = tFact;
		this.conf = conf;
		configureFactories();
	}

	@Override
	public void initializeLevel(final int level, final int gameWidth, final int gameHeight) {
		initializeRoom(level, gameWidth, gameHeight);
		initializeMobs(level, gameWidth, gameHeight);
		initializeTowers(level, gameWidth, gameHeight);
		mobSpawner = new MobSpawner(conf.spawnTime, room);
		money = conf.initialMoney;
		health = conf.initialHealth;
		killedMobs = 0;
		passedMobs = 0;
	}

	private void configureFactories() {
		mobFactory.configureFactory(conf.mobHealth, conf.mobSpeed,
			conf.blockSize);
		towerFactory.configureFactory(conf.firingSpeed, conf.initialDamage,
			conf.towerRange, conf.levelUpBonus);
	}

	private void initializeTowers(final int level, final int gameWidth, final int gameHeight) {
		towers = new ArrayList<Tower>();
	}

	private void initializeRoom(final int level, final int gameWidth, final int gameHeight) {
		int initialX = (gameWidth / 2) - (conf.roomWidth * conf.blockSize / 2);
		int initialY = (gameHeight / 2)
				- (conf.roomHeight * conf.blockSize / 2);
		initialRoomPoint = new Point(initialX, initialY);
		room = new Room(conf.roomWidth, conf.roomHeight, conf.blockSize,
			initialRoomPoint, new File("Levels/level" + level + ".txt"));
		roomEntryPoint = room.getEntryPoint();

	}

	private void initializeMobs(final int level, final int gameWidth, final int gameHeight) {
		mobs = mobFactory.createArray(conf.numberOfMobs);

		for (int i = 0; i < mobs.length; i++) {
			mobs[i] = mobFactory.create(roomEntryPoint);
		}
	}

	@Override
	public void gamePhysics() {
		mobSpawner.tick(mobs);

		for (Tower tower : towers) {
			if (tower.physics(mobs)) {
				mobKilled();
			}
		}

		for (int i = 0; i < mobs.length; i++) {
			if (!mobs[i].isInGame())
				continue;
			mobs[i].physics(room);
			if (!mobs[i].isInGame())
				mobPassed();
		}

	}

	@Override
	public void buyTower(final Point roomCoord, final int itemHolds) {
		// check if we have enough money
		if (availableItemsToBuy(itemHolds) == 0)
			throw new IllegalStateException("Not enough money to buy index "
					+ itemHolds);

		// check if we can build in roomCoord
		if (room.blockHasTower(roomCoord)
				|| room.getBlockType(roomCoord) != TILE_TYPE.GROUND)
			throw new IllegalStateException("can't build there"
					+ roomCoord.toString());
		money -= conf.prices[itemHolds];
		Tower newTower = towerFactory.create(room.blockGetRect(roomCoord),roomCoord);
		towers.add(newTower);
		room.blockSetTower(roomCoord, newTower);
		updateNeighborTowers(roomCoord, newTower);
	}

	@Override
	public boolean towerSeesPath(final Point towerCoord) {
		boolean seesPath = false;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				if (deltaX == 0 && deltaY == 0)
					continue;

				Point neighbor = new Point(towerCoord.x + deltaX, towerCoord.y
					+ deltaY);
				if (getBlockType(neighbor) == TILE_TYPE.PATH)
					seesPath = true;
			}
		}
		return seesPath;
	}

	private void updateNeighborTowers(final Point roomCoord, final Tower tower) {
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				if (deltaX == 0 && deltaY == 0)
					continue;
				Point neighbor = new Point(roomCoord.x + deltaX, roomCoord.y
					+ deltaY);
				if (pointInRoom(neighbor) && hasTower(neighbor)) {
					// level the neighbor up
					room.blockGetTower(neighbor).levelUp();
					// level self up
					tower.levelUp(); 
				}
			}
		}
	}

	private void mobKilled() {
		money += conf.mobReward;
		killedMobs++;
	}

	private void mobPassed() {
		health--;
		passedMobs++;
	}

	@Override
	public Mob[] getMobs() {
		return mobs;
	}

	@Override
	public Room getRoom() {
		return room;
	}

	@Override
	public List<Tower> getTowers() {
		return towers;
	}

	@Override
	public int getMoney() {
		return money;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int availableItemsToBuy(final int index) {
		if (conf.prices[index] == 0)
			return 1;
		return money / conf.prices[index];
	}

	@Override
	public TILE_TYPE getBlockType(final Point coord) {
		return room.getBlockType(coord);
	}

	@Override
	public boolean hasTower(final Point coord) {
		return room.blockHasTower(coord);
	}

	@Override
	public boolean isOver() {
		return health == 0 || (killedMobs + passedMobs == conf.numberOfMobs);
	}

	@Override
	public int getMobsKilled() {
		return killedMobs;
	}

	public boolean pointInRoom(final Point p) {
		return room.pointInRoom(p);
	}

	@Override
	public GameConfiguration getConfiguration() {
		return conf;
	}

	@Override
	public Iterator<Point> iterator() {
		return room.iterator();
	}
}
