package ai.utils;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class ResultsWriter {
	private static final String SEPERATOR = ",";
	private final File resultsFile = new File("Results/results.csv");
	private BufferedWriter bw;
	private final DecimalFormat df = new DecimalFormat("#.00");

	public ResultsWriter(String description, long runningTime, int beamWidth) throws IOException {
		this(description, runningTime, beamWidth, -1.0);
	}

	public ResultsWriter(String description, long runningTime, int beamWidth,
			double alpha) throws IOException {
		if (!(resultsFile.exists()))
			resultsFile.createNewFile();
		FileWriter fw = new FileWriter(resultsFile.getAbsoluteFile(), true);
		bw = new BufferedWriter(fw);

		writeCSVvalue(description);
		writeCSVvalue("" + runningTime);
		writeCSVvalue("" + beamWidth);
		writeCSVvalue("" + df.format(alpha));
	}

	public void initializeLevel(int level) {
		System.out.println("Starting level " + level);
	}

	public void buyTower(Point roomCoord, int item) {

	}

	public void sumLevel(int level, int money, int health, int killedMobs,
			int towers,int numberofMobs) {
		writeCSVvalue(df.format(killedMobs / (float)numberofMobs));
	}

	private void writeCSVvalue(String s) {
		try {
			bw.write(s + SEPERATOR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			bw.write("\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
