package gui;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bat extends JLabel implements Runnable{
	
	private ImageIcon image;
	private Thread Tmove;
	private boolean move;
	
	public static final int TAM = 229;
	
	public Bat(String img) {
		setSize(TAM, TAM);
		image = new ImageIcon(getClass().getResource(img));
		setIcon(image);
		image.setImageObserver(this);
		Tmove = new Thread(this);
		move = true;
	}

	public void calculateColition(Point bird, int width, int height) {
		
	}
	
	@Override
	public void run() {
		while (move) {
			
		}
	}
}
