package id.inisiasimimpi.micelar;

public class Sprite {
	public int width, height;
	public int[] pixels;
	
	
	public Sprite(int width, int height, int color) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		for(int i=0;i<pixels.length;i++) {
			pixels[i] = color;
		}
	}
}
