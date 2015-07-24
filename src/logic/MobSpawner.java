package logic;
import utils.TimeFrame;

/**
 * this class is responsable to the spawning of the monsters.
 * @author Tomer
 *
 */
public class MobSpawner extends TimeFrame{
	int lastSpawn = 0;
	private final Room room;

	public MobSpawner(final int _spawnTime, final Room room) {
		super(_spawnTime);
		this.room = room;
	}
	/**
	 * returns true when it spawned.
	 */
	public boolean tick(final Mob[] mobs){
		if(!super.tick())
			return false;
		spawn(mobs);
		return true;
	}

	private void spawn(final Mob[] mobs) {
		for (int i = lastSpawn; i < mobs.length; i++) {
			if(!mobs[i].isInGame()){
				mobs[i].spawnMob(room.getRandomPath());
				lastSpawn++;
				break;
			}
		}
	}

}
