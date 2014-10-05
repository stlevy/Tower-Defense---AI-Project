package gui.viewers;

import gui.GUIUtils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;

public class MobViewer {
    private static DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * draws the mob in its current location, with health amount.
	 */
	public static void draw(Graphics g,Rectangle mob, double mobHealth,boolean isInGame) {
		if (!isInGame)
			return;
		GUIUtils.drawRectImage(g, mob, GUIUtils.tile_mob);
		g.setFont(new Font("Ariel", Font.BOLD, GUIUtils.fontSize));
		
		g.drawString(df.format(mobHealth), mob.x, mob.y);
	}

}
