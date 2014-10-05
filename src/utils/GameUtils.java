package utils;


public class GameUtils {
	public static enum Direction{
		right , left, upward, downward, no_direction;
		
		public Direction opposite(){
			switch (this) {
			case downward:
				return upward;
			case left:
				return right;
			case right:
				return left;
			case upward:
				return downward;
			case no_direction:
			default:
				return no_direction;
			}
		}
	}

	public static enum TILE_TYPE {
		GROUND, PATH , END , EMPTY
	}
	
	public static enum ITEM_TYPE {
		EMPTY,TOWER1
	}
}
