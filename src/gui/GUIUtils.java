package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class GUIUtils {
	
	// Frame
	public static final String frameTitle = "Tower Defence";
	public static final Dimension frameSize = new Dimension(650,650);
	
	
	// Images
	//TODO : all these Icons in a unified size, and in an enum.
	public final static Image tile_ground = new ImageIcon(
			"resources/tile_ground.png").getImage();

	public final static Image tile_path = new ImageIcon(
			"resources/tile_path.png").getImage();

	public final static Image tile_end = new ImageIcon(
			"resources/tile_end.png").getImage();
	
	public final static Image tile_life = new ImageIcon("resources/tile_life.png").getImage();

	public final static Image tile_coin = new ImageIcon("resources/tile_coin.png").getImage();
	
	//TODO: should be tile_mobs , an array of images
	public final static Image tile_mob = new ImageIcon("resources/mob_1.png").getImage();

	public final static Image[] tile_shop = {
		new ImageIcon("resources/tower1.png").getImage(),
		new ImageIcon("resources/trash.png").getImage()
	};

	
	//Store viewer constants
	
	
	public static final int itemSize = 40;

	public static final int storeSlotsSeperator = 5;

	public static final int iconSize = 20;
	
	public static final int fontSize = 20;
	
	public static void drawRectImage(Graphics g, Rectangle rect, Image img) {
		g.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
	}

	public static void drawLinesBetweenRectangles(Graphics g, Rectangle a,
			Rectangle b) {
		g.drawLine((int) a.getCenterX(), (int) a.getCenterY(),
				(int) b.getCenterX(), (int) b.getCenterY());
	}
	
	
}
