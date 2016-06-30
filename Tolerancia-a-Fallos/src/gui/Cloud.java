package gui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Cloud extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036287892495652927L;
	
	private ImageIcon image;
	
	public Cloud(String img) {
		setSize(128, 128);
		image = new ImageIcon(getClass().getResource(img));
		setIcon(image);
		image.setImageObserver(this);
	}
	
}
