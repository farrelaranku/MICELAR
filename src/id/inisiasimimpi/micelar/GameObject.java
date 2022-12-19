package id.inisiasimimpi.micelar;

import java.awt.event.*;
import java.util.Random;

public class GameObject {

		public float x, y, newY;
		public int width, height;
		public Sprite sprite;
		public boolean moving, animate = false;
		public double speed = 1.0, maxSpeed = 3.0;
		
		public Random rand = new Random();
		
		public GameObject(int x, int y, Sprite sprite, boolean moving) {
			this.x = x;
			this.y = y;
			this.newY = y;
			this.width = sprite.width;
			this.height = sprite.height;
			this.sprite = sprite;
			this.moving = moving;
			this.speed = rand.nextDouble() * (maxSpeed - 1.0) + 1.0;
			if(rand.nextInt(2) == 0) this.speed *= -1;
		}
		
		public void setWidth(int newWidth) {
			this.sprite = new Sprite(newWidth, height, 0);
			this.width = newWidth;
		}
		
		public void update() {
			if(moving) {
				x += speed;
				if(x + width >= Stack.WIDTH) {
					x = Stack.WIDTH -width;
					speed *= -1;
				}
				
				if(x < 0) {
					x = 0;
					speed *= -1;
				}
				
				if(Keyboard.keyDown(KeyEvent.VK_SPACE)) {
					this.moving = false;
					int previousWidth = Game.objects.get(Game.objects.size() - 2).width;
					int newWidth =(int) ((previousWidth - Math.abs(Stack.WIDTH / 2 - (x + width / 2))));
					if(newWidth < 0) {
						Game.gameOver = true;
						return;
					}
					setWidth(newWidth);
					MusicPlayer.set(1);
					MusicPlayer.play();
				
				}
				
				
			}
			if(Game.animate) {
				this.animate = true;
				newY += height + 1;
				x = (int) x;
			}
			
			if(animate) {
				boolean hasAnimated = false;
				if(y < newY) {
					y++;
					hasAnimated = true;
				}
				if(x + width / 2 < Stack.WIDTH / 2) {
					x++;
					hasAnimated = true;
				}
				else if(x + width / 2 > Stack.WIDTH / 2) {
					x--;
					hasAnimated = true;
				}
				if(!hasAnimated) animate = false;
			}
		}
		
		public void render() {
			Renderer.renderSprite(sprite, (int) x, (int) y);
		}
}
