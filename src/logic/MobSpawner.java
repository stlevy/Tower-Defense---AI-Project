package logic;
import utils.TimeFrame;

public class MobSpawner extends TimeFrame{
	int lastSpawn = 0;
	public MobSpawner(int _spawnTime){
		super(_spawnTime);
	}
	
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
