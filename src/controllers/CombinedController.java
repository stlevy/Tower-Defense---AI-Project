package controllers;

import gui.GUIConfiguration;
import gui.GameViewer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import logic.Game;
import utils.GameConfiguration;
import ai.algorithms.AnytimeAlgorithm;
import ai.heuristics.Heuristic;
import ai.nodes.BoardState;
import ai.utils.ResultsWriter;

/**
 * This is the AI+GUI controller, no mouse is used here, you can just watch the
 * AI player as he makes his way through the game.
 * 
 * @author Tomer
 * 
 */
public class CombinedController implements Runnable {
	private final GameViewer gui_viewer;
	private final Game game;
	private final Thread gameThread = new Thread(this);

	private int level;

	private final AnytimeAlgorithm<BoardState> algorithm;
	private final Heuristic h;
//	private double currentHeuristicValue;
	private final ResultsWriter writer;
	private final GameConfiguration conf;
	private final GUIConfiguration gui_conf;

	public CombinedController(Game _game, ResultsWriter _writer,
			AnytimeAlgorithm<BoardState> _algo, Heuristic _h,
			GUIConfiguration gui_conf) {
		game = _game;
		algorithm = _algo;
		writer = _writer;
		h = _h;
		conf = game.getConfiguration();
		this.gui_conf = gui_conf;
		gui_viewer = new GameViewer(conf, gui_conf);
	}

	public void startGame() {
		gameThread.run();
	}

	// TODO: think how to prevent code duplication between controllers
	@Override
	public void run() {
		for (level = 1; level <= conf.numberOfLevels; level++) {
			startLevel();

			while (!game.isOver()) {
				// ai
				int availableItemsToBuy = game.availableItemsToBuy(0);
				if (availableItemsToBuy > 0) {
					for (Point towerCoords : AISearch(availableItemsToBuy)) {
						game.buyTower(towerCoords, 0);
						 writer.buyTower(towerCoords, 0);
					}
				}

				game.gamePhysics();
				gui_viewer.draw(true, game.getMoney(), game.getHealth());
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			writer.sumLevel(level, game.getMoney(), game.getHealth(),
					game.getMobsKilled(), game.getTowers().size(),
					conf.numberOfMobs);
			if (!continueToNextLevel())
				break;
		}
		endGame();
	}

	private List<Point> AISearch(int availableTowers) {
		BoardState root = new BoardState(availableTowers,h,game);
//		System.out.print("Searching...");
		BoardState best = algorithm.search(root);
		// System.out.println("node count:" + algorithm.getExpendedNodes());
		algorithm.reset();
		if (best == null || best.getWorth() < 0)
			return new ArrayList<Point>();
//		currentHeuristicValue += best.getWorth();
//		System.out.println("chosen " + best.toString()
//				+ "current heuristic value: " + currentHeuristicValue);
		return best.getTowerCoordinates();
	}

	private void startLevel() {
//		currentHeuristicValue = 0.0;
		game.initializeLevel(level, gui_conf.frameSize.width,
				gui_conf.frameSize.height - 20);

		gui_viewer.initializeLevel(level, game.getRoom(), game.getMobs(),
				game.getTowers());

		writer.initializeLevel(level);
	}

	private void endGame() {
		writer.close();
		JOptionPane.showMessageDialog(null, "Game over", "nah nah",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	private boolean continueToNextLevel() {
		if (level == conf.numberOfLevels)
			return false;
		return true;
	}
}
