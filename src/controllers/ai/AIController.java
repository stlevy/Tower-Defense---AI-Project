package controllers.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.Game;
import utils.GameConfiguration;
import ai.algorithms.AnytimeAlgorithm;
import ai.heuristics.Heuristic;
import ai.nodes.BoardState;
import ai.utils.ResultsWriter;

/**
 * this is the AI Controller, it has no GUI.
 * 
 * @author Tomer
 * 
 */
public class AIController implements Runnable {
	private Game game;

	private Thread gameThread = new Thread(this);

	private int level;

	private AnytimeAlgorithm<BoardState> algorithm;

	private ResultsWriter writer;

	private Heuristic h;

	private final GameConfiguration conf;
	private final int gameWidth, gameHeight;

	public AIController(Game _game, ResultsWriter _writer,
			AnytimeAlgorithm<BoardState> algo, Heuristic _h, int width,
			int height) {
		game = _game;
		algorithm = algo;
		writer = _writer;
		h = _h;
		gameWidth = width;
		gameHeight = height;
		conf = game.getConfiguration();
	}

	public void startGame() {
		gameThread.run();
	}

	@Override
	public void run() {
		for (level = 1; level <= conf.numberOfLevels; level++) {
			startLevel();

			while (!game.isOver()) {
				int availableItemsToBuy = game.availableItemsToBuy(0);
				if (availableItemsToBuy > 0) {
					for (Point towerCoords : AISearch(availableItemsToBuy)) {
						game.buyTower(towerCoords, 0);
						writer.buyTower(towerCoords, 0);
					}
				}
				game.gamePhysics();
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
		BoardState root = new BoardState(availableTowers, h, game);
		// System.out.print("Searching...");
		BoardState best = algorithm.search(root);
		// System.out.println("node count:" + algorithm.getExpendedNodes());
		algorithm.reset();
		if (best == null || best.getWorth() < 0)
			return new ArrayList<Point>();
		System.out.println("chosen: " + best.toString()
				+ " current heuristic value: " + best.getWorth());
		return best.getTowerCoordinates();
	}

	private void startLevel() {
		game.initializeLevel(level, gameWidth, gameHeight);
		writer.initializeLevel(level);
	}

	private void endGame() {
		writer.close();
		System.exit(0);
	}

	private boolean continueToNextLevel() {
		if (level == conf.numberOfLevels)
			return false;
		return true;
	}

	public boolean isOver() {
		return game.isOver();
	}

}
