package logic;
import java.awt.Rectangle;

import utils.GameUtils;

/**
 * a block in the room.
 * @author Tomer
 *
 */
public class Block extends Rectangle {

	private GameUtils.TILE_TYPE type;
	
	private Tower tower = null;


	public Block(int _x, int _y, int _width, int _height, int intType) {
		setBounds(_x, _y, _width, _height);
		setTypeFromInt(intType);
	}

	private void setTypeFromInt(int _type) {
		switch (_type) {
		case 0:
			type = GameUtils.TILE_TYPE.GROUND;
			break;
		case 1:
			type = GameUtils.TILE_TYPE.PATH;
			break;
		case 2:
			type = GameUtils.TILE_TYPE.END;
			break;
		default:
			throw new IllegalArgumentException("Unknown type , cannot parse");
		}
	}

	public GameUtils.TILE_TYPE getType() {
		return type;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public boolean hasTower() {
		return tower != null;
	}

	public Tower getTower(){
		return tower;
	}
}