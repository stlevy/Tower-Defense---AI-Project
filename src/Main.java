import gui.GUIConfiguration;

import java.io.IOException;

import logic.Game;
import logic.GameModel;
import logic.factories.MobFactory;
import logic.factories.TowerFactory;
import utils.GameConfiguration;
import ai.algorithms.AnytimeAlgorithm;
import ai.algorithms.BeamSearchPrioriryQueue;
import ai.heuristics.Heuristic;
import ai.heuristics.LevelsHeuristic;
import ai.heuristics.PathHeuristic;
import ai.heuristics.WeightedHeuristics;
import ai.nodes.BoardState;
import ai.utils.ResultsWriter;
import controllers.CombinedController;
import controllers.ai.AIController;
import controllers.gui.GUIController;

public class Main {
	public static void main(String[] args) throws IOException {
		int speedFactor = getSpeedFactor(args[0]);
		GUIConfiguration guiConf = new GUIConfiguration();
		Game server = new GameModel(new MobFactory(), new TowerFactory(),
				new GameConfiguration(speedFactor));
		if (args[0].equals("human")) {
			guiNoAI(server, guiConf);
			return;
		}
		// Experiment constants
		int runningTime = Integer.parseInt(args[1]);
		int beamSize = Integer.parseInt(args[2]);
		double alpha = Double.parseDouble(args[3]);
		// AI algorithm
		BeamSearchPrioriryQueue<BoardState> algo = new BeamSearchPrioriryQueue<>(
				beamSize, runningTime);

		// Heuristic function
		Heuristic bonus = new LevelsHeuristic();
		Heuristic path = new PathHeuristic();
		Heuristic h = new WeightedHeuristics(server, path, bonus, alpha);

		ResultsWriter text_viewer = new ResultsWriter(
				server.getConfiguration().numberOfLevels, runningTime,
				beamSize, alpha);

		if (args[0].equals("gui")) {
			guiAI(server, text_viewer, algo, h, guiConf);
			return;
		}

		if (args[0].equals("ai")) {
			guilessAI(server, text_viewer, algo, h, guiConf);
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
			AnytimeAlgorithm<BoardState> algo, Heuristic h,
			GUIConfiguration guiConf) {
		CombinedController controller = new CombinedController(server,
				text_viewer, algo, h, guiConf);
		controller.run();
	}

	public static void guilessAI(Game server, ResultsWriter text_viewer,
			AnytimeAlgorithm<BoardState> algo, Heuristic h,
			GUIConfiguration guiConf) throws IOException {

		AIController controller = new AIController(server, text_viewer, algo,
				h, guiConf.frameSize.width, guiConf.frameSize.height);
		controller.startGame();
	}

	public static void guiNoAI(Game server, GUIConfiguration guiConf) {

		GUIController controller = new GUIController(server, guiConf);
		controller.startGame();
	}
}
