package gui.viewers;

import gui.GUIConfiguration;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class TowerViewer{
	
	public static void draw(Graphics g,Rectangle rect , Rectangle towerRange , boolean isAiming , Rectangle aimedMob,int neighbors,GUIConfiguration conf) {
		int centrelizedX = rect.x + (rect.width / 2) - (conf.itemSize / 2);
		int centrelizedY = rect.y + (rect.height / 2) - (conf.itemSize / 2);
		Rectangle drawn = new Rectangle(centrelizedX,centrelizedY,conf.itemSize,conf.itemSize);
		GUIConfiguration.drawRectImage(g, drawn, GUIConfiguration.towers[neighbors]); // draws the tower
		g.setFont(new Font("Ariel", Font.BOLD, conf.fontSize));
		g.drawString(""+neighbors, rect.x, rect.y+rect.height-conf.fontSize);
		g.drawRect(towerRange.x, towerRange.y, towerRange.width,
				towerRange.height); // draws the range
		
		if (isAiming) {
			GUIConfiguration.drawLinesBetweenRectangles(g, towerRange, aimedMob); //draws the shot
		}
	}

}
