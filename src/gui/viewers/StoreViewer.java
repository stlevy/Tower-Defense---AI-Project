package gui.viewers;

import gui.GUIUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class StoreViewer extends Rectangle {
	protected final int buttonSize = GUIUtils.itemSize;
	protected final int slotsSeperator = GUIUtils.storeSlotsSeperator;
	private final int iconSize = GUIUtils.iconSize;

	private final Rectangle[] buttons ;
	private final Rectangle buttonHealth;
	private final Rectangle buttonCoins;
	private final int[] prices;
	
	public StoreViewer(Point _topLeftPoint, Point healthPoint,
			Point coinagePoint , int slots, int[] prices) {
		buttons = new Rectangle[slots];
		setBounds(_topLeftPoint.x, _topLeftPoint.y, buttonSize * buttons.length
				+ slotsSeperator * (buttons.length - 1), buttonSize);

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new Rectangle(x + (buttonSize + slotsSeperator) * i,
					y, buttonSize, buttonSize);
		}

		buttonHealth = new Rectangle(healthPoint.x, healthPoint.y, iconSize,
				iconSize);
		buttonCoins = new Rectangle(coinagePoint.x, coinagePoint.y, iconSize,
				iconSize);
		this.prices = prices;
		
	}

	public void draw(Graphics g, int money, int health) {
		g.setFont(new Font("Ariel", Font.BOLD, GUIUtils.fontSize));

		for (int i = 0; i < buttons.length -1 ; i++)
			drawButton(g, i);

		drawHealth(g, health);
		drawCoinage(g, money);
	}

	/**
	 * @param index
	 *            the indx in the button array
	 */
	private void drawButton(Graphics g, int index) {
		GUIUtils.drawRectImage(g, buttons[index], GUIUtils.tile_shop[index]);

		if (prices[index] > 0)
			g.drawString(Integer.toString(prices[index]),
					buttons[index].x, buttons[index].y - GUIUtils.fontSize / 2);
	}

	private void drawCoinage(Graphics g, int money) {
		g.setColor(new Color(192, 192, 67));
		GUIUtils.drawRectImage(g, buttonCoins, GUIUtils.tile_coin);

		g.drawString("" + money, buttonCoins.x + buttonCoins.width
				+ slotsSeperator, buttonCoins.y + buttonCoins.height);
	}

	private void drawHealth(Graphics g, int health) {
		g.setColor(Color.red);
		GUIUtils.drawRectImage(g, buttonHealth, GUIUtils.tile_life);

		g.drawString("" + health, buttonHealth.x + buttonHealth.width
				+ slotsSeperator, buttonHealth.y + buttonHealth.height);
	}

	/**
	 * @return returns the index in the storeViewer if the mouse is in the
	 *         storeViewer , -1 otherwise
	 * @param mse
	 *            the item index in the storeViewer
	 */
	public int mseInStore(Point mse) {

		if (mse.x < x || mse.x > x + width || mse.y < y || mse.y > y + height)
			return -1;

		return (mse.x - x) / buttonSize;
	}
}
