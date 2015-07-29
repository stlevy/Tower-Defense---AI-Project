package logic;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import utils.GameUtils;

/**
 * a block in the room.
 * @author Tomer
 *
 */
public class Block extends Rectangle {

	private GameUtils.TILE_TYPE type;

	private Tower tower = null;
	private final Set<Path> pathsOnBlock;

	public Block(final int _x, final int _y, final int _width, final int _height, final int intType) {
		setBounds(_x, _y, _width, _height);
		setTypeFromInt(intType);
		pathsOnBlock = new HashSet<>();
	}

	public void addPath(final Path p) {
		pathsOnBlock.add(p);
	}

	private void setTypeFromInt(final int _type) {
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

	public void setTower(final Tower tower) {
		this.tower = tower;
	}

	public boolean hasTower() {
		return tower != null;
	}

	public Tower getTower(){
		return tower;
	}

	public boolean isOnPath(final Path p) {
		return pathsOnBlock.contains(p);
	}
}