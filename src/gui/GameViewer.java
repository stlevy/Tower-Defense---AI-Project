package gui;

import gui.viewers.MobViewer;
import gui.viewers.RoomViewer;
import gui.viewers.StoreViewer;
import gui.viewers.TowerViewer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import logic.Mob;
import logic.Room;
import logic.Tower;
import utils.GameConfiguration;

/**
 * The main viewer, this class controlls all the other viewers under gui.viewers
 * package in a single class
 * 
 * @author Tomer
 * 
 */
public class GameViewer extends JPanel {

	protected boolean isFirst = true;

	private Room room;
	private Mob[] mobs;
	private List<Tower> towers;
	private GameConfiguration conf;

	private StoreViewer storeViewer;

	private int currentHealth;
	private int currentMoney;
	private int currentLevel;

	private final GUIConfiguration gui_conf;

	private boolean levelStarted;

	public GameViewer(GameConfiguration conf, GUIConfiguration gui_conf) {
		this.conf = conf;
		this.gui_conf = gui_conf;
		@SuppressWarnings("unused")
		Frame gameFrame = new Frame(this);
	}

	public void initializeLevel(int level, Room _room, Mob[] _mobs,
			List<Tower> _towers) {
		currentLevel = level;
		room = _room;
		mobs = _mobs;
		towers = _towers;
		initializeStore(level, getWidth(), getHeight());
		isFirst = false;
	}

	public void draw(boolean started, int money, int health) {
		currentHealth = health;
		currentMoney = money;
		levelStarted = started;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isFirst)
			return;
		g.clearRect(0, 0, getGuiConf().frameSize.width,
				getGuiConf().frameSize.height);
		int font_size = getGuiConf().fontSize * 2;
		g.setFont(new Font("Ariel", Font.BOLD, font_size));
		if (levelStarted)
			g.drawString("Playing level " + currentLevel,
					getGuiConf().frameSize.width / 4, font_size * 2);
		else
			g.drawString("Press here to begin",
					getGuiConf().frameSize.width / 4, font_size * 2);
		drawRoom(g);
		drawTowers(g);
		drawMobs(g);
		drawStore(g);
	}

	private void drawStore(Graphics g) {
		storeViewer.draw(g, currentMoney, currentHealth);
	}

	private void drawTowers(Graphics g) {
		for (Tower tower : towers) {
			TowerViewer.draw(g, tower, tower.getRange(), tower.isAiming(),
					tower.getAimedMob(), tower.getLevel(), gui_conf);
		}
	}

	private void drawRoom(Graphics g) {
		for (Point p : room) {
			RoomViewer.drawBlock(g, room.blockGetRect(p), room.getBlockType(p));
		}
	}

	private void drawMobs(Graphics g) {
		for (Mob mob : mobs) {
			MobViewer.draw(g, mob, mob.getHealth(), mob.isInGame(),
					conf.mobHealth, gui_conf);
		}
	}

	public int mseIndexInStore(Point mse) {
		return storeViewer.mseInStore(mse);
	}

	public Point mseCoordsInRoom(Point mse) {
		return RoomViewer.mseInRoom(mse, room, conf.blockSize);
	}

	private void initializeStore(int level, int gameWidth, int gameHeight) {
		int initialX = (gameWidth / 2)
				- (conf.storeSlots * getGuiConf().itemSize / 2);
		int initialY = gameHeight - getGuiConf().itemSize
				- getGuiConf().storeSlotsSeperator;
		Point initialStorePoint = new Point(initialX, initialY);
		Point healthPoint = new Point(initialX / 4, initialY - 2
				* getGuiConf().iconSize);
		Point coinagePoint = new Point(initialX / 4, initialY);
		storeViewer = new StoreViewer(initialStorePoint, healthPoint,
				coinagePoint, conf.storeSlots, conf.prices, gui_conf);
	}

	public boolean getIsFirst() {
		return isFirst;
	}

	public GUIConfiguration getGuiConf() {
		return gui_conf;
	}

}
