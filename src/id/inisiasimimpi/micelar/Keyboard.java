package id.inisiasimimpi.micelar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 

public class Keyboard implements KeyListener{

	public static boolean[] keys = new boolean[120];
	public static boolean[] LastKeys = new boolean [120];
	
	public void update() {
		for(int i=0;i<keys.length;i++) {
			LastKeys[i] = keys[i];
		}
	}
	
	
	public static boolean key(int key) {
		return  keys[key];
	}
	
	public static boolean keyDown(int key) {
		return  keys[key] && !LastKeys[key];
	}
	
	public static boolean keyUp(int key) {
		return !keys[key] && LastKeys[key];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
	}
	
	
}
