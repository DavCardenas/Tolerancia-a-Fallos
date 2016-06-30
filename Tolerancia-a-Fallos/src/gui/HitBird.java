package gui;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import logic.Bird;


public class HitBird extends JFrame implements MouseMotionListener{

	private static final long serialVersionUID = 7976785545428545342L;
	
	public static final String TITLE = "Hits The Birt";
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	
	private Background background;
	private Cloud cloud1;
	private Cloud cloud2;
	private Cloud cloud3;
	private Cloud cloud4;
	private BirdGUI birdGUI;
	private Bat bat;
	
	private Cursor cursor;
	private Toolkit toolkit;
	
	private Bird bird;
	
	public HitBird() {
		setTitle(TITLE);
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		
		//toolkit = Toolkit.getDefaultToolkit();
		
		//cursor = toolkit.createCustomCursor(new ImageIcon(getClass().getResource("/img/bate.png")).getImage(), new Point(2, 2), "bate");
		
		bird = new Bird();
		
		background = new Background();
		background.setBounds(0, 0, WIDTH, HEIGHT);
		
		cloud1 = new Cloud("/img/clouds.gif");
		cloud1.setBounds(100, 0, 630, 130);
		
		cloud2 = new Cloud("/img/clouds.gif");
		cloud2.setBounds(320, 140, 630, 130);
		
		cloud3 = new Cloud("/img/clouds.gif");
		cloud3.setBounds(-190, 140, 630, 130);
		
		cloud4 = new Cloud("/img/clouds.gif");
		cloud4.setBounds(100, 270, 630, 130);
		
		birdGUI = new BirdGUI("/img/birdL.gif");
		birdGUI.setBounds(bird.getPosition().x, bird.getPosition().y, bird.getWidth(), bird.getHeight());
		
		bat = new Bat("/img/bate.png");
		bat.setBounds((WIDTH / 2) - (Bat.TAM / 2), (HEIGHT / 2) - (Bat.TAM / 2), Bat.TAM, Bat.TAM);

		add(bat);
		add(cloud1);
		add(cloud2);
		add(cloud3);
		add(birdGUI);
		add(cloud4);
		add(background);
		
		addMouseMotionListener(this);
		this.setCursor(cursor);
	}
	
	public void moveBirdRight() {
		bird.moveRight();
		birdGUI.setBounds(bird.getPosition().x, bird.getPosition().y, bird.getWidth(), bird.getHeight());
	}
	
	public void moveBirdLeft() {
		bird.moveLeft();
		birdGUI.setBounds(bird.getPosition().x, bird.getPosition().y, bird.getWidth(), bird.getHeight());
	}
	
	public static void main(String[] args) {
		HitBird bird = new HitBird();
		bird.setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		moveBirdLeft();
		bat.setLocation(e.getX() - (Bat.TAM / 10), e.getY() - (Bat.TAM ));
	}
}
