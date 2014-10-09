package controllers.ai;

import gui.GUIUtils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import logic.Game;
import utils.GameConfiguration;
import ai.algorithms.AnytimeAlgorithm;
import ai.heuristics.Heuristic;
import ai.heuristics.PathHeuristic;
import ai.nodes.BoardState;
import ai.utils.ResultsWriter;

public class AIController implements Runnable {
	private Game game;

	private Thread gameThread = new Thread(this);

	private int level;

	private AnytimeAlgorithm<BoardState> algorithm;

	private ResultsWriter writer;

	private Heuristic h;
	double currentHeuristicValue;
	
	private final GameConfiguration conf;

	public AIController(Game _game, ResultsWriter _writer,
			AnytimeAlgorithm<BoardState> algo, Heuristic _h) {
		game = _game;
		algorithm = algo;
		writer = _writer;
		h = _h;
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
						// writer.buyTower(towerCoords, 0);
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
		BoardState root = getGreedyRoot(availableTowers);
		System.out.print("Searching...");
		BoardState best = algorithm.search(root);
		// System.out.println("node count:" + algorithm.getExpendedNodes());
		algorithm.reset();
		if (best == null || best.getWorth() < 0)
			return new ArrayList<Point>();
		currentHeuristicValue += best.getWorth();
		System.out.println("chosen: "+best.toString() + "current heuristic value: " + currentHeuristicValue);
		return best.getTowerCoordinates();
	}

	private BoardState getGreedyRoot(int availableTowers) {
		final PathHeuristic greedyHeu = new PathHeuristic();
		List<Point> l = new ArrayList<>();
		for (Point p : game)
			l.add(p);
		Comparator<Point> comareByHeuristics = new Comparator<Point>() {

			@Override
			public int compare(Point p1, Point p2) {
				return greedyHeu.pathIntersections(p2,game)
						- greedyHeu.pathIntersections(p1,game);
			}
		};
		Collections.sort(l, comareByHeuristics);
		List<Point> best = l.subList(0, availableTowers);
		return new BoardState(best, h, game);
	}

	private void startLevel() {
		currentHeuristicValue = 0.0;
		game.initializeLevel(level, GUIUtils.frameSize.width,
				GUIUtils.frameSize.height - 20);
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
