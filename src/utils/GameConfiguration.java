package utils;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

public class GameConfiguration {

	private static final String CONFINGURAION_FILE = "Levels/conf.ini";
	
	public GameConfiguration(int speedFactor) throws InvalidFileFormatException, IOException {
		Ini confFile = new Ini(new File(CONFINGURAION_FILE));		
		Section mobSect = confFile.get("Mob");
		mobHealth = Integer.parseInt(mobSect.get("mobHealth"));
		mobSpeed = Integer.parseInt(mobSect.get("mobSpeed")) /speedFactor;
		mobReward = Integer.parseInt(mobSect.get("mobReward"));
		
		Section towerSect = confFile.get("Tower");
		towerRange = Integer.parseInt(towerSect.get("towerRange"));
		firingSpeed= Integer.parseInt(towerSect.get("firingSpeed")) / speedFactor;
		initialDamage = Double.parseDouble(towerSect.get("initialDamage"));
		damagePrecentageBonus = Integer.parseInt(towerSect.get("damagePrecentageBonus"));
	
		Section spawnerSect = confFile.get("Spawner");
		spawnTime = Integer.parseInt(spawnerSect.get("spawnTime")) / speedFactor;

		Section roomSect = confFile.get("Room");
		roomWidth = Integer.parseInt(roomSect.get("width"));
		roomHeight = Integer.parseInt(roomSect.get("height"));
		blockSize = Integer.parseInt(roomSect.get("blockSize"));
		
		Section storeSect = confFile.get("Store");
		storeSlots = Integer.parseInt(storeSect.get("slots"));
		prices = new int[storeSlots];
		for (int i = 0; i < storeSlots-1; i++) {
			prices[i] = Integer.parseInt(storeSect.get("price"+i));
		}
		
		Section etcSect = confFile.get("Etc");
		initialHealth = Integer.parseInt(etcSect.get("initialHealth"));
		initialMoney = Integer.parseInt(etcSect.get("initialMoney"));
		numberOfMobs = Integer.parseInt(etcSect.get("numberOfMobs"));
		numberOfLevels = Integer.parseInt(etcSect.get("numberOfLevels"));
	}

	// Mob
	public final int mobHealth;
	public final int mobSpeed; // bigger -> slower
	public final int mobReward;
	
	// Tower
	public final int towerRange;
	public final int firingSpeed; // bigger -> slower
	public final double initialDamage;
	public final int damagePrecentageBonus;


	// Spawner
	public final int spawnTime;

	// Room
	public final int roomWidth;
	public final int roomHeight;
	public final int blockSize;

	// Store constants
	public final int storeSlots;
	public final int[] prices;

	// Etc
	public final int initialHealth;
	public final int initialMoney;
	public final int numberOfMobs;
	public final int numberOfLevels;

}
