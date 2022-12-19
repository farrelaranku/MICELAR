package id.inisiasimimpi.micelar;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.Random;


import java.util.*;

public class Game extends MusicPlayer{
	
	private Random rand = new Random();
	
	public static List<GameObject> objects;
	boolean spawnNew = false;
	public static boolean animate = false, animating = false;
	public static boolean gameOver = false;
	
	Sprite objectSprite;
	
	Configuration config = new Configuration();
	public static int bestScore = 0, score = 0;
	
	public Game() {
		config.loadConfiguration("config.xml");
		MusicPlayer.set(0);
		MusicPlayer.play();
		MusicPlayer.loop();
		init();
	}
	
	public void init() {
		objectSprite = new Sprite (100, 29, 0);
		objects = new ArrayList<GameObject>();
		objects.add(new GameObject(Stack.WIDTH / 2 - objectSprite.width / 2, Stack.HEIGHT - 30, objectSprite, false));
		objects.add(new GameObject(Stack.WIDTH / 2 - objectSprite.width / 2, Stack.HEIGHT - 30 * 2, objectSprite, false));
		objects.add(new GameObject(Stack.WIDTH / 2 - objectSprite.width / 2, Stack.HEIGHT - 30 * 3, objectSprite, false));
		spawnNew = true;
		gameOver = false;
		animate = false;
		animating = false;
		
		if(score > bestScore) {
			bestScore = score;
			config.saveConfiguration("best", bestScore);
		}
		score = -1;
		


	}
	
	public void update() {
		if(gameOver ) {
			if(Keyboard.key(KeyEvent.VK_R) || Keyboard.key(KeyEvent.VK_ENTER)) {
				init();
			}
			return;
		}
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
		}
		animate = false;
		
		if (spawnNew) {
			if(!animating) {
				score++;
				objectSprite = new Sprite(objects.get(objects.size() - 1).width, 29, 0);
				objects.add(new GameObject(rand.nextInt(Stack.WIDTH) - objectSprite.width, Stack.HEIGHT - 30 * 4, objectSprite, true));
				spawnNew = false;
			}else {
				boolean endOfAnim = true;
				for(int i = 0; i < objects.size(); i++) {
					if(objects.get(i).animate) {
						endOfAnim = false;
					}
				}
				if(endOfAnim) animating = false;
			}
		}else {
			if(!objects.get(objects.size() - 1).moving) {
				spawnNew = true;
				animate = true;
				animating = true;
			}
		}
	}
	
	public void render() {
		
		Renderer.renderBackground();
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).render();
		}

		
		for(int i = 0; i < Stack.pixels.length; i++) {
			Stack.pixels[i]= Renderer.pixels[i]; 
		}
		
	}
	
	public void renderText(Graphics2D g) {
		g.setFont(new Font("Arial",0, 20));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		String s = "Score: " + Game.score;
		int w = g.getFontMetrics().stringWidth(s) / 2;
		g.drawString(s, Stack.WIDTH * Stack.scale/2 - w, 130);
		String best = "High Score: " + Game.bestScore;
		int bw = g.getFontMetrics().stringWidth(best) / 2;
		g.drawString(best, Stack.WIDTH * Stack.scale/2 - bw, 150);
		g.setFont(new Font("Times New Roman",0,75));
		String m= "MICELAR";
		g.drawString(m, 90, 90);
	}

	}
