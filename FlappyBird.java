package game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends Applet implements MouseListener, KeyListener, Runnable 
{
	Image buffer;
	Graphics gg;
	
	int y;
	int jumpAmt;
	int score;
	int xadd;
	int highscore = 0;
	
	boolean jump = false;
	boolean clicked = false;
	
	String message = "Click to start";
	
	ArrayList<int[]> pipes = new ArrayList<int[]>();
	Random gen = new Random();
	
	public void init()
	{
		addKeyListener(this);
		addMouseListener(this);
		setSize(1200, 600);
		
		y = 300;
		jumpAmt = 0;
		score = 0;
		xadd = 800;
		
		buffer = createImage(getWidth(), getHeight());
		gg = buffer.getGraphics();
		
		for (int i = 0; i < 6; i ++) {
			xadd += 200;
			pipes.add(new int[] {xadd, getHeight() - gen.nextInt(350) - 100});
		}
		
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void paint(Graphics g)
	{
		gg.setColor(getBackground());
		gg.fillRect(0, 0, getWidth(), getHeight());
		gg.setColor(Color.black);
		
		if (!clicked)
			gg.drawString(message, 588, 50);
		
		gg.drawString(Integer.toString(score), 588, 50);
		
		gg.setColor(Color.YELLOW);
		gg.fillOval(588, y, 25, 25);
		
		for (int[] p : pipes)
		{
			gg.setColor(Color.GREEN);
			gg.fillRect(p[0], p[1], 25, getHeight() - p[1] - 1);
			gg.fillRect(p[0], 0, 25, p[1] - 150);
		}
		
		kill();
		
		g.drawImage(buffer, 0, 0, this);
	}
	
	public void kill()
	{
		if (y > getHeight() - 25)
		{
			clicked = false;
			message = "You died. Your score was " + score;
			
			y = 300;
			jumpAmt = 0;
			score = 0;
			xadd = 800;
			pipes.clear();
			
			for (int i = 0; i < 6; i ++) {
				xadd += 200;
				pipes.add(new int[] {xadd, getHeight() - gen.nextInt(350) - 100});
			}
		}
		
		for (int[] p : pipes)
		{
			if (613 > p[0] && 563 < p[0] && (y + 25 > p[1] || y < p[1] - 150))
			{
				clicked = false;
				message = "You died. Your score was " + score;
				
				y = 300;
				jumpAmt = 0;
				score = 0;
				xadd = 800;
				pipes.clear();
				
				for (int i = 0; i < 6; i ++) {
					xadd += 200;
					pipes.add(new int[] {xadd, getHeight() - gen.nextInt(350) - 100});
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE)
			jump = true;
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

	public void start()
	{
		Thread th = new Thread(this);
		th.start();
	}
	
	public void run() 
	{
		while (clicked)
		{
			if (!jump)
				y += 10;
			
			if (jump && jumpAmt < 100) {
				y -= 25;
				jumpAmt += 25;
				if (y < 25)
					y = 0;
			}
			
			if (jumpAmt == 100) {
				jump = false;
				jumpAmt = 0;
			}
			
			for (int p = 0; p < pipes.size(); p ++) {
				pipes.get(p)[0] -= 10;
				if (pipes.get(p)[0] < 100)
				{
					pipes.remove(p);
					xadd = pipes.get(pipes.size() - 1)[0] + 200;
					pipes.add(new int[] {xadd, getHeight() - gen.nextInt(350) - 100});
					score ++;
				}
			}
			
			try {Thread.sleep(50);}
			catch (InterruptedException ex) {}
			
			repaint();
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		clicked = true;
		start();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
