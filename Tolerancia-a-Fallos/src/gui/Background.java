package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class Background extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6100761641875589655L;
	
	private Color colorF;
	private Color colorS;
	
	public static final String COLORF = "#00AAF8"; //00AAF8
	public static final String COLORS = "#FFFFFF"; // FFFFFF
	
	public Background() {
		
		colorF = Color.decode(COLORF);
		colorS = Color.decode(COLORS);
		
		setPreferredSize(new Dimension(HitBird.WIDTH, HitBird.HEIGHT));
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g.create(); 
		 
        Rectangle clip = g2.getClipBounds(); 

        float x=getWidth(); 

        float y=getHeight(); 

        
        g2.setPaint(new GradientPaint(0, 0,   colorF, 
        		0, getHeight(), colorS)); 

        g2.fillRect(clip.x, clip.y, clip.width, clip.height);
	}
}
