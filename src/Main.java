import java.io.IOException;
import java.text.DecimalFormat;

import logic.Game;
import logic.GameModel;
import logic.factories.MobFactory;
import logic.factories.TowerFactory;
import utils.GameConfiguration;
import ai.algorithms.AnytimeAlgorithm;
import ai.algorithms.BeamSearchPrioriryQueue;
import ai.algorithms.heuristics.LevelsHeuristic;
import ai.algorithms.heuristics.Heuristic;
import ai.algorithms.heuristics.PathHeuristic;
import ai.algorithms.heuristics.WeightedHeuristics;
import ai.algorithms.nodes.BoardState;
import ai.utils.ResultsWriter;
import controllers.CombinedController;
import controllers.ai.AIController;
import controllers.gui.GUIController;

public class Main {
	public static void main(String[] args) throws IOException {
		int speedFactor = getSpeedFactor(args[0]);
		Game server = new GameModel(new MobFactory(), new TowerFactory(),
				new GameConfiguration(speedFactor));
		if (args[0].equals("human")) {
			guiNoAI(server);
			return;
		}
		// Experiment constants
		int runningTime = Integer.parseInt(args[1]);
		int beamSize = Integer.parseInt(args[2]);
		double alpha = Double.parseDouble(args[3]);
		String description = "Weighted Heuristics "
				+ new DecimalFormat("#.00").format(alpha);

		// AI algorithm
		BeamSearchPrioriryQueue<BoardState> algo = new BeamSearchPrioriryQueue<>(
				beamSize, runningTime);

		// Heuristic function
		Heuristic bonus = new LevelsHeuristic();
		Heuristic path = new PathHeuristic();
		Heuristic h = new WeightedHeuristics(server, path, bonus, alpha);

		ResultsWriter text_viewer = new ResultsWriter(description, runningTime,
				beamSize, alpha);

		if (args[0].equals("gui")) {
			guiAI(server, text_viewer, algo, h);
			return;
		}

		if (args[0].equals("ai")) {
			guilessAI(server, text_viewer, algo, h);
			return;
		}
		throw new IllegalArgumentException("args[0] was not a valid argument");
	}

	private static int getSpeedFactor(String mode) {
		if (mode.equals("human"))
			return 1;
		if (mode.equals("gui"))
			return 2;
		if (mode.equals("ai"))
			return 4;
		return -1;
	}

	private static void guiAI(Game server, ResultsWriter text_viewer,
			AnytimeAlgorithm<BoardState> algo, Heuristic h) {
		CombinedController controller = new CombinedController(server,
				text_viewer, algo, h);
		controller.run();
	}

	public static void guilessAI(Game server, ResultsWriter text_viewer,
			AnytimeAlgorithm<BoardState> algo, Heuristic h) throws IOException {

		AIController controller = new AIController(server, text_viewer, algo, h);
		controller.startGame();
	}

	public static void guiNoAI(Game server) {

		GUIController controller = new GUIController(server);
		controller.startGame();
	}
}
