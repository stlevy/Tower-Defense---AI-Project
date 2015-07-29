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
	private final BufferedWriter bw;
	private final DecimalFormat df = new DecimalFormat("#.00");
	private final int levels;
	private int sumKilled = 0;
	private int sumSpawned = 0;

	public ResultsWriter(final int levels, final long runningTime, final int beamWidth)
			throws IOException {
		this(levels, runningTime, beamWidth, -1.0, -1);
	}

	public ResultsWriter(final int levels, final long runningTime, final int beamWidth,
			final double alpha,
			final int experiment) throws IOException {
		this.levels = levels;
		File resFolder = new File("Results");
		if (!resFolder.exists())
			if (!resFolder.mkdir())
				throw new IllegalStateException("cannot create folder Results");

		resultsFile = new File("Results/results3.csv");
		boolean headerNeeded = ! (resultsFile.exists()) ;
		FileWriter fw = new FileWriter(resultsFile.getAbsoluteFile(), true);
		bw = new BufferedWriter(fw);
		if (headerNeeded)
			initializeResultsFile(levels);

		writeCSVvalue("" + runningTime);
		writeCSVvalue("" + beamWidth);
		writeCSVvalue("" + df.format(alpha));
		writeCSVvalue("" + experiment);
		writeCSVvalue(" ");
	}

	private void initializeResultsFile(final int levels) {
		writeCSVvalue("Running Time");
		writeCSVvalue("Beam Width");
		writeCSVvalue("alpha");
		writeCSVvalue("experiment");
		writeCSVvalue(" ");
		for (int i = 1; i <= levels; i++)
			writeCSVvalue("L-" + i);
		writeCSVvalue("average score");
		writeLineFeed();
	}

	public void initializeLevel(final int level) {
		System.out.println("Starting level " + level);
	}

	public void buyTower(final Point roomCoord, final int item) {

	}

	public void sumLevel(final int level, final int money, final int health, final int killedMobs,
			final int towers, final int numberofMobs) {
		sumKilled += killedMobs;
		sumSpawned += numberofMobs;
		writeCSVvalue(df.format(killedMobs / (float) numberofMobs));
		if (level == levels)
			System.out.println("level "+level+" score:"+df.format(sumKilled / (float) sumSpawned));
	}

	private void writeCSVvalue(final String s) {
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
