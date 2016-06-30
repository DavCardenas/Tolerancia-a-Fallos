package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BirdGUI extends JLabel{
	
	private ImageIcon image;
	
	public static final int TAM = 128;
	
	public BirdGUI(String img) {
		setSize(TAM, TAM);
		image = new ImageIcon(getClass().getResource(img));
		setIcon(image);
		image.setImageObserver(this);
	}
	
	public void moveRight() {
		setLocation(getLocation().x + 3, getLocation().y);
		updateUI();
	}
	
	public void moveLeft() {
		setLocation(getLocation().x - 3, getLocation().y);
		updateUI();
	}

}
