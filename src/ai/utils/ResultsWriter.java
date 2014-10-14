package ai.utils;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * this class is responsible for the output of the game, 
 * it writes the results file : Results/results.csv.
 * @author Tomer
 *
 */
public class ResultsWriter {
	private static final String SEPERATOR = ",";
	private final File resultsFile;
	private BufferedWriter bw;
	private final DecimalFormat df = new DecimalFormat("#.00");
	private final int levels;
	private int sumKilled = 0;
	private int sumSpawned = 0;

	public ResultsWriter(int levels, long runningTime, int beamWidth)
			throws IOException {
		this(levels, runningTime, beamWidth, -1.0);
	}

	public ResultsWriter(int levels, long runningTime, int beamWidth,
			double alpha) throws IOException {
		this.levels = levels;
		File resFolder = new File("Results");
		if (!resFolder.exists())
			if (!resFolder.mkdir())
				throw new IllegalStateException("cannot create folder Results");

		resultsFile = new File("Results/results.csv");
		boolean headerNeeded = ! (resultsFile.exists()) ;
		FileWriter fw = new FileWriter(resultsFile.getAbsoluteFile(), true);
		bw = new BufferedWriter(fw);
		if (headerNeeded)
			initializeResultsFile(levels);

		writeCSVvalue("" + runningTime);
		writeCSVvalue("" + beamWidth);
		writeCSVvalue("" + df.format(alpha));
	}

	private void initializeResultsFile(int levels) {
		writeCSVvalue("Running Time");
		writeCSVvalue("Beam Width");
		writeCSVvalue("alpha");
		for (int i = 1; i <= levels; i++)
			writeCSVvalue("L-" + i);
		writeCSVvalue("average score");
		writeLineFeed();
	}

	public void initializeLevel(int level) {
		System.out.println("Starting level " + level);
	}

	public void buyTower(Point roomCoord, int item) {

	}

	public void sumLevel(int level, int money, int health, int killedMobs,
			int towers, int numberofMobs) {
		sumKilled += killedMobs;
		sumSpawned += numberofMobs;
		writeCSVvalue(df.format(killedMobs / (float) numberofMobs));
		if (level == levels)
			System.out.println("level "+level+" score:"+df.format(sumKilled / (float) sumSpawned));
	}

	private void writeCSVvalue(String s) {
		try {
			bw.write(s + SEPERATOR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeLineFeed() {
		try {
			bw.write('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		writeLineFeed();
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
