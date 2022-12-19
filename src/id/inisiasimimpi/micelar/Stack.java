package id.inisiasimimpi.micelar;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.*;

import javax.swing.JFrame;

public class Stack extends Canvas implements Runnable {
	
	
	public static final int WIDTH = 250, HEIGHT = 300;
	public static float scale = 2.0f;
	
	public Game game;
	public Keyboard key;
	public JFrame frame;
	public Thread thread;
	public boolean running = false;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	
	public Stack() {
		setPreferredSize(new Dimension((int) (WIDTH *scale), (int) (HEIGHT *scale)));
		frame = new JFrame();
		game = new Game();
		key = new Keyboard();
		addKeyListener(key); 
	}
	
	
public void start() {
		running = true;
		thread = new Thread(this, "loopThread");
		thread.start();
	}

public void stop() {
	try {
		thread.join();
	}catch (InterruptedException e) {
		e.printStackTrace();
	}
}


public void run() {
	long lastTimeInNanoSeconds = System.nanoTime();
	long timer = System.currentTimeMillis();
	final double nanoSecondsPerUpdate = 1000000000.0/60.0;
	double updatesToPerform = 0;
	int frames = 0;
	int updates = 0;
	requestFocus();
	while(running) {
		long currentTimeInNanoSeconds = System.nanoTime();
		updatesToPerform += (currentTimeInNanoSeconds - lastTimeInNanoSeconds) / nanoSecondsPerUpdate;
		lastTimeInNanoSeconds = currentTimeInNanoSeconds;
		while (updatesToPerform >= 1) {
			update();
			updates++;
			updatesToPerform--;
		}
		render();
		frames++;
		if(System.currentTimeMillis() - timer > 1000) {
			timer+=1000;
			frame.setTitle("Stack " + updates + "updates, " + frames + " frames");
			updates = 0;
			frames=0;
		}
	}
	stop();
}
	
public void update() {
	game.update();
	key.update();
	}

public void render() {
	BufferStrategy bs = getBufferStrategy();
	if(bs == null) {
		createBufferStrategy(3);
		return ;
	}
	
	game.render();
	Graphics2D g = (Graphics2D) bs.getDrawGraphics();
	g.drawImage(image, 0, 0, (int) (WIDTH*scale), (int) (HEIGHT*scale), null);
	game.renderText(g);
	g.dispose();
	bs.show();
}
	
	public static void main(String[] args) {
		Stack s = new Stack();
		s.frame.setResizable(true);
		s.frame.setTitle("MICELAR");
		s.frame.add(s);
		s.frame.pack();
		s.frame.setVisible(true);
		s.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.frame.setLocationRelativeTo(null);
		s.frame.setAlwaysOnTop(true);
		s.start();
		}
	
	
	
	
	
	
}
