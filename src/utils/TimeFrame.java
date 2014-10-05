package utils;

/**
 * a utility class that count time frames, each frameSize ticks, the tick function returns true 
 * @author Tomer
 *
 */
public class TimeFrame {
	private final int frameSize;
	private int step = 0;
	
	public TimeFrame(int _frameSize) {
		frameSize = _frameSize;
	}
	
	/**
	 * @return returns true if frameSize ticks passed since last frame (if there was any)
	 */
	public boolean tick(){
		if (step == frameSize ) {
			step = 0;
			return true;
		}
		step++;
		return false;
	}
	
	public void reset(){
		step = 0;
	}

	public boolean peakLast() {
		return step==0;
	}
}
