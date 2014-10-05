package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;


public class Frame extends JFrame{


	public Frame(GameViewer viewer) {
		setTitle(GUIUtils.frameTitle);
		setSize(GUIUtils.frameSize);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init(viewer);
	}

	public void init(GameViewer gameViewer){
		setLayout(new GridLayout(1,1,0,0));
			
		add(gameViewer);
		
		setVisible(true);
	}	
}
