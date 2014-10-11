package gui.viewers;

import gui.GUIConfiguration;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class StoreViewer extends Rectangle {
	protected final int buttonSize;
	protected final int slotsSeperator;
	private final int iconSize;

	private final Rectangle[] buttons ;
	private final Rectangle buttonHealth;
	private final Rectangle buttonCoins;
	private final int[] prices;
	private final GUIConfiguration conf;
	
	public StoreViewer(Point _topLeftPoint, Point healthPoint,
			Point coinagePoint , int slots, int[] prices,GUIConfiguration conf) {
		this.conf = conf;
		buttonSize = conf.itemSize;
		slotsSeperator = conf.storeSlotsSeperator;
		iconSize = conf.iconSize;
		
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
		g.setFont(new Font("Ariel", Font.BOLD, conf.fontSize));

		for (int i = 0; i < buttons.length ; i++)
			drawButton(g, i);
		drawHealth(g, health);
		drawCoinage(g, money);
	}


	/**
	 * @param index
	 *            the indx in the button array
	 */
	private void drawButton(Graphics g, int index) {
		GUIConfiguration.drawRectImage(g, buttons[index], GUIConfiguration.tile_shop[index]);

		if (prices[index] > 0)
			g.drawString(Integer.toString(prices[index]),
					buttons[index].x, buttons[index].y - conf.fontSize / 2);
	}

	private void drawCoinage(Graphics g, int money) {
		g.setColor(new Color(192, 192, 67));
		GUIConfiguration.drawRectImage(g, buttonCoins, GUIConfiguration.tile_coin);

		g.drawString("" + money, buttonCoins.x + buttonCoins.width
				+ slotsSeperator, buttonCoins.y + buttonCoins.height);
	}

	private void drawHealth(Graphics g, int health) {
		g.setColor(Color.red);
		GUIConfiguration.drawRectImage(g, buttonHealth, GUIConfiguration.tile_life);

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
