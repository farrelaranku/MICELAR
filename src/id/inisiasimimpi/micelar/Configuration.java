package id.inisiasimimpi.micelar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {

	Properties properties = new Properties();
	
	public void saveConfiguration(String key, int value) {
		try {
			String path = "config.xml";
			File file = new File(path);
			boolean exist = file.exists();
			if(!exist) file.createNewFile();
			OutputStream write = new FileOutputStream(path);
			properties.setProperty(key, Integer.toString(value));
			properties.storeToXML(write, "Options");
		} catch (IOException e) {
			 System.out.println("Error: " + e.getMessage());
		
		}
	} 
	
	public void loadConfiguration (String path) {
		try {
			InputStream read = new FileInputStream(path);
			properties.loadFromXML(read);
			String best = properties.getProperty("best");
			setBest(Integer.parseInt(best));
			read.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			saveConfiguration("best", 0);
			loadConfiguration(path);
		} catch (Exception e) {
			// TODO: handle exception
			 System.out.println("Error: " + e.getMessage());
		}
	}
	
	private void setBest(int best) {
		Game.bestScore = best;
	}
}
