package logic;

import gui.BirdGUI;
import gui.HitBird;

import java.awt.Point;

public class Bird {

	private Point position;
	private String image;
	private int width;
	private int height;
	private boolean sense;
	private int move;
	
	
	public Bird() {
		width = 128;
		height = 128;
		move = 3;
		position = new Point((HitBird.WIDTH / 2) - (BirdGUI.TAM / 2), (HitBird.HEIGHT / 2) - (BirdGUI.TAM / 2));
		image = "/img/birdR.";
	}
	
	public void moveRight() {
		position.setLocation(position.x + move, position.y);
	}
	
	public void moveLeft() {
		position.setLocation(position.x - move, position.y);
	}


	public Point getPosition() {
		return position;
	}


	public void setPosition(Point position) {
		this.position = position;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public boolean isSense() {
		return sense;
	}


	public void setSense(boolean sense) {
		this.sense = sense;
	}
	
	
}
