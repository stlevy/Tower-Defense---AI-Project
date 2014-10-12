package controllers.gui;

import gui.GUIConfiguration;
import gui.GameViewer;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import logic.Game;
import utils.GameConfiguration;
import utils.GameUtils;

/**
 * This is the GUI Controller, or 'Human', no AI is involved here, just the human player with the mouse.
 * @author Tomer
 *
 */
public class GUIController implements Runnable {
	private GameViewer viewer;
	private Game game;
	private MouseHandler mouseHandle;
	private int itemHolds = -1;
	private Point mse;

	public final int trashIdx;

	private Thread gameThread = new Thread(this);

	private int level;
	private int numberOfLevels;
	private boolean started = false;
	private GUIConfiguration gui_conf;
	public GUIController(Game _game,GUIConfiguration gui_conf) {
		game = _game;
		this.gui_conf = gui_conf;
		GameConfiguration configuration = game.getConfiguration();
		viewer = new GameViewer(configuration,gui_conf);
		
		trashIdx = configuration.storeSlots - 1;
		numberOfLevels = configuration.numberOfLevels;
		
		mouseHandle = new MouseHandler(this);
		mse = new Point(0, 0);

		viewer.addMouseListener(mouseHandle);
		viewer.addMouseMotionListener(mouseHandle);

	}

	public void startGame() {
		gameThread.run();
	}

	@Override
	public void run() {
		for (level = 1; level <= numberOfLevels; level++) {
			startLevel();
			while (!game.isOver()) {
				game.gamePhysics();

				viewer.draw(started,game.getMoney(), game.getHealth());

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			started = false;
		}
		endGame();
	}

	private void startLevel() {
		game.initializeLevel(level, gui_conf.frameSize.width,
				gui_conf.frameSize.height);
		viewer.initializeLevel(level, game.getRoom(), game.getMobs(),
				game.getTowers());
		while(!started)
			viewer.draw(started,game.getMoney(), game.getHealth());
	}

	private void endGame() {
		JOptionPane.showMessageDialog(null, "Game over", "nah nah",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	public void click(MouseEvent e) {
		Point roomCoord = viewer.mseCoordsInRoom(mse);
		if (roomCoord != null) {
			roomClick(roomCoord);
			return;
		}
		int itemIdx = viewer.mseIndexInStore(mse);
		if (itemIdx >= 0) {
			storeClick(itemIdx);
			return;
		}
		if ( !started )
			started = true;
	}

	private void roomClick(Point roomCoord) {
		if (isHoldingItem() && game.availableItemsToBuy(itemHolds) > 0
				&& game.getBlockType(roomCoord) == GameUtils.TILE_TYPE.GROUND
				&& !game.hasTower(roomCoord)) {
			game.buyTower(roomCoord, itemHolds);
			cancelHold();
		}
	}

	private void storeClick(int itemIdx) {
		if (itemIdx == trashIdx) {
			cancelHold();
			return;
		}
		itemHolds = itemIdx;
	}

	private boolean isHoldingItem() {
		return itemHolds != -1;
	}

	private void cancelHold() {
		itemHolds = -1;
	}

	public void setMSE(Point mse) {
		this.mse = mse;
	}
}
