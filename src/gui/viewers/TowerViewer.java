package gui.viewers;

import gui.GUIUtils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class TowerViewer{
	
	public static void draw(Graphics g,Rectangle rect , Rectangle towerRange , boolean isAiming , Rectangle aimedMob,int neighbors) {
		int centrelizedX = rect.x + (rect.width / 2) - (GUIUtils.itemSize / 2);
		int centrelizedY = rect.y + (rect.height / 2) - (GUIUtils.itemSize / 2);
		Rectangle drawn = new Rectangle(centrelizedX,centrelizedY,GUIUtils.itemSize,GUIUtils.itemSize);
		GUIUtils.drawRectImage(g, drawn, GUIUtils.towers[neighbors]); // draws the tower
		g.setFont(new Font("Ariel", Font.BOLD, GUIUtils.fontSize));
		g.drawString(""+neighbors, rect.x, rect.y+rect.height-GUIUtils.fontSize);
		g.drawRect(towerRange.x, towerRange.y, towerRange.width,
				towerRange.height); // draws the range
		
		if (isAiming) {
			GUIUtils.drawLinesBetweenRectangles(g, towerRange, aimedMob); //draws the shot
		}
	}

}
