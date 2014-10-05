package controllers.gui;

import gui.GUIUtils;
import gui.GameViewer;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import logic.Game;
import utils.GameConfiguration;
import utils.GameUtils;

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

	public GUIController(Game _game) {
		game = _game;
		
		GameConfiguration configuration = game.getConfiguration();
		viewer = new GameViewer(configuration);
		
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

				viewer.draw(game.getMoney(), game.getHealth());

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!continueToNextLevel())
				break;
		}
		endGame();
	}

	private void startLevel() {
		game.initializeLevel(level, GUIUtils.frameSize.width,
				GUIUtils.frameSize.height - 20);
		viewer.initializeLevel(level, game.getRoom(), game.getMobs(),
				game.getTowers());
	}

	private void endGame() {
		JOptionPane.showMessageDialog(null, "Game over", "nah nah",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	private boolean continueToNextLevel() {
//		if (level == GameUtils.numberOfLevels)
//			return false;
//		return JOptionPane.showConfirmDialog(null,
//				"Press OK for next level, Cancel to exit", "Bad AI!",
//				JOptionPane.OK_CANCEL_OPTION) != JOptionPane.CANCEL_OPTION;
		return true;
	}

	public void click(MouseEvent e) {
		Point roomCoord = viewer.mseCoordsInRoom(mse);
		if (roomCoord != null) {
			roomClick(roomCoord);
		}
		int itemIdx = viewer.mseIndexInStore(mse);
		if (itemIdx >= 0) {
			storeClick(itemIdx);
		}

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
