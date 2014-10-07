package gui.viewers;

import gui.GUIUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MobViewer {

	/**
	 * draws the mob in its current location, with health amount.
	 */
	public static void draw(Graphics g,Rectangle mob, double mobHealth,boolean isInGame,double maxHealth) {
		if (!isInGame)
			return;
		GUIUtils.drawRectImage(g, mob, GUIUtils.tile_mob);
		Rectangle healthBar = new Rectangle(mob.x,mob.y-GUIUtils.healthBarHeight,mob.width,GUIUtils.healthBarHeight);
		int width = (int) (mob.width*mobHealth/maxHealth);
		g.drawRect(healthBar.x, healthBar.y, width, healthBar.height);
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillRect(healthBar.x, healthBar.y, width, healthBar.height);
		g.setColor(c);
	}

}
