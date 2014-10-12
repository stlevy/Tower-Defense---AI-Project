package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

/**
 * a configuration class initialized from the ini file specified in the constant
 * {@code}CONFIGURATION_FILE{@code}
 * contains configurable constants regarding the GUI in the game
 * @author Tomer
 * 
 */
public class GUIConfiguration {
	private final String CONFIGURATION_FILE = "configurations/gui_conf.ini";

	public GUIConfiguration() throws InvalidFileFormatException, IOException {
		Ini confFile = new Ini(new File(CONFIGURATION_FILE));

		Section frameSect = confFile.get("Frame");
		frameTitle = frameSect.get("frameTitle");
		int frameWidth = Integer.parseInt(frameSect.get("width"));
		int frameHeigt = Integer.parseInt(frameSect.get("height"));
		frameSize = new Dimension(frameWidth, frameHeigt);

		Section storeSect = confFile.get("Store");
		itemSize = Integer.parseInt(storeSect.get("itemSize"));
		storeSlotsSeperator = Integer.parseInt(storeSect.get("slotsSeperator"));
		iconSize = Integer.parseInt(storeSect.get("iconSize"));
		fontSize = Integer.parseInt(storeSect.get("fontSize"));

		Section mobSect = confFile.get("Mob");
		healthBarHeight = Integer.parseInt(mobSect.get("healthBarHeight"));
	}

	// Frame
	public final String frameTitle;
	public final Dimension frameSize;

	// Images
	// TODO : all these Icons in a unified size, and in an enum.
	public final static Image tile_ground = new ImageIcon(
			"resources/tile_ground.png").getImage();

	public final static Image tile_path = new ImageIcon(
			"resources/tile_path.png").getImage();

	public final static Image tile_end = new ImageIcon("resources/tile_end.png")
			.getImage();

	public final static Image tile_life = new ImageIcon(
			"resources/tile_life.png").getImage();

	public final static Image tile_coin = new ImageIcon(
			"resources/tile_coin.png").getImage();

	// TODO: should be tile_mobs , an array of images
	public final static Image tile_mob = new ImageIcon("resources/mob_1.png")
			.getImage();

	public final static Image[] tile_shop = {
			new ImageIcon("resources/tower0.png").getImage(),
			new ImageIcon("resources/trash.png").getImage() };

	public final static Image[] towers = {
			new ImageIcon("resources/tower0.png").getImage(),
			new ImageIcon("resources/tower1.png").getImage(),
			new ImageIcon("resources/tower2.png").getImage(),
			new ImageIcon("resources/tower3.png").getImage(),
			new ImageIcon("resources/tower4.png").getImage(),
			new ImageIcon("resources/tower5.png").getImage(),
			new ImageIcon("resources/tower6.png").getImage(),
			new ImageIcon("resources/tower7.png").getImage(),
			new ImageIcon("resources/tower8.png").getImage() };

	// Store viewer constants
	public final int itemSize;

	public final int storeSlotsSeperator;

	public final int iconSize;

	public final int fontSize;

	public static void drawRectImage(Graphics g, Rectangle rect, Image img) {
		g.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
	}

	public static void drawLinesBetweenRectangles(Graphics g, Rectangle a,
			Rectangle b) {
		g.drawLine((int) a.getCenterX(), (int) a.getCenterY(),
				(int) b.getCenterX(), (int) b.getCenterY());
	}

	// Mob Viewer constants

	public final int healthBarHeight;

}
