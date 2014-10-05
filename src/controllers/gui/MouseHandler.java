package controllers.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseMotionListener, MouseListener {
	GUIController controller;
	public MouseHandler(GUIController _controller) {
		controller = _controller;
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			controller.click(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateMSE(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMSE(e);
	}

	private void updateMSE(MouseEvent e) {
		controller.setMSE(new Point(e.getX(), e.getY()));
	}

}
