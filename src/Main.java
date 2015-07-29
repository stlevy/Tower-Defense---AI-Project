import java.io.IOException;

import ai.algorithms.AnytimeAlgorithm;
import ai.algorithms.BeamSearchPrioriryQueue;
import ai.heuristics.Heuristic;
import ai.heuristics.LevelsHeuristic;
import ai.heuristics.NewPathHeuristic;
import ai.heuristics.NewProductHeuristics;
import ai.heuristics.PathHeuristic;
import ai.heuristics.ProductHeuristic;
import ai.heuristics.WeightedHeuristics;
import ai.nodes.BoardState;
import ai.utils.ResultsWriter;
import controllers.CombinedController;
import controllers.ai.AIController;
import controllers.gui.GUIController;
import gui.GUIConfiguration;
import logic.Game;
import logic.GameModel;
import logic.factories.MobFactory;
import logic.factories.TowerFactory;
import utils.GameConfiguration;

public class Main {
	public static void main(final String[] args) throws IOException {
		// in ai+gui mode the game runs twice as fast, in ai mode, 4 times faster 
		int speedFactor = getSpeedFactor(args[0]);

		GUIConfiguration guiConf = new GUIConfiguration();

		Game server = new GameModel(new MobFactory(), new TowerFactory(),
			new GameConfiguration(speedFactor));
		//HUMAN mode
		if (args[0].equals("human")) {
			guiNoAI(server, guiConf);
			return;
		}
		// Experiment constants
		int runningTime = Integer.parseInt(args[1]);
		int beamSize = Integer.parseInt(args[2]);
		double alpha = Double.parseDouble(args[3]);
		int experiment = Integer.parseInt(args[4]);

		// AI algorithm
		BeamSearchPrioriryQueue<BoardState> algo = new BeamSearchPrioriryQueue<>(
				beamSize, runningTime);

		Heuristic h = getHeuristic(server, alpha, experiment);

		// Results Writer
		ResultsWriter text_viewer = new ResultsWriter(
			server.getConfiguration().numberOfLevels, runningTime,
			beamSize,
			alpha, experiment);

		//AI + GUI
		if (args[0].equals("gui")) {
			guiAI(server, text_viewer, algo, h, guiConf);
			return;
		}
		// just AI
		if (args[0].equals("ai")) {
			guilessAI(server, text_viewer, algo, h, guiConf);
			return;
		}
		throw new IllegalArgumentException("args[0] was not a valid argument");
	}

	private static Heuristic getHeuristic(final Game server, final double alpha, final int experiment) {
		// Heuristic function
		switch (experiment) {
		case 1:
			return new WeightedHeuristics(server, new PathHeuristic(), new LevelsHeuristic(), alpha);
		case 2:
			return new WeightedHeuristics(server, new NewPathHeuristic(), new LevelsHeuristic(), alpha);
		case 3:
			return new ProductHeuristic(server, new NewProductHeuristics(), new LevelsHeuristic());
		default:
			break;
		}
		return null;
	}

	private static int getSpeedFactor(final String mode) {
		if (mode.equals("human"))
			return 1;
		if (mode.equals("gui"))
			return 4;
		if (mode.equals("ai"))
			return 16;
		return -1;
	}

	private static void guiAI(final Game server, final ResultsWriter text_viewer,
			final AnytimeAlgorithm<BoardState> algo, final Heuristic h,
			final GUIConfiguration guiConf) {
		CombinedController controller = new CombinedController(server,
			text_viewer, algo, h, guiConf);
		controller.run();
	}

	public static void guilessAI(final Game server, final ResultsWriter text_viewer,
			final AnytimeAlgorithm<BoardState> algo, final Heuristic h,
			final GUIConfiguration guiConf) throws IOException {

		AIController controller = new AIController(server, text_viewer, algo,
			h, guiConf.frameSize.width, guiConf.frameSize.height);
		controller.startGame();
	}

	public static void guiNoAI(final Game server, final GUIConfiguration guiConf) {

		GUIController controller = new GUIController(server, guiConf);
		controller.startGame();
	}
}
