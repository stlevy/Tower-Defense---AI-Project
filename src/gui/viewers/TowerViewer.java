package gui.viewers;

import gui.GUIUtils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class TowerViewer{
	private static final Image towerImg = GUIUtils.tile_shop[0];
	
	public static void draw(Graphics g,Rectangle rect , Rectangle towerRange , boolean isAiming , Rectangle aimedMob) {
		int centrelizedX = rect.x + (rect.width / 2) - (GUIUtils.itemSize / 2);
		int centrelizedY = rect.y + (rect.height / 2) - (GUIUtils.itemSize / 2);
		Rectangle drawn = new Rectangle(centrelizedX,centrelizedY,GUIUtils.itemSize,GUIUtils.itemSize);
		GUIUtils.drawRectImage(g, drawn, towerImg); // draws the tower

		g.drawRect(towerRange.x, towerRange.y, towerRange.width,
				towerRange.height); // draws the range
		
		if (isAiming) {
			GUIUtils.drawLinesBetweenRectangles(g, towerRange, aimedMob); //draws the shot
		}
	}

}
