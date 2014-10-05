package gui.viewers;

import gui.GUIUtils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import utils.GameUtils.TILE_TYPE;

public class RoomViewer {

	/**
	 * @return returns the xCoord,yCoord in the room if the mouse is in the
	 *         room, null otherwise
	 * @param mse
	 *            mouse location as a 2d-point
	 */
	public static Point mseInRoom(Point mse , Rectangle room,int blockSize) {

		if (mse.x < room.x || mse.x > room.x + room.width || mse.y < room.y || mse.y > room.y + room.height)
			return null;
		Point coords = new Point();
		coords.x = (mse.x - room.x) / blockSize;
		coords.y = (mse.y - room.y) / blockSize;
		return coords;
	}

	public static void drawBlock(Graphics g, Rectangle block ,TILE_TYPE type) {
		Image img = getImage(type);
		GUIUtils.drawRectImage(g, block, img);
	}

	private static Image getImage(TILE_TYPE _type) {
		switch (_type) {
		case GROUND:
			return GUIUtils.tile_ground;
		case PATH:
			return GUIUtils.tile_path;
		case END:
			return GUIUtils.tile_end;
		case EMPTY:
		default:
			throw new IllegalArgumentException("Unknown type , cannot parse");
		}
	}
}
