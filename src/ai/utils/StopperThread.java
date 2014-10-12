package ai.utils;

import ai.algorithms.AnytimeAlgorithm;

/**
 * this utility class is responsable to stop the algorithm after it's time is
 * elapsed.
 * 
 * @author Tomer
 * 
 */
public class StopperThread extends Thread {
	AnytimeAlgorithm<?> algorithm;
	long runningTime;

	public StopperThread(long runningTime) {
		this.runningTime = runningTime;
	}

	public StopperThread(long runningTime, AnytimeAlgorithm<?> algo) {
		this.runningTime = runningTime;
		algorithm = algo;
	}

	public void setAlgorithm(AnytimeAlgorithm<?> algo) {
		algorithm = algo;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(runningTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		algorithm.stop();
	}
}
