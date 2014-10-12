package logic;
import utils.TimeFrame;

/**
 * this class is responsable to the spawning of the monsters.
 * @author Tomer
 *
 */
public class MobSpawner extends TimeFrame{
	int lastSpawn = 0;
	public MobSpawner(int _spawnTime){
		super(_spawnTime);
	}
	/**
	 * returns true when it spawned.
	 */
	public boolean tick(Mob[] mobs){
		if(!super.tick())
			return false;
		spawn(mobs);
		return true;
	}

	private void spawn(Mob[] mobs) {
		for (int i = lastSpawn; i < mobs.length; i++) {
			if(!mobs[i].isInGame()){
				mobs[i].spawnMob();
				lastSpawn++;
				break;
			}
		}
	}
	
}
