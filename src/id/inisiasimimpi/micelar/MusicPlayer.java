package id.inisiasimimpi.micelar;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicPlayer{
	
	static Clip clip;
	static URL sound[] = new URL[2];
	
	private static boolean muted = false;
	
	public MusicPlayer() {
		
		sound[0] = getClass().getResource("/mainsound.wav");
		sound[1] = getClass().getResource("/space.wav");
	}
	
	public static void set (int i) {
		
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(sound[i]);
			clip = AudioSystem.getClip();
			clip.open(audio);
			refreshVolume();
		} catch (Exception e) {
			System.out.println("Error");
		}
		
	}
	
	public static void play() {
		
		clip.start();
	}
	
	public static void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void stop() {
		
		clip.stop();
	}
	
	private static void refreshVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		if (!muted) gainControl.getValue();
		else gainControl.setValue(-80.0f);
	}
	
	
	public void changeMuted() {
		muted = !(muted);
		refreshVolume();
	}
}